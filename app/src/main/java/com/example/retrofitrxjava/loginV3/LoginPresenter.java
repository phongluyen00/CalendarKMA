package com.example.retrofitrxjava.loginV3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void verifyAccount(final MyAPI myAPI, final String userAccount, final String password) {
        myAPI.loginStatus(userAccount, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginResponse -> {
                    loginResponse.getData().setToken(userAccount);
                    loginResponse.getData().setPassword(password);
                    PrefUtils.saveData((Context) view, loginResponse.getData());
                    if (loginResponse.getErrorCode().equals(SUCCESS)){
                        view.pushView();
                    }else {
                        view.verifyAccountFailed(((Context) view).getString(R.string.login_failed));
                    }
                }, error -> {
                    view.verifyAccountFailed(((Context) view).getString(R.string.error_default));
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void synchronization(MyAPI myAPI, String userName, String password) {
        myAPI.synchronization(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.equals(SUCCESS))
                .subscribe(response -> {
                    view.synchronizationSuccess(String.valueOf(R.string.synchronization_success));
                }, throwable -> Toast.makeText((Context) view, R.string.error_default
                        , Toast.LENGTH_SHORT).show());
    }
}
