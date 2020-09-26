package com.example.retrofitrxjava.persional;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.retrofit.MyAPI;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.retrofitrxjava.utils.Constant.SUCCESS;

public class PersonalPresenter implements PersonalContract.Presenter {

    private PersonalContract.View view;

    public PersonalPresenter(PersonalContract.View view) {
        this.view = view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void retrieveSchedule(String token, MyAPI myAPI) {
        myAPI.getSchedule(token).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(response -> response.getErrorCode().equals(SUCCESS))
                .subscribe(response ->  view.retrieveSuccess(response),
                        throwable -> Toast.makeText((Context) view,
                        ((Context) view).getString(R.string.error_default),
                                Toast.LENGTH_SHORT).show());
    }

    @Override
    public void updateSchedule(String token, String password, MyAPI myAPI) {

    }

    @Override
    public void updateScore(String token, String password, MyAPI myAPI) {

    }

    @Override
    public void updateMoney(String token, String password, MyAPI myAPI) {

    }
}
