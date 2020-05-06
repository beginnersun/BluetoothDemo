package com.example.bluetoothdemo.wifi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetWorkUtil {


    /**
     * 判断你是否为wifi网络
     * @param context
     * @return
     */
    public static boolean isWifiConnection(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) { //链接状态
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 判断是否有网络
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo.isConnected();
    }

    /**
     * 获取本地IP
     * @return
     */
    public static String getLocalIp(){
        String ip = "";
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces.hasMoreElements()){
                Enumeration<InetAddress> inetAddresses =  networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()){
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address){
                        ip = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ip;
    }


}
