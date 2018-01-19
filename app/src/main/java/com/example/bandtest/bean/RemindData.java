package com.example.bandtest.bean;

/**
 * Created by Harry on 2018/1/15.
 */

public class RemindData {

    private String day;
    private String time;
    private String repeat;
    private String remindId;
    private String switch1;
    private int number;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
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

    public int getNumber() {
        return number;
    }
;
    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "day='" + day + '\'' +
                ", time='" + time + '\'' +
                ", repeat='" + repeat + '\'' +
                ", ID='" + remindId + '\'' +
                ", switch1='" + switch1 + '\'' +
                ", number=" + number;
    }
}
