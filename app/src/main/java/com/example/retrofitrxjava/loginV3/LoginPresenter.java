package com.example.retrofitrxjava.loginV3;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.AccountModel;
import com.example.retrofitrxjava.model.ModelResponse;
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
                        AccountModel.userName = userAccount;
                        AccountModel.password = password;
                        String errorCode = loginResponse.getErrorCode();
                        if (errorCode.equals("-1")) {
                            view.verifyAccountFailed();
                            Toast.makeText((Context) view, "Tài khoản hoặc mật khẩu sai !", Toast.LENGTH_SHORT).show();
                        } else if (errorCode.equals("0")) {
                            view.verifyAccountFailed();
                            Toast.makeText((Context) view, "Sai mật khẩu !", Toast.LENGTH_SHORT).show();
                        } else if (errorCode.equals(SUCCESS)) {
                            view.pushView(loginResponse.getData());
                        } else if (errorCode.equals(ACCOUNT_NO_EXITS)) {
                            view.verifyAccountSuccess(userAccount, password);
                        } else {
                            Toast.makeText((Context) view, R.string.error_default
                                    , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.verifyAccountFailed();
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
