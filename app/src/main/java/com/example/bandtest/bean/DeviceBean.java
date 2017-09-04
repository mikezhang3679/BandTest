package com.example.bandtest.bean;

import android.bluetooth.BluetoothDevice;


public class DeviceBean {
    private BluetoothDevice device;
    private int rssi;

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }


    @Override
    public String toString() {
        return "DeviceBean{" +
                "device=" + device +
                ", rssi=" + rssi +
                '}';
    }
}
