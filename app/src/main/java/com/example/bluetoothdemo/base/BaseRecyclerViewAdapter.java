package com.example.bluetoothdemo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List mDatas;
    private Context mContext;
    private onItemClickListener onItemClickListener;

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(viewType),parent,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (onItemClickListener != null){
            this.onItemClickListener.onItemClick(position);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ?0:mDatas.size();
    }

    public abstract int getLayoutId(int viewType);

    public void setOnItemClickListener(BaseRecyclerViewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

}
