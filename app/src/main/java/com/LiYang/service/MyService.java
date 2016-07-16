package com.LiYang.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.LiYang.R;
import com.LiYang.activity.WeatherActivity;
import com.LiYang.util.HttpCallbackListener;
import com.LiYang.util.HttpUtil;
import com.LiYang.util.Utility;

import java.net.URLEncoder;

/**
 * Created by A555LF on 2016/7/15.
 */
public class MyService extends Service {
    private String districtName;

    NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
    private String saveDistrictName;


    @Override
    public IBinder onBind(Intent intent){

        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setContentTitle(prefs.getString("city_name",""));
        builder.setContentText(prefs.getString("todayWeather",""));


        Log.d("MyService","验证城市/n"+prefs.getString("city_name","")+"验证气候/n"+prefs.getString("todayWeather",""));
        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        Notification noti=builder.build();

        startForeground(1,noti);



    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                updateWeather();
            }
        }).start();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        builder.setContentTitle(prefs.getString("city_name",""));
        builder.setContentText(prefs.getString("todayWeather",""));




        Notification noti=builder.build();


        startForeground(1,noti);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int sixHour =6*60*60*1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + sixHour;
        Intent alarmIntent = new Intent(this, AlarmUpdateReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        districtName = prefs.getString("city_name", "");


        try {
            String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(districtName, "UTF-8") +
                    "&key=97bd106d65a01a5e6b283518cc7474fa";
            HttpUtil.httpClientSend(address, new HttpCallbackListener() {
                @Override
                public void onFinish(String respone) {
                    Utility.handleWeatherResponse(MyService.this, respone);
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

