package com.example.retrofitrxjava.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.retrofit.MyAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class CommonPresenter implements CommonContract.Presenter {

    private CommonContract.View view;

    public CommonPresenter(CommonContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveSchedule(String token, MyAPI myAPI) {
        myAPI.getSchedule(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getErrorCode().equals(SUCCESS))
                .subscribe(response -> view.retrieveSuccess(response),
                        throwable -> Toast.makeText((Context) view,
                                ((Context) view).getString(R.string.error_default),
                                Toast.LENGTH_SHORT).show());
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateSchedule(String token, String password, MyAPI myAPI) {
        myAPI.synSchedule(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateScore(String token, String password, MyAPI myAPI) {
        myAPI.synScoreDetail(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateMoney(String token, String password, MyAPI myAPI) {
        myAPI.synMoney(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @Override
    public void syncCertificate(String token, String password, MyAPI myAPI) {
        myAPI.syncCertificate(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void syncHandlingService(String token, String password, MyAPI myAPI) {
        myAPI.syncHandlingService(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    public void syncDTB(String token, String password, MyAPI myAPI) {
        myAPI.synchronization(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }
}
