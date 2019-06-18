package com.example.bluetoothdemo.activity.bean;

import android.bluetooth.BluetoothDevice;

import androidx.annotation.Nullable;

public class DeviceBean {

    private boolean showOptions;
    private BluetoothDevice device;

    public DeviceBean(BluetoothDevice device) {
        this.device = device;
        this.showOptions = false;
    }

    public boolean isShowOptions() {
        return showOptions;
    }

    public void setShowOptions(boolean showOptions) {
        this.showOptions = showOptions;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof DeviceBean) {
            DeviceBean bean = (DeviceBean) obj;
            if (bean.getDevice().getAddress().equals(this.device.getAddress())) {
                return true;
            } else {
                return false;
            }
        }else if (obj instanceof BluetoothDevice){
            BluetoothDevice device = (BluetoothDevice) obj;
            if (device.getAddress().equals(this.device.getAddress())){
                return true;
            }
        }
        return false;
    }
}
