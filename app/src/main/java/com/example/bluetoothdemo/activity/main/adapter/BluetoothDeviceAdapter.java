package com.example.bluetoothdemo.activity.main.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.base.BaseRecyclerViewAdapter;
import com.example.bluetoothdemo.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BluetoothDeviceAdapter extends BaseRecyclerViewAdapter {

    public static final int DEVICE_PHONE = 2;
    public static final int DEVICE_COMPUTER = 1;
    public static final int DEVICE_AUDIO = 3;
    public static final int DEVICE_MISC = 4;
    public static final int DEVICE_BLUETOOTH = 5;

    private int type;

    public BluetoothDeviceAdapter( Context mContext,List mDatas, int type) {
        super(mDatas, mContext);
        this.type = type;
    }

    @Override
    public @LayoutRes int getLayoutId(int viewType) {
        return R.layout.item_recyclerview_device;
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        BluetoothDevice device = (BluetoothDevice) getItem(position);

        ImageView dev_type = holder.get(R.id.iv_device_type);
        TextView dev_name = holder.get(R.id.tv_device_name);

        switch (device.getBluetoothClass().getMajorDeviceClass()){
            case DEVICE_COMPUTER:
                dev_type.setImageResource(R.mipmap.computer);
                break;
            case DEVICE_PHONE:
                dev_type.setImageResource(R.mipmap.phone);
                break;
            case DEVICE_AUDIO:
                dev_type.setImageResource(R.mipmap.audio);
                break;
            case DEVICE_MISC:
                dev_type.setImageResource(R.mipmap.bluetooth);
                break;
            case DEVICE_BLUETOOTH:  //其他设备  暂未分类
                dev_type.setImageResource(R.mipmap.bluetooth);
                break;
        }
        if (!TextUtils.isEmpty(device.getName())) {
            dev_name.setText(device.getName());
        }else {
            dev_name.setText(device.getAddress());
        }
    }
}
