package com.LiYang.gsonbean;

/**
 * Created by A555LF on 2016/7/19.
 */
public class Result {//基于Gson解析对聚合数据的Json数据设置的一些类
    int id;
    String  province;
    String city;
    String district;

    public String getDistrict() {
        return district;
    }

    public String getCity() {

        return city;
    }

    public String getProvince() {

        return province;
    }

    public int getId() {

        return id;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCity(String city) {

        this.city = city;
    }

    public void setProvince(String province) {

        this.province = province;
    }

    public void setId(int id) {

        this.id = id;
    }

}
