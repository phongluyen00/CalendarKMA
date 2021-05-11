package com.example.retrofitrxjava;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by luyenphong on 10/14/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
public class NetworkUtils {
    public static boolean isConnect(Context context) {
        boolean isCon = false;
        ConnectivityManager cm = null;
        if (context != null){
            cm= (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
        }

        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                isCon = true;
            }
        }
        return isCon;
    }
}
