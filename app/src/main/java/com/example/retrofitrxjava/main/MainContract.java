package com.example.retrofitrxjava.main;

import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

public interface MainContract {

    interface View {
        void retrieveScoreSuccess(ScoreMediumResponse scoreMediumResponse);
    }

    interface Presenter {
        void retrieveScore(MyAPI myAPI);
    }
}
