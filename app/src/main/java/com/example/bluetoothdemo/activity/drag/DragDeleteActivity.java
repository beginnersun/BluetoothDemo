package com.example.bluetoothdemo.activity.drag;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bluetoothdemo.R;
import com.example.bluetoothdemo.activity.drag.adapter.DragAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DragDeleteActivity extends AppCompatActivity implements DragAdapter.OnDragItemClickListener{

    RecyclerView recyclerView;
    DragAdapter adapter;
    List<String> datas = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_delete);
        recyclerView = findViewById(R.id.recyclerView);
        datas.add("1 afsdfasdf");
        datas.add("2 afsdfasdf");
        datas.add("3 afsdfasdf");
        datas.add("4 afsdfasdf");
        datas.add("5 afsdfasdf");
        datas.add("6 afsdfasdf");
        datas.add("7 afsdfasdf");
        datas.add("8 afsdfasdf");
        datas.add("9 afsdfasdf");
        datas.add("10 afsdfasdf");
        datas.add("11 afsdfasdf");
        datas.add("12 afsdfasdf");
        datas.add("13 afsdfasdf");
        datas.add("14 afsdfasdf");
        datas.add("15 afsdfasdf");
        adapter = new DragAdapter(this,datas,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void delete(int position) {
        Toast.makeText(this,"弹窗提示删除"+position,Toast.LENGTH_LONG).show();
    }

    @Override
    public void itemClick(int position) {
        Toast.makeText(this,"弹窗提示点击Item"+position,Toast.LENGTH_LONG).show();

    }
}
