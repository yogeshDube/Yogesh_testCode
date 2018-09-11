package com.example.yogeshd.sampleapplication.network;

public class NetworkConstants {
    public static final long HTTP_HIT_TIMEOUT = 60;
    static final long HTTP_READ_TIMEOUT = 60;
    static final long HTTP_WRITE_TIMEOUT = 60;

    public static final String HEADERS_CONTENT_TYPE = "Content-Type: application/json";
    public static final String HEADERS_CONNECTION = "Connection: Keep-Alive";
    //public static final String HEADERS_KEEP_ALIVE = "Keep-Alive: " + Control.HTTP_HIT_TIMEOUT;
    public static final String HEADERS_KEEP_ALIVE = "Keep-Alive: ";
    public static final String HEADERS_CACHE = "Cache-Control: btnApply-cache";

    static final String BASE_URL = "http://foryouloans.com/api/";
    public static final String LOGIN_URL = "user/login";
    public static final String GET_OTP = "user/otp";
    public static final String REQUEST_PARA_mobile = "mobile";
    public static final String signUp_URL = "user/signup";
}