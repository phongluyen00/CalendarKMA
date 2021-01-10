package com.example.retrofitrxjava.main;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.security.AESHelper;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveScore(MyAPI myAPI) {
            myAPI.getScoreMedium(PrefUtils.loadData((Context) view).getUserEntry()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        String decryptText = new AESHelper().decrypt(response.getData(), AppUtils.privateKey);
                        ScoreMediumResponse scoreMediumResponse1 = new ScoreMediumResponse();
                        scoreMediumResponse1.setData((ArrayList<ScoreMediumResponse.Datum>) AppUtils.getScoreMediumResponse(decryptText));
                        if (response.getErrorCode() == 1) {
                            LoginResponse.Data data = PrefUtils.loadData((Context) view);
                            data.setMediumScore(scoreMediumResponse1.getData().
                                    get(scoreMediumResponse1.getData().size() - 1).getH4N1());
                            data.setScoreMediumResponse(scoreMediumResponse1);
                            PrefUtils.saveData((Context) view, data);
                            view.retrieveScoreSuccess(scoreMediumResponse1);
                        }
                    });
    }
}
