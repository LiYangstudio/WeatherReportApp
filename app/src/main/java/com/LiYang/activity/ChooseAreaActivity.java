package com.LiYang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LiYang.R;
import com.LiYang.adapter.CityAdapter;
import com.LiYang.bean.CityBean;
import com.LiYang.bean.DistrictBean;
import com.LiYang.bean.ProvinceBean;
import com.LiYang.db.WeatherDB;
import com.LiYang.service.NotiAndUpdateService;
import com.LiYang.util.GsonUtilityDistrict;
import com.LiYang.util.UtilTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/15.
 */
public class ChooseAreaActivity extends Activity implements View.OnClickListener{

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_DISTRICT = 2;

    private ProgressDialog mProgressDialog;
    private TextView mTitleText;
    private ListView mListView;
    private ListView mSelectedCityListView;
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
    private CityAdapter mSelectedCityAdapter;
    private int mNumberOfCity=1;//这个标志用于标识该选中城市在已选中城市列表的位置，从1开始
    private List<String> mSelectedCityList;
    private Button mClearButton;//清除常用城市的按钮

    private Button mQueryName;

    private String mSelectedName;

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
        setContentView(R.layout.activity_newchoosearea);

        mTitleText = (TextView) findViewById(R.id.chooseareaactivity_tv_titletext);
        mListView = (ListView) findViewById(R.id.chooseareaactivity_lv_list);
        mSelectedCityListView=(ListView)findViewById(R.id.chooseareaactivity_lv_listview);
        mClearButton=(Button) findViewById(R.id.chooseareaactivity_bu_clear);


        mQueryName=(Button)findViewById(R.id.chooseareaactivity_bu_query) ;
        mQueryName.setOnClickListener(this);
        mClearButton.setOnClickListener(this);


        mLists = new ArrayList<>();
        mSelectedCityList=new ArrayList<>();


       SharedPreferences prefs=getSharedPreferences("selectedCity",MODE_PRIVATE);//由于每一次Activity跳转时，
                                                                                // 容器里储存的称市列表会清空，
                                                                                 // 所以每次跳转活动把之前储存在SharePreference里的信息读到容器
        for(int g=1;g<5;g++){    //储存容器里保存的已选择城市，key为g+city，使用数字开头为了方便循环。
            String name=prefs.getString(g+"city","");
            if(!(name.equals(""))){
                mSelectedCityList.add(name);
                mNumberOfCity++;

            }
        }


        mCityAdapter = new CityAdapter(this, R.layout.activity_citylist, mLists);//全国城市列表的适配器
        mSelectedCityAdapter=new CityAdapter(this,R.layout.activity_citylist,mSelectedCityList);
        mWeatherDB = WeatherDB.getInstance(this);
        mSelectedCityListView.setAdapter(mSelectedCityAdapter);
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

                    mSelectedName = mDistrictList.get(i).getDistrictName();
                    if(mNumberOfCity<5) {//限制选择常用城市最多4个，该数从1开始计数


                        SharedPreferences prefs = getSharedPreferences("selectedCity", MODE_PRIVATE);//定义一个Selected的表用于储存已经被选为常用城市的列表
                        SharedPreferences.Editor editor = prefs.edit();

                        editor.putString(mNumberOfCity + "city", mSelectedName);

                        editor.commit();
                        mSelectedCityList.add(mSelectedName);

                        mSelectedCityAdapter.notifyDataSetChanged();

                        mListView.setAdapter(mCityAdapter);


                        mNumberOfCity++;


                        }




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

        UtilTask utilTask=new UtilTask("http://v.juhe.cn/weather/citys?key=fcfd0e92c41ad993b93b49ba0f840aff", this,0);

        utilTask.execute();
        utilTask.setTaskHelper(new UtilTask.TaskHelper() {
            @Override
            public void onSuccess(String response,int h) {
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
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.chooseareaactivity_bu_query:

               Intent notificationIntent = new Intent(this, NotiAndUpdateService.class);

                startService(notificationIntent);//按下查询城市的同时开始后台服务。

                Intent intent = new Intent(ChooseAreaActivity.this, WeatherActivity.class);
                startActivity(intent);

                finish();
                break;
            case R.id.chooseareaactivity_bu_clear://清除常用城市列表的按键，按下一次常用列表按从后往前的顺序删除一个城市，同时把该城市从SharePreference从去除
                if(mNumberOfCity>=2) {
                    SharedPreferences prefs = getSharedPreferences("selectedCity", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString((mNumberOfCity - 1) + "city", "");
                    editor.commit();

                    mSelectedCityList.remove(mNumberOfCity - 2);
                    mNumberOfCity = mNumberOfCity - 1;
                    mSelectedCityAdapter.notifyDataSetChanged();
                }
                break;



        }

    }

}



