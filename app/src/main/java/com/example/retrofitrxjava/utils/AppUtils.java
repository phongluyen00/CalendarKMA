package com.example.retrofitrxjava.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.custom.MyDynamicCalendar;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
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

    // check object null or empty
    public static boolean isNullOrEmpty(Object input) {
        if (input == null) {
            return true;
        }
        if (input instanceof String) {
            return input.toString().trim().isEmpty();
        }
        if (input instanceof EditText) {
            return ((EditText) input).getText().toString().trim().isEmpty();
        }
        if (input instanceof List) {
            return ((List) input).isEmpty();
        }

        if (input instanceof HashMap) {
            return ((HashMap) input).isEmpty();
        }

        return false;
    }

    public static <T> void HandlerRXJava(Observable<T> observable, Observer<T> observer) {
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(observer);
    }

    // HOURS 1:
    public static final String HOURS1 = "1,2,3";
    public static final String START_HOURS1 = "7:00";
    public static final String END_HOURS1 = "9:35";
    // HOURS 2:
    public static final String HOURS2 = "4,5,6";
    public static final String START_HOURS2 = "9:35";
    public static final String END_HOURS2 = "11:30";
    // HOURS 3:
    public static final String HOURS3 = "7,8,9";
    public static final String START_HOURS3 = "12:00";
    public static final String END_HOURS3 = "14:55";
    // HOURS 4:
    public static final String HOURS4 = "10,11,12";
    public static final String START_HOURS4 = "15:00";
    public static final String END_HOURS4 = "17:00";
    // HOURS 5:
    public static final String HOURS5 = "13,14,15,16";
    public static final String START_HOURS5 = "18:00";
    public static final String END_HOURS5 = "20:30";
    // HOURS 6: DO AN TOT NGHIEP SANG
    public static final String DO_AN_TN_S = "1,2,3,4";
    public static final String START_DA_TN = "7:00";
    public static final String END_DN_TN = "10:45";

    // HOURS 7: DO AN TOT NGHIEP CHIEU
    public static final String DO_AN_C = "9,10,11,12";
    public static final String START_DA_C = "13:45";
    public static final String END_DN_C = "17:00";

    public static void putData(MyDynamicCalendar myCalendar, String date, String startTime, String endTime, String name) {
        myCalendar.setWeekDayLayoutTextColor(R.color.red);
        myCalendar.setCurrentDateBackgroundColor(R.color.black);
        myCalendar.addEvent(date, startTime, endTime, name);
        myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
        myCalendar.isSundayOff(true, "#ffffff", "#ff0000");
    }

    public static void putDataMyCalendars(com.desai.vatsal.mydynamiccalendar.MyDynamicCalendar myCalendar, String date, String startTime, String endTime, String name) {
        myCalendar.setWeekDayLayoutTextColor(R.color.red);
        myCalendar.setCurrentDateBackgroundColor(R.color.black);
        myCalendar.addEvent(date, startTime, endTime, name);
        myCalendar.isSaturdayOff(true, "#ffffff", "#ff0000");
        myCalendar.isSundayOff(true, "#ffffff", "#ff0000");
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context context, Uri uri) {
        String path = "";
        if (context.getContentResolver() != null) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

}
