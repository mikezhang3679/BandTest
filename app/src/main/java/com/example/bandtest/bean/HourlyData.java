package com.example.bandtest.bean;

import com.example.bandtest.utils.MyUtils;

/**
 * Created by ZhangGuangyao on 2017/9/18.
 */

public class HourlyData {
    private long timeInMillis;
    private int stepCount;
    private int calories;
    private float distance;
    private int heartRate;
    private int bloodOxygen;
    private int bloodPressureHigh;
    private int bloodPressureLow;
    private long shallowSleepTime;
    private long deepSleepTime;
    private long sleepTime;
    private int wakeupTimes;
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

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(int bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public int getBloodPressureHigh() {
        return bloodPressureHigh;
    }

    public void setBloodPressureHigh(int bloodPressureHigh) {
        this.bloodPressureHigh = bloodPressureHigh;
    }

    public int getBloodPressureLow() {
        return bloodPressureLow;
    }

    public void setBloodPressureLow(int bloodPressureLow) {
        this.bloodPressureLow = bloodPressureLow;
    }

    public long getShallowSleepTime() {
        return shallowSleepTime;
    }

    public void setShallowSleepTime(long shallowSleepTime) {
        this.shallowSleepTime = shallowSleepTime;
    }

    public long getDeepSleepTime() {
        return deepSleepTime;
    }

    public void setDeepSleepTime(long deepSleepTime) {
        this.deepSleepTime = deepSleepTime;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getWakeupTimes() {
        return wakeupTimes;
    }

    public void setWakeupTimes(int wakeupTimes) {
        this.wakeupTimes = wakeupTimes;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "HourlyData{" +
                "timeInMillis=" + MyUtils.formatTime(timeInMillis,"yyyy-MM-dd HH:mm:ss") +
                ", stepCount=" + stepCount +
                ", calories=" + calories +
                ", distance=" + distance +
                ", heartRate=" + heartRate +
                ", bloodOxygen=" + bloodOxygen +
                ", bloodPressureHigh=" + bloodPressureHigh +
                ", bloodPressureLow=" + bloodPressureLow +
                ", shallowSleepTime=" + shallowSleepTime +
                ", deepSleepTime=" + deepSleepTime +
                ", sleepTime=" + sleepTime +
                ", wakeupTimes=" + wakeupTimes +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
