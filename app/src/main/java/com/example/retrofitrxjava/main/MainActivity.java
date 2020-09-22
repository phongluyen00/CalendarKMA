package com.example.retrofitrxjava.main;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.retrofitrxjava.AppBinding;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.menu.MenuFragment;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.persional.PersonalFragment;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutMainBinding;
import com.example.retrofitrxjava.home.HomeFrg;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.schedule.ScheduleFragment;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class MainActivity extends BActivity<LayoutMainBinding> implements MainListener, MainContract.View, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private MainPresenter presenter;
    private ArrayList<ScoreMediumResponse.Datum> datumArrayList = new ArrayList<>();
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private MenuFragment menuFragment = new MenuFragment();
    private PersonalFragment personalFragment = new PersonalFragment();
    private HomeFrg homeFrg = new HomeFrg();
    private LoginResponse.Data data;

    @Override
    protected void initLayout() {
        presenter = new MainPresenter(this);
        binding.setListener(this);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        binding.leftMenu.setItemIconTintList(null);
        data = PrefUtils.loadData(this);
        presenter.retrieveScore(compositeDisposable, myAPI, data.getToken());
        binding.setAccount(data);

        binding.leftMenu.setNavigationItemSelectedListener(this);

        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        AppBinding.setName(binding.tvTitle,data.getName());
                        binding.rlToolbar.setVisibility(View.VISIBLE);
                        AppUtils.loadView(MainActivity.this, homeFrg);
                        return true;
                    case R.id.manager:
                        AppUtils.loadView(MainActivity.this, scheduleFragment);
                        binding.rlToolbar.setVisibility(View.GONE);
                        return true;
                    case R.id.personal:
                        personalFragment.setData(datumArrayList);
                        binding.tvTitle.setText("Bảng điểm trung bình");
                        binding.rlToolbar.setVisibility(View.VISIBLE);
                        AppUtils.loadView(MainActivity.this, personalFragment);
                        return true;
                    case R.id.menu:
                        binding.tvTitle.setText("Trang cá nhân");
                        binding.rlToolbar.setVisibility(View.VISIBLE);
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void openLeftMenu() {
        binding.drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void retrieveScoreSuccess(ArrayList<ScoreMediumResponse.Datum> datumArrayList) {
        this.datumArrayList = datumArrayList;
        homeFrg.setDataHome(datumArrayList);
        AppUtils.loadView(this, homeFrg);
    }

    @Override
    public void retrieveDataHomeSuccess(ArrayList<Advertisement> advertisements) {
        if (datumArrayList.size() > 0) {
            HomeFrg homeFrg = new HomeFrg();
            homeFrg.setDataHome(datumArrayList);
            AppUtils.loadView(this, homeFrg);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_out:
                LoginResponse.Data data = new LoginResponse.Data();
                PrefUtils.saveData(this,data);
                finish();
                Toast.makeText(this, R.string.log_out, Toast.LENGTH_SHORT).show();
                return true;
        }
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        return false;
    }
}
