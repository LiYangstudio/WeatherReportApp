package com.LiYang.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.LiYang.R;
import com.LiYang.db.WeatherDB;
import com.LiYang.util.GsonUtilityWeather;
import com.LiYang.util.UtilTask;

import java.net.URLEncoder;

/**
 * Created by A555LF on 2016/7/22.
 */
public class WeatherFragment extends LazyFragment {  //因为常用城市列表限制为最多4个，所以会将这4个碎片实例所需的城市信息储存在四个
    private ViewGroup mLayout;                       //SharePerence列表中，表名为“g+cityWeatherInformation”

    private TextView mWeatherDespText;
    private TextView mTemperature;
    private TextView mCurrentDateText;

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
    private Button mSearchButton;
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

    private WeatherDB mWeatherDB;


    //Fragment的标志
    private int mId;
    private String cityName;
    private Handler handler = new Handler() {
        public synchronized void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    showWeather();
                    break;
                case 0:
                    break;


            }
        }
    };

    //获取实例
    public static WeatherFragment newInstance(String name, int id) {
        WeatherFragment fragment = new WeatherFragment();

        fragment.setmId(id);
        fragment.setCityName(name);
        return fragment;
    }




    @Override
    protected void lazyLoad() {
        mLayout = getLayout();

        queryWeather(cityName, mId);


    }


    private synchronized void queryWeather(String name, int i) {




        try {
            String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(name, "UTF-8") +
                    "&key=fcfd0e92c41ad993b93b49ba0f840aff";


            UtilTask utilTask = new UtilTask(address, getContext(), i);
            utilTask.execute();
            utilTask.setTaskHelper(new UtilTask.TaskHelper() {
                @Override
                public synchronized void onSuccess(String response, int i) {

                    GsonUtilityWeather.handleWeatherResponse(getContext(), response, i);

                    Message message = new Message();

                    message.what = 1;
                    handler.sendMessage(message);
                }

                @Override
                public void onFail(Exception e) {
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public void setCityName(String name) {
        this.cityName = name;
    }

    public void showWeather() {

        mLayout.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_weather, null);

        mFirstDayWeek = (TextView) view.findViewById(R.id.weather_tv_firstDayWeek);
        mFirstDayTemp = (TextView) view.findViewById(R.id.weather_tv_firstDayTemperature);
        mFirstDayWeather = (TextView) view.findViewById(R.id.weather_tv_firstDayWeather);
        mSecondDayWeek = (TextView) view.findViewById(R.id.weather_tv_secondDayWeek);
        mSecondDayTemp = (TextView) view.findViewById(R.id.weather_secondDayTemperature);
        mSecondDayWeather = (TextView) view.findViewById(R.id.weather_tv_secondDayWeather);
        mThirdDayWeek = (TextView) view.findViewById(R.id.weather_tv_thirdDayWeek);
        mThirdDayTemp = (TextView) view.findViewById(R.id.weather_tv_thirdDayTemperature);
        mThirdDayWeather = (TextView) view.findViewById(R.id.weather_tv_thirdDayWeather);
        mFourthDayWeek = (TextView) view.findViewById(R.id.weather_tv_fourthDayWeek);
        mFourthDayWeather = (TextView) view.findViewById(R.id.weather_tv_fourthDayWeather);
        mFourthDayTemp = (TextView) view.findViewById(R.id.weather_tv_fourthDayTemperature);
        mFifthDayWeek = (TextView) view.findViewById(R.id.weather_tv_fifthDayWeek);
        mFifthDayTemp = (TextView) view.findViewById(R.id.weather_tv_fifthDayTemperature);
        mFifthDayWeather = (TextView) view.findViewById(R.id.weather_tv_fifthDayWeather);
        mSixthDayWeek = (TextView) view.findViewById(R.id.weather_tv_sixthDayWeek);
        mSixthDayTemp = (TextView) view.findViewById(R.id.weather_tv_sixthDayTemperature);
        mSixthDayWeather = (TextView) view.findViewById(R.id.weather_tv_sixthDayWeather);
        mSeventhDayWeek = (TextView) view.findViewById(R.id.weather_tv_seventhDayWeek);
        mSeventhDayTemp = (TextView) view.findViewById(R.id.weather_tv_seventhDayTemperature);
        mSeventhDayWeather = (TextView) view.findViewById(R.id.weather_tv_seventhDayWeather);
        mWashCar = (TextView) view.findViewById(R.id.weather_tv_washCar);
        mWindDirection = (TextView) view.findViewById(R.id.weather_tv_windDirection);
        mWindStrength = (TextView) view.findViewById(R.id.weather_tv_windStrength);
        mFirstImage = (ImageView) view.findViewById(R.id.weather_iv_firstImage);
        mSecondImage = (ImageView) view.findViewById(R.id.weather_iv_secondImage);
        mFirstDayFa = (ImageView) view.findViewById(R.id.weather_iv_firstDayFa);
        mFirstDayFb = (ImageView) view.findViewById(R.id.weather_iv_firstDayFb);
        mSecondDayFa = (ImageView) view.findViewById(R.id.weather_iv_secondDayFa);
        mSecondDayFb = (ImageView) view.findViewById(R.id.weather_iv_secondDayFb);
        mThirdDayFa = (ImageView) view.findViewById(R.id.weather_iv_thirdDayFa);
        mThirdDayFb = (ImageView) view.findViewById(R.id.weather_iv_thirdDayFb);
        mFourthDayFa = (ImageView) view.findViewById(R.id.weather_iv_fourthDayFa);
        mFourthDayFb = (ImageView) view.findViewById(R.id.weather_iv_fourthDayFb);
        mFifthDayFa = (ImageView) view.findViewById(R.id.weather_iv_fifthDayFa);
        mFifthDayFb = (ImageView) view.findViewById(R.id.weather_iv_fifthDayFb);
        mSixthDayFa = (ImageView) view.findViewById(R.id.weather_iv_sixthDayFa);
        mSixthDayFb = (ImageView) view.findViewById(R.id.weather_iv_sixthDayFb);
        mSeventhDayFa = (ImageView) view.findViewById(R.id.weather_iv_seventhDayFa);
        mSeventhDayFb = (ImageView) view.findViewById(R.id.weather_iv_seventhDayFb);
        mTime = (TextView) view.findViewById(R.id.weather_tv_todayTime);
        mWeatherDespText = (TextView) view.findViewById(R.id.weather_tv_weatherDesp);
        mTemperature = (TextView) view.findViewById(R.id.weather_tv_temperature);
        mCurrentDateText = (TextView) view.findViewById(R.id.weather_tv_currentDate);
        mEditText = (EditText) view.findViewById(R.id.weather_et_text);
        mSun = (TextView) view.findViewById(R.id.weather_tv_sun);
        mExercises = (TextView) view.findViewById(R.id.weather_tv_exercises);
        mCloth = (TextView) view.findViewById(R.id.weather_tv_cloth);
        mHumdity = (TextView) view.findViewById(R.id.weather_tv_humidity);
        mClothAdvice = (TextView) view.findViewById(R.id.weather_tv_clothadvice);
        mTravel = (TextView) view.findViewById(R.id.weather_tv_travel);

        mTodayWeek = (TextView) view.findViewById(R.id.weather_tv_todayWeek);
        mCity = (TextView) view.findViewById(R.id.weather_tv_city);
        mSearchButton = (Button) view.findViewById(R.id.weather_bu_send);
        mShare = (Button) view.findViewById(R.id.weather_bu_share);
        mEditText = (EditText) view.findViewById(R.id.weather_et_text);

        mSearchButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {//查找城市按键的监听，设置为每次查找城市，当前碎片全来的城市会被取代
                mWeatherDespText.setText("加载中···");
                String inputText = mEditText.getText().toString();
                mWeatherDB = mWeatherDB.getInstance(getContext());
                if (mWeatherDB.findDistric(inputText)) {
                    showErrorProgressDialog();
                }

                    searchWeather(inputText);




            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          showShareDialog();
                                      }
                                  });

        SharedPreferences prefs = getContext().getSharedPreferences(mId + "cityWeatherInformation", getContext().MODE_PRIVATE);


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
        mFirstImage.setImageResource(getResources().getIdentifier(prefs.getString("todayFa", ""), "drawable", getContext().getPackageName()));
        mSecondImage.setImageResource(getResources().getIdentifier(prefs.getString("todayFb", ""), "drawable", getContext().getPackageName()));
        mFirstDayFa.setImageResource(getResources().getIdentifier(prefs.getString("1dayFa", ""), "drawable", getContext().getPackageName()));
        mFirstDayFb.setImageResource(getResources().getIdentifier(prefs.getString("1dayFb", ""), "drawable", getContext().getPackageName()));
        mSecondDayFa.setImageResource(getResources().getIdentifier(prefs.getString("2dayFa", ""), "drawable", getContext().getPackageName()));
        mSecondDayFb.setImageResource(getResources().getIdentifier(prefs.getString("2dayFb", ""), "drawable", getContext().getPackageName()));
        mThirdDayFa.setImageResource(getResources().getIdentifier(prefs.getString("3dayFa", ""), "drawable", getContext().getPackageName()));
        mThirdDayFb.setImageResource(getResources().getIdentifier(prefs.getString("3dayFb", ""), "drawable", getContext().getPackageName()));
        mFourthDayFa.setImageResource(getResources().getIdentifier(prefs.getString("4dayFa", ""), "drawable", getContext().getPackageName()));
        mFourthDayFb.setImageResource(getResources().getIdentifier(prefs.getString("4dayFb", ""), "drawable", getContext().getPackageName()));
        mFifthDayFa.setImageResource(getResources().getIdentifier(prefs.getString("5dayFa", ""), "drawable", getContext().getPackageName()));
        mFifthDayFb.setImageResource(getResources().getIdentifier(prefs.getString("5dayFb", ""), "drawable", getContext().getPackageName()));
        mSixthDayFa.setImageResource(getResources().getIdentifier(prefs.getString("6dayFa", ""), "drawable", getContext().getPackageName()));
        mSixthDayFb.setImageResource(getResources().getIdentifier(prefs.getString("6dayFb", ""), "drawable", getContext().getPackageName()));
        mSeventhDayFa.setImageResource(getResources().getIdentifier(prefs.getString("7dayFa", ""), "drawable", getContext().getPackageName()));
        mSeventhDayFb.setImageResource(getResources().getIdentifier(prefs.getString("7dayFb", ""), "drawable", getContext().getPackageName()));

        mWeatherDespText.setText(prefs.getString("todayWeather", ""));
        mTemperature.setText(prefs.getString("todayTemperature", ""));
        mCurrentDateText.setText(prefs.getString("date", ""));//填充各控件的数




        mLayout.addView(view);
        mLayout.invalidate();



    }

    private void showErrorProgressDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("错误提示")
                .setMessage("查询不到该城市，请确认后再输入")
                .setCancelable(true)
                .show();
    }

    private void searchWeather(String cityname) {


        SharedPreferences prefs = getContext().getSharedPreferences("selectedCity", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(mId + "city", cityname);
        editor.commit();

        try {
            String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(cityname, "UTF-8") +
                    "&key=fcfd0e92c41ad993b93b49ba0f840aff";
            UtilTask utilTask = new UtilTask(address, getContext(),mId);
            utilTask.execute();
            utilTask.setTaskHelper(new UtilTask.TaskHelper() {
                @Override
                public void onSuccess(String response, int g) {
                    GsonUtilityWeather.handleWeatherResponse(getContext(), response, g);
                    lazyLoad();
                }

                @Override
                public void onFail(Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showShareDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        SharedPreferences prefs = getContext().getSharedPreferences(mId + "cityWeatherInformation", getContext().MODE_PRIVATE);

        String emailBody = prefs.getString("citySMS","");//邮件的主要内容
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, subjectStr);
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
        startActivityForResult(Intent.createChooser(email, "请选择邮件发送内容"), 1001);
    }


    private void sendSMS() {
        SharedPreferences prefs = getContext().getSharedPreferences(mId + "cityWeatherInformation", getContext().MODE_PRIVATE);


        String smsBody = prefs.getString("citySMS","");//短信的主要内容
        Uri smsToUri = Uri.parse("今日天气预报");
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
        sendIntent.putExtra("sms_body", smsBody);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivityForResult(sendIntent, 1002);
    }


}




