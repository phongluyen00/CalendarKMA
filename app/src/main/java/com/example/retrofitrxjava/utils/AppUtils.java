package com.example.retrofitrxjava.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.retrofitrxjava.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static void loadView(Context context, Fragment fragment) {
        FragmentActivity fragmentActivity = (FragmentActivity) context;
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String formatDate(String input) {
        SimpleDateFormat fromUser = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            input = myFormat.format(fromUser.parse(input));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return input;
    }
}
