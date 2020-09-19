package com.example.retrofitrxjava.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.retrofitrxjava.tuition.fragment.FeesFragment;
import com.example.retrofitrxjava.tuition.fragment.TuitionFragment;

public class PagerAdapter  extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TuitionFragment();
            default:
                return new FeesFragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
