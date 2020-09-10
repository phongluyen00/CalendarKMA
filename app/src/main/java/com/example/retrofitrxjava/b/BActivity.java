package com.example.retrofitrxjava.b;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.retrofitrxjava.Retrofit.MyAPI;

public abstract class BActivity<BD extends ViewDataBinding> extends AppCompatActivity {
    protected BD binding;
    protected MyAPI myAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        initLayout();
    }

    protected abstract void initLayout();

    protected abstract int getLayoutId();
}
