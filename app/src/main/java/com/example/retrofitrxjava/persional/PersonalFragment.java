package com.example.retrofitrxjava.persional;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutPersonalBinding;
import com.example.retrofitrxjava.persional.adapter.TabLayOut;
import com.example.retrofitrxjava.persional.dialog.DialogSync;
import com.example.retrofitrxjava.persional.model.ScheduleModelResponse;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.utils.AppUtils;
import com.google.android.material.tabs.TabLayoutMediator;

public class PersonalFragment extends BFragment<LayoutPersonalBinding> implements PersonalContract.View {

    private TabLayOut tabLayOut;
    private boolean isType;
    private boolean isMenu;
    private PersonalPresenter presenter;
    private String token;
    DialogSync dialogSync;

    public void setType(boolean type) {
        isType = type;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    @Override
    protected void initLayout() {
        presenter = new PersonalPresenter(this);
        token = PrefUtils.loadData(getActivity()).getToken();
        if (isMenu) {
            binding.groupMenuLayout.setVisibility(View.VISIBLE);
            binding.myCalendar.setVisibility(View.GONE);
            binding.groupTabLayout.setVisibility(View.GONE);
            binding.setData(PrefUtils.loadData(getActivity()));
            binding.updateData.setOnClickListener(view -> {
                dialogSync = new DialogSync(new DialogSync.itemOnClick() {
                    @Override
                    public void onClickSynScore() {
                        dialogSync.dismiss();
                        binding.progressUpdate.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onClickSynSchedule() {
                        dialogSync.dismiss();
                        binding.progressUpdate.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onClickSyncMoney() {
                        dialogSync.dismiss();
                        binding.progressUpdate.setVisibility(View.VISIBLE);
                    }
                });
                dialogSync.show(getFragmentManager(),"");
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
            for (ScheduleModelResponse.Data response : data.getData()) {
                binding.myCalendar.addEvent(AppUtils.formatDate(response.getDatetime()),
                        "8:00", "8:15", response.getTitle());
            }
            binding.myCalendar.showMonthViewWithBelowEvents();
        }
    }

    @Override
    public void updateSuccess(String message) {

    }
}
