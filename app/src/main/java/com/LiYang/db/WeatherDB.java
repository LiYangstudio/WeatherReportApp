package com.LiYang.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.LiYang.bean.CityBean;
import com.LiYang.bean.DistrictBean;
import com.LiYang.bean.ProvinceBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/15.
 */
public class WeatherDB {
    private static final String DB_NAME = "weather";
    private static final int VERSION = 1;

    private static WeatherDB mWeatherDB;
    private SQLiteDatabase mDB;


    private WeatherDB(Context context) {
        WeatherOpenHelper dbHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        mDB = dbHelper.getWritableDatabase();
    }


    public synchronized static WeatherDB getInstance(Context context) {
        if (mWeatherDB == null) {
            mWeatherDB = new WeatherDB(context);
        }
        return mWeatherDB;
    }


    public synchronized void saveProvince(ProvinceBean province) {
        ContentValues values = new ContentValues();
        values.put("province_name", province.getProvinceName());
        mDB.insert("Province", null, values);
    }


    public List<ProvinceBean> loadProvinces() {
        List<ProvinceBean> list = new ArrayList<>();
        Cursor cursor = mDB.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                ProvinceBean province = new ProvinceBean();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                list.add(province);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


    public synchronized void saveCity(CityBean city) {
        ContentValues values = new ContentValues();
        values.put("city_name", city.getCityName());
        values.put("province_id", city.getProvinceId());
        mDB.insert("City", null, values);

    }


    public List<CityBean> loadCities(int provinceId) {
        List<CityBean> list = new ArrayList<>();
        Cursor cursor = mDB.query("City", null, "province_id = ?", new String[]{String.valueOf(provinceId)},
                null, null, null);
        if (cursor.moveToFirst()) {
            do {
                CityBean city = new CityBean();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }


    public synchronized void saveDistrict(DistrictBean district) {
        ContentValues values = new ContentValues();
        values.put("district_name", district.getDistrictName());
        values.put("city_id", district.getCityId());
        mDB.insert("District", null, values);

    }


    public List<DistrictBean> loadDistricts(int cityId) {
        List<DistrictBean> lists = new ArrayList<>();
        Cursor cursor = mDB.query("District", null, "city_id = ?", new String[]{String.valueOf(cityId)},

                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                DistrictBean district = new DistrictBean();
                district.setId(cursor.getInt(cursor.getColumnIndex("id")));
                district.setDistrictName(cursor.getString(cursor.getColumnIndex("district_name")));
                district.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                lists.add(district);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return lists;
    }

    public boolean findDistric(String provinceName) {//作为搜索功能中的一个的方法

        boolean judge = false;
        Cursor cursor = mDB.query("District", null, "district_name=?", new String[]{String.valueOf(provinceName)}, null, null, null);

        if (cursor.getCount() == 0) {
            judge = true;


        }

        return judge;


    }
}






