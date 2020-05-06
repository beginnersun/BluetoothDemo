package com.example.bluetoothdemo.base;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.bluetoothdemo.activity.bean.WifiBean;

import java.lang.reflect.Field;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LayoutInflater.from()
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >=21) {
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(), new ConnectivityManager.NetworkCallback() {
                /**
                 * 网络可用
                 * @param network
                 */
                @Override
                public void onAvailable(Network network) {
                    super.onAvailable(network);
                }

                /**
                 * 网络不可用
                 * @param network
                 */
                @Override
                public void onLost(Network network) {
                    super.onLost(network);
                    Intent intent = new Intent();
                    intent.setAction("android.provider.Settings.ACTION_WIFI_SETTINGS"); //打开wifi设置页面
                    startActivity(intent);
                }

                /**
                 * 网络建立连接时，回调连接的属性
                 * @param network
                 * @param linkProperties
                 */
                @Override
                public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
                    super.onLinkPropertiesChanged(network, linkProperties);
                }

                /**
                 * 网络状态信息发生变化回调
                 * @param network
                 * @param networkCapabilities
                 */
                @Override
                public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    super.onCapabilitiesChanged(network, networkCapabilities);
                }

                /**
                 * 失去连接时回调
                 * @param network
                 * @param maxMsToLive
                 */
                @Override
                public void onLosing(Network network, int maxMsToLive) {
                    super.onLosing(network, maxMsToLive);
                }

                /**
                 * 没有找到可用网络
                 */
                @Override
                public void onUnavailable() {
                    super.onUnavailable();
                }
            });
        }
    }
}
