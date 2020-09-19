package com.example.retrofitrxjava.tuition.fragment;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.b.BFragment;
import com.example.retrofitrxjava.databinding.LayoutDoubleBinding;

public class FeesFragment extends BFragment<LayoutDoubleBinding> {
    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initLayout() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_double;
    }

    @Override
    public int getTitle() {
        return R.string.add_widget;
    }
}
