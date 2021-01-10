package com.example.retrofitrxjava.common;

import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

public interface CommonContract {

    interface View {
        void retrieveSuccess(ScheduleModelResponse response);
        void updateSuccess(String message);
    }

    interface Presenter {
        void retrieveSchedule(String entryData, MyAPI myAPI);
        void updateSchedule(String entryData, MyAPI myAPI);
        void updateScore(String entryData, MyAPI myAPI);
        void updateMoney( String entryData, MyAPI myAPI);
        void syncCertificate( String entryData, MyAPI myAPI);
        void syncHandlingService(String entryData, MyAPI myAPI);
    }
}
