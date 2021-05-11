package com.example.retrofitrxjava.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.retrofitrxjava.AppBinding;
import com.example.retrofitrxjava.common.view.MapsActivity;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.parser.RecruitmentFrg;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.main.dialog.DialogLogout;
import com.example.retrofitrxjava.common.view.CommonFragment;
import com.example.retrofitrxjava.parser.event.EventUpdateTitle;
import com.example.retrofitrxjava.utils.PrefUtils;
import com.example.retrofitrxjava.base.BaseActivity;
import com.example.retrofitrxjava.databinding.LayoutMainBinding;
import com.example.retrofitrxjava.home.HomeFrg;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.retrofitrxjava.common.view.CommonFragment.MAN_HINH_THONG_TIN;
import static com.example.retrofitrxjava.common.view.CommonFragment.MAN_HINH_XEM_DIEM;

public class MainActivity extends BaseActivity<LayoutMainBinding> implements
        NavigationView.OnNavigationItemSelectedListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private CommonFragment personalFragment;
    private HomeFrg homeFrg = new HomeFrg();
    private DataResponse userModel;
    private DialogLogout logoutDialog;
    RxPermissions rxPermissions;
    private MainViewModel mainViewModel;
    private ResponseSchedule schedule;
    private ResponseBDCT responseBDCTLst;

    @SuppressLint("CheckResult")
    @Override
    protected void initLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).subscribe(granted -> {
        });
        userModel = PrefUtils.loadCacheData(this);
        mainViewModel = getViewModel(MainViewModel.class);
        // load api
        initCallAPI();
        initLiveData();
        // end load

        binding.navView.setOnClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));
        binding.leftMenu.setItemIconTintList(null);
        binding.setData(userModel);
        binding.leftMenu.setNavigationItemSelectedListener(this);
        binding.navView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    AppBinding.setName(binding.tvTitle, userModel.getName());
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    AppUtils.loadView(MainActivity.this, homeFrg);
                    return true;
                case R.id.manager:
                    if (!AppUtils.isNullOrEmpty(schedule)) {
                        personalFragment = new CommonFragment();
                        binding.rlToolbar.setVisibility(View.GONE);
                        personalFragment.setSetView(CommonFragment.MAN_HINH_LICH_HOC);
                        AppUtils.loadView(MainActivity.this, personalFragment);
                    } else {
                        Toast.makeText(activity, "Chưa có dữ liệu lịch học", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.personal:
                    personalFragment = new CommonFragment();
                    binding.tvTitle.setText(R.string.average_transcript);
                    personalFragment.setSetView(MAN_HINH_XEM_DIEM);
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    AppUtils.loadView(MainActivity.this, personalFragment);
                    return true;
                case R.id.menu:
                    personalFragment = new CommonFragment();
                    binding.rlToolbar.setVisibility(View.VISIBLE);
                    binding.tvTitle.setText(R.string.info_account);
                    personalFragment.setSetView(MAN_HINH_THONG_TIN);
                    AppUtils.loadView(MainActivity.this, personalFragment);
                    return true;
            }
            return false;
        });
    }

    private void initLiveData() {
        mainViewModel.getScheduleMutableLiveData().observe(this, responseSchedule -> {
            if (!AppUtils.isNullOrEmpty(responseSchedule) && responseSchedule.isSuccess()) {
                this.schedule = responseSchedule;
                userModel.setSchedule(schedule);
                PrefUtils.cacheData(this, userModel);
                Toast.makeText(activity, "call schedule success", Toast.LENGTH_SHORT).show();
            } else {
                mainViewModel.retrieveSchedule();
            }
            checkData();
        });

        mainViewModel.getDetailScoreLiveData().observe(this, responseBDCT -> {
            if (!AppUtils.isNullOrEmpty(responseBDCT) && responseBDCT.isSuccess()) {
                responseBDCTLst = responseBDCT;
                userModel.setResponseBDCT(responseBDCT);
                Toast.makeText(activity, "call responseBDCT success", Toast.LENGTH_SHORT).show();
                PrefUtils.cacheData(this, userModel);
            } else {
                mainViewModel.retrieveBDCT();
            }
            checkData();
        });
    }

    private void checkData(){
        binding.progressMain.setVisibility(!AppUtils.isNullOrEmpty(responseBDCTLst)
                && !AppUtils.isNullOrEmpty(this.schedule) ? View.GONE : View.VISIBLE);
        binding.dongBoData.setVisibility(!AppUtils.isNullOrEmpty(responseBDCTLst)
                && !AppUtils.isNullOrEmpty(this.schedule) ? View.GONE : View.VISIBLE);
        if (!AppUtils.isNullOrEmpty(responseBDCTLst) && !AppUtils.isNullOrEmpty(this.schedule)){
            AppUtils.loadView(this, HomeFrg.getInstance());
        }
    }

    private void initCallAPI() {
        mainViewModel.setActivity(this);
        mainViewModel.retrieveSchedule();
        mainViewModel.retrieveBDCT();
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

    /**
     * Cập nhật title
     *
     * @param noteEvent
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventTitle(EventUpdateTitle noteEvent) {
        if (noteEvent != null) {
            binding.tvTitle.setText(noteEvent.getTitle());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                dialogContactUs.show(getSupportFragmentManager(), "");
                return true;
            case R.id.study:
                binding.tvTitle.setText(R.string.hoc);
                RecruitmentFrg recruitmentFrg = new RecruitmentFrg();
                recruitmentFrg.setCheck(true);
                AppUtils.loadView(this, recruitmentFrg);
                return true;
            case R.id.map:
                startActivity(new Intent(this, MapsActivity.class));
                return true;
        }
        return false;
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public interface MainListener {
        void openLeftMenu();
    }
}
