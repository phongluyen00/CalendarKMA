package com.example.retrofitrxjava.common.average;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

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
                .subscribe(scoreMediumResponse -> {
                    if (scoreMediumResponse.getData() != null) {
                        ArrayList<ScoreMediumResponse.Datum> datumArrayList = new ArrayList<>();
                        for (int i = 1; i < scoreMediumResponse.getData().size(); i++) {
                            datumArrayList.add(scoreMediumResponse.getData().get(i));
                        }
                        responses.clear();
                        responses.addAll(datumArrayList);
                    }
                }, throwable -> {
                    view.retrieveScoreFailed();
                    Toast.makeText((Context) view, R.string.error_default
                            , Toast.LENGTH_SHORT).show();
                }, () -> view.retrieveScoreSuccess((ArrayList<ScoreMediumResponse.Datum>) responses));
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
                        },
                        throwable -> view.retrieveScoreFailed());
    }
}
