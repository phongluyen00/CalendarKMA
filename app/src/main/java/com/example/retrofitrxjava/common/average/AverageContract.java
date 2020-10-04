package com.example.retrofitrxjava.common.average;

import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.example.retrofitrxjava.retrofit.MyAPI;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public interface AverageContract {

    interface View {
        void retrieveScoreSuccess(ScoreMediumResponse scoreMediumResponse, ArrayList<ScoreMediumResponse.Datum> responses);

        void retrieveScoreFailed();

        void retrieveDetailScoreSuccess(DetailScoreModel detailScoreModel);
    }

    interface Presenter {
        void retrieveScore(CompositeDisposable compositeDisposable, MyAPI myAPI, String token);

        void retrieveDetailScore(MyAPI myAPI, String token);
    }
}
