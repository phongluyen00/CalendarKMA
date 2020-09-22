package com.example.retrofitrxjava.loginV3;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

public interface LoginContract {

    interface View {
        void verifyAccountSuccess(String userAccount, String password);
        void pushView();
        void synchronizationSuccess(String message);
        void synchronizationFailed(String message);
        void verifyAccountFailed(String message);
    }

    interface Presenter {
        void verifyAccount(MyAPI myAPI, String userAccount, String password);

        void synchronization(MyAPI myAPI, String userName, String password);
    }
}
