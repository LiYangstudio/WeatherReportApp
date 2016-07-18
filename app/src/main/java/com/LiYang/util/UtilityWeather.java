package com.LiYang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.LiYang.bean.FutureWeatherBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/18.
 */
public class UtilityWeather {


    private static String mTodayInfo;  //发短信和邮件的文本

    public static String getTodayInfo() {
        return mTodayInfo;
    }




    public static boolean handleWeatherResponse(Context context, String data) {
        List<FutureWeatherBean> futureWeatherList = new ArrayList<>();
        try {
            Log.d("Utility","进入解析/n");
            JSONObject response = new JSONObject(data);
            String resultCode = response.getString("resultcode");
            Log.d("Utility","code数字为"+resultCode);

            if (resultCode.equals("200")) {
                Log.d("Utility","code通过/n");
                JSONObject result = response.getJSONObject("result");

                JSONObject object = result.getJSONObject("sk"); //解析时间，读出时间的字符串，并对时间进行拆解，读取：号前面的小时数
                String humidity = object.getString("humidity");
                String windDirection = object.getString("wind_direction");
                String windStrength = object.getString("wind_strength");

                String time = object.getString("time");
                Log.d("Utility","刚解析的时间：/n"+time);
                String[] timestring = time.split(":");
                int hour = Integer.parseInt(timestring[0]);

                JSONObject today = result.getJSONObject("today");
                String temperature = today.getString("temperature");
                String cityName = today.getString("city");
                String weather = today.getString("weather");
                String date = today.getString("date_y");
                String exercises = today.getString("exercise_index");
                String wind = today.getString("wind");
                String cloth = today.getString("dressing_index");
                String travel = today.getString("travel_index");
                String sun = today.getString("uv_index");
                String todayWeek = today.getString("week");
                String feel = today.getString("comfort_index");
                String washCar = today.getString("wash_index");
                String clothAdvice = today.getString("dressing_advice");
                Log.d("Utility","数据展示\n数据已读取成功"+todayWeek+"\n"+washCar+"\n"+wind+"\n"+date);

                String fa = null;
                String fb = null;

                JSONObject wid = today.getJSONObject("weather_id");
                if (hour <= 18 && hour >= 6) {
                    fa = "d" + wid.getString("fa");      //根据小时数给每天的fa和fb前加上n和d，来确定调用白天或黑夜的图片
                    fb = "d" + wid.getString("fb");
                } else {
                    fa = "n" + wid.getString("fa");
                    fb = "n" + wid.getString("fb");

                }


                JSONArray futureArray = result.getJSONArray("future");
                for (int i = 0; i < futureArray.length(); i++) {

                    JSONObject futureObject = futureArray.getJSONObject(i);
                    FutureWeatherBean futureBean = new FutureWeatherBean();
                    futureBean.setWeek(futureObject.getString("week"));
                    futureBean.setTemperature(futureObject.getString("temperature"));
                    futureBean.setWeather(futureObject.getString("weather"));
                    JSONObject weatherid = futureObject.getJSONObject("weather_id");
                    if (hour <= 18 && hour >= 6) {                      //根据小时数给每天的fa和fb前加上n和d，来确定调用白天或黑夜的图片
                        futureBean.setFa("d" + weatherid.getString("fa"));
                        futureBean.setFb("d" + weatherid.getString("fb"));
                    } else {
                        futureBean.setFa("n" + weatherid.getString("fa"));
                        futureBean.setFb("n" + weatherid.getString("fb"));

                    }

                    futureWeatherList.add(futureBean);
                }
                mTodayInfo = date + cityName + "天气信息列表:" + "\n  温度：" + temperature + "\n  天气：" + weather + "\n  风力:" + wind + "\n  湿度：" + humidity + "\n穿衣指数：" + cloth + "\n旅游指数：" + travel + "\n紫外线强度：" + sun;


                return  saveWeatherInfo(context, cityName, weather, temperature, date, futureWeatherList, time, fa, fb, sun, exercises, humidity, wind, travel, cloth, todayWeek, washCar, windStrength, windDirection, clothAdvice);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Utility","解析失败/n");
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
