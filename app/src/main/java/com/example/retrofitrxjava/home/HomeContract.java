package com.example.retrofitrxjava.home;

import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.model.Article;

import java.util.List;

public interface HomeContract {
    interface View {
        void retrieveDataSuccess(List<Advertisement> data);
        void retrieveDataEnglishSuccess(List<Article> articles);
        void retrieveDataEnglishFFailed(String message);
    }

    interface Presenter {
        void retrieveDataHome();
        void retrieveDataEnglish();
    }
}
