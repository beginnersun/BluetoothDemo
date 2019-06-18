package com.example.bluetoothdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluetoothdemo.R;

import androidx.annotation.Nullable;

public class AddCourseActivity extends Activity {

    EditText name;
    EditText num;
    EditText teacher;
    EditText credit;
    TextView ok;
    TextView title;
    TextView tvMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        name = findViewById(R.id.name);
        num = findViewById(R.id.num);
        teacher = findViewById(R.id.teacher);
        credit = findViewById(R.id.credit);

        title = findViewById(R.id.tv_title);
        tvMenu = findViewById(R.id.tv_menu);

        title.setText("新增课程信息");
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(num.getText()) || TextUtils.isEmpty(teacher.getText()) || TextUtils.isEmpty(credit.getText())){
                    Toast.makeText(AddCourseActivity.this,"不允许有空值",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("name",name.getText());
                intent.putExtra("num",num.getText());
                intent.putExtra("teacher",teacher.getText());
                intent.putExtra("credit",credit.getText());
                setResult(RESULT_OK,intent);
            }
        });

    }
}
