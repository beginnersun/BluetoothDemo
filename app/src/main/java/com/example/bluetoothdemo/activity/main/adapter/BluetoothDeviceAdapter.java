package com.example.bluetoothdemo.activity.main.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.activity.bean.DeviceBean;
import com.example.bluetoothdemo.base.BaseRecyclerViewAdapter;
import com.example.bluetoothdemo.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;

public class BluetoothDeviceAdapter extends BaseRecyclerViewAdapter implements View.OnClickListener {

    public static final int DEVICE_PHONE = 2;
    public static final int DEVICE_COMPUTER = 1;
    public static final int DEVICE_AUDIO = 3;
    public static final int DEVICE_MISC = 4;
    public static final int DEVICE_BLUETOOTH = 5;

    private int type;
    private int old_options_position;
    private OnOptionsClick onOptionsClick;

    public interface OnOptionsClick{
        void clickOptions(View view);
    }

    public BluetoothDeviceAdapter( Context mContext,List mDatas, int type) {
        super(mDatas, mContext);
        this.type = type;
    }

    public void setOnOptionsClick(OnOptionsClick onOptionsClick) {
        this.onOptionsClick = onOptionsClick;
    }

    @Override
    public @LayoutRes int getLayoutId(int viewType) {
        return R.layout.item_recyclerview_device;
    }

    @Override
    public void convert(ViewHolder holder, int position) {
        DeviceBean bean = (DeviceBean) getItem(position);
        BluetoothDevice device = bean.getDevice();

        ImageView dev_type = holder.get(R.id.iv_device_type);
        TextView dev_name = holder.get(R.id.tv_device_name);
        LinearLayout options = holder.get(R.id.device_options);
        TextView connect = holder.get(R.id.connect);
        TextView send = holder.get(R.id.send);
        switch (type){
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
        if (bean.isShowOptions()){
            options.setVisibility(View.VISIBLE);
        }else {
            options.setVisibility(View.GONE);
        }
        connect.setTag(position);
        connect.setOnClickListener(this);
        send.setOnClickListener(this);
        send.setTag(position);
    }

    public void showItemOptions(int position){
        if (old_options_position != -1){
            hideItemOptions(old_options_position);
        }
        ((DeviceBean)getItem(position)).setShowOptions(true);
        notifyItemChanged(position);
        old_options_position = position;
    }

    private void hideItemOptions(int position){
        ((DeviceBean)getItem(position)).setShowOptions(false);
        notifyItemChanged(position);
    }

    @Override
    public void onClick(View view) {
        if (onOptionsClick != null){
            onOptionsClick.clickOptions(view);
        }
    }
}
