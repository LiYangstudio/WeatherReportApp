package com.LiYang.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by A555LF on 2016/7/15.
 */
public class AlarmUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("AlaramUpdateReceiver","广播已被收到");
        Intent receiverIntent=new Intent(context,NotiAndUpdateService.class);
        context.startService(receiverIntent);
    }
}

