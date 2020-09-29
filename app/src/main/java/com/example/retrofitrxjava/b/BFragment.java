package com.example.retrofitrxjava.b;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.model.ModelResponse;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

public abstract class BFragment<BD extends ViewDataBinding> extends Fragment {
    protected BD binding;
    protected MyAPI myAPI;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        initLayout();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getView() == null) {
            return;
        }
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                onBackPressed();
                return true;
            }
            return false;
        });
    }

    protected abstract void onBackPressed();

    protected abstract void initLayout();

    protected abstract int getLayoutId();

    public abstract @StringRes
    int getTitle();

}
