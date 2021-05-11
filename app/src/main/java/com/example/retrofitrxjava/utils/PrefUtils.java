package com.example.retrofitrxjava.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.retrofitrxjava.loginV3.model.DataResponse;
import com.google.gson.Gson;

import static com.example.retrofitrxjava.common.view.CommonFragment.SHARED_PREFERENCE_NAME;
import static com.example.retrofitrxjava.utils.Constant.IS_FACE_ID;
import static com.example.retrofitrxjava.utils.Constant.KEY_ACCOUNT;

public class PrefUtils {

    /**
     * Storing API Key in shared preferences to
     * add it in header part of every retrofit request
     */
    public PrefUtils() {
    }

    public static void cacheData(Context context, DataResponse response) {
        SharedPreferences mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(response); // myObject - instance of MyObject
        prefsEditor.putString(KEY_ACCOUNT, json);
        prefsEditor.apply();
    }

    public static DataResponse loadCacheData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(KEY_ACCOUNT, "");
        DataResponse obj = gson.fromJson(json, DataResponse.class);
        return obj;
    }

    public static boolean getSetting(Context context) {
        SharedPreferences sharedPreferences  = context.
                getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_FACE_ID, false);
    }

}
