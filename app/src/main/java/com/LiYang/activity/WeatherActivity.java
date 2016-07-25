package com.LiYang.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.LiYang.R;
import com.LiYang.adapter.MyFrageStatePagerAdapter;
import com.LiYang.fragment.WeatherFragment;
import com.LiYang.util.GsonUtilityWeather;
import com.LiYang.util.UtilTask;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A555LF on 2016/7/15.
 */
public class WeatherActivity extends FragmentActivity implements View.OnClickListener {

   private  Button mButtonone;   //底下可以切换view的几个按钮
   private  Button mButtontwo;
    private Button mButtonthree;
    private Button mButtonfour;


    List<Fragment> mFragmentList = new ArrayList<>();

    ImageView mImageviewOvertab;



    private Button mSwitchCity;
    private Button mRefreshWeather;



    private ViewPager mViewPager;

    int mScreenWidth;
    //当前选中的项
    int mCurrenttab = -1;//用于标记下方跟随滑动的小方块的位置
    private MyFrageStatePagerAdapter mMyFrageStatePagerAdapter = new MyFrageStatePagerAdapter(getSupportFragmentManager(), mFragmentList);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weathermain);

        //初始化各控件


        mButtonone = (Button) findViewById(R.id.weather_ll_buttonone);
        mButtontwo = (Button) findViewById(R.id.weather_ll_buttontwo);
        mButtonthree = (Button) findViewById(R.id.weather_ll_buttonthree);
        mButtonfour = (Button) findViewById(R.id.weather_ll_buttonfour);
        mButtonone.setOnClickListener(this);
        mButtontwo.setOnClickListener(this);
        mButtonthree.setOnClickListener(this);
        mButtonfour.setOnClickListener(this);
        mViewPager = (ViewPager) findViewById(R.id.weather_viewpager);



        mScreenWidth = getResources().getDisplayMetrics().widthPixels;  //设置下方跟随滑动的小方块的初始值
        mButtontwo.measure(0, 0);
        mImageviewOvertab = (ImageView) findViewById(R.id.weahther_iv_fugai);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(mScreenWidth / 4, mButtontwo.getMeasuredHeight());
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mImageviewOvertab.setLayoutParams(imageParams);


        mSwitchCity = (Button) findViewById(R.id.weather_bu_switchCity);
        mRefreshWeather = (Button) findViewById(R.id.weather_bu_refreshWeather);




        mRefreshWeather.setOnClickListener(this);//刷新键


        mSwitchCity.setOnClickListener(this);//选择城市列表键


        mViewPager.setOffscreenPageLimit(5);//设置缓存页数

        SharedPreferences prefs = getSharedPreferences("selectedCity", MODE_PRIVATE);//读取已经被选择的城市名
        String mOneCity = prefs.getString("1city", "");
        String mTwoCity = prefs.getString("2city", "");
        String mThreeCity = prefs.getString("3city", "");
        String mFourCity = prefs.getString("4city", "");





        if (!mOneCity.equals("")) { //有县级代号时就去查询天气

            if (!mOneCity.equals("")) {
                int h = 1;

                mFragmentList.add(WeatherFragment.newInstance(mOneCity,h));
                mViewPager.setAdapter(mMyFrageStatePagerAdapter);
            }

            if (!mTwoCity.equals("")) {
                int h = 2;

                mFragmentList.add(WeatherFragment.newInstance(mTwoCity,h));
                mViewPager.setAdapter(mMyFrageStatePagerAdapter);
            }
            if (!mThreeCity.equals("")) {
                int h = 3;

                mFragmentList.add(WeatherFragment.newInstance(mThreeCity,h));
                mViewPager.setAdapter(mMyFrageStatePagerAdapter);
            }

            if (!mFourCity.equals("")) {
                int h = 4;

                mFragmentList.add(WeatherFragment.newInstance(mFourCity,h));
                mViewPager.setAdapter(mMyFrageStatePagerAdapter);

            }


        } else {//没有县级代号时就直接显示本地天气


            //  showWeather();
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { //监听
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                int currentItem = mViewPager.getCurrentItem();
                if (currentItem == mCurrenttab) {
                    return;
                }
                imageMove(mViewPager.getCurrentItem());
                mCurrenttab = mViewPager.getCurrentItem();//设置下方关于跟随滑动的方块
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    //查询天气

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
               updateWeather();

                break;





            case R.id.weather_ll_buttonone:
                changeView(0);
                break;
            case R.id.weather_ll_buttontwo:
                changeView(1);
                break;
            case R.id.weather_ll_buttonthree:
                changeView(2);
                break;
            case R.id.weather_ll_buttonfour:
                changeView(3);
                break;


            default:
                break;
        }
    }


    private void imageMove(int moveToTab) {//下方的动画效果
        int startPosition = 0;
        int movetoPosition = 0;

        startPosition = mCurrenttab * (mScreenWidth / 4);
        movetoPosition = moveToTab * (mScreenWidth / 4);
        //平移动画
        TranslateAnimation translateAnimation = new TranslateAnimation(startPosition, movetoPosition, 0, 0);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(200);
        mImageviewOvertab.startAnimation(translateAnimation);

    }


    private void changeView(int desTab) {
        mViewPager.setCurrentItem(desTab, true);
    }


    private void updateWeather() {  //刷新键在主布局中
        for (int i = 1; i < 5; i++) {
            SharedPreferences share = getSharedPreferences("selectedCity", MODE_PRIVATE);
            String cityname = share.getString(i + "city", "");

            if (!cityname.equals("")) {
                SharedPreferences prefs = getSharedPreferences(i + "city", MODE_PRIVATE);


                try {
                    String address = "http://v.juhe.cn/weather/index?format=2&cityname=" + URLEncoder.encode(cityname, "UTF-8") +
                            "&key=fcfd0e92c41ad993b93b49ba0f840aff";
                    UtilTask utilTask = new UtilTask(address, this, i);
                    utilTask.execute();
                    utilTask.setTaskHelper(new UtilTask.TaskHelper() {
                        @Override
                        public void onSuccess(String response, int g) {
                            GsonUtilityWeather.handleWeatherResponse(WeatherActivity.this, response, g);
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
        }
    }
}



















