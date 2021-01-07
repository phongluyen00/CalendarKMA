package com.example.retrofitrxjava.loginV3;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

public interface LoginContract {

    interface View {
        void pushView(LoginResponse.Data phone);
        void verifyAccountFailed(String message);
    }

    interface Presenter {
        void verifyAccount(MyAPI myAPI, String userAccount, String password);
    }
}
