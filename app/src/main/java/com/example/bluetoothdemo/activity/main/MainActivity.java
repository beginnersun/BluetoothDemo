package com.example.bluetoothdemo.activity.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bluetoothdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_BLUETOOTH = 0x01;
    private BluetoothAdapter bluetoothAdapter;
    private List<BluetoothDevice> bluetoothDeviceList = new ArrayList<>();

    private List<BluetoothDevice> phoneDevices = new ArrayList<>();
    private List<BluetoothDevice> miscDevices = new ArrayList<>();
    private List<BluetoothDevice> computerDevices = new ArrayList<>();
    private List<BluetoothDevice> audioDevices = new ArrayList<>();
    private List<BluetoothDevice> unknowDevices = new ArrayList<>();


    private List<String> bluetoothType = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter.enable()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_BLUETOOTH);
        }
        bluetoothAdapter.startLeScan(new BluetoothScanCallback());
    }

    private class BluetoothScanCallback implements BluetoothAdapter.LeScanCallback {

        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (bluetoothDeviceList != null && device != null) {
                bluetoothDeviceList.add(device);
                switch (device.getBluetoothClass().getMajorDeviceClass()) {
                    case BluetoothClass.Device
                            .Major.MISC:
                        miscDevices.add(device);
                        break;
                    case BluetoothClass.Device.Major.AUDIO_VIDEO:
                        audioDevices.add(device);
                        break;
                    case BluetoothClass.Device.Major.PHONE:
                        phoneDevices.add(device);
                        break;
                    case BluetoothClass.Device.Major.COMPUTER:
                        computerDevices.add(device);
                        break;
                    default:
                        unknowDevices.add(device);
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

            } else {

            }
        }
    }
}
