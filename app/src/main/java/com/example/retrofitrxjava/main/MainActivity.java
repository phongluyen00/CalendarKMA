package com.example.retrofitrxjava.main;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

import com.example.retrofitrxjava.AppBinding;
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

public class MainActivity extends BActivity<LayoutMainBinding> implements MainListener, MainContract.View, NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private MainPresenter presenter;
    private CommonFragment personalFragment;
    private HomeFrg homeFrg = new HomeFrg();
    private LoginResponse.Data data;
    private DialogLogout logoutDialog;
    private String token, password;

    @Override
    protected void initLayout() {
        token = PrefUtils.loadData(MainActivity.this).getToken();
        password = PrefUtils.loadData(MainActivity.this).getPassword();
        presenter = new MainPresenter(this);
        binding.progressMain.setVisibility(View.VISIBLE);
        presenter.retrieveScore(myAPI);
        binding.setListener(this);
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
                    personalFragment = new CommonFragment();
                    if (item.getItemId() == R.id.manager) {
                        binding.rlToolbar.setVisibility(View.GONE);
                        personalFragment.setType(false);
                    } else if (item.getItemId() == R.id.personal) {
                        binding.tvTitle.setText(R.string.average_transcript);
                        binding.rlToolbar.setVisibility(View.VISIBLE);
                        personalFragment.setType(true);
                    } else if (item.getItemId() == R.id.menu) {
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
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        switch (item.getItemId()) {
            case R.id.log_out:
                logoutDialog = new DialogLogout(new DialogLogout.onItemClick() {
                    @Override
                    public void onClickAccept(View view) {
                        logoutDialog.dismiss();
                        PrefUtils.saveData(MainActivity.this, new LoginResponse.Data());
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
                AppUtils.loadView(this, new RecruitmentFrg());
                return true;
        }

        return false;
    }

    @Override
    public void retrieveScoreSuccess(ScoreMediumResponse response) {
        binding.progressMain.setVisibility(View.GONE);
        if (!(response.getData() != null && response.getData().size() > 0))
            presenter.synchronization(myAPI, token, password);
    }

    @Override
    public void synScoreDetail() {
        presenter.synScoreDetail(myAPI, token, password);
    }

    @Override
    public void synSchedule() {
        presenter.synSchedule(myAPI, token, password);
    }

    @Override
    public void synchronization() {
        presenter.synchronization(myAPI, token, password);
    }
}
