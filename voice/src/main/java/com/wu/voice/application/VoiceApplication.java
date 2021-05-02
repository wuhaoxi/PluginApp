package com.wu.voice.application;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wu.ft_audio.app.AudioHelper;
import com.wu.lib_share.share.ShareManager;
import com.wu.lib_update.app.UpdateHelper;
import com.wu.lib_video.app.VideoHelper;

public class VoiceApplication extends Application {

    private static VoiceApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //视频SDK初始化
        VideoHelper.init(this);
        //音视频SDK初始化
        AudioHelper.init(this);
        //分享SDK初始化
        ShareManager.init(this);
        //更新组件下载
        UpdateHelper.init(this);
        //ARouter初始化
        ARouter.init(this);
    }

    public static VoiceApplication getInstance() {
        return mApplication;
    }
}
