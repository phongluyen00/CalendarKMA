package com.example.retrofitrxjava.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                        data.setScoreMediumResponse(scoreMediumResponse);
                        PrefUtils.saveData((Context) view, data);
                        view.retrieveScoreSuccess(scoreMediumResponse);
                    }
                });
    }

}
