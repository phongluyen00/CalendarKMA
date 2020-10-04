package com.example.retrofitrxjava.home;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.model.Article;

import java.util.List;

public interface HomeContract {
    interface View {
        void retrieveDataSuccess(List<Advertisement> data);
        void retrieveDataEnglishSuccess(List<Article> articles);
    }

    interface Presenter {
        void retrieveDataHome(String userName);
        void setData(LoginResponse.Data data);
        void retrieveDataEnglish();
    }
}
