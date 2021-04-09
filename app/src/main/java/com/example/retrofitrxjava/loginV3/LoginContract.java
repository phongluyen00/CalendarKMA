package com.example.retrofitrxjava.loginV3;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

public interface LoginContract {

    interface View {
        void pushView(LoginResponse.Data phone);
        void verifyAccountFailed(String message);
    }

    interface Presenter {
        void verifyAccount(String userAccount);

        // syn data ngam
        void updateSchedule(String entryData, MyAPI myAPI);
        void updateScore(String entryData, MyAPI myAPI);
        void updateMoney( String entryData, MyAPI myAPI);
        void syncCertificate( String entryData, MyAPI myAPI);
        void syncHandlingService(String entryData, MyAPI myAPI);
    }
}
