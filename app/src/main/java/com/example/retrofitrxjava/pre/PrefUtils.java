package com.example.retrofitrxjava.pre;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.retrofitrxjava.loginV3.model.LoginResponse;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;
import static com.example.retrofitrxjava.utils.Constant.KEY_ACCOUNT;

public class PrefUtils {

    /**
     * Storing API Key in shared preferences to
     * add it in header part of every retrofit request
     */
    public PrefUtils() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("APP_PREF", MODE_PRIVATE);
    }

    public static void storeApiKey(Context context, String apiKey) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("API_KEY", apiKey);
        editor.commit();
    }

    public static String getApiKey(Context context) {
        return getSharedPreferences(context).getString("API_KEY", null);
    }

    public static void saveData(Context context, LoginResponse.Data myObject) {
        SharedPreferences mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(myObject); // myObject - instance of MyObject
        prefsEditor.putString(KEY_ACCOUNT, json);
        prefsEditor.commit();
    }

    public static LoginResponse.Data loadData(Context context) {
        SharedPreferences mPrefs = context.getSharedPreferences("", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(KEY_ACCOUNT, "");
        LoginResponse.Data obj = gson.fromJson(json, LoginResponse.Data.class);
        return obj;
    }
}
