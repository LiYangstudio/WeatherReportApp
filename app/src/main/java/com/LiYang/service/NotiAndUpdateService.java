package com.LiYang.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.LiYang.R;
import com.LiYang.activity.WeatherActivity;
import com.LiYang.util.HttpCallbackListener;
import com.LiYang.util.HttpUtil;
import com.LiYang.util.UtilityWeather;

import java.net.URLEncoder;

/**
 * Created by A555LF on 2016/7/18.
 */
public class NotiAndUpdateService extends Service {  //一个Service完成后台更新服务和定时更新
    private String mDistrictName;

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences prefs =getSharedPreferences("weatherInformation",MODE_PRIVATE);
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle(prefs.getString("city_name", ""));
        mBuilder.setContentText(prefs.getString("todayWeather", ""));


        Log.d("MyService", "验证城市/n" + prefs.getString("city_name", "") + "验证气候/n" + prefs.getString("todayWeather", ""));
        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);


        Notification noti = mBuilder.build();

        startForeground(1, noti);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                updateWeather();
            }
        }).start();
        SharedPreferences prefs =getSharedPreferences("weatherInformation",MODE_PRIVATE);
        mBuilder.setContentTitle(prefs.getString("city_name", ""));
        mBuilder.setContentText(prefs.getString("todayWeather", ""));


        Notification noti = mBuilder.build();


        startForeground(1, noti);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int sixHour = 6 * 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + sixHour;
        Intent alarmIntent = new Intent(this, AlarmUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void updateWeather() {
        SharedPreferences prefs =getSharedPreferences("weatherInformation",MODE_PRIVATE);
        mDistrictName = prefs.getString("city_name", "");


        try {
            String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(mDistrictName, "UTF-8") +
                    "&key=97bd106d65a01a5e6b283518cc7474fa";
            HttpUtil.httpClientSend(address, new HttpCallbackListener() {
                @Override
                public void onFinish(String respone) {
                    UtilityWeather.handleWeatherResponse(NotiAndUpdateService.this, respone);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


