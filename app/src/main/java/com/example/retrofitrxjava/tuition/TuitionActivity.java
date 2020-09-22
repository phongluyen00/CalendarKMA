package com.example.retrofitrxjava.tuition;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.adapter.PagerAdapter;
import com.example.retrofitrxjava.b.BActivity;
import com.example.retrofitrxjava.databinding.LayoutTuitionBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TuitionActivity extends BActivity<LayoutTuitionBinding> {

    private PagerAdapter adapter;

    @Override
    protected void initLayout() {
        adapter = new PagerAdapter(this);
        binding.pager.setAdapter(adapter);
        binding.setListener(this);
        binding.pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        new TabLayoutMediator(binding.tabLayout, binding.pager, new TabLayoutMediator.OnConfigureTabCallback() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("Lệ phí");
                } else if (position == 1) {
                    tab.setText("Học phí");
                }
            }
        }).attach();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_tuition;
    }

}
