package com.LiYang.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.LiYang.R;
import com.LiYang.db.WeatherDB;
import com.LiYang.service.NotiAndUpdateService;
import com.LiYang.util.HttpCallbackListener;
import com.LiYang.util.HttpUtil;
import com.LiYang.util.UtilityWeather;

import java.net.URLEncoder;

/**
 * Created by A555LF on 2016/7/15.
 */
public class WeatherActivity extends Activity implements View.OnClickListener {

    private WeatherDB mWeatherDB;
    private String mNewName;


    private TextView mWeatherDespText;
    private TextView mTemperature;
    private TextView mCurrentDateText;
    private Button mSwitchCity;
    private Button mRefreshWeather;
    private TextView mFirstDayWeather;
    private TextView mFirstDayWeek;
    private TextView mFirstDayTemp;
    private TextView mSecondDayWeather;
    private TextView mSecondDayWeek;
    private TextView mSecondDayTemp;
    private TextView mThirdDayWeather;
    private TextView mThirdDayTemp;
    private TextView mThirdDayWeek;
    private TextView mFourthDayWeather;
    private TextView mFourthDayWeek;
    private TextView mFourthDayTemp;
    private TextView mFifthDayWeather;
    private TextView mFifthDayWeek;
    private TextView mFifthDayTemp;
    private TextView mSixthDayWeather;
    private TextView mSixthDayWeek;
    private TextView mSixthDayTemp;
    private TextView mSeventhDayWeather;
    private TextView mSeventhDayWeek;
    private TextView mSeventhDayTemp;
    private ImageView mFirstImage;
    private ImageView mSecondImage;
    private ImageView mFirstDayFa;
    private ImageView mFirstDayFb;
    private ImageView mSecondDayFa;
    private ImageView mSecondDayFb;
    private ImageView mThirdDayFa;
    private ImageView mThirdDayFb;
    private ImageView mFourthDayFa;
    private ImageView mFourthDayFb;
    private ImageView mFifthDayFa;
    private ImageView mFifthDayFb;
    private ImageView mSixthDayFa;
    private ImageView mSixthDayFb;
    private ImageView mSeventhDayFa;
    private ImageView mSeventhDayFb;
    private TextView mTime;
    private Button mButton;
    private EditText mEditText;
    private TextView mSun;
    private TextView mWashCar;
    private TextView mWindStrength;
    private TextView mWindDirection;
    private TextView mExercises;
    private TextView mCloth;
    private TextView mHumdity;
    private TextView mTravel;
    private Button mShare;
    private TextView mTodayWeek;
    private TextView mCity;
    private TextView mClothAdvice;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showWeather();
                    break;
                case 0:
                    mWeatherDespText.setText("加载失败，请检查网络设置");
                    break;

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);

        //初始化各控件

        mWeatherDespText = (TextView) findViewById(R.id.weather_tv_weatherDesp);
        mTemperature = (TextView) findViewById(R.id.weather_tv_temperature);
        mCurrentDateText = (TextView) findViewById(R.id.weather_tv_currentDate);
        mSwitchCity = (Button) findViewById(R.id.weather_bu_switchCity);
        mRefreshWeather = (Button) findViewById(R.id.weather_bu_refreshWeather);
        mFirstDayWeek = (TextView) findViewById(R.id.weather_tv_firstDayWeek);
        mFirstDayTemp = (TextView) findViewById(R.id.weather_tv_firstDayTemperature);
        mFirstDayWeather = (TextView) findViewById(R.id.weather_tv_firstDayWeather);
        mSecondDayWeek = (TextView) findViewById(R.id.weather_tv_secondDayWeek);
        mSecondDayTemp = (TextView) findViewById(R.id.weather_secondDayTemperature);
        mSecondDayWeather = (TextView) findViewById(R.id.weather_tv_secondDayWeather);
        mThirdDayWeek = (TextView) findViewById(R.id.weather_tv_thirdDayWeek);
        mThirdDayTemp = (TextView) findViewById(R.id.weather_tv_thirdDayTemperature);
        mThirdDayWeather = (TextView) findViewById(R.id.weather_tv_thirdDayWeather);
        mFourthDayWeek = (TextView) findViewById(R.id.weather_tv_fourthDayWeek);
        mFourthDayWeather = (TextView) findViewById(R.id.weather_tv_fourthDayWeather);
        mFourthDayTemp = (TextView) findViewById(R.id.weather_tv_fourthDayTemperature);
        mFifthDayWeek = (TextView) findViewById(R.id.weather_tv_fifthDayWeek);
        mFifthDayTemp = (TextView) findViewById(R.id.weather_tv_fifthDayTemperature);
        mFifthDayWeather = (TextView) findViewById(R.id.weather_tv_fifthDayWeather);
        mSixthDayWeek = (TextView) findViewById(R.id.weather_tv_sixthDayWeek);
        mSixthDayTemp = (TextView) findViewById(R.id.weather_tv_sixthDayTemperature);
        mSixthDayWeather = (TextView) findViewById(R.id.weather_tv_sixthDayWeather);
        mSeventhDayWeek = (TextView) findViewById(R.id.weather_tv_seventhDayWeek);
        mSeventhDayTemp = (TextView) findViewById(R.id.weather_tv_seventhDayTemperature);
        mSeventhDayWeather = (TextView) findViewById(R.id.weather_tv_seventhDayWeather);
        mWashCar = (TextView) findViewById(R.id.weather_tv_washCar);
        mWindDirection = (TextView) findViewById(R.id.weather_tv_windDirection);
        mWindStrength = (TextView) findViewById(R.id.weather_tv_windStrength);
        mFirstImage = (ImageView) findViewById(R.id.weather_iv_firstImage);
        mSecondImage = (ImageView) findViewById(R.id.weather_iv_secondImage);
        mFirstDayFa = (ImageView) findViewById(R.id.weather_iv_firstDayFa);
        mFirstDayFb = (ImageView) findViewById(R.id.weather_iv_firstDayFb);
        mSecondDayFa = (ImageView) findViewById(R.id.weather_iv_secondDayFa);
        mSecondDayFb = (ImageView) findViewById(R.id.weather_iv_secondDayFb);
        mThirdDayFa = (ImageView) findViewById(R.id.weather_iv_thirdDayFa);
        mThirdDayFb = (ImageView) findViewById(R.id.weather_iv_thirdDayFb);
        mFourthDayFa = (ImageView) findViewById(R.id.weather_iv_fourthDayFa);
        mFourthDayFb = (ImageView) findViewById(R.id.weather_iv_fourthDayFb);
        mFifthDayFa = (ImageView) findViewById(R.id.weather_iv_fifthDayFa);
        mFifthDayFb = (ImageView) findViewById(R.id.weather_iv_fifthDayFb);
        mSixthDayFa = (ImageView) findViewById(R.id.weather_iv_sixthDayFa);
        mSixthDayFb = (ImageView) findViewById(R.id.weather_iv_sixthDayFb);
        mSeventhDayFa = (ImageView) findViewById(R.id.weather_iv_seventhDayFa);
        mSeventhDayFb = (ImageView) findViewById(R.id.weather_iv_seventhDayFb);
        mTime = (TextView) findViewById(R.id.weather_tv_todayTime);
        mButton = (Button) findViewById(R.id.weather_bu_send);
        mEditText = (EditText) findViewById(R.id.weather_et_text);
        mSun = (TextView) findViewById(R.id.weather_tv_sun);
        mExercises = (TextView) findViewById(R.id.weather_tv_exercises);
        mCloth = (TextView) findViewById(R.id.weather_tv_cloth);
        mHumdity = (TextView) findViewById(R.id.weather_tv_humidity);
        mClothAdvice = (TextView) findViewById(R.id.weather_tv_clothadvice);
        mTravel = (TextView) findViewById(R.id.weather_tv_travel);
        mShare = (Button) findViewById(R.id.weather_bu_share);
        mTodayWeek = (TextView) findViewById(R.id.weather_tv_todayWeek);
        mCity = (TextView) findViewById(R.id.weather_tv_city);
        mSwitchCity.setOnClickListener(this);
        mRefreshWeather.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mShare.setOnClickListener(this);

        String districtName = getIntent().getStringExtra("district_name");


        if (!TextUtils.isEmpty(districtName)) { //有县级代号时就去查询天气
            mWeatherDespText.setText("加载中...");
            queryWeather(districtName);


        } else {//没有县级代号时就直接显示本地天气


            showWeather();
        }
    }

    //查询天气
    private void queryWeather(String name) {      //从服务器查询天气信息，
        mNewName = name;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(mNewName, "UTF-8") +
                            "&key=97bd106d65a01a5e6b283518cc7474fa";
                    Log.d("WeatherActivity", "网络申请/n");

                    HttpUtil.httpClientSend(address, new HttpCallbackListener() {
                        @Override
                        public void onFinish(String response) {
                            UtilityWeather.handleWeatherResponse(WeatherActivity.this, response);
                            Log.d("WeatherActivity", "网络申请成功/n");
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private void showWeather() {   //从SharedPreferences里查询相关数据并显示在屏幕上
        SharedPreferences prefs = getSharedPreferences("weatherInformation", MODE_PRIVATE);


        mWeatherDespText.setText(prefs.getString("todayWeather", ""));
        mTemperature.setText(prefs.getString("todayTemperature", ""));
        mCurrentDateText.setText(prefs.getString("date", ""));
        mFirstDayWeek.setText(prefs.getString("1dayWeek", "'"));
        mFirstDayWeather.setText(prefs.getString("1dayWeather", ""));
        mFirstDayTemp.setText(prefs.getString("1dayTemp", ""));
        mSecondDayWeather.setText(prefs.getString("2dayWeather", ""));
        mSecondDayTemp.setText(prefs.getString("2dayTemp", ""));
        mSecondDayWeek.setText(prefs.getString("2dayWeek", ""));
        mThirdDayWeather.setText(prefs.getString("3dayWeather", ""));
        mThirdDayTemp.setText(prefs.getString("3dayTemp", ""));
        mThirdDayWeek.setText(prefs.getString("3dayWeek", ""));
        mFourthDayWeather.setText(prefs.getString("4dayWeather", ""));
        mFourthDayWeek.setText(prefs.getString("4dayWeek", ""));
        mFourthDayTemp.setText(prefs.getString("4dayTemp", ""));
        mFifthDayTemp.setText(prefs.getString("5dayTemp", ""));
        mFifthDayWeather.setText(prefs.getString("5dayWeather", ""));
        mFifthDayWeek.setText(prefs.getString("5dayWeek", ""));
        mSixthDayWeather.setText(prefs.getString("6dayWeather", ""));
        mSixthDayTemp.setText(prefs.getString("6dayTemp", ""));
        mSixthDayWeek.setText(prefs.getString("6dayWeek", ""));
        mSeventhDayWeather.setText(prefs.getString("7dayWeather", ""));
        mSeventhDayTemp.setText(prefs.getString("7dayTemp", ""));
        mSeventhDayWeek.setText(prefs.getString("7dayWeek", ""));
        mSun.setText(prefs.getString("sun", ""));
        mTravel.setText(prefs.getString("travel", ""));
        mHumdity.setText(prefs.getString("humidity", ""));
        mExercises.setText(prefs.getString("exercises", ""));
        mCloth.setText(prefs.getString("cloth", ""));
        mWashCar.setText(prefs.getString("washCar", ""));
        mWindDirection.setText(prefs.getString("windDirection", ""));
        mWindStrength.setText(prefs.getString("windStrength", ""));
        mClothAdvice.setText(prefs.getString("clothAdvice", ""));
        Log.d("WeatherActivity", "这里是时间：/n" + prefs.getString("time", ""));
        mTime.setText(prefs.getString("time", ""));
        mTodayWeek.setText(prefs.getString("todayWeek", ""));
        mCity.setText(prefs.getString("city_name", ""));
        mFirstImage.setImageResource(getResources().getIdentifier(prefs.getString("todayFa", ""), "drawable", getPackageName()));
        mSecondImage.setImageResource(getResources().getIdentifier(prefs.getString("todayFb", ""), "drawable", getPackageName()));
        mFirstDayFa.setImageResource(getResources().getIdentifier(prefs.getString("1dayFa", ""), "drawable", getPackageName()));
        mFirstDayFb.setImageResource(getResources().getIdentifier(prefs.getString("1dayFb", ""), "drawable", getPackageName()));
        mSecondDayFa.setImageResource(getResources().getIdentifier(prefs.getString("2dayFa", ""), "drawable", getPackageName()));
        mSecondDayFb.setImageResource(getResources().getIdentifier(prefs.getString("2dayFb", ""), "drawable", getPackageName()));
        mThirdDayFa.setImageResource(getResources().getIdentifier(prefs.getString("3dayFa", ""), "drawable", getPackageName()));
        mThirdDayFb.setImageResource(getResources().getIdentifier(prefs.getString("3dayFb", ""), "drawable", getPackageName()));
        mFourthDayFa.setImageResource(getResources().getIdentifier(prefs.getString("4dayFa", ""), "drawable", getPackageName()));
        mFourthDayFb.setImageResource(getResources().getIdentifier(prefs.getString("4dayFb", ""), "drawable", getPackageName()));
        mFifthDayFa.setImageResource(getResources().getIdentifier(prefs.getString("5dayFa", ""), "drawable", getPackageName()));
        mFifthDayFb.setImageResource(getResources().getIdentifier(prefs.getString("5dayFb", ""), "drawable", getPackageName()));
        mSixthDayFa.setImageResource(getResources().getIdentifier(prefs.getString("6dayFa", ""), "drawable", getPackageName()));
        mSixthDayFb.setImageResource(getResources().getIdentifier(prefs.getString("6dayFb", ""), "drawable", getPackageName()));
        mSeventhDayFa.setImageResource(getResources().getIdentifier(prefs.getString("7dayFa", ""), "drawable", getPackageName()));
        mSeventhDayFb.setImageResource(getResources().getIdentifier(prefs.getString("7dayFb", ""), "drawable", getPackageName()));


        Intent notificationIntent = new Intent(this, NotiAndUpdateService.class);


        startService(notificationIntent);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weather_bu_switchCity:
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.weather_bu_refreshWeather:
                mWeatherDespText.setText("加载中···");
                SharedPreferences preferences = getSharedPreferences("weatherInformation",MODE_PRIVATE);
                String districtNameA = preferences.getString("city_name", "");
                queryWeather(districtNameA);


                break;
            case R.id.weather_bu_send:
                mWeatherDespText.setText("加载中···");
                String inputText = mEditText.getText().toString();
                mWeatherDB = mWeatherDB.getInstance(WeatherActivity.this);
                if (mWeatherDB.findDistric(inputText)) {
                    showErrorProgressDialog();
                }

                queryWeather(inputText);
                break;

            case R.id.weather_bu_share:  //特色功能，可以通过短信及邮件的方式分享今天的天气预报信息

                showShareDialog();
                break;
            default:
                break;
        }
    }

    public void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherActivity.this);
        builder.setTitle("选择您的分享类型");
        builder.setItems(new String[]{"通过邮件分享", "通过短信分享",}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                switch (which) {
                    case 0:       //选择邮件的分享方式
                        sendMail();
                        break;
                    case 1:       //选择短信的分享方式
                        sendSMS();
                        break;
                    default:
                        break;
                }

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private void sendMail() {
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");
        String subjectStr = "天气预报";
        String emailBody = UtilityWeather.getTodayInfo();//邮件的主要内容
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectStr);
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
        startActivityForResult(Intent.createChooser(email, "请选择邮件发送内容"), 1001);
    }


    private void sendSMS() {
        String smsBody = UtilityWeather.getTodayInfo();//短信的主要内容
        Uri smsToUri = Uri.parse("今日天气预报");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }

    private void showErrorProgressDialog() {
        new AlertDialog.Builder(this)
                .setTitle("错误提示")
                .setMessage("查询不到该城市，请确认后再输入")
                .setCancelable(true)
                .show();
    }


}


