package com.example.bluetoothdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bluetoothdemo.R;

import java.util.List;

import androidx.annotation.Nullable;

public class TeachActivity extends Activity {

    TextView title;
    TextView tvMenu;
    ListView students;
    ImageView ivBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach);

        title = findViewById(R.id.tv_title);
        tvMenu = findViewById(R.id.tv_menu);
        ivBack = findViewById(R.id.iv_back);
        students = findViewById(R.id.student_list);

        title.setText("学生信息");
        tvMenu.setText("增加");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(TeachActivity.this,AddStudentActivity.class),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                if (data!=null){
                    String name = data.getStringExtra("name");
                    String num = data.getStringExtra("num");
                    String password = data.getStringExtra("password");
                }
            }
        }
    }

    public class StudentAdapter extends BaseAdapter{

        private Context context;
        private List datas;

        public StudentAdapter(Context context, List datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas == null?0:datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas==null?null:datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_student,parent,false);
            TextView num = view.findViewById(R.id.num);
            TextView name = view.findViewById(R.id.name);
            TextView password = view.findViewById(R.id.password);
            Button edit = view.findViewById(R.id.edit);
            Button delete = view.findViewById(R.id.delete);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
