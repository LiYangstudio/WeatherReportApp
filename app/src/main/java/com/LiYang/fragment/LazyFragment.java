package com.LiYang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LiYang.R;

/**
 * Created by A555LF on 2016/7/21.
 */
public abstract class LazyFragment extends Fragment {

    protected boolean mIsLoaded = false;

    protected View mMainView;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser && !mIsLoaded && mMainView != null) {
            mIsLoaded = true;
            lazyLoad();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //在加载号之前先放置“正在加载的View”
        if (mMainView == null) {
            mMainView = inflater.inflate(R.layout.activity_loading, container, false);

        }

        if (getUserVisibleHint() && !mIsLoaded) {
            mIsLoaded = true;

            lazyLoad();
        }
        return mMainView;
    }


    protected ViewGroup getLayout() {
        if (mMainView != null) {

            return (ViewGroup) mMainView.findViewById(R.id.weather_test);


        } else {

            return null;
        }
    }


    protected void setLoadFail() {
        mIsLoaded = false;
    }


    protected abstract void lazyLoad();


}
