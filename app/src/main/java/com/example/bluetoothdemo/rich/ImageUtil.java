package com.example.bluetoothdemo.rich;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Home on 2017/4/24.
 */


/**
 * 图片简单处理工具类
 */
public class ImageUtil {

    /**
     * 屏幕宽
     *
     * @param context
     * @return
     */
    public static int getWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 屏幕高
     *
     * @param context
     * @return
     */
    public static int getHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 解决小米、魅族等定制ROM
     * @param context
     * @param intent
     * @return
     */
    public static Uri getUri(Context context , Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = context.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                        Log.i("urishi", uri.toString());
                    }
                }
            }
        }
        return uri;
    }

    /**
     * 根据文件Uri获取路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathByFileUri(Context context, Uri uri) {
        String filePath = null;
        Cursor cursor = context.getContentResolver().query(uri, null, null,
                null, null);
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        return filePath;
    }

    /**
     * 根据图片原始路径获取图片缩略图
     *
     * @param imagePath 图片原始路径
     * @param width		缩略图宽度
     * @param height	缩略图高度
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int width,
                                           int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载直接获取Bitmap宽高
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        if(bitmap == null){
            // 计算缩放比
            int h = options.outHeight;
            int w = options.outWidth;
            Log.i("test", "optionsH"+h+"optionsW"+w);
            int beWidth = w / width;
            int beHeight = h / height;
            int rate = 1;
            if (beWidth < beHeight) {
                rate = beWidth;
            } else {
                rate = beHeight;
            }
            if (rate <= 0) {//图片实际大小小于缩略图,不缩放
                rate = 1;
            }
            options.inSampleSize = rate;
            options.inJustDecodeBounds = false;
            // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    public static Bitmap compressImage(String imagePath,int maxWidth,int maxHeight){
        // 设置参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);
        int height = options.outHeight;
        int width= options.outWidth;
        int max = Math.max(width,height);
        float radio1 = 0,radio2 = 0,radio;
        if (width > maxWidth){
            radio1 = 1f * width / maxWidth;
        }
        if (height > maxHeight){
            radio2 = 1f * height / maxHeight;
        }
        radio = Math.max(radio1,radio2);
        if (radio <= 2){
            radio = 2;
        }
        options.inSampleSize = (int) radio;
        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
        Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 解码文件
        Log.w("TAG", "size: " + bm.getByteCount() + " width: " + bm.getWidth() + " heigth:" + bm.getHeight()); // 输出图像数据
        return bm;
    }

}

