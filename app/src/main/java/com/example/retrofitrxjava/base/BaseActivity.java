package com.example.retrofitrxjava.base;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.example.retrofitrxjava.retrofit.RequestAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public abstract class BaseActivity<BD extends ViewDataBinding> extends AppCompatActivity {
    protected BD binding;
    protected RequestAPI requestAPI;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        Retrofit retrofit = RetrofitClient.getInstance();
        requestAPI = retrofit.create(RequestAPI.class);
        activity = this;
        initLayout();
    }

    protected <OUT extends BaseViewModel>  OUT getViewModel(Class<? extends BaseViewModel> viewModelType) {
        ViewModelProvider.Factory factory = new ViewModelProvider.NewInstanceFactory();
        OUT vm = (OUT) new ViewModelProvider(this, factory).get(viewModelType);
        vm.setActivity(activity);
        return vm;
    }

    protected abstract void initLayout();

    protected abstract int getLayoutId();
}
