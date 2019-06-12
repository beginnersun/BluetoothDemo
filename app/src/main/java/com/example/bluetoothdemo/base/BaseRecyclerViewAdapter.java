package com.example.bluetoothdemo.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List mDatas;
    private Context mContext;
    private onItemClickListener onItemClickListener;

    public BaseRecyclerViewAdapter(List mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(getLayoutId(viewType),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        holder.itemView.setTag(position);
        convert(holder,position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ?0:mDatas.size();
    }

    public Object getItem(int position){
        if (position >=0 && position <getItemCount()){
            return mDatas.get(position);
        }
        return  null;
    }

    public abstract @LayoutRes int getLayoutId(int viewType);

    public abstract void convert(ViewHolder holder, int position);

    public void setOnItemClickListener(BaseRecyclerViewAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }

}
