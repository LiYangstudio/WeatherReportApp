package com.LiYang.gsonbean;

import java.util.List;

/**
 * Created by A555LF on 2016/7/19.
 */
public class CityListUtility {   //基于Gson解析对聚合数据的Json数据设置的一些类
    String resultcode;
    String reason;
    List<Result> result;

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {

        return reason;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public void setResultcode(String resultcode) {

        this.resultcode = resultcode;
    }

    public List<Result> getResult() {

        return result;
    }

    public String getResultcode() {

        return resultcode;
    }
}
