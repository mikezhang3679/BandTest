package com.example.bandtest.bean;

import com.example.bandtest.utils.MyUtils;

/**
 * Created by liuqiong on 2017/9/18.
 */

public class CurrentData {
    private long timeInMillis;
    private int stepCount;
    private int calories;
    private float distance;
    private String macAddress;

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "CurrentData{" +
                "timeInMillis='" + MyUtils.formatTime(timeInMillis,"yyyy-MM-dd HH:mm:ss")  + '\'' +
                ", stepCount=" + stepCount +
                ", calories=" + calories +
                ", distance=" + distance +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
