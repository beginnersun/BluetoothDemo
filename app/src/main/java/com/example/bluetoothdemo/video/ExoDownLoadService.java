package com.example.bluetoothdemo.video;

import android.app.Notification;

import com.example.bluetoothdemo.R;
import com.google.android.exoplayer2.offline.Download;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloadService;
import com.google.android.exoplayer2.scheduler.PlatformScheduler;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.ui.DownloadNotificationHelper;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import androidx.annotation.Nullable;

public class ExoDownLoadService extends DownloadService {
    private static final int JOB_ID = 1;
    private static final int FOREGROUND_NOTIFICATION_ID = 1;

    public ExoDownLoadService() {
        super(
                FOREGROUND_NOTIFICATION_ID,
                DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL,
                "download_channel",
                R.string.app_name,
                /* channelDescriptionResourceId= */ 0);
    }

    @Override
    protected DownloadManager getDownloadManager() {
        return null;
    }

    @Nullable
    @Override
    protected Scheduler getScheduler() {
        return Util.SDK_INT >= 21 ? new PlatformScheduler(this, JOB_ID) : null;
    }

    @Override
    protected Notification getForegroundNotification(List<Download> downloads) {
        return new DownloadNotificationHelper(this,"download_channel").buildProgressNotification(R.drawable.ic_launcher_foreground,null,null,downloads);
    }
}
