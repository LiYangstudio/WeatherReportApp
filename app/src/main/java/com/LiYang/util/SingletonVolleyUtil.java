package com.LiYang.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by A555LF on 2016/7/20.
 */
public class SingletonVolleyUtil {
    public static void VolleyUtilSend(String adress, Context context, final VolleyCallbackListener listener){

        RequestQueue mQueue= MySingleton.getInstance(context.getApplicationContext()).getRequestQueue();
        StringRequest stringRequest=new StringRequest(adress,new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                listener.onFinish(response.toString());
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                listener.onError(error);
            }
        });
        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);

    }
}
