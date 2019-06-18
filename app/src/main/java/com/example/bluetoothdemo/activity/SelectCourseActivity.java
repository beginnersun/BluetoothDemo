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
import com.example.bluetoothdemo.activity.bean.CurseBean;

import java.util.List;

import androidx.annotation.Nullable;

public class SelectCourseActivity extends Activity {

    TextView title;
    ListView courses;
    String id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        
        title = findViewById(R.id.tv_title);
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
        private List<CurseBean> datas;

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
            View view = LayoutInflater.from(context).inflate(R.layout.item_select_course,parent,false);
            final CurseBean bean = (CurseBean) getItem(position);
            TextView num = view.findViewById(R.id.num);
            TextView name = view.findViewById(R.id.name);
            TextView teacher = view.findViewById(R.id.teacher);
            TextView credit = view.findViewById(R.id.credit);
            final Button select = view.findViewById(R.id.select);

            if (bean.haveId(id)){
                select.setText("已选");
            }else {
                select.setText("选择");
            }

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (select.getText().equals("选择")){
                        bean.addId(id);
                    }else {
                        bean.removeId(id);
                    }
                    notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
