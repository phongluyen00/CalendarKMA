package com.example.retrofitrxjava.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit ourInstance;

    public static String BASE_MOCK = "https://samples.openweathermap.org/data/2.5/";
    public static String BASE_MOCK_API = "http://27.71.231.75:82/api/Values/";

    public static Retrofit getInstance() {
        if (ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl(BASE_MOCK_API)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return ourInstance;
    }

    private RetrofitClient() {
    }
}