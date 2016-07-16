package com.LiYang.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by A555LF on 2016/7/15.
 */
public class HttpUtil {  //发送网络请求


    public static void HttpURLConnectionSend(final String address, final HttpCallbackListener listener) {//使用HttpURLConnection发送网络请求

        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            if (listener != null) {
                //回调onFinish()方法
                listener.onFinish(response.toString());
            }
        } catch (Exception e) {
            if (listener != null) {

                //回调onError()方法
                listener.onError(e);
            }

        } finally {
            if (connection != null) {
                connection.disconnect();

            }
        }
    }

    public static void httpClientSend(final String adress, final HttpCallbackListener Listener) { //使用HttpClient发送网络请求
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(adress);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String response;
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                response = EntityUtils.toString(entity, "utf-8");

                if (Listener != null) {
                    Listener.onFinish(response.toString());
                }
            }

        } catch (Exception e) {
            if (Listener != null) {

                //回调onError()方法
                Listener.onError(e);
            }

        }
    }

}






