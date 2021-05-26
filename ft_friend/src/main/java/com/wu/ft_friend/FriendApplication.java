package com.wu.ft_friend;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wu.lib_video.app.VideoHelper;

public class FriendApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        VideoHelper.init(this);
    }
}
