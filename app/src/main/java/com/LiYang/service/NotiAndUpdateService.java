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

import com.LiYang.R;
import com.LiYang.activity.WeatherActivity;
import com.LiYang.util.GsonUtilityWeather;
import com.LiYang.util.UtilTask;

import java.net.URLEncoder;

/**
 * Created by A555LF on 2016/7/18.
 */
public class NotiAndUpdateService extends Service {  //一个Service完成后台更新服务和定时更新

    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);


    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences prefs = getSharedPreferences("1cityWeatherInformation", MODE_PRIVATE);//默认通知栏显示已选中城市列表第一个城市的信息
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setSmallIcon(R.drawable.ic_notification);
        mBuilder.setContentTitle(prefs.getString("city_name", ""));
        mBuilder.setContentText(prefs.getString("todayWeather", ""));



        Intent intent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);


        Notification noti = mBuilder.build();

        startForeground(1, noti);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Notification noti = mBuilder.build();

        SharedPreferences prefs = getSharedPreferences("1cityWeatherInformation", MODE_PRIVATE);//保证通知栏信息与手机界面同步
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle(prefs.getString("city_name", ""));
        mBuilder.setContentText(prefs.getString("todayWeather", ""));


        updateWeather(); //6小时更新，会将当前所有在已选中的城市列表中的城市都更新


        startForeground(1, noti);

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int sixHour = 6 * 60 * 60 * 1000;

        long triggerAtTime = SystemClock.elapsedRealtime() + sixHour;  //定时服务
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
        for (int i = 1; i < 5; i++) {
            SharedPreferences share = getSharedPreferences("selectedCity", MODE_PRIVATE);
            String cityname = share.getString(i + "city", "");

            if (!cityname.equals("")) {
                SharedPreferences prefs = getSharedPreferences(i + "city", MODE_PRIVATE);


                try {
                    String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(cityname, "UTF-8") +
                            "&key=fcfd0e92c41ad993b93b49ba0f840aff";
                    UtilTask utilTask = new UtilTask(address, this, i);
                    utilTask.execute();
                    utilTask.setTaskHelper(new UtilTask.TaskHelper() {
                        @Override
                        public void onSuccess(String response, int g) {
                            GsonUtilityWeather.handleWeatherResponse(NotiAndUpdateService.this, response, g);
                        }

                        @Override
                        public void onFail(Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}






