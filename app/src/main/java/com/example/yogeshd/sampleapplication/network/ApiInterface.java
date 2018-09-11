package com.example.yogeshd.sampleapplication.network;

import com.example.yogeshd.sampleapplication.pojo.GetOtpResponse;
import com.example.yogeshd.sampleapplication.pojo.LoginPojo;
import com.example.yogeshd.sampleapplication.pojo.LoginResponse;
import com.example.yogeshd.sampleapplication.pojo.OtpLoginResponse;
import com.example.yogeshd.sampleapplication.pojo.SignUpPojo;
import com.example.yogeshd.sampleapplication.pojo.SignUpResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface
{
    @Headers({
            NetworkConstants.HEADERS_CONTENT_TYPE,
            // NetworkConstants.HEADERS_CONNECTION,
            //NetworkConstants.HEADERS_KEEP_ALIVE,
            //NetworkConstants.HEADERS_CACHE
    })

    @POST(NetworkConstants.LOGIN_URL)
    Call<LoginResponse> login(@Body LoginPojo loginpojo);

    @POST(NetworkConstants.LOGIN_URL)
    Call<OtpLoginResponse> loginOtp(@Body LoginPojo loginpojo);

    @GET(NetworkConstants.GET_OTP)
    Call<GetOtpResponse> getOTP(@Query(NetworkConstants.REQUEST_PARA_mobile) String mobile);

    @POST(NetworkConstants.signUp_URL)
    Call<SignUpResponse> singUp(@Body SignUpPojo signUpPojo);


}