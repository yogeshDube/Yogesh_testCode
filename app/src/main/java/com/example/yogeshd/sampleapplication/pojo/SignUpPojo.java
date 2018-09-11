package com.example.yogeshd.sampleapplication.pojo;

/**
 * Created by yogeshd on 9/11/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpPojo {

    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("rep_password")
    @Expose
    private String repPassword;

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

    public String getRepPassword() {
        return repPassword;
    }

    public void setRepPassword(String repPassword) {
        this.repPassword = repPassword;
    }


}