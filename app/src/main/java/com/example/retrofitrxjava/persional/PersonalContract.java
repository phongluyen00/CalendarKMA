package com.example.retrofitrxjava.persional;

import com.example.retrofitrxjava.persional.model.ScheduleModelResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;

public interface PersonalContract {

    interface View {
        void retrieveSuccess(ScheduleModelResponse response);
        void updateSuccess(String message);
    }

    interface Presenter {
        void retrieveSchedule(String token, MyAPI myAPI);
        void updateSchedule(String token, String password, MyAPI myAPI);
        void updateScore(String token, String password, MyAPI myAPI);
        void updateMoney(String token, String password, MyAPI myAPI);
    }
}
