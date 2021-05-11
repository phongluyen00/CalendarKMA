package com.example.retrofitrxjava.retrofit;

import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseSchedule;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestAPI {
    @GET("api")
    Observable<DataResponse> loginWebSite(@Query("userName") String token,
                                          @Query("passWord") String passWord);

    @GET("api/retrieveSchedule")
    Observable<ResponseSchedule> retrieveSchedule(@Query("userName") String token,
                                                  @Query("passWord") String passWord);

    @GET("api/retrieveBDCT")
    Observable<ResponseBDCT> retrieveBDCT(@Query("userName") String token,
                                          @Query("passWord") String passWord);
}
