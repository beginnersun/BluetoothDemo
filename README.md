# BluetoothDemo
蓝牙详解：
   经典蓝牙：
      流程：得到BluetoothAdapter（BluetoothAdapter.getAdapter()) -> 搜索设备（adapter.startDiscovery()) -> 得到周围设备（注册广播接收者，监听
           1、BluetoothAdapter.ACTION_DISCOVERY_FINISHED 搜索完成
           2、BluetoothDevice.ACTION_FOUND 发现一个设备
           3、BluetoothDevice.ACTION_PAIRING_REQUEST 请求配对 这三个action)  
        -> 链接设备(BluetoothSocket socket = device.createInsecureRfcommSocketToServiceRecord("uuid"));
   Ble蓝牙：
      流程：得到BluetoothAdapter(BluetoothManager manager = getSystemService(BLUETOOTH_SERVICE) manager.getAdapter())
        -> 搜索设备(adapter.startLeScan(new LeScanCallback())需要停止时调用 adapter.stopLeScan(...)) 在api21以后这个方法被代替
           BluetoothLeScanner scanner = adapter.getBluetoothLeScanner();  scanner.startScan(new ScanCallback())
        -> 得到设备api21之前：在LeScanCallback 的onLeScan中可以得到扫描到的设备（可能有重复)
                  api21之后：在ScanCallback 的onScanResult中返回扫描到的设备result.getDevice();在onScanFailed中得到扫描失败的原因
        -> 链接设备BluetoothGatt gatt = device.connectGatt(this,false,new BluetoothGattCallback())
           
