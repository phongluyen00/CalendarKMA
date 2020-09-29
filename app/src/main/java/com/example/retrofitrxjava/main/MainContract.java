package com.example.retrofitrxjava.main;

import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

public interface MainContract {

    interface View {
        void retrieveScoreSuccess(ScoreMediumResponse scoreMediumResponse);

        void synScoreDetail();

        void synSchedule();

        void synchronization();
    }

    interface Presenter {
        void retrieveScore(MyAPI myAPI);

        void retrieveSchedule(String token, MyAPI myAPI);

        void retrieveDetailScore(MyAPI myAPI, String token);

        void synchronization(MyAPI myAPI, String token, String password);

        void synScoreDetail(MyAPI myAPI, String token, String password);

        void synSchedule(MyAPI myAPI, String token, String password);
    }
}
