package com.example.retrofitrxjava.main;

import android.util.Log;

import com.example.retrofitrxjava.model.Advertisement;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private List<ScoreMediumResponse.Datum> responses = new ArrayList<>();

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void retrieveScore(CompositeDisposable compositeDisposable, MyAPI myAPI, String user, String password) {
        myAPI.getScoreMedium("CT010215").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ScoreMediumResponse>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(ScoreMediumResponse scoreMediumResponse) {
                                   ArrayList<ScoreMediumResponse.Datum> datumArrayList = new ArrayList<>();
                                   for (int i = 1; i < scoreMediumResponse.getData().size(); i++) {
                                       datumArrayList.add(scoreMediumResponse.getData().get(i));
                                   }
                                   responses.clear();
                                   responses.addAll(datumArrayList);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.d("AAAA", "rêr");
                               }

                               @Override
                               public void onComplete() {
                                   view.retrieveScoreSuccess((ArrayList<ScoreMediumResponse.Datum>) responses);
                               }
                           });

    }

    @Override
    public void retrieveDataHome(String userName) {
        ArrayList<Advertisement> advertisements = new ArrayList<>();
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/nco.jpg"));
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/299.png"));
        advertisements.add(new Advertisement("http://home.actvn.edu.vn/Upload/svda/ts19-8-web.jpg"));
        view.retrieveDataHomeSuccess(advertisements);
    }
}
