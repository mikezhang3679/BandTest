package com.example.bandtest.bean;

/**
 * Created by Harry on 2018/1/15.
 */

public class RemindData {
    private int number;
    private String time;
    private String remindId;
    private String switch1;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRemindId() {
        return remindId;
    }

    public void setRemindId(String remindId) {
        this.remindId = remindId;
    }

    public String getSwitch1() {
        return switch1;
    }

    public void setSwitch1(String switch1) {
        this.switch1 = switch1;
    }

    @Override
    public String
    toString() {
        return "RemindData{" +
                "number=" + number +
                ", time='" + time + '\'' +
                ", remindId='" + remindId + '\'' +
                ", switch1='" + switch1 + '\'' +
                '}';
    }
}
