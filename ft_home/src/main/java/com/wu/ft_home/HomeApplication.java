package com.wu.ft_home;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * 首页业务入口类
 */
public class HomeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
