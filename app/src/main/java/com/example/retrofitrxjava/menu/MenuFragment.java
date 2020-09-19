package com.example.retrofitrxjava.menu;

import android.content.Intent;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutMenuBinding;
import com.example.retrofitrxjava.tuition.TuitionActivity;

public class MenuFragment extends BFragment<LayoutMenuBinding> implements MenuListener{
    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initLayout() {
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
        startActivity(new Intent(getActivity(), TuitionActivity.class));

    }

    @Override
    public void onClickTwo() {

    }

    @Override
    public void onClickThree() {

    }
}
