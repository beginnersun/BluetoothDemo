package com.example.bluetoothdemo.activity.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.activity.bean.DeviceBean;
import com.example.bluetoothdemo.activity.main.adapter.BluetoothDeviceAdapter;
import com.example.bluetoothdemo.base.BaseRecyclerViewAdapter;
import com.example.bluetoothdemo.base.ViewHolder;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String TAG1 = "MainActivityDevice";

    private final int REQUEST_BLUETOOTH = 0x01;
    private BluetoothAdapter bluetoothAdapter;
    private List<DeviceBean> bluetoothDeviceList = new ArrayList<>();
    private List<String> deviceMacs = new ArrayList<>();

    private List<DeviceBean> normalDevices = new ArrayList<>();
    /**
     * 以下是ble蓝牙设备
     */
    private List<DeviceBean> phoneDevices = new ArrayList<>();
    private List<DeviceBean> miscDevices = new ArrayList<>();
    private List<DeviceBean> computerDevices = new ArrayList<>();
    private List<DeviceBean> audioDevices = new ArrayList<>();
    private List<DeviceBean> unknowDevices = new ArrayList<>();

    private BluetoothDeviceAdapter phoneAdapter;
    private BluetoothDeviceAdapter miscAdapter;
    private BluetoothDeviceAdapter computerAdapter;
    private BluetoothDeviceAdapter audioAdapter;
    private BluetoothDeviceAdapter unknowAdapter;

    private RecyclerView phoneRecyclerView;
    private RecyclerView computerRecyclerView;
    private RecyclerView audioRecyclerView;
    private RecyclerView unknowRecyclerView;
    private ImageView loadingView;


    private List<String> bluetoothType = new ArrayList<>();
    private BluetoothScanCallback bluetoothScanCallback;  //扫描设备回掉
    private int scanTime = 10;  //10秒后 停止扫描设备
    private Handler handler = new Handler();
    private BluetoothReceiver bluetoothReceiver;

    private BluetoothDevice connect_device;  //准备连接的设备
    private BluetooothGattCallback connect_callback;
    /**
     * 是否正在扫描蓝牙设备
     */
    private boolean mScaning = false;
