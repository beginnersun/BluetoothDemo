package com.example.bluetoothdemo.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        if (clickListener != null){
            this.itemView.setOnClickListener(clickListener);
        }
    }
}
