package com.example.retrofitrxjava.loginV3;

import android.content.Context;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.ACCOUNT_NO_EXITS;
import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }

    @Override
    public void verifyAccount(final MyAPI myAPI, final String userAccount, final String password) {
        myAPI.loginStatus(userAccount, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        loginResponse.getData().setToken(userAccount);
                        loginResponse.getData().setPassword(password);
                        PrefUtils.saveData((Context) view, loginResponse.getData());
                        String errorCode = loginResponse.getErrorCode();
                        if (errorCode.equals("-1")) {
                            view.verifyAccountFailed(((Context) view).getString(R.string.User_account_or_password_incorrect));
                        } else if (errorCode.equals("0")) {
                            view.verifyAccountFailed(((Context) view).getString(R.string.wrong_password));
                        } else if (errorCode.equals(SUCCESS)) {
                            view.pushView();
                        } else if (errorCode.equals(ACCOUNT_NO_EXITS)) {
                            view.verifyAccountSuccess(userAccount, password);
                        } else {
                            view.verifyAccountFailed(((Context) view).getString(R.string.error_default));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.verifyAccountFailed(((Context) view).getString(R.string.error_default));
                    }


                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void synchronization(MyAPI myAPI, String userName, String password) {
        myAPI.synchronization(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ModelResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ModelResponse response) {
                        if (response.getErrorCode() == SUCCESS) {
                            view.synchronizationSuccess(String.valueOf(R.string.synchronization_success));
                        } else {
                            view.synchronizationFailed(String.valueOf(R.string.error_default));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText((Context) view, R.string.error_default
                                , Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onComplete() {

                    }
                });
    }
}
