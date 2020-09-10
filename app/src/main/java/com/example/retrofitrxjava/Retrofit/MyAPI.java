package com.example.retrofitrxjava.Retrofit;

import com.example.retrofitrxjava.model.ResponseAPI;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyAPI {

    @GET("weather")
    Observable<ResponseAPI> getWeather(@Query("id") String id, @Query("appid") String appId);

}
