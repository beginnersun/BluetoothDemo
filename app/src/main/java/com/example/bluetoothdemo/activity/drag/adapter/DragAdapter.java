package com.example.bluetoothdemo.activity.drag.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bluetoothdemo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class DragAdapter extends RecyclerView.Adapter<DragAdapter.DragViewHolder> {

    private Context context;
    private List<String> mDatas;
    private OnDragItemClickListener itemClickListener;

    public DragAdapter(Context context, List<String> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    public DragAdapter(Context context, List<String> mDatas, OnDragItemClickListener itemClickListener) {
        this.context = context;
        this.mDatas = mDatas;
        this.itemClickListener = itemClickListener;
    }

    public void setItemClickListener(OnDragItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DragViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DragViewHolder(LayoutInflater.from(context).inflate(R.layout.item_drag_delete,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DragViewHolder dragViewHolder, final int i) {
        if (dragViewHolder!= null){
//            ((SwipeMenuLayout) dragViewHolder.itemView).setIos(false).setLeftSwipe(i % 2 == 0 ? true : false);//这句话关掉IOS阻塞式交互效果 并依次打开左滑右滑

            dragViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.delete(i);
                }
            });
            dragViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.itemClick(i);
                    }
                }
            });
            dragViewHolder.content.setText(mDatas.get(i));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas==null?0:mDatas.size();
    }

    static class DragViewHolder extends RecyclerView.ViewHolder{

        TextView content;
        Button delete;
        View rootView;
        public DragViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
            delete = itemView.findViewById(R.id.delete);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }

    public interface OnDragItemClickListener{
        void delete(int position);

        void itemClick(int position);
    }
}
