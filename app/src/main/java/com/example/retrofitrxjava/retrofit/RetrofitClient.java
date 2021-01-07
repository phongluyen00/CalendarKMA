package com.example.retrofitrxjava.retrofit;

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

    public static String BASE_MOCK = "https://mockapi.superoffice.vn/api/3pi69i/";
    public static String BASE_MOCK_API = "https://mockapi.superoffice.vn/api/swu86/";


    public static  OkHttpClient okHttpClient(long time) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(time, TimeUnit.SECONDS)
                .readTimeout(time, TimeUnit.SECONDS)
                .writeTimeout(time, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public static Retrofit getInstance() {
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .client(okHttpClient(30))
                    .baseUrl(BASE_MOCK_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return ourInstance;
    }

    private RetrofitClient() {
    }

}
