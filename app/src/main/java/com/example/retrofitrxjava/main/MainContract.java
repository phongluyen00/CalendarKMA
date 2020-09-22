package com.example.retrofitrxjava.main;

import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public interface MainContract {

    interface View {
        void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> data);

        void retrieveDataHomeSuccess(ArrayList<Advertisement> data);
    }

    interface Presenter {
        void retrieveScore(CompositeDisposable compositeDisposable, MyAPI myAPI,
                           String user);

        void retrieveDataHome(String userName);
    }
}
