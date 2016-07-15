package com.LiYang.bean;

/**
 * Created by A555LF on 2016/7/15.
 */
public class City {
    private int id;
    private String cityName;
    private int provinceId; //该地区对应的省序号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}




