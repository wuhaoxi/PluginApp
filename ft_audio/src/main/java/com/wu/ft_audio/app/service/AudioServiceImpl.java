package com.wu.ft_audio.app.service;

import android.app.Activity;
import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wu.ft_audio.app.AudioHelper;
import com.wu.ft_audio.mediaplayer.core.AudioController;
import com.wu.lib_base.ft_audio.model.CommonAudioBean;
import com.wu.lib_base.ft_audio.service.AudioService;

import java.util.ArrayList;

/**
 * @author wuhaoxuan
 * 音乐播放模块接口实现类
 */
@Route(path = "/audio/audio_service")
public class AudioServiceImpl implements AudioService {

    @Override
    public void pauseAudio() {
        AudioController.getInstance().pause();
    }

    @Override
    public void resumeAudio() {
        AudioController.getInstance().resume();
    }

    @Override
    public void addAudio(Activity activity, CommonAudioBean bean) {
        AudioHelper.addAudio(activity, bean);
    }

    @Override
    public void startMusicService(ArrayList<CommonAudioBean> audios) {
        AudioHelper.startMusicService(audios);
    }

    @Override
    public void init(Context context) {

    }
}
