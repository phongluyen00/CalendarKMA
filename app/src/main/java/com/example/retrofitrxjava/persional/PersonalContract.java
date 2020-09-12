package com.example.retrofitrxjava.persional;

import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

import java.util.ArrayList;

public interface PersonalContract {
    interface Model {
    }

    interface View {
        void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> data);
    }

    interface Presenter {
        void retrieveScore(String id);
    }
}
