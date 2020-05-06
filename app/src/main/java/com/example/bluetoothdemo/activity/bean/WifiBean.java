package com.example.bluetoothdemo.activity.bean;

import android.net.wifi.ScanResult;

import androidx.annotation.Nullable;

public class WifiBean {
    public static final int WIFI_NO_PW = 0;
    public static final int WIFI_HAVE_PW = 1;
    private int type;
    private ScanResult scanResult;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ScanResult getScanResult() {
        return scanResult;
    }

    public void setScanResult(ScanResult scanResult) {
        this.scanResult = scanResult;
    }

    public WifiBean(int type, ScanResult scanResult) {
        this.type = type;
        this.scanResult = scanResult;
    }

    public WifiBean() {
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof WifiBean){
            WifiBean bean = (WifiBean) obj;
            if (bean.getScanResult().BSSID.equals(this.scanResult.BSSID)){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
}
