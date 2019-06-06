package com.example.bluetoothdemo.base;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends View> T get(@IdRes int id){
        if (views == null){
            views = new SparseArray<>();
        }
        if (views.get(id) == null){
            views.put(id,itemView.findViewById(id));
        }
        return (T) views.get(id);
    }

    public void setViewClickListener(@IdRes int id, View.OnClickListener clickListener){
        get(id).setOnClickListener(clickListener);
    }

}
