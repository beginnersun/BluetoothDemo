package com.example.bluetoothdemo.video;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.shuyu.gsyvideoplayer.cache.ICacheManager;

import java.io.File;
import java.util.Map;

import tv.danmaku.ijk.media.exo2.ExoSourceManager;
import tv.danmaku.ijk.media.exo2.IjkExo2MediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;

import static com.google.android.exoplayer2.DefaultLoadControl.*;

public class CustomExoPlayerCacheManager implements ICacheManager {

    protected ExoSourceManager mExoSourceManager;

    @Override
    public void doCacheLogic(Context context, IMediaPlayer mediaPlayer, String url, Map<String, String> header, File cachePath) {
        if (!(mediaPlayer instanceof IjkExo2MediaPlayer)) {
            throw new UnsupportedOperationException("ExoPlayerCacheManager only support IjkExo2MediaPlayer");
        }
        IjkExo2MediaPlayer exoPlayer = ((IjkExo2MediaPlayer) mediaPlayer);
        mExoSourceManager = exoPlayer.getExoHelper();
        //通过自己的内部缓存机制
        exoPlayer.setCache(true);
        exoPlayer.setCacheDir(cachePath);
        exoPlayer.setDataSource(context, Uri.parse(url), header);
        DefaultLoadControl loadControl = new DefaultLoadControl.Builder()
                .setBufferDurationsMs(50000000,50000000,DEFAULT_BUFFER_FOR_PLAYBACK_MS,DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS).createDefaultLoadControl();
        exoPlayer.setLoadControl(loadControl);

    }

    @Override
    public void clearCache(Context context, File cachePath, String url) {
        ExoSourceManager.clearCache(context, cachePath, url);
    }

    @Override
    public void release() {
        mExoSourceManager = null;
    }

    @Override
    public boolean hadCached() {
        return mExoSourceManager != null && mExoSourceManager.hadCached();
    }

    @Override
    public boolean cachePreview(Context context, File cacheDir, String url) {
        return ExoSourceManager.cachePreView(context, cacheDir, url);
    }

    @Override
    public void setCacheAvailableListener(ICacheAvailableListener cacheAvailableListener) {

    }
}
