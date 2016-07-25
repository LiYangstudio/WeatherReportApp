package com.LiYang.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by A555LF on 2016/7/19.
 */
public class UtilTask extends AsyncTask<Void,Integer,Boolean> {
    private TaskHelper mTaskHelper;
    private String mAddress;
    private int mI;

    Context context;



    public UtilTask(String address, Context context,int i) {
        this.mAddress = address;
        this.context = context;
        this.mI=i;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("UtilTask","新写的东西生效");
        SingletonVolleyUtil.VolleyUtilSend(mAddress, context, new VolleyCallbackListener() {
            @Override
            public void onFinish(String response) {

             mTaskHelper.onSuccess(response.toString(),mI);
            }

            @Override
            public void onError(Exception e) {
                mTaskHelper.onFail(e);
            }
        });

  return true;
    }

    public interface TaskHelper{
        void onSuccess(String response,int i);
        void onFail(Exception e);


    }
    public void setTaskHelper(TaskHelper taskHelper){
        this.mTaskHelper=taskHelper;
    }


    }


