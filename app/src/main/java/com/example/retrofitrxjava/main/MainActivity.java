package com.example.retrofitrxjava.main;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.model.AccountModel;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.persional.PersonalFragment;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutMainBinding;
import com.example.retrofitrxjava.home.HomeFrg;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.schedule.ScheduleFragment;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class MainActivity extends BActivity<LayoutMainBinding> implements MainListener, MainContract.View {

    private MainPresenter presenter;
    private LoginResponse.Data data;

    @Override
    protected void initLayout() {
        presenter = new MainPresenter(this);
        binding.setListener(this);
        data = getIntent().getParcelableExtra("obj");
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        presenter.retrieveDataHome("");

        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        presenter.retrieveDataHome("");
                        return true;
                    case R.id.manager:
                        fragment = new ScheduleFragment();
                        AppUtils.loadView(MainActivity.this, fragment);
                        return true;
                    case R.id.personal:
                        presenter.retrieveScore(compositeDisposable, myAPI, AccountModel.userName, AccountModel.password);
                        return true;
                    case R.id.menu:
//                        presenter.retrieveDataHome("");
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

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    @Override
    public void onClick() {
    }

    @Override
    public void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> data) {
        PersonalFragment personalFragment = new PersonalFragment();
        personalFragment.setData(data);
        AppUtils.loadView(MainActivity.this, personalFragment);
    }

    @Override
    public void retrieveDataHomeSuccess(ArrayList<Advertisement> advertisements) {
        HomeFrg fragment = new HomeFrg();
        fragment.setDataHome(advertisements,data);
        AppUtils.loadView(this, fragment);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
