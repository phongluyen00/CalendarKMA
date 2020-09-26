package com.example.retrofitrxjava.retrofit;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.CertificateResponse;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.example.retrofitrxjava.model.HandleLearning;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.model.PaymentModel;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.persional.model.ScheduleModelResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyAPI {

    @GET("getScoreMedium")
    Observable<ScoreMediumResponse> getScoreMedium(@Query("mssv") String token);

    @GET("getXuLyHocVu")
    Observable<HandleLearning> getHandleLearning(@Query("mssv") String token);

    @GET("getDanhSachKhoanPhaiDong")
    Observable<PaymentModel> getPayment(@Query("mssv") String token);

    @GET("getChungChi")
    Observable<PaymentModel> getCertificate(@Query("mssv") String token);

    @GET("getLichHoc")
    Observable<ScheduleModelResponse> getSchedule(@Query("mssv") String token);

    @GET("getDanhSachKhoanPhaiDong")
    Observable<CertificateResponse> getListData(@Query("mssv") String token);

    @GET("getBangDiemChiTiet")
    Observable<DetailScoreModel> getDetailScore(@Query("mssv") String token);

    @GET("loginStatus")
    Observable<LoginResponse> loginStatus(@Query("userName") String token,
                                          @Query("passWord") String passWord);

    @GET("synchronizationMarkAvg")
    Observable<ModelResponse> synchronization(@Query("userName") String token,
                                              @Query("passWord") String passWord);
}
