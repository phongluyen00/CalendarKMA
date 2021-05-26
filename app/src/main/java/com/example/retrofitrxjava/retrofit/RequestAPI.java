package com.example.retrofitrxjava.retrofit;

import com.example.retrofitrxjava.admin.Account;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseBDTB;
import com.example.retrofitrxjava.main.model.ResponseSchedule;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestAPI {
    @POST("api")
    Observable<DataResponse> loginWebSite(@Query("username") String username,
                                          @Query("password") String passWord,
                                          @Query("permission") int permission);

    @POST("api/addTeacher")
    Observable<DataResponse> addTeacher(@Query("username") String username,
                                        @Query("password") String password,
                                        @Query("nameTeacher") String name,
                                        @Query("faculty") String faculty);

    @POST("api/updateTeacher")
    Observable<DataResponse> updateTeacher(@Query("username") String username,
                                           @Query("password") String password,
                                           @Query("nameTeacher") String name,
                                           @Query("faculty") String faculty);

    @POST("api/deleteTeacher")
    Observable<DataResponse> removeTeacher(@Query("username") String username);

    @GET("api/retrieveSchedule")
    Observable<ResponseSchedule> retrieveSchedule(@Query("username") String username,
                                                  @Query("password") String passWord);

    @GET("api/retrieveBDCT")
    Observable<ResponseBDCT> retrieveBDCT(@Query("username") String username,
                                          @Query("password") String passWord,
                                          @Query("status") String status);

    @GET("api/retrieveBDTB")
    Observable<ResponseBDTB> retrieveBDTB(@Query("username") String username,
                                          @Query("password") String passWord,
                                          @Query("status") String status);

    @GET("api/retrieveAccount")
    Observable<List<Account>> retrieveAccount();

    @GET("api/scheduleTeacher")
    Observable<ResponseSchedule> scheduleTeacher(@Query("teacherName") String teacherName);
}
