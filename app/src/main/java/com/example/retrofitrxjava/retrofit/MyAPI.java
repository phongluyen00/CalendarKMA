package com.example.retrofitrxjava.retrofit;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.model.ResponseAPI;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAPI {

    @GET("weather")
    Observable<ResponseAPI> getWeather(@Query("id") String id, @Query("appid") String appId);

    @GET("getScoreMedium")
    Observable<ScoreMediumResponse> getScoreMedium(@Query("mssv") String id);

    @GET("loginStatus")
    Observable<LoginResponse> loginStatus(@Query("userName") String userName,
                                          @Query("passWord") String passWord);

    @GET("synchronizationMarkAvg")
    Observable<ModelResponse> synchronization(@Query("userName") String userName,
                                              @Query("passWord") String passWord);

}
