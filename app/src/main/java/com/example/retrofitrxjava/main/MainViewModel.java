package com.example.retrofitrxjava.main;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitrxjava.base.BaseViewModel;
import com.example.retrofitrxjava.base.BaseObserver;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.utils.AppUtils;

/**
 * Created by luyenphong on 13/11/2020.
 */
public class MainViewModel extends BaseViewModel {

    private MutableLiveData<ResponseSchedule> scheduleMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseBDCT> detailScoreLiveData = new MutableLiveData<>();

    public void retrieveSchedule(){
        AppUtils.HandlerRXJava(requestAPI.retrieveSchedule(username, password), new BaseObserver<ResponseSchedule>() {
            @Override
            public void onSuccess(ResponseSchedule responseSchedule) {
                scheduleMutableLiveData.postValue(responseSchedule);
            }

            @Override
            public void onFailed(String message) {
                scheduleMutableLiveData.postValue(null);
            }
        });
    }

    public void retrieveBDCT(){
        AppUtils.HandlerRXJava(requestAPI.retrieveBDCT(username, password), new BaseObserver<ResponseBDCT>() {
            @Override
            public void onSuccess(ResponseBDCT responseBDCT) {
                detailScoreLiveData.postValue(responseBDCT);
            }

            @Override
            public void onFailed(String message) {
                detailScoreLiveData.postValue(null);
            }
        });
    }

    public MutableLiveData<ResponseSchedule> getScheduleMutableLiveData() {
        return scheduleMutableLiveData;
    }

    public MutableLiveData<ResponseBDCT> getDetailScoreLiveData() {
        return detailScoreLiveData;
    }
}
