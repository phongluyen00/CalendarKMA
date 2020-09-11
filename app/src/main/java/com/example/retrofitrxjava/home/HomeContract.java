package com.example.retrofitrxjava.home;

import com.example.retrofitrxjava.model.Advertisement;

import java.util.List;

public interface HomeContract {
    interface View {
        void retrieveDataSuccess(List<Advertisement> data);
    }

    interface Presenter {
        void retrieveDataHome(String userName);
    }
}
