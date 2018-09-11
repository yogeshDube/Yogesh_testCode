package com.example.yogeshd.sampleapplication.otpLogin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import com.example.yogeshd.sampleapplication.CustomProgressDialog;
import com.example.yogeshd.sampleapplication.MainActivity;
import com.example.yogeshd.sampleapplication.R;
import com.example.yogeshd.sampleapplication.account.AccountActivity;
import com.example.yogeshd.sampleapplication.network.ApiInterface;
import com.example.yogeshd.sampleapplication.network.RetrofitClient;
import com.example.yogeshd.sampleapplication.pojo.LoginPojo;
import com.example.yogeshd.sampleapplication.pojo.LoginResponse;
import com.example.yogeshd.sampleapplication.pojo.OtpLoginResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class VerifyActivity extends AppCompatActivity {

    private EditText otpTextView;
    private AppCompatButton btn_verify;
    private String session;
    private ApiInterface apiService;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        otpTextView = (EditText) findViewById(R.id.et_opt);
        apiService = RetrofitClient.getClient().create(ApiInterface.class);

        session =  getIntent().getStringExtra("session");
        account =  getIntent().getStringExtra("account");

        btn_verify =  findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyOtp();
            }
        });
    }
    private void verifyOtp()
    {
          if(otpTextView.getText().toString().isEmpty())
          {
              otpTextView.setError("Please enter otp");
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

        /*{
            "login_from" : "mobile",
                "account" : "8055679938",
                "password" : "",

                "otp": "3386",
                "session": "28da8ab5-b5a7-11e8-a895-0200cd936042"


        }

*/
        CustomProgressDialog.show(VerifyActivity.this,"","Verifying Otp..Please wait");
        LoginPojo loginPojo = new LoginPojo();
         loginPojo.setAccount(account);
         loginPojo.setPassword("");
        loginPojo.setLoginFrom("mobile");
        loginPojo.setSession(session);
        loginPojo.setOtp(otpTextView.getText().toString());


        Call<OtpLoginResponse> logincall = apiService.loginOtp(loginPojo);
        logincall.enqueue(new Callback<OtpLoginResponse>()
        {
            @Override
            public void onResponse(Call<OtpLoginResponse> call, retrofit2.Response<OtpLoginResponse> response) {
                try {
                    CustomProgressDialog.dismiss();
                    if (response.isSuccessful()) {

                        OtpLoginResponse loginResponse = response.body();

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
            public void onFailure(Call<OtpLoginResponse> call, Throwable t) {

                CustomProgressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }
    private void loginSuccess(OtpLoginResponse loginResponse)
    {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else
        {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("success")
                .setMessage(loginResponse.getMsg())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent i = new Intent(VerifyActivity.this, AccountActivity.class);
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
}
