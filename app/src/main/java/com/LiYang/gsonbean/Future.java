package com.LiYang.gsonbean;

/**
 * Created by A555LF on 2016/7/19.
 */
public class Future {
    String temperature;
    String weather;
    WeatherId weather_id;
    String wind;
    String week;
    String date;

    public void setDate(String date) {
        this.date = date;
    }

    public void setWeek(String week) {

        this.week = week;
    }

    public void setWind(String wind) {

        this.wind = wind;
    }

    public void setWeather_id(WeatherId weather_id) {

        this.weather_id = weather_id;
    }

    public void setWeather(String weather) {

        this.weather = weather;
    }

    public void setTemperature(String temperature) {

        this.temperature = temperature;
    }

    public String getDate() {

        return date;
    }

    public String getWeek() {

        return week;
    }

    public String getWind() {

        return wind;
    }

    public WeatherId getWeather_id() {

        return weather_id;
    }

    public String getWeather() {

        return weather;
    }

    public String getTemperature() {

        return temperature;
    }
}
