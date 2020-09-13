package com.example.retrofitrxjava.main;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.menu.MenuFragment;
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
    private ArrayList<ScoreMediumResponse.Datum> datumArrayList = new ArrayList<>();
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private MenuFragment menuFragment = new MenuFragment();
    private PersonalFragment personalFragment = new PersonalFragment();
    private HomeFrg homeFrg = new HomeFrg();

    @Override
    protected void initLayout() {
        presenter = new MainPresenter(this);
        binding.setListener(this);
        data = getIntent().getParcelableExtra("obj");
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        presenter.retrieveScore(compositeDisposable, myAPI, AccountModel.userName, "");

        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        presenter.retrieveDataHome("");
                        return true;
                    case R.id.manager:
                        AppUtils.loadView(MainActivity.this, scheduleFragment);
                        return true;
                    case R.id.personal:

                        personalFragment.setData(datumArrayList);
                        AppUtils.loadView(MainActivity.this, personalFragment);
                        return true;
                    case R.id.menu:
                        AppUtils.loadView(MainActivity.this, menuFragment);
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick() {
    }

    @Override
    public void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> datumArrayList) {
        this.datumArrayList = datumArrayList;
        homeFrg.setDataHome(data, datumArrayList);
        AppUtils.loadView(this, homeFrg);
    }

    @Override
    public void retrieveDataHomeSuccess(ArrayList<Advertisement> advertisements) {
        if (datumArrayList.size() > 0) {
            HomeFrg homeFrg = new HomeFrg();
            homeFrg.setDataHome(data, datumArrayList);
            AppUtils.loadView(this, homeFrg);
        } else {
            presenter.retrieveScore(compositeDisposable, myAPI, "CT010233", "");
        }
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
