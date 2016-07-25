package com.LiYang.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by A555LF on 2016/7/19.
 */
public class VolleyUtil {
    public static void VolleyUtilSend(String adress, Context context,final VolleyCallbackListener listener){

        RequestQueue mQueue= Volley.newRequestQueue(context);
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
        mQueue.add(stringRequest);

    }
}
