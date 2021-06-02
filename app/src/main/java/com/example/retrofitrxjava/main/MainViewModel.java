package com.example.retrofitrxjava.main;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.retrofitrxjava.base.BaseViewModel;
import com.example.retrofitrxjava.base.BaseObserver;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseBDTB;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.utils.AppUtils;

/**
 * Created by luyenphong on 13/11/2020.
 */
public class MainViewModel extends BaseViewModel {

    private MutableLiveData<ResponseSchedule> scheduleMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseBDCT> detailScoreLiveData = new MutableLiveData<>();
    private MutableLiveData<ResponseBDTB> liveData = new MutableLiveData<>();

    public void retrieveSchedule(String status) {
        AppUtils.HandlerRXJava(requestAPI.retrieveSchedule(username, password, status),
                new BaseObserver<ResponseSchedule>() {
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

    public void retrieveBDCT(String status) {
        AppUtils.HandlerRXJava(requestAPI.retrieveBDCT(username, password, status), new BaseObserver<ResponseBDCT>() {
            @Override
            public void onSuccess(ResponseBDCT responseBDCT) {
                if (!AppUtils.isNullOrEmpty(responseBDCT) && responseBDCT.isSuccess()) {
                    detailScoreLiveData.postValue(responseBDCT);
                } else {
                    Toast.makeText(activity, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(String message) {
                detailScoreLiveData.postValue(null);
                Toast.makeText(activity, "Cập nhật dữ liệu thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void retrieveBDTB(String status) {
        AppUtils.HandlerRXJava(requestAPI.retrieveBDTB(username, password, status), new BaseObserver<ResponseBDTB>() {
            @Override
            public void onSuccess(ResponseBDTB responseBDTB) {
                if (!AppUtils.isNullOrEmpty(responseBDTB)) {
                    liveData.postValue(responseBDTB);
                }
            }

            @Override
            public void onFailed(String message) {

            }
        });
    }

    public MutableLiveData<ResponseSchedule> getScheduleMutableLiveData() {
        return scheduleMutableLiveData;
    }

    public MutableLiveData<ResponseBDCT> getDetailScoreLiveData() {
        return detailScoreLiveData;
    }

    public MutableLiveData<ResponseBDTB> getLiveData() {
        return liveData;
    }
}
