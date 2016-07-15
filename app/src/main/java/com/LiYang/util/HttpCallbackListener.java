package com.LiYang.util;

/**
 * Created by A555LF on 2016/7/15.
 */
public interface HttpCallbackListener {  //
    void onFinish(String response);

    void onError(Exception e);
}