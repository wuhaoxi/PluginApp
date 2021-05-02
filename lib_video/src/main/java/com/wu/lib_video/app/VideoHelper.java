package com.wu.lib_video.app;

import android.content.Context;

/**
 * @function 用来为调用者创建视频
 */
public class VideoHelper {

    //SDK全局context
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        //初始化SDK的时候，初始化Realm数据库
    }

    public static Context getContext() {
        return mContext;
    }

}
