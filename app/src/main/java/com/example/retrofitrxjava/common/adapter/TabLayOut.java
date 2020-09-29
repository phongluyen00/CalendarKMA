package com.example.retrofitrxjava.common.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.retrofitrxjava.common.average.AverageTranscriptFragment;

public class TabLayOut extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    private AverageTranscriptFragment fragment;

    public TabLayOut(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                fragment = new AverageTranscriptFragment();
                fragment.checkStatus(false);
                return fragment;
            default:
                fragment = new AverageTranscriptFragment();
                fragment.checkStatus(true);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
