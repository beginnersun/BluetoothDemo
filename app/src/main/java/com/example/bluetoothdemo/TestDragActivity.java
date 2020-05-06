package com.example.bluetoothdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

public class TestDragActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_drag);

        TextView two = findViewById(R.id.two);

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestDragActivity.this,"点击事件",Toast.LENGTH_LONG).show();
            }
        });

//        two.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(TestDragActivity.this,"长按事件",Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

//        final DragViewGroup linearLayout = findViewById(R.id.ll_drag);
//        final View root = findViewById(R.id.rootView);

//        linearLayout.setOpenDrag(false);
//
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(TestDragActivity.this,"点击事件",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                Toast.makeText(TestDragActivity.this,"长按事件",Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

    }
}
