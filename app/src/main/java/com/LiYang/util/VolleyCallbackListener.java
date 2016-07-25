package com.LiYang.util;

/**
 * Created by A555LF on 2016/7/19.
 */
public interface VolleyCallbackListener {  //该接口用于将Volley网络请求的结果回调到AsyncTask

        void onFinish(String response);

        void onError(Exception e);

}
