package com.LiYang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LiYang.R;
import com.LiYang.adapter.CityAdapter;
import com.LiYang.bean.CityBean;
import com.LiYang.bean.DistrictBean;
import com.LiYang.bean.ProvinceBean;
import com.LiYang.db.WeatherDB;
import com.LiYang.util.GsonUtilityDistrict;
import com.LiYang.util.UtilTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/15.
 */
public class ChooseAreaActivity extends Activity {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_DISTRICT = 2;

    private ProgressDialog mProgressDialog;
    private TextView mTitleText;
    private ListView mListView;
    private List<String> mLists;

    private WeatherDB mWeatherDB;
    private List<ProvinceBean> mProvinceList;
    private List<CityBean> mCityList;
    private List<DistrictBean> mDistrictList;
    private ProvinceBean mSelectedProvince;
    private CityBean mSelectedCity;
    private int mCurrentLevel;
  private boolean mFromWeatherActivity;
    private CityAdapter mCityAdapter;


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String type = (String) msg.obj;
            switch (msg.what) {
                case 1:
                    closeProgressDialog();
                    if (type.equals("Province")) {
                        queryProvinces();
                    } else if (type.equals("City")) {
                        queryCities();
                    } else if (type.equals("District")) {
                        queryDistricts();
                    }
                    break;
                case 0:
                    closeProgressDialog();
                    Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       int findProvinceId = getIntent().getIntExtra("find_provinceId", -1);
       mFromWeatherActivity = getIntent().getBooleanExtra("from_weather_activity", false);
        SharedPreferences preferences =getSharedPreferences("weatherInformation",MODE_PRIVATE); //已经选择了城市且不是从WeatherActivity跳转过来,才会直接跳转到WeatherActivity


        if (preferences.getBoolean("citySelected", false) && !mFromWeatherActivity && findProvinceId == -1) {
            Intent intent = new Intent(this, WeatherActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        setContentView(R.layout.activity_choosearea);

        mTitleText = (TextView) findViewById(R.id.chooseareaactivity_tv_titletext);
        mListView = (ListView) findViewById(R.id.chooseareaactivity_lv_list);
        mLists = new ArrayList<>();
        mCityAdapter = new CityAdapter(this, R.layout.activity_citylist, mLists);
        mWeatherDB = WeatherDB.getInstance(this);

        mListView.setAdapter(mCityAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if (mCurrentLevel == LEVEL_PROVINCE) {
                    mSelectedProvince = mProvinceList.get(i);
                    queryCities();
                } else if (mCurrentLevel == LEVEL_CITY) {
                    mSelectedCity = mCityList.get(i);
                    queryDistricts();
                } else if (mCurrentLevel == LEVEL_DISTRICT) {

                    String districtName = mDistrictList.get(i).getDistrictName();
                    Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                    intent.putExtra("district_name", districtName);
                    startActivity(intent);
                    finish();
                }
            }
        });
        queryProvinces(); //加载省级数据

    }


    private void queryProvinces() {
        mProvinceList = mWeatherDB.loadProvinces();
        if (mProvinceList.size() > 0) {
            mLists.clear();
            for (ProvinceBean province : mProvinceList) {
                mLists.add(province.getProvinceName());
            }
            mCityAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mTitleText.setText("中国");
            mCurrentLevel = LEVEL_PROVINCE;
        } else {
            queryFromServer("Province");
        }
    }


    private void queryCities() {
        mCityList = mWeatherDB.loadCities(mSelectedProvince.getId());
        if (mCityList.size() > 0) {
            mLists.clear();
            for (CityBean city : mCityList) {
                mLists.add(city.getCityName());
            }
            mCityAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mTitleText.setText(mSelectedProvince.getProvinceName());
            mCurrentLevel = LEVEL_CITY;
        } else {
            queryFromServer("City");
        }
    }


    private void queryDistricts() {
        mDistrictList = mWeatherDB.loadDistricts(mSelectedCity.getId());
        if (mDistrictList.size() > 0) {
            mLists.clear();
            for (DistrictBean district : mDistrictList) {
                mLists.add(district.getDistrictName());
            }
            mCityAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mTitleText.setText(mSelectedCity.getCityName());
            mCurrentLevel = LEVEL_DISTRICT;
        } else {
            queryFromServer("District");
        }
    }


    /** private void queryFromServer(final String type) {
        showProgressDialog();


               VolleyUtil.VolleyUtilSend("http://v.juhe.cn/weather/citys?key=97bd106d65a01a5e6b283518cc7474fa", this, new VolleyCallbackListener() {

                    @Override
                    public void onFinish(String response) {
                        boolean result = UtilityDistrict.handleResponse(WeatherDB.getInstance(getApplicationContext()), response);
                        if (result) {
                            Message message = new Message();
                            message.what = 1;
                            message.obj = type;
                            handler.sendMessage(message);


                        }


                    }

                    @Override
                    public void onError(Exception e) {
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);

                    }
                });
            }
     **/



    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在初始化数据，请稍候···");
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    private void closeProgressDialog() {  //关闭弹框
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void onBackPressed() {    //根据当前的level来判断回到哪个界面
        if (mCurrentLevel == LEVEL_DISTRICT) {
            queryCities();
        } else if (mCurrentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            if (mFromWeatherActivity) {
                Intent intent = new Intent(this, WeatherActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
    private void queryFromServer(final String type){
        showProgressDialog();
        Log.d("CHOOSE","在方格栏之后");
        UtilTask utilTask=new UtilTask("http://v.juhe.cn/weather/citys?key=97bd106d65a01a5e6b283518cc7474fa", this);
        Log.d("CHOOSE","在网址之后");
        utilTask.execute();
        utilTask.setTaskHelper(new UtilTask.TaskHelper() {
            @Override
            public void onSuccess(String response) {
                boolean result = GsonUtilityDistrict.handleResponse(WeatherDB.getInstance(getApplicationContext()), response);
                if (result) {
                    Message message = new Message();
                    message.what = 1;
                    message.obj = type;
                    handler.sendMessage(message);


                }
            }

            @Override
            public void onFail(Exception e) {
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        });


    }

}



