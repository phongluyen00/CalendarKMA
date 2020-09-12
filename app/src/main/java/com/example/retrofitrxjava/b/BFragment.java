package com.example.retrofitrxjava.b;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.retrofitrxjava.main.model.ScoreMediumResponse;

public abstract class BFragment<BD extends ViewDataBinding> extends Fragment {
    protected BD binding;
    protected BAdapter<ScoreMediumResponse.Datum> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLayout();
    }

    protected abstract void initLayout();

    protected abstract int getLayoutId();

}
