package com.example.retrofitrxjava.base;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.utils.PrefUtils;
import com.example.retrofitrxjava.retrofit.RequestAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;

import retrofit2.Retrofit;

public class BaseViewModel extends ViewModel {
    protected Activity activity;
    protected RequestAPI requestAPI;
    protected String username, password;
    protected DataResponse dataResponse;

    public BaseViewModel setActivity(Activity activity) {
        this.activity = activity;
        this.dataResponse = PrefUtils.loadCacheData(activity);
        username = dataResponse.getId().toUpperCase();
        password = dataResponse.getPassword();
        Retrofit retrofit = RetrofitClient.getInstance();
        requestAPI = retrofit.create(RequestAPI.class);
        return this;
    }
}
