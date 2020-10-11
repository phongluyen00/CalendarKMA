package com.example.retrofitrxjava.retrofit;

import com.example.retrofitrxjava.common.model.TuitionResponse;
import com.example.retrofitrxjava.common.model.Update;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.CertificateResponse;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.example.retrofitrxjava.model.HandleLearning;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.model.PaymentModel;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.common.model.ScheduleModelResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
public interface MyAPI {

    @GET("getScoreMedium")
    Observable<ScoreMediumResponse> getScoreMedium(@Query("mssv") String token);

    @GET("getXuLyHocVu")
    Observable<HandleLearning> getHandleLearning(@Query("mssv") String token);

    @GET("getDanhSachKhoanPhaiDong")
    Observable<PaymentModel> getPayment(@Query("mssv") String token);

    @GET("getChungChi")
    Observable<CertificateResponse> getCertificate(@Query("mssv") String token);

    @GET("getLichHoc")
    Observable<ScheduleModelResponse> getSchedule(@Query("mssv") String token);

    @GET("getBangDiemChiTiet")
    Observable<DetailScoreModel> getDetailScore(@Query("mssv") String token);

    @GET("getlephiHocphi")
    Observable<TuitionResponse> getlephiHocphi(@Query("mssv") String token);

    @GET("loginStatus")
    Observable<LoginResponse> loginStatus(@Query("userName") String token,
                                          @Query("passWord") String passWord);

    // update

    // đồng bộ bảng điểm TB
    @GET("synchronizationMarkAvg")
    Observable<Update> synchronization(@Query("userName") String token,
                                       @Query("passWord") String passWord);

    @GET("dongBoBangDiemChiTiet")
    Observable<ModelResponse> synScoreDetail(@Query("userName") String token,
                                             @Query("passWord") String passWord);

    @GET("dongBoLicHoc")
    Observable<ModelResponse> synSchedule(@Query("userName") String token,
                                          @Query("passWord") String passWord);

    @GET("DongBolephihocphi")
    Observable<ModelResponse> synMoney(@Query("userName") String token,
                                       @Query("passWord") String passWord);

    @GET("dongBoXuLyHocVu")
    Observable<ModelResponse> syncHandlingService(@Query("userName") String token,
                                                  @Query("passWord") String passWord);

    @GET("dongBoChungChi")
    Observable<ModelResponse> syncCertificate(@Query("userName") String token,
                                              @Query("passWord") String passWord);
}
