package com.example.retrofitrxjava.common.average;

import android.annotation.SuppressLint;

import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.security.AESHelper;
import com.example.retrofitrxjava.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class AveragePresenter implements AverageContract.Presenter {
    private List<ScoreMediumResponse.Datum> responses = new ArrayList<>();
    private AverageContract.View view;

    public AveragePresenter(AverageContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveScore(CompositeDisposable compositeDisposable, MyAPI myAPI, String token) {
        myAPI.getScoreMedium(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    String decryptText = new AESHelper().decrypt(response.getData(), AppUtils.privateKey);
                    ScoreMediumResponse scoreMediumResponse1 = new ScoreMediumResponse();
                    scoreMediumResponse1.setData((ArrayList<ScoreMediumResponse.Datum>) AppUtils.getScoreMediumResponse(decryptText));

                    if (scoreMediumResponse1.getData() != null) {
                        ArrayList<ScoreMediumResponse.Datum> datumArrayList = new ArrayList<>();
                        for (int i = 1; i < scoreMediumResponse1.getData().size(); i++) {
                            datumArrayList.add(scoreMediumResponse1.getData().get(i));
                        }
                        responses.clear();
                        responses.addAll(datumArrayList);
                        view.retrieveScoreSuccess(scoreMediumResponse1, (ArrayList<ScoreMediumResponse.Datum>) responses);
                    }
                }, throwable -> view.retrieveScoreFailed());
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveDetailScore(MyAPI myAPI, String token) {
        myAPI.getDetailScore(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(detailScoreModel -> detailScoreModel.getErrorCode().equals(SUCCESS))
                .subscribe(detailScoreModel -> {
                            if (detailScoreModel.getErrorCode().equals(SUCCESS))
                                view.retrieveDetailScoreSuccess(detailScoreModel);
                        }, throwable -> view.retrieveScoreFailed());
    }
}
