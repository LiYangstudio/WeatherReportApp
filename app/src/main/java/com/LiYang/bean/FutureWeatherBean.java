package com.LiYang.bean;

/**
 * Created by A555LF on 2016/7/18.
 */
public class FutureWeatherBean {
    String temperature;
    String week;
    String weather;
    String fa;   //fa和fb为要读取要显示的天气图标的代号
    String fb;

    public void setFb(String fb) {
        this.fb = fb;
    }

    public void setFa(String fa) {

        this.fa = fa;
    }

    public String getFb() {

        return fb;
    }

    public String getFa() {

        return fa;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeek() {
        return week;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }


    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }


    public String getTemperature() {

        return temperature;
    }


}
