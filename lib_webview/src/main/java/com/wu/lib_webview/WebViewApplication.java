package com.wu.lib_webview;


import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class WebViewApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
