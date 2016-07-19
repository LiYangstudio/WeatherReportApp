package com.LiYang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.LiYang.bean.FutureWeatherBean;
import com.LiYang.gsonbean.Future;
import com.LiYang.gsonbean.Sk;
import com.LiYang.gsonbean.Today;
import com.LiYang.gsonbean.WeatherInformaionUtility;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/19.
 */
public class GsonUtilityWeather {
    private static String mTodayInfo;  //发短信和邮件的文本

    public static String getTodayInfo() {
        return mTodayInfo;
    }

    public static boolean handleWeatherResponse(Context context, String data) {
        List<FutureWeatherBean> futureWeatherList = new ArrayList<>();
        Gson gson = new Gson();
        WeatherInformaionUtility weatherInformaionUtility = gson.fromJson(data, WeatherInformaionUtility.class);
        if (weatherInformaionUtility.getResultcode().equals("200")) {
            Sk sk = weatherInformaionUtility.getResult().getSk();
            List<Future> future = weatherInformaionUtility.getResult().getFuture();
            Today today = weatherInformaionUtility.getResult().getToday();


            String fa = weatherInformaionUtility.getResult().getToday().getWeather_id().getFa();
            String fb = weatherInformaionUtility.getResult().getToday().getWeather_id().getFb();
            String humidity = weatherInformaionUtility.getResult().getSk().getHumidity();
            String windDirection = weatherInformaionUtility.getResult().getSk().getWind_direction();
            String windStrength = weatherInformaionUtility.getResult().getSk().getWind_strength();

            String temperature = weatherInformaionUtility.getResult().getToday().getTemperature();
            String cityName = weatherInformaionUtility.getResult().getToday().getCity();
            String weather = weatherInformaionUtility.getResult().getToday().getWeather();
            String date = weatherInformaionUtility.getResult().getToday().getDate_y();
            String exercises = weatherInformaionUtility.getResult().getToday().getExercise_index();
            String wind = weatherInformaionUtility.getResult().getToday().getWind();
            String cloth = weatherInformaionUtility.getResult().getToday().getDressing_index();
            String travel = weatherInformaionUtility.getResult().getToday().getTravel_index();
            String sun = weatherInformaionUtility.getResult().getToday().getUv_index();
            String todayWeek = weatherInformaionUtility.getResult().getToday().getWeek();
            String feel = weatherInformaionUtility.getResult().getToday().getComfort_index();
            String washCar = weatherInformaionUtility.getResult().getToday().getWash_index();
            String clothAdvice = weatherInformaionUtility.getResult().getToday().getDressing_advice();

            String time = weatherInformaionUtility.getResult().getSk().getTime();
            Log.d("Utility", "刚解析的时间：/n" + time);
            String[] timestring = time.split(":");

            int hour = Integer.parseInt(timestring[0]);
            if (hour <= 18 && hour >= 6) {
                fa = "d" + weatherInformaionUtility.getResult().getToday().getWeather_id().getFa();      //根据小时数给每天的fa和fb前加上n和d，来确定调用白天或黑夜的图片
                fb = "d" + weatherInformaionUtility.getResult().getToday().getWeather_id().getFb();
            } else {
                fa = "n" + weatherInformaionUtility.getResult().getToday().getWeather_id().getFa();
                fb = "n" + weatherInformaionUtility.getResult().getToday().getWeather_id().getFb();

            }
            for (int i = 0; i < future.size(); i++) {
                FutureWeatherBean futureBean = new FutureWeatherBean();
                futureBean.setWeek(future.get(i).getWeek());
                futureBean.setTemperature(future.get(i).getTemperature());
                futureBean.setWeather(future.get(i).getWeather());
                if (hour <= 18 && hour >= 6) {                      //根据小时数给每天的fa和fb前加上n和d，来确定调用白天或黑夜的图片
                    futureBean.setFa("d" + future.get(i).getWeather_id().getFa());
                    futureBean.setFb("d" + future.get(i).getWeather_id().getFb());
                } else {
                    futureBean.setFa("n" + future.get(i).getWeather_id().getFa());
                    futureBean.setFb("n" + future.get(i).getWeather_id().getFb());

                }

                futureWeatherList.add(futureBean);
            }
            mTodayInfo = date + cityName + "天气信息列表:" + "\n  温度：" + temperature + "\n  天气：" + weather + "\n  风力:" + wind + "\n  湿度：" + humidity + "\n穿衣指数：" + cloth + "\n旅游指数：" + travel + "\n紫外线强度：" + sun;

            return saveWeatherInfo(context, cityName, weather, temperature, date, futureWeatherList, time, fa, fb, sun, exercises, humidity, wind, travel, cloth, todayWeek, washCar, windStrength, windDirection, clothAdvice);


        }
        return false;
    }

    private static boolean saveWeatherInfo(Context context, String cityName, String weather,
                                           String temperature, String date, List<FutureWeatherBean> list, String time, String fa, String fb, String sun, String exercises, String humidity, String wind, String travel, String cloth, String todayWeek, String washCar, String windStrength, String windDirection, String clothAdvice) {
        SharedPreferences prefs =context.getSharedPreferences("weatherInformation",context.MODE_PRIVATE);
        SharedPreferences.Editor editor =prefs.edit();
        editor.putBoolean("citySelected", true);
        editor.putString("city_name", cityName);
        editor.putString("todayWeather", weather);
        editor.putString("todayTemperature", temperature);
        editor.putString("date", date);
        editor.putString("time", time);
        editor.putString("todayFa", fa);
        editor.putString("todayFb", fb);
        editor.putString("sun", sun);
        editor.putString("humidity", humidity);
        editor.putString("wind", wind);
        editor.putString("exercises", exercises);
        editor.putString("travel", travel);
        editor.putString("cloth", cloth);
        editor.putString("todayWeek", todayWeek);
        editor.putString("windStrength", windStrength);
        editor.putString("windDirection", windDirection);
        editor.putString("washCar", washCar);
        editor.putString("clothAdvice", clothAdvice);
        for (int i = 0, j = 1; i < list.size(); i++, j++) {          //用循环把未来几天的数据填入SharedPreferences，并给出相应键值

            editor.putString(j + "dayWeather", list.get(i).getWeather());
            editor.putString(j + "dayTemp", list.get(i).getTemperature());
            editor.putString(j + "dayWeek", list.get(i).getWeek());
            editor.putString(j + "dayFa", list.get(i).getFa());
            editor.putString(j + "dayFb", list.get(i).getFb());

        }

        editor.commit();
        return false;
    }


    }

