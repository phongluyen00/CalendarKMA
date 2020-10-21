package com.example.retrofitrxjava.notification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.retrofitrxjava.NetworkUtils;
import com.example.retrofitrxjava.R;
import com.example.retrofitrxjava.main.MainActivity;
import com.example.retrofitrxjava.notification.event.EventUpdateNotification;
import com.example.retrofitrxjava.retrofit.RetrofitClient;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import retrofit2.Retrofit;

/**
 * Created by luyenphong on 10/16/2020.
 * 0358844343
 * luyenphong00@gmail.com
 */
public class NotificationBackground extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    public static final String EXTRA_NOTIFICATION = "extra_notification";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @SuppressLint("CheckResult")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("")
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setContentText("")
                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        startForeground(1, notification);
        Handler handler = new Handler();
        final Runnable sendData = new Runnable() {
            public void run() {
                if (!NetworkUtils.isConnect(getApplicationContext())) {
                    return;
                }
                AndroidNetworking.post("https://mockapi.superoffice.vn/api/3pi69i/notification")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("aaa", response.toString());
                                Gson gson = new Gson();
                                com.example.retrofitrxjava.main.model.Notification obj = gson.fromJson(response.toString(), com.example.retrofitrxjava.main.model.Notification.class);
                                Log.d("aaaaa", obj.getData().size() + "");
                                // do anything with response
                                if (obj != null && obj.getData() != null && obj.getData().size() > 0) {
                                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
                                    notificationIntent.putExtra(EXTRA_NOTIFICATION,true);
                                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                            0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                    int count = obj.getData().size();
                                    Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                                            .setPriority(Notification.PRIORITY_MAX)
                                            .setContentTitle("Bạn có " + count +" thông báo mới !")
                                            .setSmallIcon(R.drawable.ic_baseline_notifications)
                                            .setContentText(obj.getData().get(count-1).getTitle())
                                            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                                            .setSound(Uri.parse("android.resource://"
                                                    + getApplicationContext().getPackageName() + "/" + R.raw.notification))
                                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                            .setContentIntent(pendingIntent)
                                            .setAutoCancel(true)
                                            .build();
                                    startForeground(1, notification);
                                    EventBus.getDefault().post(new EventUpdateNotification(obj.getData()));
                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });

                handler.postDelayed(this, 600000);
                Log.d("Call Api Notification", "lyenphong");

            }
        };

        handler.postDelayed(sendData, 10000);
        //do heavy work on a background thread
//        stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
