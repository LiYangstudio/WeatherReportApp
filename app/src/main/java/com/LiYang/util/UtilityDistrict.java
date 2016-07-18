package com.LiYang.util;

import android.util.JsonReader;
import android.util.Log;

import com.LiYang.bean.CityBean;
import com.LiYang.bean.DistrictBean;
import com.LiYang.bean.ProvinceBean;
import com.LiYang.db.WeatherDB;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/18.
 */
public class UtilityDistrict {
    private static WeatherDB mWeatherDB;


    public static boolean handleResponse(WeatherDB weatherDB, String response) {

        mWeatherDB = weatherDB;
        JsonReader reader = new JsonReader(new StringReader(response));
        boolean flag = false;
        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String nodeName = reader.nextName();
                if (nodeName.equals("resultcode")) {
                    if (reader.nextString().equals("200")) {
                        flag = true;
                    }
                } else if (nodeName.equals("result") && flag) {
                    saveAreaToDatabase(reader);
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean saveAreaToDatabase(JsonReader reader) {  //解析包含城市列表的JSON数据

        String provinceName = null;
        String cityName = null;
        String districtName = null;
        List<String> provinceNames = new ArrayList<>();
        List<String> cityNames = new ArrayList<>();
        boolean changedProvince = false;
        boolean changedCity = false;
        int provinceId = 0;
        int cityId = 0;
        int districtId = 0;
        ProvinceBean previousProvince = new ProvinceBean();
        CityBean previousCity = new CityBean();

        try {
            reader.beginArray();
            while (reader.hasNext()) {
                reader.beginObject();
                while (reader.hasNext()) {
                    String nodeName = reader.nextName();
                    if (nodeName.equals("province")) {
                        provinceName = reader.nextString().trim();
                        if (!provinceNames.contains(provinceName)) {
                            provinceNames.add(provinceName);
                            changedProvince = true;
                            provinceId++;
                        }
                    } else if (nodeName.equals("city")) {
                        cityName = reader.nextString().trim();
                        if (!cityNames.contains(cityName)) {
                            cityNames.add(cityName);
                            changedCity = true;
                            cityId++;

                        }
                    } else if (nodeName.equals("district")) {
                        districtName = reader.nextString().trim();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
                Log.d("Utility", "\nprovince_name = " + provinceName +
                        "\ncity_name = " + cityName + "\ndistrict_name = " + districtName);//显示初始化载入城市列表的过程

                if (changedProvince) {
                    ProvinceBean province = new ProvinceBean();
                    province.setId(provinceId);
                    province.setProvinceName(provinceName);
                    previousProvince = province;
                    mWeatherDB.saveProvince(province);
                    changedProvince = false;

                }

                if (changedCity) {

                    CityBean city = new CityBean();
                    city.setId(cityId);
                    city.setCityName(cityName);
                    city.setProvinceId(previousProvince.getId());
                    previousCity = city;
                    mWeatherDB.saveCity(city);
                    changedCity = false;

                }

                DistrictBean district = new DistrictBean();
                districtId++;
                district.setId(districtId);
                district.setDistrictName(districtName);
                district.setCityId(previousCity.getId());
                mWeatherDB.saveDistrict(district);

            }
            reader.endArray();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
