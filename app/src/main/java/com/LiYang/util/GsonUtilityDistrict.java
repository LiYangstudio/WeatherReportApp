package com.LiYang.util;

import android.util.Log;

import com.LiYang.bean.CityBean;
import com.LiYang.bean.DistrictBean;
import com.LiYang.bean.ProvinceBean;
import com.LiYang.db.WeatherDB;
import com.LiYang.gsonbean.CityListUtility;
import com.LiYang.gsonbean.Result;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/19.
 */
public class GsonUtilityDistrict {
    private static WeatherDB mWeatherDB;

    public static boolean handleResponse(WeatherDB weatherDB, String response) {
        mWeatherDB = weatherDB;
        boolean flag = false;
        Gson gson = new Gson();
        CityListUtility cityListUtility = gson.fromJson(response, CityListUtility.class);
        if (cityListUtility.getResultcode().equals("200")) {
            flag = true;
            saveAreaToDatabase(response,cityListUtility);



        }
        return flag;
    }

    private static boolean saveAreaToDatabase(String response, CityListUtility cityListUtility) {
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
        List<Result> result=cityListUtility.getResult();


        for (int i = 0; i < result.size(); i++) {

            provinceName = result.get(i).getProvince();
            cityName = result.get(i).getCity();
            districtName = result.get(i).getDistrict();
            Log.d("Utility", "\nprovince_name = " + provinceName +
                    "\ncity_name = " + cityName + "\ndistrict_name = " + districtName);
            if (!provinceNames.contains(provinceName)) {
                provinceNames.add(provinceName);
                changedProvince = true;
                provinceId++;
            }

            if (!cityNames.contains(cityName)) {
                cityNames.add(cityName);
                changedCity = true;
                cityId++;

            }


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

        return true;

    }


}