//    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        rxPermissions = new RxPermissions(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initBluetoothDevices();
        if (!bluetoothAdapter.enable()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_BLUETOOTH);
        } else {
            searchBleDevice();
        }
    }

    private void searchBleDevice() {
        startLoading();
        bluetoothAdapter.startLeScan(bluetoothScanCallback = new BluetoothScanCallback());  //仅能发现低功耗蓝牙(ble)
        mScaning = true;
        handler.postDelayed(stopScan, scanTime * 1000);
        searchJindDianDevice();
    }

    private Runnable stopScan = new Runnable() {
        @Override
        public void run() {
            mScaning = true;
            bluetoothAdapter.stopLeScan(bluetoothScanCallback);
            stopLoading();
        }
    };


    private void initBluetoothDevices() {
        phoneAdapter = new BluetoothDeviceAdapter(this, phoneDevices, BluetoothDeviceAdapter.DEVICE_PHONE);
        miscAdapter = new BluetoothDeviceAdapter(this, miscDevices, BluetoothDeviceAdapter.DEVICE_BLUETOOTH);
        computerAdapter = new BluetoothDeviceAdapter(this, computerDevices, BluetoothDeviceAdapter.DEVICE_COMPUTER);
        audioAdapter = new BluetoothDeviceAdapter(this, audioDevices, BluetoothDeviceAdapter.DEVICE_AUDIO);
        unknowAdapter = new BluetoothDeviceAdapter(this, unknowDevices, BluetoothDeviceAdapter.DEVICE_BLUETOOTH);

        computerRecyclerView = findViewById(R.id.computer_recyclerView);
        phoneRecyclerView = findViewById(R.id.phone_recyclerView);
        audioRecyclerView = findViewById(R.id.audio_recyclerView);
        unknowRecyclerView = findViewById(R.id.unknow_recyclerView);
        loadingView = findViewById(R.id.loading_view);

        computerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        phoneRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        audioRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        unknowRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        computerRecyclerView.setAdapter(computerAdapter);
        phoneRecyclerView.setAdapter(phoneAdapter);
        audioRecyclerView.setAdapter(audioAdapter);
        unknowRecyclerView.setAdapter(unknowAdapter);

        phoneAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                phoneAdapter.showItemOptions(position);
//                device.createInsecureRfcommSocketToServiceRecord("uuid"); //传统蓝牙连接方式
//                device.connectGatt();  //ble蓝牙连接方式
//                device.createInsecureRfcommSocketToServiceRecord();
//                device.createRfcommSocketToServiceRecord();
//                device.createIn
            }
        });
        phoneAdapter.setOnOptionsClick(new BluetoothDeviceAdapter.OnOptionsClick() {
            @Override
            public void clickOptions(View view) {
                int position = (int) view.getTag();
                switch (view.getId()) {
                    case R.id.connect:
                        connect_device = phoneDevices.get(position).getDevice();
                        if (connect_device.getBondState() != BluetoothDevice.BOND_BONDED) {
                            connect_device.createBond();
                        } else {
                            Toast.makeText(MainActivity.this, "已经配对过了", Toast.LENGTH_SHORT).show();
                            switch (connect_device.getType()) {
                                case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                                    try {
                                        BluetoothSocket socket = connect_device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001106-0000-1000-8000-00805F9B34FB")); //信息传输固定的uuid
                                        new Thread(new SocketSendRunnable(socket)).start();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case BluetoothDevice.DEVICE_TYPE_LE:
                                    connect_callback = new BluetooothGattCallback();
                                    connect_device.connectGatt(MainActivity.this, false, connect_callback);
                                    break;
                                case BluetoothDevice.DEVICE_TYPE_DUAL:
                                    break;
                            }
                        }
                        break;
                    case R.id.send:
                        break;
                }
            }
        });
    }


    class SocketReceiveRunnable implements Runnable{

        BluetoothSocket client;

        public SocketReceiveRunnable(BluetoothSocket client) {
            this.client = client;
        }

        @Override
        public void run() {

        }
    }

    class SocketSendRunnable implements Runnable{

        private BluetoothSocket client;
        private OutputStream out;
        public SocketSendRunnable(BluetoothSocket client) {
            this.client = client;
            try {
                this.client.connect();
                if (this.client.isConnected()) {
                    out = this.client.getOutputStream();
                }
            } catch (IOException e) {
                Log.e(TAG, "SocketSendRunnable: 错误信息");
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                if (this.client.isConnected()) {
                    Log.e(TAG, "run: 准备发送");
                    out.write("nihao".getBytes());
                    out.close();
                    Log.e(TAG, "run: 发送完成");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void startLoading() {
        loadingView.setVisibility(View.VISIBLE);
        loadingView.setImageResource(R.drawable.loading_bg);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
        animationDrawable.start();
//        loadingView.getBackground()
    }

    private void stopLoading() {
        loadingView.setImageResource(R.drawable.loading_bg);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingView.getDrawable();
        animationDrawable.stop();
        loadingView.setVisibility(View.GONE);
    }

    private void searchJindDianDevice() {
        bluetoothAdapter.startDiscovery();  //经典蓝牙设备的发现设备所调用的方法   蓝牙设备分为 单模  双模   和经典蓝牙   单模又称为低功耗蓝牙
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); //监听搜索完成
        filter.addAction(BluetoothDevice.ACTION_FOUND);  //监听是否配对
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);  //监听配对状态

        registerReceiver(bluetoothReceiver = new BluetoothReceiver(), filter);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (mScaning) {  //如果还未停止就 停止
            handler.removeCallbacks(stopScan); //移除消息
            bluetoothAdapter.stopLeScan(bluetoothScanCallback);
        }
        unregisterReceiver(bluetoothReceiver);
        if (bluetoothAdapter != null) {
            bluetoothAdapter.cancelDiscovery();
        }
    }

    class BluetoothReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:  //扫描完毕
                    Log.e(TAG, "onReceive: 总共有" +
                            bluetoothAdapter.getBondedDevices().size() + "个设备");
                    break;
                case BluetoothDevice.ACTION_FOUND:   //扫描到一个设备
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (!deviceMacs.contains(device.getAddress())) {
//                        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
//
//                        } else if (device.getBondState() == BluetoothDevice.BOND_NONE) {
                        Log.e(TAG, "onReceive: " + device.getName());
                        DeviceBean deviceBean = new DeviceBean(device);
                        normalDevices.add(deviceBean);
                        deviceMacs.add(device.getAddress());
                        String type = "";
                        switch (device.getType()) {
                            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                                type = "经典";
                                break;
                            case BluetoothDevice.DEVICE_TYPE_LE:
                                type = "低功耗";
                                break;
                            case BluetoothDevice.DEVICE_TYPE_DUAL:
                                type = "双模";
                                break;
                            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                                type = "未知设备";
                                break;
                        }
                        Log.e(TAG1, "onReceive: " + device.getName() + "  属于 " + type);
                        switch (device.getBluetoothClass().getMajorDeviceClass()) {
                            case BluetoothClass.Device
                                    .Major.MISC:
                                miscDevices.add(deviceBean);
                                miscAdapter.notifyDataSetChanged();
                                break;
                            case BluetoothClass.Device.Major.AUDIO_VIDEO:
                                audioDevices.add(deviceBean);
                                audioAdapter.notifyDataSetChanged();
                                break;
                            case BluetoothClass.Device.Major.PHONE:
                                phoneDevices.add(deviceBean);
                                phoneAdapter.notifyDataSetChanged();
                                break;
                            case BluetoothClass.Device.Major.COMPUTER:
                                computerDevices.add(deviceBean);
                                computerAdapter.notifyDataSetChanged();
                                break;
                            default:
                                unknowDevices.add(deviceBean);
                                unknowAdapter.notifyDataSetChanged();
                                break;
//                            }
                        }
                    }
                    break;
                case BluetoothDevice.ACTION_PAIRING_REQUEST:
                    Log.e(TAG, "onReceive: SCAN_REQUEST");
                    BluetoothDevice device1 = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    switch (device1.getBondState()) {
                        case BluetoothDevice.BOND_BONDED: //配对成功
                            Log.e(TAG, "onReceive: SCAN_REQUEST SUCCESS");
                            Toast.makeText(MainActivity.this, "已经配对过了", Toast.LENGTH_SHORT).show();
                            connect_callback = new BluetooothGattCallback();
                            connect_device.connectGatt(MainActivity.this, false, connect_callback);
//                            BluetoothGatt gatt = null;
//                            BluetooothGattCallback callback = new BluetooothGattCallback();
//                            gatt = connect_device.connectGatt(MainActivity.this, false, callback);
//                            callback.setmBluetoothGatt(gatt);
                            break;
                        case BluetoothDevice.BOND_BONDING://配对中
                            Log.e(TAG, "onReceive: SCAN_REQUEST BINDING");
                            break;
                        case BluetoothDevice.BOND_NONE://配对失败
                            Log.e(TAG, "onReceive: SCAN_REQUEST FAILED");
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * Ble设备扫描监听回调
     */
    private class BluetoothScanCallback implements BluetoothAdapter.LeScanCallback {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (deviceMacs != null && device != null && !deviceMacs.contains(device.getAddress())) {
                Log.e(TAG, "onLeScan: 找到一个");
                DeviceBean deviceBean = new DeviceBean(device);
                normalDevices.add(deviceBean);
                deviceMacs.add(device.getAddress());

                String type = "";
                switch (device.getType()) {
                    case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                        type = "经典";
                        break;
                    case BluetoothDevice.DEVICE_TYPE_LE:
                        type = "低功耗";
                        break;
                    case BluetoothDevice.DEVICE_TYPE_DUAL:
                        type = "双模";
                        break;
                    case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                        type = "未知设备";
                        break;
                }
                Log.e(TAG1, "onLeScan: " + device.getName() + "  属于 " + type);
                switch (device.getBluetoothClass().getMajorDeviceClass()) {
                    case BluetoothClass.Device
                            .Major.MISC:
                        miscDevices.add(deviceBean);
                        miscAdapter.notifyDataSetChanged();
                        break;
                    case BluetoothClass.Device.Major.AUDIO_VIDEO:
                        audioDevices.add(deviceBean);
                        audioAdapter.notifyDataSetChanged();
                        break;
                    case BluetoothClass.Device.Major.PHONE:
                        phoneDevices.add(deviceBean);
                        phoneAdapter.notifyDataSetChanged();
                        break;
                    case BluetoothClass.Device.Major.COMPUTER:
                        computerDevices.add(deviceBean);
                        computerAdapter.notifyDataSetChanged();
                        break;
                    default:
                        unknowDevices.add(deviceBean);
                        unknowAdapter.notifyDataSetChanged();
                        break;
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "开启蓝牙，开始扫描", Toast.LENGTH_SHORT).show();
                searchBleDevice();
            } else {
                Toast.makeText(this, "开启蓝牙失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
