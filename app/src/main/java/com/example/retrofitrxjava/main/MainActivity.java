package com.example.retrofitrxjava.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.retrofitrxjava.AppBinding;
import com.example.retrofitrxjava.main.dialog.DialogContactUs;
import com.example.retrofitrxjava.parser.RecruitmentFrg;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.example.retrofitrxjava.main.dialog.DialogLogout;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.common.CommonFragment;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutMainBinding;
import com.example.retrofitrxjava.home.HomeFrg;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends BActivity<LayoutMainBinding> implements MainListener, MainContract.View, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private MainPresenter presenter;
    private CommonFragment personalFragment;
    private RecruitmentFrg recruitmentFrg;
    private HomeFrg homeFrg = new HomeFrg();
    private LoginResponse.Data userModel;
    private DialogLogout logoutDialog;
    RxPermissions rxPermissions;

    @SuppressLint("CheckResult")
    @Override
    protected void initLayout() {
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).subscribe(granted -> {
                    if (granted) {
                    }
                });
        userModel = PrefUtils.loadData(this);
        presenter = new MainPresenter(this);
        if (!(userModel.getScoreMediumResponse() != null)) {
            binding.progressMain.setVisibility(View.VISIBLE);
            presenter.retrieveScore(myAPI);
        }
        binding.setListener(this);
        binding.leftMenu.setItemIconTintList(null);
        binding.setAccount(userModel);
        AppUtils.loadView(this, HomeFrg.getInstance());
        binding.leftMenu.setNavigationItemSelectedListener(this);
        binding.navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    AppBinding.setName(binding.tvTitle, userModel.getName());
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    AppUtils.loadView(MainActivity.this, homeFrg);
                    return true;
                case R.id.manager:
                    personalFragment = new CommonFragment();
                    binding.rlToolbar.setVisibility(View.GONE);
                    personalFragment.setType(false);
                    AppUtils.loadView(MainActivity.this, personalFragment);
                    return true;
                case R.id.personal:
                    personalFragment = new CommonFragment();
                    binding.tvTitle.setText(R.string.average_transcript);
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    personalFragment.setType(true);
                    AppUtils.loadView(MainActivity.this, personalFragment);
                    return true;
                case R.id.menu:
                    personalFragment = new CommonFragment();
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    binding.tvTitle.setText(R.string.info_account);
                    personalFragment.setMenu(true);
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        switch (item.getItemId()) {
            case R.id.log_out:
                logoutDialog = new DialogLogout(new DialogLogout.onItemClick() {
                    @Override
                    public void onClickAccept(View view) {
                        logoutDialog.dismiss();
                        PrefUtils.saveData(MainActivity.this, null);
                        Toast.makeText(MainActivity.this, R.string.log_out_success, Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onClickCancel(View view) {
                        logoutDialog.dismiss();
                    }
                });
                logoutDialog.show(getSupportFragmentManager(), "Dialog logout");
                return true;
            case R.id.it:
                binding.tvTitle.setText(R.string.recruitment);
                AppUtils.loadView(this, RecruitmentFrg.getInstance());
                return true;
            case R.id.contact:
                DialogContactUs dialogContactUs = new DialogContactUs();
                dialogContactUs.show(getSupportFragmentManager(),"");
                return true;
            case R.id.study:
                binding.tvTitle.setText("Học Lập trình Căn Bản");
                recruitmentFrg = new RecruitmentFrg();
                recruitmentFrg.setCheck(true);
                AppUtils.loadView(this,recruitmentFrg);
                return true;
        }
        return false;
    }

    @Override
    public void retrieveScoreSuccess(ScoreMediumResponse response) {
        binding.progressMain.setVisibility(View.GONE);
    }

}
