package com.example.yogeshd.sampleapplication.pojo;

/**
 * Created by yogeshd on 9/11/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPojo {

    @SerializedName("login_from")
    @Expose
    private String loginFrom;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("session")
    @Expose
    private String session;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("token")
    @Expose
    private String token;

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}