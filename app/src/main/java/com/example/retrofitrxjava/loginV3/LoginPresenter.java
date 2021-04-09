package com.example.retrofitrxjava.loginV3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private MyAPI myAPI;

    public LoginPresenter(LoginContract.View view) {
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void verifyAccount(String entryDataLogin) {
        myAPI.loginStatus(entryDataLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    String decryptText = new AESHelper().decrypt(loginResponse.getData(), AppUtils.privateKey);
                    LoginResponse.Data data = (LoginResponse.Data) AppUtils.GenericInstance(decryptText,LoginResponse.Data.class);
                    if (loginResponse.getErrorCode() == 1) {
                        view.pushView(data);
                    } else {
                        view.verifyAccountFailed(((Context) view).getString(R.string.login_failed));
                    }
                }, error -> {
                    view.verifyAccountFailed(((Context) view).getString(R.string.error_default));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateSchedule(String password, MyAPI myAPI) {
        myAPI.synSchedule( password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("AAAAA", "SUCCESS ---- dongBoLicHoc"),
                        throwable -> Log.d("AAAAA", "FAILED ---- dongBoLicHoc"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateScore(String password, MyAPI myAPI) {
        myAPI.synScoreDetail(password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("AAAAA", "SUCCESS ---- dongBoBangDiemChiTiet"),
                        throwable -> Log.d("AAAAA", "FAILED ---- dongBoBangDiemChiTiet"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateMoney( String password, MyAPI myAPI) {
        myAPI.synMoney( password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("AAAAA", "SUCCESS ---- DongBolephihocphi"),
                        throwable -> Log.d("AAAAA", "FAILED ---- DongBolephihocphi"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void syncCertificate(String password, MyAPI myAPI) {
        myAPI.syncCertificate( password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("AAAAA", "SUCCESS ---- dongBoChungChi"),
                        throwable -> Log.d("AAAAA", "FAILED ---- dongBoChungChi"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void syncHandlingService(String entryData, MyAPI myAPI) {
        myAPI.syncHandlingService(entryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("AAAAA", "SUCCESS ---- dongBoXuLyHocVu"),
                        throwable -> Log.d("AAAAA", "FAILED ---- dongBoXuLyHocVu"));
    }

    @SuppressLint("CheckResult")
    public void syncDTB(String entryData, MyAPI myAPI) {
        myAPI.synchronization(entryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> Log.d("AAAAA", "SUCCESS ---- DB DIEM TB"),
                        throwable -> Log.d("AAAAA", "FAILED ---- DB DIEM TB"));
    }

}
