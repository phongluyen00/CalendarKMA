package com.example.retrofitrxjava.common;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.security.AESHelper;
import com.example.retrofitrxjava.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                .subscribe(response -> {
                            String decryptText = new AESHelper().decrypt(response.getData(), AppUtils.privateKey);
                            ScheduleModelResponse scheduleModelResponse = new ScheduleModelResponse();
                            Log.d("AAAAAAAAAAAAA", decryptText);
                            scheduleModelResponse.setData(AppUtils.getListDataSchedule(decryptText));
                            view.retrieveSuccess(scheduleModelResponse);
                        },
                        throwable -> Log.d("AAAAAAAAAAAAAAAAA", "CCCCCCCCCC"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateSchedule(String password, MyAPI myAPI) {
        myAPI.synSchedule( password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateScore(String password, MyAPI myAPI) {
        myAPI.synScoreDetail(password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateMoney( String password, MyAPI myAPI) {
        myAPI.synMoney( password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @Override
    public void syncCertificate(String password, MyAPI myAPI) {
        myAPI.syncCertificate( password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    @SuppressLint("CheckResult")
    @Override
    public void syncHandlingService(String entryData, MyAPI myAPI) {
        myAPI.syncHandlingService(entryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }

    public void syncDTB(String entryData, MyAPI myAPI) {
        myAPI.synchronization(entryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.updateSuccess("Cập nhật dữ liệu thành công"),
                        throwable -> view.updateSuccess("Cập nhật dữ liệu thất bại"));
    }
}
