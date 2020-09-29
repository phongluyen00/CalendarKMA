package com.example.retrofitrxjava.common;

import android.view.View;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.common.dialog.DialogFullScreen;
import com.example.retrofitrxjava.databinding.LayoutPersonalBinding;
import com.example.retrofitrxjava.common.adapter.TabLayOut;
import com.example.retrofitrxjava.common.dialog.DialogSync;
import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.tabs.TabLayoutMediator;

import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS3;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.END_HOURS5;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS3;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.HOURS5;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS1;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS2;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS4;
import static com.example.retrofitrxjava.utils.AppUtils.START_HOURS5;

public class CommonFragment extends BFragment<LayoutPersonalBinding> implements CommonContract.View {

    private TabLayOut tabLayOut;
    private boolean isType;
    private boolean isMenu;
    private CommonPresenter presenter;
    private String token, password;
    DialogSync dialogSync;

    public void setType(boolean type) {
        isType = type;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    @Override
    protected void initLayout() {
        presenter = new CommonPresenter(this);
        token = PrefUtils.loadData(getActivity()).getToken();
        password = PrefUtils.loadData(getActivity()).getPassword();
        if (isMenu) {
            binding.groupMenuLayout.setVisibility(View.VISIBLE);
            binding.myCalendar.setVisibility(View.GONE);
            binding.groupTabLayout.setVisibility(View.GONE);
            binding.setData(PrefUtils.loadData(getActivity()));
            binding.llTuition.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFullScreen dialogFullScreen = new DialogFullScreen();
                    dialogFullScreen.show(getFragmentManager(), "");
                }
            });
            binding.updateData.setOnClickListener(view -> {
                dialogSync = new DialogSync(new DialogSync.itemOnClick() {
                    @Override
                    public void onClickSynScore() {
                        validateUpdate();
                        presenter.updateScore(token, password, myAPI);
                    }

                    @Override
                    public void onClickSynSchedule() {
                        validateUpdate();
                        presenter.updateSchedule(token, password, myAPI);
                    }

                    @Override
                    public void onClickSyncMoney() {
                        validateUpdate();
                        presenter.updateMoney(token, password, myAPI);
                    }

                    @Override
                    public void onClickSyncWaring() {
                        validateUpdate();
                        presenter.syncHandlingService(token, password, myAPI);
                    }

                    @Override
                    public void onClickSyncCC() {
                        validateUpdate();
                        presenter.syncCertificate(token, password, myAPI);
                    }

                    @Override
                    public void close() {
                        dialogSync.dismiss();
                    }
                });
                dialogSync.show(getFragmentManager(), "");
            });
            return;
        }

        if (isType) {
            binding.groupTabLayout.setVisibility(View.VISIBLE);
            binding.myCalendar.setVisibility(View.GONE);
        } else {
            presenter.retrieveSchedule(token, myAPI);
            binding.groupTabLayout.setVisibility(View.GONE);
            binding.myCalendar.setVisibility(View.VISIBLE);
            binding.myCalendar.showMonthView();
        }

        tabLayOut = new TabLayOut(getActivity());
        binding.pager.setAdapter(tabLayOut);
        binding.pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(binding.tabLayout, binding.pager, (tab, position) -> {
            if (position == 0) {
                tab.setText(R.string.score_medium);
            } else if (position == 1) {
                tab.setText(R.string.detail_score);
            }
        }).attach();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_personal;
    }

    @Override
    public int getTitle() {
        return R.string.personal;
    }

    @Override
    protected void onBackPressed() {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void retrieveSuccess(ScheduleModelResponse data) {
        if (data != null && data.getData().size() > 0) {
            binding.myCalendar.deleteAllEvent();
            for (ScheduleModelResponse.Data schedule : data.getData()) {
                putData(schedule.getCaHoc(), AppUtils.formatDate(schedule.getDatetime()), schedule.getTitle());
            }
        }
    }

    private void putData(String time, String date, String name) {
        if (HOURS1.equals(time)) {
            AppUtils.putData(binding.myCalendar, date, START_HOURS1, END_HOURS1, name);
        } else if (HOURS2.equals(time)) {
            AppUtils.putData(binding.myCalendar, date, START_HOURS2, END_HOURS2, name);
        } else if (HOURS3.equals(time)) {
            AppUtils.putData(binding.myCalendar, date, AppUtils.START_HOURS3, END_HOURS3, name);
        } else if (HOURS4.equals(time)) {
            AppUtils.putData(binding.myCalendar, date, START_HOURS4, END_HOURS4, name);
        } else if (HOURS5.equals(time)) {
            AppUtils.putData(binding.myCalendar, date, START_HOURS5, END_HOURS5, name);
        }
    }

    @Override
    public void updateSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        binding.updateData.setEnabled(true);
        binding.progressUpdate.setVisibility(View.GONE);
    }

    private void validateUpdate() {
        dialogSync.dismiss();
        binding.updateData.setEnabled(false);
        binding.progressUpdate.setVisibility(View.VISIBLE);
    }
}
