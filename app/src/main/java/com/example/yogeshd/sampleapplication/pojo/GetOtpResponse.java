package com.example.yogeshd.sampleapplication.pojo;

/**
 * Created by yogeshd on 9/11/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOtpResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("session")
    @Expose
    private String session;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }



}