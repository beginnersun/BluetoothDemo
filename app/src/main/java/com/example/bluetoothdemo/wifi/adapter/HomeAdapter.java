package com.example.bluetoothdemo.wifi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.wifi.util.OnAdapterItemClick;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private List<String> ips;
    private Context context;
    private OnAdapterItemClick itemClick;

    public void setItemClick(OnAdapterItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public OnAdapterItemClick getItemClick() {
        return itemClick;
    }

    public HomeAdapter(List<String> ips, Context context) {
        this.ips = ips;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_home,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (holder!=null){
            holder.tv_home_ip.setText(ips.get(position));
            if (itemClick!=null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemClick.onItemClick(holder,position);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return ips!=null?ips.size():0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_home;
        TextView tv_home_ip;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_home = itemView.findViewById(R.id.iv_home);
            tv_home_ip = itemView.findViewById(R.id.tv_home_ip);
        }
    }
}
