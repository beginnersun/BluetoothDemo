package com.example.bluetoothdemo.wifi;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.activity.bean.BluetoothBean;
import com.example.bluetoothdemo.activity.bean.DeviceBean;
import com.example.bluetoothdemo.activity.bean.WifiBean;
import com.example.bluetoothdemo.wifi.adapter.HomeAdapter;
import com.example.bluetoothdemo.wifi.util.OnAdapterItemClick;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.SCAN_RESULTS_AVAILABLE_ACTION;
import static android.net.wifi.WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION;
import static android.net.wifi.WifiManager.SUPPLICANT_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.WIFI_STATE_CHANGED_ACTION;
import static android.net.wifi.WifiManager.WIFI_STATE_DISABLED;
import static android.net.wifi.WifiManager.WIFI_STATE_ENABLED;

public class WifiActivity extends AppCompatActivity {

    /**
     * wifi对应状态
     */
    public static final int WIFI_DISABLING = 0;
    public static final int WIFI_DISAABLE = 1;
    public static final int WIFI_ENABLING = 2;
    public static final int WIFI_ENABLED = 3;
    public static final int WIFI_UNKNOW = 4;
    public static final int REQUEST_FINE_LOCATION = 5;

    private WifiManager wifiManager;
    private WifiReceiver receiver;
    private RoomThread room;
    private int ipPort = 5030;
    private List<WifiBean> wifiBeanList = new ArrayList<>();
    private List<String> homes = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private RecyclerView homeRecyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        homeRecyclerview = findViewById(R.id.homes);
        homeAdapter = new HomeAdapter(homes,this);
        homeRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        homeRecyclerview.setAdapter(homeAdapter);

