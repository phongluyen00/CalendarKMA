package com.example.retrofitrxjava.main;

import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public interface MainContract {

    interface View {
        void retrieveSuccess();
    }

    interface Presenter {
        void retrieveScore(MyAPI myAPI);
    }
}
