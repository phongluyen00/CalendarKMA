package com.example.retrofitrxjava.base;

import com.example.retrofitrxjava.utils.AppUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by luyenphong on 16/11/2020.
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (!AppUtils.isNullOrEmpty(t)) {
            onSuccess(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailed(e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(String message);
}
