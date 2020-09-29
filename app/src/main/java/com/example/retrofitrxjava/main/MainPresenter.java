package com.example.retrofitrxjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.FAILED;
import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveScore(MyAPI myAPI) {
        myAPI.getScoreMedium(PrefUtils.loadData((Context) view).getToken()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(scoreMediumResponse -> {
                    if (scoreMediumResponse.getErrorCode().equals(SUCCESS)) {
                        LoginResponse.Data data = PrefUtils.loadData((Context) view);
                        data.setMediumScore(scoreMediumResponse.getData().
                                get(scoreMediumResponse.getData().size() - 1).getH4N1());
                        PrefUtils.saveData((Context) view, data);
                        view.retrieveScoreSuccess(scoreMediumResponse);
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveSchedule(String token, MyAPI myAPI) {
        myAPI.getSchedule(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getErrorCode().equals(FAILED))
                .subscribe(response -> view.synSchedule());
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveDetailScore(MyAPI myAPI, String token) {
        myAPI.getDetailScore(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(detailScoreModel -> detailScoreModel.getErrorCode().equals(SUCCESS))
                .subscribe(detailScoreModel -> view.synScoreDetail());
    }

    @SuppressLint("CheckResult")
    @Override
    public void synchronization(MyAPI myAPI, String token, String password) {
        myAPI.synchronization(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.equals(FAILED))
                .subscribe(response -> view.synchronization());
    }

    @SuppressLint("CheckResult")
    @Override
    public void synScoreDetail(MyAPI myAPI, String token, String password) {

        myAPI.synScoreDetail(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                                Log.d("luyenphong", response.getErrorCode() + "synScoreDetail"),
                        throwable -> Log.d("luyenphong", throwable.getMessage()));
    }

    @Override
    public void synSchedule(MyAPI myAPI, String token, String password) {
        myAPI.synSchedule(token, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->
                                Log.d("luyenphong", response.getErrorCode() + "synSchedule"),
                        throwable -> Log.d("luyenphong", throwable.getMessage()));
    }
}
