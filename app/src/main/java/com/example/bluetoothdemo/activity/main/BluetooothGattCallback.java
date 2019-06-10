package com.example.bluetoothdemo.activity.main;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.net.wifi.aware.Characteristics;
import android.util.Log;

import java.util.List;
import java.util.UUID;

public class BluetooothGattCallback extends BluetoothGattCallback {

    private static final String TAG = "BluetooothGattCallback";

    private BluetoothGatt mBluetoothGatt;
    private List<BluetoothGattService> gattServices;

    public BluetooothGattCallback(BluetoothGatt mBluetoothGatt) {
        this.mBluetoothGatt = mBluetoothGatt;
    }

    @Override
    public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        super.onPhyUpdate(gatt, txPhy, rxPhy, status);
    }

    @Override
    public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
        super.onPhyRead(gatt, txPhy, rxPhy, status);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status == BluetoothProfile.STATE_CONNECTED){
            Log.i(TAG, "onConnectionStateChange: 连接成功" );
            mBluetoothGatt.discoverServices(); //寻找服务     搜索外围服务
            if (gatt == mBluetoothGatt){
                Log.e(TAG, "onConnectionStateChange: 两个gatt一样");
            }
        }else if (status == BluetoothProfile.STATE_DISCONNECTED){
            Log.i(TAG, "onConnectionStateChange: 断开蓝牙服务");
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status == BluetoothGatt.GATT_SUCCESS){  //搜索到服务
            Log.i(TAG, "onServicesDiscovered: 搜索到服务");
            gattServices = gatt.getServices();
        }
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {   //读取特性
        super.onCharacteristicRead(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS){  //读取特性成功
            Log.i(TAG, "onCharacteristicRead: 读取特性"+new String(characteristic.getValue()));
        }
//        characteristic
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) { //写入特性
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS){
            Log.i(TAG, "onCharacteristicWrite: 写入数据" + new String(characteristic.getValue()));
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        Log.i(TAG, "onCharacteristicChanged: 接收到特性变化" + new String(characteristic.getValue()));
        gatt.readCharacteristic(characteristic);
    }

    @Override
    public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorRead(gatt, descriptor, status);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
    }

    @Override
    public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
        super.onReliableWriteCompleted(gatt, status);
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
    }

    @Override
    public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
        super.onMtuChanged(gatt, mtu, status);
    }

    public BluetoothGattService findGattService(String service_uuid){
        for (BluetoothGattService service:gattServices){
            if (service.getUuid().equals(service_uuid)){
                return service;
            }
        }
        return  null;
    }

    /**
     * 知道service的uuid与character的uuid
     * @param service_uuid
     * @param character_uuid
     * @return
     */
    public BluetoothGattCharacteristic findBluetoothCharacteristic(String service_uuid,String character_uuid){
        BluetoothGattService service = findGattService(service_uuid);
        if (service != null){
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString(character_uuid));

//            characteristic.setValue();
            return characteristic;
        }
        return null;
    }


    /**
     * 仅仅知道character的uuid
     * @param character_uuid
     * @return
     */
    public BluetoothGattCharacteristic findBluetoothCharacteristic(String character_uuid){
        for (BluetoothGattService service:gattServices){
            for (BluetoothGattCharacteristic characteristic:service.getCharacteristics()){
                if (characteristic.getUuid().equals(character_uuid)){
                    return characteristic;
                }
            }
        }
        return null;
    }

    /**
     * 开启特性变化通知
     * @param service_uuid  特性所处service的uuid  特性的uuid
     * @param character_uuid
     */
    public void openCharacteristicNotification(String service_uuid,String character_uuid){
        mBluetoothGatt.setCharacteristicNotification(findBluetoothCharacteristic(service_uuid,character_uuid),true);
    }


    public void setValue(String service_uuid,String character_uuid,String value){
        BluetoothGattCharacteristic characteristic = findBluetoothCharacteristic(service_uuid,character_uuid);
        characteristic.setValue(value);
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public void setVlaue(String character_uuid,String value){
        BluetoothGattCharacteristic characteristic = findBluetoothCharacteristic(character_uuid);
        characteristic.setValue(value);
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

}
