package com.example.retrofitrxjava.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.retrofitrxjava.retrofit.RetrofitClient;
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
        Retrofit retrofit = RetrofitClient.getInstance();
        createNotificationChannel();
//        myAPI.getThongBao(userModel.getToken()).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(response -> response.getErrorCode().equals(SUCCESS))
//                .subscribe(response -> {
//                            retrieveSuccess(response.getData());
//                            Log.d("statrt service", response.getData().size() + "");
//                        },
//                        throwable ->
//                                Log.d("statrt service", "khong co du lieu" + ""));
//        Handler handler = new Handler();
//        final Runnable sendData = new Runnable() {
//            public void run() {
//                myAPI.getThongBao(userModel.getToken()).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .filter(response -> response.getErrorCode().equals(SUCCESS))
//                        .subscribe(response -> {
//                                    retrieveSuccess(response.getData());
//                                    Log.d("statrt service", response.getData().size() + "");
//                                },
//                                throwable ->
//                                        Log.d("statrt service", "khong co du lieu" + ""));
//                handler.postDelayed(this, 600000);
//                Log.d("Call Api Notification", "lyenphong");
//            }
//        };

//        handler.postDelayed(sendData, 10000);
        //do heavy work on a background thread
//        stopSelf();

        return START_NOT_STICKY;
    }

//    private void retrieveSuccess(List<com.example.retrofitrxjava.main.model.Notification.Data> data) {
//        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
//        notificationIntent.putExtra(EXTRA_NOTIFICATION, true);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
//                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        int count = data.size();
////        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
////                .setPriority(Notification.PRIORITY_MAX)
////                .setContentTitle("Bạn có " + count + " thông báo mới !")
////                .setSmallIcon(R.drawable.ic_baseline_notifications)
////                .setContentText(data.get(count - 1).getTieuDe())
////                .setSound(Uri.parse("android.resource://"
////                        + getApplicationContext().getPackageName() + "/" + R.raw.notification))
////                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
////                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
////                .setContentIntent(pendingIntent)
////                .setAutoCancel(true)
////                .build();
////        startForeground(1, notification);
////        EventBus.getDefault().post(new EventUpdateNotification(data));
//    }

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
