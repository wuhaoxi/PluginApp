package com.wu.ft_audio.app;

import android.app.Activity;
import android.content.Context;

import com.wu.ft_audio.mediaplayer.core.AudioController;
import com.wu.ft_audio.mediaplayer.core.MusicService;
import com.wu.ft_audio.mediaplayer.db.GreenDaoHelper;
import com.wu.ft_audio.mediaplayer.model.AudioBean;
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

    //外部启动MusicService方法
    public static void startMusicService(ArrayList<CommonAudioBean> audioBeans) {
        MusicService.startMusicService(Utils.convertFrom(audioBeans));
    }

    public static void addAudio(Activity activity, CommonAudioBean audioBean) {
        AudioController.getInstance().addAudio(Utils.convertFrom(audioBean));
        MusicPlayerActivity.start(activity);
    }

    public static void pauseAudio() {
        AudioController.getInstance().pause();
    }

    public static void resumeAudio() {
        AudioController.getInstance().resume();
    }
}
