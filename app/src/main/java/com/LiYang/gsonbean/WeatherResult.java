package com.LiYang.gsonbean;

import java.util.List;

/**
 * Created by A555LF on 2016/7/19.
 */
public class WeatherResult {
    Sk sk;
    Today today;
    List<Future> future;

    public void setFuture(List<Future> future) {
        this.future = future;
    }

    public void setToday(Today today) {

        this.today = today;
    }

    public void setSk(Sk sk) {

        this.sk = sk;
    }

    public List<Future> getFuture() {

        return future;
    }

    public Today getToday() {

        return today;
    }

    public Sk getSk() {

        return sk;
    }
}
