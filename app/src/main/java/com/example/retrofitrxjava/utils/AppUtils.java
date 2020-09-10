package com.example.retrofitrxjava.utils;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.retrofitrxjava.R;

public class AppUtils {

    public static void loadView(Context context, Fragment fragment) {
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
