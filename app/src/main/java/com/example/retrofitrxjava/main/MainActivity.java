package com.example.retrofitrxjava.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.retrofitrxjava.AppBinding;
import com.example.retrofitrxjava.admin.Account;
import com.example.retrofitrxjava.admin.AccountFragment;
import com.example.retrofitrxjava.base.BaseObserver;
import com.example.retrofitrxjava.common.view.MapsActivity;
import com.example.retrofitrxjava.databinding.NavHeaderMainBinding;
import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.example.retrofitrxjava.main.dialog.DialogAddTeacher;
import com.example.retrofitrxjava.main.model.ResponseBDCT;
import com.example.retrofitrxjava.main.model.ResponseSchedule;
import com.example.retrofitrxjava.parser.RecruitmentFrg;
import com.example.retrofitrxjava.R;
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
    RxPermissions rxPermissions;
    private MainViewModel mainViewModel;
    private ResponseSchedule schedule;
    private ResponseBDCT responseBDCTLst;
    private AccountFragment accountFragment = new AccountFragment();
    private static final String REQUEST_DB = "request_DB";
    private static final String UPDATE_DB = "update_db";

    public AccountFragment getAccountFragment() {
        return accountFragment;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initLayout() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mainViewModel = getViewModel(MainViewModel.class);

        rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).subscribe(granted -> {
        });

        userModel = PrefUtils.loadCacheData(this);
        binding.setData(userModel);
        binding.btnLefMenu.setOnClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));
        binding.leftMenu.setNavigationItemSelectedListener(this);
        binding.leftMenu.setItemIconTintList(null);

        NavHeaderMainBinding _bind = NavHeaderMainBinding.bind(binding.leftMenu.getHeaderView(0));
        _bind.setItem(userModel);
        _bind.navHeader.setOnClickListener(v -> {
            if (userModel.getPermission() == 1) {
                DialogAddTeacher dialogAddTeacher = new DialogAddTeacher((username, password, name, faculty) -> {
                    password = AppUtils.isNullOrEmpty(password) ? userModel.getPassword() : password;
                    name = AppUtils.isNullOrEmpty(name) ? userModel.getName() : name;
                    faculty = AppUtils.isNullOrEmpty(faculty) ? userModel.getFaculty() : faculty;
                    String finalName = name;
                    AppUtils.HandlerRXJava(requestAPI.updateTeacher(userModel.getId(), password, name, faculty),
                            new BaseObserver<DataResponse>() {
                        @Override
                        public void onSuccess(DataResponse dataResponse) {
                            _bind.tvName.setText(finalName);
                            binding.tvTitle.setText(finalName);
                            Toast.makeText(MainActivity.this, dataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(MainActivity.this, "Hệ Thống Bận !", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                dialogAddTeacher.setIsChangeAccount(1);
                Account account = new Account();
                account.setUsername(userModel.getId());
                account.setName(userModel.getName());
                account.setFaculty(userModel.getFaculty());
                dialogAddTeacher.setAccount(account);
                dialogAddTeacher.show(getSupportFragmentManager(), dialogAddTeacher.getTag());
            }
        });

        binding.addTeacher.setVisibility(userModel.getPermission() == 1 ? View.GONE : View.VISIBLE);
        binding.addTeacher.setImageResource(userModel.getPermission() == 0 ? R.drawable.ic_cloud_sync : R.drawable.ic_baseline_person_add_24);
        // check admin
        binding.addTeacher.setOnClickListener(v -> {
            if (userModel.getPermission() == 2){
                DialogAddTeacher dialogAddTeacher = new DialogAddTeacher((username, password, name, faculty) -> {
                    AppUtils.HandlerRXJava(requestAPI.addTeacher(username, password, name, faculty), new BaseObserver<DataResponse>() {
                        @Override
                        public void onSuccess(DataResponse dataResponse) {
                            Toast.makeText(MainActivity.this, dataResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            getAccountFragment().refreshData();
                        }

                        @Override
                        public void onFailed(String message) {
                            Toast.makeText(MainActivity.this, "Hệ thống bận !", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                dialogAddTeacher.show(getSupportFragmentManager(), dialogAddTeacher.getTag());
            }else {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Update database")
                        .setMessage("Are you sure you want to update this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mainViewModel.retrieveBDCT(UPDATE_DB);
                                mainViewModel.retrieveBDTB(UPDATE_DB);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_cloud_sync)
                        .show();
            }
        });

        if (userModel.getPermission() != 0) {
            binding.navView.setVisibility(View.GONE);
            binding.groupAdmin.setVisibility(View.GONE);
            AppUtils.loadView(this, accountFragment);
            return;
        }

        // load api
        initCallAPI();
        initLiveData();
        // end load

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
                    personalFragment.setSetView(CommonFragment.MAN_HINH_LICH_HOC);
                    AppUtils.loadView(MainActivity.this, personalFragment);
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
                mainViewModel.retrieveBDCT(REQUEST_DB);
            }
            checkData();
        });
    }

    private void checkData() {
        binding.groupAdmin.setVisibility(!AppUtils.isNullOrEmpty(responseBDCTLst)
                && !AppUtils.isNullOrEmpty(this.schedule) ? View.GONE : View.VISIBLE);
        if (!AppUtils.isNullOrEmpty(responseBDCTLst) && !AppUtils.isNullOrEmpty(this.schedule)) {
            AppUtils.loadView(this, HomeFrg.getInstance());
        }
    }

    private void initCallAPI() {
        mainViewModel.setActivity(this);
        mainViewModel.retrieveSchedule();
        mainViewModel.retrieveBDCT(REQUEST_DB);
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
                finish();
                Toast.makeText(MainActivity.this, R.string.log_out_success, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.it:
                binding.tvTitle.setText(R.string.recruitment);
                AppUtils.loadView(this, RecruitmentFrg.getInstance());
                return true;
            case R.id.contact:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/phong.luyen.96/"));
                startActivity(browserIntent);
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

}
