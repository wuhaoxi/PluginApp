package com.wu.ft_audio.app;

import android.app.Activity;
import android.content.Context;

import com.wu.ft_audio.mediaplayer.core.AudioController;
import com.wu.ft_audio.mediaplayer.core.MusicService;
import com.wu.ft_audio.mediaplayer.db.GreenDaoHelper;
import com.wu.ft_audio.mediaplayer.utils.Utils;
import com.wu.ft_audio.mediaplayer.view.MusicPlayerActivity;
import com.wu.lib_base.ft_audio.model.CommonAudioBean;

import java.util.ArrayList;

/**
 * @function 唯一与外界通信的帮助类
 */
public final class AudioHelper {

    //SDK全局Context,供子模块用
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
        GreenDaoHelper.initDatabase();
    }

    public static Context getContext() {
        return mContext;
    }

}
