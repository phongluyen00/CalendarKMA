package com.example.retrofitrxjava;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.retrofitrxjava.Retrofit.MyAPI;
import com.example.retrofitrxjava.Retrofit.RetrofitClient;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutMainBinding;
import com.example.retrofitrxjava.home.HomeFrg;
import com.example.retrofitrxjava.model.ResponseAPI;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends BActivity<LayoutMainBinding> implements MainListener {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void initLayout() {
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        Fragment fragment;
        fragment = new HomeFrg();
        AppUtils.loadView(this, fragment);
        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFrg();
                        AppUtils.loadView(MainActivity.this, fragment);
                        return true;
                    case R.id.manager:
                        fragment = new HomeFrg();
                        AppUtils.loadView(MainActivity.this, fragment);
                        return true;
                    case R.id.personal:
                        fragment = new HomeFrg();
                        AppUtils.loadView(MainActivity.this, fragment);
                        return true;
                    case R.id.menu:
                        fragment = new HomeFrg();
                        AppUtils.loadView(MainActivity.this, fragment);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_main;
    }

    private void fetchData() {
        compositeDisposable.add(myAPI.getWeather("2172797", "439d4b804bc8187953eb36d2a8c26a02")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseAPI>() {
                    @Override
                    public void accept(ResponseAPI responseAPI) throws Exception {
                        Log.d("response", responseAPI.getName());
                    }
                }));
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onClick(View view) {

    }
}
