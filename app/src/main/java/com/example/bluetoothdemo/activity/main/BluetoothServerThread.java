package com.example.bluetoothdemo.activity.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class BluetoothServerThread extends Thread {

    BluetoothServerSocket mBluetoothServerSocket;
    BluetoothSocket clientSocket;
    BluetoothAdapter bluetoothAdapter;
    private String name,uuid;

    public BluetoothServerThread(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
        try {
            mBluetoothServerSocket = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(name, UUID.fromString(uuid));
            clientSocket = mBluetoothServerSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
