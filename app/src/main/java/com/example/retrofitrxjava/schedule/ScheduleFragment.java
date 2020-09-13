package com.example.retrofitrxjava.schedule;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutScheduleBinding;

public class ScheduleFragment extends BFragment<LayoutScheduleBinding> {

    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initLayout() {
        binding.myCalendar.showMonthViewWithBelowEvents();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_schedule;
    }
}
