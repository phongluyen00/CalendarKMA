package com.example.retrofitrxjava.retrofit;

import com.example.retrofitrxjava.common.model.TuitionResponse;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.loginV3.model.ModelLogin;
import com.example.retrofitrxjava.main.model.Notification;
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
    Observable<ModelLogin> getScoreMedium(@Query("key") String enCodeUserName);

    @GET("getXuLyHocVu")
    Observable<HandleLearning> getHandleLearning(@Query("key") String enCodeUserName);

    @GET("getDanhSachKhoanPhaiDong")
    Observable<PaymentModel> getPayment(@Query("key") String enCodeUserName);

    @GET("getChungChi")
    Observable<CertificateResponse> getCertificate(@Query("key") String enCodeUserName);

    @GET("getLichHoc")
    Observable<ModelLogin>  getSchedule(@Query("key") String enCodeUserName);

    @GET("getBangDiemChiTiet")
    Observable<DetailScoreModel> getDetailScore(@Query("key") String enCodeUserName);

    @GET("getlephiHocphi")
    Observable<TuitionResponse> getlephiHocphi(@Query("key") String enCodeUserName);

    @GET("loginStatus")
    Observable<ModelLogin> loginStatus(@Query("key") String text);

    // update

    // đồng bộ bảng điểm TB
    @GET("synchronizationMarkAvg")
    Observable<ScoreMediumResponse> synchronization(@Query("key") String entryData );

    @GET("dongBoBangDiemChiTiet")
    Observable<ModelResponse> synScoreDetail(@Query("key") String entryData);

    @GET("dongBoLicHoc")
    Observable<ModelResponse> synSchedule(@Query("key") String entryData);

    @GET("DongBolephihocphi")
    Observable<ModelResponse> synMoney(@Query("key") String entryData);

    @GET("dongBoXuLyHocVu")
    Observable<ModelResponse> syncHandlingService(@Query("key") String entryData);

    @GET("dongBoChungChi")
    Observable<ModelResponse> syncCertificate(@Query("key") String entryData);

    @GET("getThongBao")
    Observable<Notification> getThongBao(@Query("mssv") String enCodeUserName);
}
