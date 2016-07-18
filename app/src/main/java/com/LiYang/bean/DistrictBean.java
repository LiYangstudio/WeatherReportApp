package com.LiYang.bean;

/**
 * Created by A555LF on 2016/7/18.
 */
public class DistrictBean {
    private int id;
    private String districtName;
    private int cityId; //该地区对应的市级区号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

}
