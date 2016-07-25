package com.LiYang.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by A555LF on 2016/7/20.
 */
public class MySingleton {  //单例模式
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private MySingleton(Context context){
        mContext=context;
        mRequestQueue=getRequestQueue();
    }
    public static synchronized MySingleton getInstance(Context context){
        if(mInstance==null){
            mInstance=new MySingleton(context);

        }
        return mInstance;
    }
    public RequestQueue getRequestQueue(){
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(mContext.getApplicationContext());

        }
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> rep){
        getRequestQueue().add(rep);
    }





}
