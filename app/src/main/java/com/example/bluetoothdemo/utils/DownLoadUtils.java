package com.example.bluetoothdemo.utils;

import android.app.ProgressDialog;
import android.app.usage.ExternalStorageStats;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownLoadUtils {

    public static DownLoadUtils downLoadInstance;

    private int thread_count;

//    private ProgressDialog

    private DownLoadUtils(int thread_count){
        this.thread_count = thread_count;
    }

    public static DownLoadUtils getDownLoadInstance(int thread_count) {
        if (downLoadInstance == null){
            synchronized (DownLoadUtils.class){
                if (downLoadInstance == null){
                    downLoadInstance = new DownLoadUtils(thread_count <= 0?3:thread_count);
                }
            }
        }
        return downLoadInstance;
    }

    public void DownLoadFile(String path) throws IOException {
        URL url = new URL(path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setReadTimeout(6*1000);
        connection.setConnectTimeout(6*1000);
        if (connection.getResponseCode() == 200){
            int file_length = connection.getContentLength();
            int item_size = file_length / thread_count;
            for (int i = 0 ;i < thread_count ;i++){
                if (i==thread_count){
                    new DownLoadThread(i*item_size,thread_count,url).start();
                    return;
                }
                new DownLoadThread(i*item_size,(i+1)*item_size-1,url).start();
            }
        }
        connection.connect();
    }


    class DownLoadThread extends Thread {
        private int start;
        private int end;
        private URL url;
        private String path;

        public DownLoadThread(int start, int end, URL url) {
            this.start = start;
            this.end = end;
            this.url = url;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getPath() {
            if (TextUtils.isEmpty(path)){
                path = Environment.getExternalStorageDirectory().getAbsolutePath() + "\\" +System.currentTimeMillis() + url.getPath().substring(url.getPath().lastIndexOf("."));
            }
            return path;
        }

        @Override
        public void run() {
            try {
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setReadTimeout(6 * 1000);
                connection.setConnectTimeout(6 * 1000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                if (connection.getResponseCode() == 206) {
                    InputStream in = connection.getInputStream();
                    byte[] bytes = new byte[1024];
                    File file = new File(getPath());
                    RandomAccessFile random = new RandomAccessFile(file,"rwd");
                    random.seek(start);
                    int len = 0;
                    while ((len=in.read(bytes))!=-1){
                        random.write(bytes,0,len);
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
