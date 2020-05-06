package com.example.bluetoothdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.bluetoothdemo.activity.login.ChildBean;
import com.example.bluetoothdemo.rich.ImageUtil;
import com.example.bluetoothdemo.rich.RichEditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class RichActivity extends AppCompatActivity {

    RichEditor richEditor;
    private static final int TAG1 = 1;
    private static final int TAG3 = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        setContentView(R.layout.activity_rich);
        richEditor = findViewById(R.id.rich);


        findViewById(R.id.iv_select_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDateImg();
            }
        });
        findViewById(R.id.iv_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ChildBean bean = new ChildBean();
        Modifier.isPublic(bean.getClass().getModifiers());
        getSupportFragmentManager().beginTransaction();
        getFragmentManager().beginTransaction();
    }


    /**
     * 从系统选择图片
     */
    private void upDateImg() {
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                //具有拍照权限，直接调用相机
                //具体调用代码
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(pickIntent, TAG3);
            } else {
                //不具有拍照权限，需要进行权限申请
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},99);
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, TAG3);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAG3) {
            Uri mUri;
            if (resultCode == RESULT_OK) {
                mUri = data.getData();
                try {
                    String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                    Cursor cursor = getContentResolver().query(mUri, filePathColumn, null,
                            null, null);
                    if (cursor == null) {
                        mUri = ImageUtil.getUri(this, data);
                    }
                    String imagePath = ImageUtil.getFilePathByFileUri(this, mUri);
//                    Bitmap bmp = ImageUtil.compressImage(imagePath,width, (int) (width*0.625));
//                    info_bitmap = bmp;
//                    Message message = new Message();
//                    message.obj = bmp;
//                    message.what = 1;
//                    mHandler.sendMessage(message);
//                    SDCardCheck.getInstances().saveBitmap(AddWxContentActivity.this, bmp, "head_file.jpg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else if (requestCode == TAG1){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            String imagePath = Environment.getExternalStorageDirectory() + "/image" ;
            String name = System.currentTimeMillis() + "2898.jpg";
            FileOutputStream fos = null;
            File file = new File(imagePath);
            if (!file.exists()) {
                file.mkdirs();// 创建文件夹
            }
            String fileName = imagePath + "/" + name;// 保存路径
            try {// 写入SD card
                fos = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.flush();
                    fos.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
//            Bitmap bmp = ImageUtil.compressImage(fileName,width ,(int) (width*0.625));
//            info_bitmap = bmp;
//            Message message = new Message();
//            message.obj = bmp;
//            message.what = 1;
//            mHandler.sendMessage(message);
//            SDCardCheck.getInstances().saveBitmap(AddWxContentActivity.this, bmp, "head_file.jpg");
        }
    }

    class Test{
        final static int a = 0;
    }
}
