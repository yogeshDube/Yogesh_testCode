package com.example.yogeshd.sampleapplication.signup;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
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
import com.example.yogeshd.sampleapplication.pojo.SignUpPojo;
import com.example.yogeshd.sampleapplication.pojo.SignUpResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    private EditText input_account;
    private EditText input_password;
    private EditText input_Reppassword;
    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        apiService = RetrofitClient.getClient().create(ApiInterface.class);

        AppCompatButton btn_Signup = findViewById(R.id.btn_Signup);
        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });

        input_account =  findViewById(R.id.input_account);
        input_password =  findViewById(R.id.input_password);
        input_Reppassword =  findViewById(R.id.input_Reppassword);
    }

    private void signUp() {

        if(input_account.getText().toString().isEmpty())
        {
            input_account.setError("Please enter account");
            return;
        }
        else  if(input_password.getText().toString().isEmpty())
        {
            input_password.setError("Please enter password");
            return;
        }
        else  if(input_Reppassword.getText().toString().isEmpty())
        {
            input_Reppassword.setError("Please enter repeat password");
            return;
        }
        proceedToAPICall();
    }

    private void proceedToAPICall()
    {

        CustomProgressDialog.show(SignUpActivity.this,"","Signing up..Please wait");
        SignUpPojo signUpPojo = new SignUpPojo();
        signUpPojo.setAccount(input_account.getText().toString());
        signUpPojo.setPassword(input_password.getText().toString());
        signUpPojo.setRepPassword(input_Reppassword.getText().toString());

        Call<SignUpResponse> logincall = apiService.singUp(signUpPojo);
        logincall.enqueue(new Callback<SignUpResponse>()
        {
            @Override
            public void onResponse(Call<SignUpResponse> call, retrofit2.Response<SignUpResponse> response) {
                try {
                    CustomProgressDialog.dismiss();
                    if (response.isSuccessful()) {

                        SignUpResponse signUpResponse = response.body();

                        if (signUpResponse != null)
                        {
                            if (signUpResponse.getStatus() != null && signUpResponse.getStatus().equals("success"))
                            {
                                SignUpSuccess(signUpResponse);

                            } else
                            {

                                showAlert(signUpResponse.getMsg());

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
            public void onFailure(Call<SignUpResponse> call, Throwable t) {

                CustomProgressDialog.dismiss();
                t.printStackTrace();

            }
        });
    }

    private void SignUpSuccess(SignUpResponse signUpResponse) {



        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("success")
                .setMessage(signUpResponse.getMsg())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(i);
                        finishAffinity();

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
