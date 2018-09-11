package com.example.yogeshd.sampleapplication.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class creates singleton instance of retrofit client
 * Created by Anuja Kothekar on 14-03-2018.
 */

public class RetrofitClient {
    private static Retrofit rfClient = null;

    public static Retrofit getClient()
    {
        if (rfClient == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient()
                    .newBuilder()
                    .connectTimeout(NetworkConstants.HTTP_HIT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(NetworkConstants.HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(NetworkConstants.HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(interceptor) //Remove interceptor when releasing app
                    .build();

            rfClient = new Retrofit.Builder()
                    .baseUrl(NetworkConstants.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rfClient;
    }

}
