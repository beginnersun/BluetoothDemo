package com.example.bluetoothdemo.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.activity.GameImageView;
import com.example.bluetoothdemo.activity.RenderingView;

import androidx.annotation.Nullable;

public class LoginActivity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setAction("com.google.zxing.client.android.scan");
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                startActivity(intent);
//            }
//        });
        final GameImageView renderingView = findViewById(R.id.render);
        renderingView.post(new Runnable() {
            @Override
            public void run() {

                renderingView.start();
            }
        });
//        renderingView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.timg));
    }
}
