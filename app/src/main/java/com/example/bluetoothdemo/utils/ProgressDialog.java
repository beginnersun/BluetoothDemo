package com.example.bluetoothdemo.utils;


import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.bluetoothdemo.R;

import androidx.annotation.NonNull;

public class ProgressDialog extends Dialog {

    private Context context;

    public ProgressDialog(@NonNull Context context) {
        super(context);
        init();
    }


    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress,null);
    }

}
