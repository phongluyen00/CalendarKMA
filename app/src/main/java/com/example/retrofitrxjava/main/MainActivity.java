package com.example.retrofitrxjava.main;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.retrofitrxjava.AppBinding;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.dialog.DialogListener;
import com.example.retrofitrxjava.main.dialog.DialogLogout;
import com.example.retrofitrxjava.home.model.Advertisement;
import com.example.retrofitrxjava.persional.PersonalFragment;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.retrofit.MyAPI;
import com.example.retrofitrxjava.retrofit.RetrofitClient;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutMainBinding;
import com.example.retrofitrxjava.home.HomeFrg;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Retrofit;

public class MainActivity extends BActivity<LayoutMainBinding> implements MainListener, MainContract.View, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private MainPresenter presenter;
    private PersonalFragment personalFragment;
    private HomeFrg homeFrg = new HomeFrg();
    private LoginResponse.Data data;
    private DialogLogout logoutDialog;

    @Override
    protected void initLayout() {
        presenter = new MainPresenter(this);
        binding.progressMain.setVisibility(View.VISIBLE);
        presenter.retrieveScore(myAPI);
        binding.setListener(this);
        Retrofit retrofit = RetrofitClient.getInstance();
        myAPI = retrofit.create(MyAPI.class);
        binding.leftMenu.setItemIconTintList(null);
        data = PrefUtils.loadData(this);
        binding.setAccount(data);
        AppUtils.loadView(this, homeFrg);
        binding.leftMenu.setNavigationItemSelectedListener(this);
        binding.navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    AppBinding.setName(binding.tvTitle, data.getName());
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    AppUtils.loadView(MainActivity.this, homeFrg);
                    return true;
                case R.id.manager:
                case R.id.personal:
                case R.id.menu:
                    personalFragment = new PersonalFragment();
                    if (item.getItemId() == R.id.manager) {
                        binding.rlToolbar.setVisibility(View.GONE);
                        personalFragment.setType(false);
                    } else if (item.getItemId() == R.id.personal) {
                        binding.tvTitle.setText(R.string.average_transcript);
                        binding.rlToolbar.setVisibility(View.VISIBLE);
                        personalFragment.setType(true);
                    } else if (item.getItemId() == R.id.menu){
                        binding.rlToolbar.setVisibility(View.VISIBLE);
                        binding.tvTitle.setText(R.string.info_account);
                        personalFragment.setMenu(true);
                    }
                    AppUtils.loadView(MainActivity.this, personalFragment);
                    return true;
            }
            return false;
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out:
                logoutDialog = new DialogLogout(new DialogListener() {
                    @Override
                    public void onClickAccept(View view) {
                        logoutDialog.dismiss();
                        PrefUtils.saveData(MainActivity.this, new LoginResponse.Data());
                        Toast.makeText(MainActivity.this, "Đăng xuất thành công !", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onClickCancel(View view) {
                        logoutDialog.dismiss();
                    }
                });
                logoutDialog.show(getSupportFragmentManager(), "Dialog logout");
                return true;
        }
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        return false;
    }

    @Override
    public void retrieveSuccess() {
        binding.progressMain.setVisibility(View.GONE);
    }
}
