package com.example.retrofitrxjava.menu;

import android.content.Intent;
import android.util.Log;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutMenuBinding;
import com.example.retrofitrxjava.pre.PrefUtils;
import com.example.retrofitrxjava.tuition.TuitionActivity;

public class MenuFragment extends BFragment<LayoutMenuBinding> implements MenuListener {

    @Override
    protected void initLayout() {
        binding.setData(PrefUtils.loadData(getActivity()));
        binding.setListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_menu;
    }

    @Override
    public int getTitle() {
        return R.string.menu;
    }

    @Override
    public void onClickOne() {
        Intent intent = new Intent(getActivity(),TuitionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickTwo() {

    }

    @Override
    public void onClickThree() {

    }

    @Override
    protected void onBackPressed() {

    }
}
