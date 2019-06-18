package com.example.bluetoothdemo.activity;

import android.app.Activity;
import android.content.Context;
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

public class CourseActivity extends Activity {

    TextView title;
    TextView tvMenu;
    ListView courses;
    ImageView ivBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


        title = findViewById(R.id.tv_title);
        tvMenu = findViewById(R.id.tv_menu);
        courses = findViewById(R.id.course_list);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public class CourseAdapter extends BaseAdapter {

        private Context context;
        private List datas;

        public CourseAdapter(Context context, List datas) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_course,parent,false);
            TextView num = view.findViewById(R.id.num);
            TextView name = view.findViewById(R.id.name);
            TextView teacher = view.findViewById(R.id.teacher);
            TextView credit = view.findViewById(R.id.credit);
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
