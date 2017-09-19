package com.example.bandtest.bean;

import com.example.bandtest.utils.MyUtils;

/**
 * Created by liuqiong on 2017/9/19.
 */

public class BandTestData {
    private long timeInMillis;
    private int heartRate;
    private int bloodOxygen;
    private int bloodPressureHigh;
    private int bloodPressureLow;
    private String macAddress;

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
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

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Override
    public String toString() {
        return "BandTestData{" +
                "timeInMillis=" + MyUtils.formatTime(timeInMillis,"yyyy-MM-dd HH:mm:ss") +
                ", heartRate=" + heartRate +
                ", bloodOxygen=" + bloodOxygen +
                ", bloodPressureHigh=" + bloodPressureHigh +
                ", bloodPressureLow=" + bloodPressureLow +
                ", macAddress='" + macAddress + '\'' +
                '}';
    }
}
