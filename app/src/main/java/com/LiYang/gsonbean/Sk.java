package com.LiYang.gsonbean;

/**
 * Created by A555LF on 2016/7/19.
 */
public class Sk {//基于Gson解析对聚合数据的Json数据设置的一些类
    String temp;
    String wind_direction;
    String wind_strength;
    String humidity;
    String time;

    public void setTime(String time) {
        this.time = time;
    }

    public void setHumidity(String humidity) {

        this.humidity = humidity;
    }

    public void setWind_strength(String wind_strength) {

        this.wind_strength = wind_strength;
    }

    public void setWind_direction(String wind_direction) {

        this.wind_direction = wind_direction;
    }

    public void setTemp(String temp) {

        this.temp = temp;
    }

    public String getTime() {

        return time;
    }

    public String getHumidity() {

        return humidity;
    }

    public String getWind_strength() {

        return wind_strength;
    }

    public String getWind_direction() {

        return wind_direction;
    }

    public String getTemp() {

        return temp;
    }
}
