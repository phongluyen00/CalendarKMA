package com.example.retrofitrxjava.retrofit;

import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
public class RetrofitClient {

    private static Retrofit ourInstance;

    public static String BASE_MOCK = "http://192.168.44.10:8087/";
    public static String BASE_MOCK_API = "";

    // ip 192.168.43.193 MOBILE
    // ip home 192.168.44.10
    // ip cong ty 192.168.30.136
    // ip LB 192.168.1.49
    // 192.168.31.184


    public static  OkHttpClient okHttpClient(long time) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(time, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .readTimeout(time, TimeUnit.SECONDS)
                .writeTimeout(time, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public static Retrofit getInstance() {
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .client(okHttpClient(30))
                    .baseUrl("http://192.168.44.5:8087/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return ourInstance;
    }

    private RetrofitClient() {
    }

}
