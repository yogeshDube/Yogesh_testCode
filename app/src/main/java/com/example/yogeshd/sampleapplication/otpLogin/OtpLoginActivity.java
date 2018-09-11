package com.example.yogeshd.sampleapplication.otpLogin;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.yogeshd.sampleapplication.CustomProgressDialog;
import com.example.yogeshd.sampleapplication.MainActivity;
import com.example.yogeshd.sampleapplication.R;
import com.example.yogeshd.sampleapplication.account.AccountActivity;
import com.example.yogeshd.sampleapplication.network.ApiInterface;
import com.example.yogeshd.sampleapplication.network.RetrofitClient;
import com.example.yogeshd.sampleapplication.pojo.GetOtpResponse;
import com.example.yogeshd.sampleapplication.pojo.LoginPojo;
import com.example.yogeshd.sampleapplication.pojo.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class OtpLoginActivity extends AppCompatActivity {

    private EditText input_mobNumber;
    private AppCompatButton btn_getOtp;
    private ApiInterface apiService;
    private String account;
    private Spinner sp_contryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_login);
        apiService = RetrofitClient.getClient().create(ApiInterface.class);
        input_mobNumber =   findViewById(R.id.input_mobNumber);
        account =  getIntent().getStringExtra("account");


        btn_getOtp =  findViewById(R.id.btn_getOtp);
        btn_getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendOtp();


            }
        });
        sp_contryCode =   findViewById(R.id.sp_contryCode);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.countryCodes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_contryCode.setAdapter(adapter);



    }

    private void sendOtp() {

        if(input_mobNumber.getText().toString().isEmpty())
        {
            input_mobNumber.setError("Please Enter mobile number");
            return;
        }
        String contryCode  = sp_contryCode.getSelectedItem().toString();
        String numberOnly= contryCode.replaceAll("[^0-9]", "");

       String finalMob =  numberOnly + ""+input_mobNumber.getText().toString();

        proceedToAPICall(finalMob);

    }

    private void proceedToAPICall(String finalMob)
    {
      /*  if (isConnectedToInternet(MainActivity.this))
        {
            showAlert("No internet connection");
            return;
        }*/
        CustomProgressDialog.show(OtpLoginActivity.this,"","Sending Otp ...Please wait");
        String mobNo = input_mobNumber.getText().toString();

        Call<GetOtpResponse> logincall = apiService.getOTP(finalMob);
        logincall.enqueue(new Callback<GetOtpResponse>()
        {
            @Override
            public void onResponse(Call<GetOtpResponse> call, retrofit2.Response<GetOtpResponse> response) {
                try {
                    CustomProgressDialog.dismiss();
                    if (response.isSuccessful()) {

                        GetOtpResponse otpResponse = response.body();

                        if (otpResponse != null)
                        {
                            if (otpResponse.getStatus() != null && otpResponse.getStatus().equals("success"))
                            {
                                otpSuccess(otpResponse);

                            } else
                            {
                                showAlert(otpResponse.getMsg());
                            }
                        }
                    }
                    else {
                        showAlert("Failure");
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetOtpResponse> call, Throwable t) {
                showAlert("Failure");
                t.printStackTrace();
                CustomProgressDialog.dismiss();

            }
        });
    }

    private void otpSuccess(final GetOtpResponse otpResponse) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("success")
                .setMessage("OTP send Successfully")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        Intent i = new Intent(OtpLoginActivity.this, VerifyActivity.class);
                        i.putExtra("session",otpResponse.getSession());
                        i.putExtra("account",account);
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
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
