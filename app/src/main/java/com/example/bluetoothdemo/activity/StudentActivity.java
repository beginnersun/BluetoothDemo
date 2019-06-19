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
import com.example.bluetoothdemo.activity.bean.ResultBean;

import java.util.List;

import androidx.annotation.Nullable;

public class StudentActivity extends Activity {

    TextView title;
    TextView tvMenu;
    ListView results;
    ImageView ivBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        title = findViewById(R.id.tv_title);
        tvMenu = findViewById(R.id.tv_menu);
        ivBack = findViewById(R.id.iv_back);
        results = findViewById(R.id.result_list);

        title.setText("成绩信息");
        tvMenu.setText("选课");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(StudentActivity.this,AddStudentActivity.class),1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                if (data!=null){

                }
            }
        }
    }

    public class ResultAdapter extends BaseAdapter {

        private Context context;
        private List<ResultBean> datas;

        public ResultAdapter(Context context, List datas) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_result,parent,false);
            TextView num = view.findViewById(R.id.num);
            TextView name = view.findViewById(R.id.name);
            TextView teacher = view.findViewById(R.id.teacher);
            TextView credit = view.findViewById(R.id.credit);
            TextView result = view.findViewById(R.id.result);
            ResultBean bean = (ResultBean) getItem(position);


            return view;
        }
    }
}