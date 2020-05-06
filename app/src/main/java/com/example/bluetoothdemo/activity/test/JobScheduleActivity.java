package com.example.bluetoothdemo.activity.test;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.app.job.JobInfo.DEFAULT_INITIAL_BACKOFF_MILLIS;

public class JobScheduleActivity extends AppCompatActivity {

    private int jobId = 0;
    private ComponentName componentName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Message.obtain();
    }

    public void initJobInfo(){
        componentName = new ComponentName(this,MyJobService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobInfo.Builder builder = new JobInfo.Builder(jobId,getComponentName());
            builder.setMinimumLatency(6*1000); //设置至少延迟多久后执行
            builder.setOverrideDeadline(10*1000); //设置最多延迟多久后执行
            /**
             * 设置需要的网络条件  有三个取值
             * JobInfo.NETWORK_TYPE_NONE   无网络时执行
             * JobInfo.NETWORK_TYPE_ANY    有网络时执行
             * JobInfo.NETWORK_TYPE_UNMETERED  网络无需付费时（大概指的是wifi）
             */
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
            builder.setRequiresDeviceIdle(true); //是否在空闲时 执行

//            builder.setPeriodic();   //设置周期执行
//            builder.setPersisted()   //重启之后任务是否还要继续执行。

            /**
             * 设置冲突回避    调度失败需要重试时   等待时间间隔（第一个参数是间隔时间  第二个是间隔模式有两种BACKOFF_POLICY_LINEAR 线性增长  BACKOFF_POLICY_EXPONENTIAL 二进制指数增长
             */
            builder.setBackoffCriteria(DEFAULT_INITIAL_BACKOFF_MILLIS,JobInfo.BACKOFF_POLICY_EXPONENTIAL);

            JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            scheduler.schedule(builder.build());
        }
    }

    public void initJobService(){
        MyJobService jobService = new MyJobService();
        startService(new Intent(this,MyJobService.class));
//        jobService.jobFinished();
    }


    /**
     * 此类只有21 也就是5.0包括5.0才能使用
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    class MyJobService extends JobService{
        /**
         * 任务开始执行时触发
         * @param params
         * @return false 代表执行完毕
         *          true代表尚未执行完毕 需开发者自己调用jobFinished通知结束
         */
        @Override
        public boolean onStartJob(JobParameters params) {
            return false;
        }

        /**
         * 收到取消请求
         * @param params
         * @return false 需要手动调用jobFinished再执行关闭
         */
        @Override
        public boolean onStopJob(JobParameters params) {
            return false;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return super.onStartCommand(intent, flags, startId);
        }


    }
}
