package com.LiYang.service;

/**
 * Created by A555LF on 2016/7/15.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class AlarmUpdateReceiver extends BroadcastReceiver { //作为定时服务的广播接收器
    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("AlaramUpdateReceiver","广播已被收到");
        Intent receiverIntent=new Intent(context,NotiAndUpdateService.class);
        context.startService(receiverIntent);
    }
}


