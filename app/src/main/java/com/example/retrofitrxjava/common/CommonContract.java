package com.example.retrofitrxjava.common;

import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

public interface CommonContract {

    interface View {
        void retrieveSuccess(ScheduleModelResponse response);
        void updateSuccess(String message);
    }

    interface Presenter {
        void retrieveSchedule(String token, MyAPI myAPI);
        void updateSchedule(String token, String password, MyAPI myAPI);
        void updateScore(String token, String password, MyAPI myAPI);
        void updateMoney(String token, String password, MyAPI myAPI);
        void syncCertificate(String token, String password, MyAPI myAPI);
        void syncHandlingService(String token, String password, MyAPI myAPI);
    }
}
