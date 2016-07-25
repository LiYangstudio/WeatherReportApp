package com.LiYang.gsonbean;

/**
 * Created by A555LF on 2016/7/19.
 */
public class WeatherInformaionUtility {//基于Gson解析对聚合数据的Json数据设置的一些类
    String resultcode;
    String reason;
    WeatherResult result;
    String error_code;

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_code() {

        return error_code;
    }

    public void setResult(WeatherResult result) {

        this.result = result;
    }

    public void setReason(String reason) {

        this.reason = reason;
    }

    public void setResultcode(String resultcode) {

        this.resultcode = resultcode;
    }




    public WeatherResult getResult() {

        return result;
    }

    public String getReason() {

        return reason;
    }

    public String getResultcode() {

        return resultcode;
    }
}
