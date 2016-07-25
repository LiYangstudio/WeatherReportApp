package com.LiYang.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by A555LF on 2016/7/22.
 */
public class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentList;



    public MyFrageStatePagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);

        this.mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }



    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);

    }




}





