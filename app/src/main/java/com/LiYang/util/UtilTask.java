package com.LiYang.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by A555LF on 2016/7/19.
 */
public class UtilTask extends AsyncTask<Void,Integer,Boolean> {
    private TaskHelper taskHelper;
    private String address;
    Context context;



    public UtilTask(String address, Context context) {
        this.address = address;
        this.context = context;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("UtilTask","新写的东西生效");
        VolleyUtil.VolleyUtilSend(address, context, new VolleyCallbackListener() {
            @Override
            public void onFinish(String response) {
             taskHelper.onSuccess(response.toString());
            }

            @Override
            public void onError(Exception e) {
                taskHelper.onFail(e);
            }
        });

  return true;
    }

    public interface TaskHelper{
        void onSuccess(String response);
        void onFail(Exception e);


    }
    public void setTaskHelper(TaskHelper taskHelper){
        this.taskHelper=taskHelper;
    }


    }


