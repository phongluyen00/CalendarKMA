package com.example.retrofitrxjava.common.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.retrofitrxjava.common.view.ScoreUserFragment;

import static com.example.retrofitrxjava.common.view.ScoreUserFragment.BANG_DIEM_CT;
import static com.example.retrofitrxjava.common.view.ScoreUserFragment.BANG_DIEM_TB;

public class TabLayOut extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    private ScoreUserFragment fragment;

    public TabLayOut(FragmentActivity fa) {
        super(fa);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                fragment = new ScoreUserFragment();
                fragment.setIsView(BANG_DIEM_CT);
                return fragment;
            default:
                fragment = new ScoreUserFragment();
                fragment.setIsView(BANG_DIEM_TB);
                return fragment;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
