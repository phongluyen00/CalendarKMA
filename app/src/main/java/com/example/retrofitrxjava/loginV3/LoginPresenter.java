package com.example.retrofitrxjava.loginV3;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;
import com.example.retrofitrxjava.security.AESHelper;
import com.example.retrofitrxjava.utils.AppUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;
    private MyAPI myAPI;

    public LoginPresenter(LoginContract.View view) {
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void verifyAccount(String entryDataLogin) {
        myAPI.loginStatus(entryDataLogin)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    String decryptText = new AESHelper().decrypt(loginResponse.getData(), AppUtils.privateKey);
                    LoginResponse.Data data = (LoginResponse.Data) AppUtils.GenericInstance(decryptText,LoginResponse.Data.class);
                    if (loginResponse.getErrorCode() == 1) {
                        view.pushView(data);
                    } else {
                        view.verifyAccountFailed(((Context) view).getString(R.string.login_failed));
                    }
                }, error -> {
                    view.verifyAccountFailed(((Context) view).getString(R.string.error_default));
                });
    }

}