        Map<String,String> hashMap = new ConcurrentHashMap<>();
        AtomicReference<BluetoothBean> atomicReference = new AtomicReference<>();
//        atomicReference.compareAndSet(); //多线程更新
//        atomicReference.getAndSet()  //返回旧的设置新的
        homeAdapter.setItemClick(new OnAdapterItemClick() {
            @Override
            public void onItemClick(HomeAdapter.ViewHolder viewHolder, int position) {

            }
        });

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        createHome();
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo.getSSID().equals("<unknown ssid>")|| wifiInfo.getIpAddress()==-1){
            initScan();
        }else {
            Log.e("wifi信息","网络已连接不比扫描");
        }
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 创建房间
                 */
                createHome();
            }
        });
        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 扫描IP
                 */
                Log.e("wifi信息","点击");
                scanIp();
            }
        });

    }

    public void initScan(){
        Log.e("wifi信息","注册广播");
        IntentFilter wifiFilter = new IntentFilter();
        wifiFilter.addAction(SCAN_RESULTS_AVAILABLE_ACTION);
        wifiFilter.addAction(WIFI_STATE_CHANGED_ACTION);
        wifiFilter.addAction(SUPPLICANT_STATE_CHANGED_ACTION); //wifi密码错误广播
        wifiFilter.addAction(NETWORK_STATE_CHANGED_ACTION); //wifi链接状态广播
        receiver = new WifiReceiver();
        registerReceiver(receiver,wifiFilter);
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"已有定位权限",Toast.LENGTH_LONG).show();
            boolean success = wifiManager.startScan();
        }else {
            Toast.makeText(this,"开启定位权限",Toast.LENGTH_LONG).show();
            locationManager.setTestProviderEnabled(LocationManager.GPS_PROVIDER,true);  //开启定位
            boolean success = wifiManager.startScan();
        }
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
//                ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//            if (!wifiManager.isWifiEnabled()){
//                wifiManager.setWifiEnabled(true);
//            }
//        }else {
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION},REQUEST_FINE_LOCATION);
//        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    class WifiReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("wifi信息", "onReceive: " + intent.getAction() );
            if (intent.getAction() .equals(SCAN_RESULTS_AVAILABLE_ACTION)){
                List<ScanResult> results = wifiManager.getScanResults();
                for (ScanResult scanResult:results){
                    String capabilities = scanResult.capabilities;
                    WifiBean bean = new WifiBean();
                    bean.setScanResult(scanResult);
                    if (TextUtils.equals(capabilities,"[ESS]") || TextUtils.equals(capabilities,"")){  //判断有无密码
                        bean.setType(WifiBean.WIFI_NO_PW);
                    }else {
                        bean.setType(WifiBean.WIFI_HAVE_PW);
                    }
                    if (!wifiBeanList.contains(bean)){
                        wifiBeanList.add(bean);
                    }
                }
            }else if (intent.getAction().equals(WIFI_STATE_CHANGED_ACTION)){
                int state = intent.getIntExtra(
                        WifiManager.EXTRA_WIFI_STATE, 0);
                switch (state){
                    case WIFI_STATE_ENABLED:
                        wifiManager.startScan();
                        break;
                    case WIFI_STATE_DISABLED:
                        break;
                }
            }else if (intent.getAction().equals(NETWORK_STATE_CHANGED_ACTION)){ //链接状态
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnected()){
                    Log.e("wifi信息","链接状态改变——已连接");
                    // TODO: 2019/8/22  扫描同一个wifi内的设备
                    scanIp();
                }else {
                    Log.e("wifi信息","链接状态改变——未连接");
                }
            }else  if (intent.getAction().equals(SUPPLICANT_STATE_CHANGED_ACTION)){
                Toast.makeText(WifiActivity.this,"密码错误",Toast.LENGTH_LONG).show();
            }
        }
    }

    public WifiConfiguration createWifiConfiguration(String ssid,String password){
        Log.e("准备链接无线","wifi信息");
        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID =  ("\"" + ssid  + "\"");
        apConfig.preSharedKey = "\"" + password + "\"";

        //不广播其SSID的网络
        apConfig.hiddenSSID = true;
        apConfig.status = WifiConfiguration.Status.ENABLED;
        //公认的IEEE 802.11验证算法。
        apConfig.allowedAuthAlgorithms.clear();
        apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        //公认的的公共组密码
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        //公认的密钥管理方案
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        //密码为WPA。
        apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        //公认的安全协议。
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return apConfig;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_FINE_LOCATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if (!wifiManager.isWifiEnabled()){
                    wifiManager.setWifiEnabled(true);
                }
            }
        }
    }

    Socket client;

    public void scanIp(){
        Log.e("wifi信息","扫描IP");
        IpScanner mScanner = new IpScanner(5030,new IpScanner.ScanCallback(){
            @Override
            public void onFound(Set<String> ip, String hostIp, int port) {
                if (Looper.getMainLooper() == Looper.myLooper()){ //判断是否在主线程
                    Log.e("wifi信息","主线程");
                }else {
                    Log.e("wifi信息","子线程");
                }
                Iterator<String> iterator = ip.iterator();
                Log.e("wifi信息hostIp :"+hostIp,"端口："+port);
                while (iterator.hasNext()){
                    String server_ip = iterator.next();
                    homes.add(server_ip);
//                    Log.e("wifi信息______ip地址",server_ip);
//                    try {
//                        client = new Socket(server_ip,ipPort);
//                        Log.e("wifi信息","判断是否链接成功");
//                        if (client.isConnected()){
//                            Log.e("wifi信息","链接到服务器");
//                        }
//                    } catch (IOException e) {
//                        Log.e("wifi信息",e.toString());
//                        e.printStackTrace();
//                    }
                }
                homeAdapter.notifyDataSetChanged();
            }
            @Override
            public void onNotFound(String hostIp, int port) {
                Log.e("wifi信息","没有扫描到");
            }
        });
        mScanner.startScan();

    }


    /**
     * 创建房间
     */
    public void createHome(){
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ipAddress = "";
        if (wifiInfo!=null){
            ipAddress = intToIp(wifiInfo.getIpAddress());
            Log.e("wifi信息，房间IP地址:",ipAddress);
            try {
                ServerSocket serverSocket = new ServerSocket(ipPort);
                room = new RoomThread(serverSocket);
                room.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.e("wifi信息","为空");
        }
//        wifiInfo.
    }

    public String intToIp(int ipAddress)  {
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
    }

    /**
     * 房间线程
     */
    class RoomThread extends Thread{

        ServerSocket server;

        public RoomThread(ServerSocket server) {
            this.server = server;
        }

        @Override
        public void run() {
            super.run();
            while (true){
                try {
                    Log.e("wifi信息","等待游客进入");
                    Socket client = server.accept();
                    Log.e("wifi信息","接入一个游客");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
