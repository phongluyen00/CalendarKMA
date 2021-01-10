package com.example.retrofitrxjava.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.common.model.ScheduleModelResponse;
import com.example.retrofitrxjava.custom.MyDynamicCalendar;
import com.example.retrofitrxjava.main.model.ScoreMediumResponse;
import com.example.retrofitrxjava.model.DetailScoreModel;
import com.example.retrofitrxjava.security.AESHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Create by Luyenphong
 * luyenphong00@gmail.com
 */
public class AppUtils {

    static {
        System.loadLibrary("security");
    }

    private static native String getKeyAES();

    public static native String getAPI();

    public static String privateKey = getKeyAES();

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
    public static final String HOURS5= "13,14,15,16";
    public static final String START_HOURS5 = "18:00";
    public static final String END_HOURS5 = "20:30";

    // DO AN TOT NGHIEP SANG
    public static final String DATNS = "1,2,3,4 ";
    public static final String START_DATNS = "7:00";
    public static final String END_DATNS = "10:30";
    // DO AN TOT NGHIEP CHIEU
    public static final String DATNC = "9,10,11,12";
    public static final String START_DATNC = "10:30";
    public static final String END_DATNC = "14:55";


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

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static <T> Object GenericInstance(String decryptText, Class<T> tClass) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        JSONArray jArray;
        Gson gson = new Gson();
        try {
            jArray = new JSONArray(decryptText);
            for (int i = 0; i < jArray.length(); i++) {
                stringArrayList.add(jArray.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gson.fromJson(stringArrayList.get(0), tClass);
    }

    public static  List<ScheduleModelResponse.Data> getListDataSchedule(String decryptText) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ScheduleModelResponse.Data>>(){}.getType();
        List<ScheduleModelResponse.Data> contactList = gson.fromJson(decryptText, type);
        return contactList;
    }

    public static  List<ScoreMediumResponse.Datum> getScoreMediumResponse(String decryptText) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ScoreMediumResponse.Datum>>(){}.getType();
        List<ScoreMediumResponse.Datum> contactList = gson.fromJson(decryptText, type);
        return contactList;
    }

    public static  List<DetailScoreModel.Data> getDetailScoreModel(String decryptText) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<DetailScoreModel.Data>>(){}.getType();
        List<DetailScoreModel.Data> contactList = gson.fromJson(decryptText, type);
        return contactList;
    }

    public static String entryData(String textEncrypt) {
        String encryptText;
        try {
            encryptText = new AESHelper().encrypt(textEncrypt, getKeyAES());
            return encryptText;
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException
                | NoSuchPaddingException | InvalidAlgorithmParameterException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getRequestData(Context context, String input1,String input2){
        return context.getString(R.string.encode_data,input1,input2);
    }

}
