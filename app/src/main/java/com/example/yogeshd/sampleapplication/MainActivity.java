package com.example.yogeshd.sampleapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yogeshd.sampleapplication.account.AccountActivity;
import com.example.yogeshd.sampleapplication.network.ApiInterface;
import com.example.yogeshd.sampleapplication.network.RetrofitClient;
import com.example.yogeshd.sampleapplication.otpLogin.OtpLoginActivity;
import com.example.yogeshd.sampleapplication.pojo.LoginPojo;
import com.example.yogeshd.sampleapplication.pojo.LoginResponse;
import com.example.yogeshd.sampleapplication.signup.SignUpActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private EditText input_account;
    private EditText input_password;
    private ProgressDialog pd;
    private ApiInterface apiService;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiService = RetrofitClient.getClient().create(ApiInterface.class);
       TextView  tv_link_signup = findViewById(R.id.link_signup);
       tv_link_signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
               startActivity(intent);
           }
       });

        AppCompatButton btn_otp = findViewById(R.id.btn_otp);
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(input_account.getText().toString().isEmpty())
                {
                    input_account.setError("Please Enter account");
                    return;
                }

                Intent intent = new Intent(MainActivity.this, OtpLoginActivity.class);
                intent.putExtra("account",input_account.getText().toString());
                startActivity(intent);

            }
        });
        AppCompatButton btn_Login = findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              doLogin();

            }
        });

         input_account =  findViewById(R.id.input_account);
         input_password =  findViewById(R.id.input_password);
         pd = new ProgressDialog(MainActivity.this);
    }

    private void doLogin() {

        if(input_account.getText().toString().isEmpty())
        {
            input_account.setError("Please Enter Account");
            return;
        }
        else if(input_password.getText().toString().isEmpty())
        {
            input_password.setError("Please Enter Password");
            return;
        }

        proceedToAPICall();

    }

    private void proceedToAPICall()
    {
      /*  if (isConnectedToInternet(MainActivity.this))
        {
            showAlert("No internet connection");
            return;
        }*/

        CustomProgressDialog.show(MainActivity.this,"","Logging in ...Please wait");
        LoginPojo loginPojo = new LoginPojo();
        loginPojo.setAccount(input_account.getText().toString());
        loginPojo.setLoginFrom("password");
        loginPojo.setPassword(input_password.getText().toString());


        Call<LoginResponse> logincall = apiService.login(loginPojo);
        logincall.enqueue(new Callback<LoginResponse>()
        {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                try {
                    CustomProgressDialog.dismiss();
                    if (response.isSuccessful()) {

                        LoginResponse loginResponse = response.body();

                        if (loginResponse != null)
                        {
                            if (loginResponse.getStatus() != null && loginResponse.getStatus().equals("success"))
                            {
                                loginSuccess(loginResponse);

                            } else
                            {

                                showAlert(loginResponse.getMsg());

                            }
                        }
                    }
                    else {
                       showAlert("Error");
                    }
                }
                catch (Exception e) {
                  e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
            CustomProgressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }

    private void loginSuccess(LoginResponse loginResponse) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("success")
                .setMessage("Login Success")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Intent i = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(i);


                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public  void  showAlert(String msg)
    {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Alert")
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public static boolean isConnectedToInternet(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
}