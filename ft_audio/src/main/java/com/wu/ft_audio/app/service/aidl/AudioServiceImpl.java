package com.wu.ft_audio.app.service.aidl;

import com.google.gson.Gson;
import com.wu.ft_audio.mediaplayer.core.AudioController;
import com.wu.ft_audio.mediaplayer.model.AudioBean;
import com.wu.lib_base.service.ft_audio.IAudioService;

/**
 * IAudioService的实现类
 */
public class AudioServiceImpl extends IAudioService.Stub {

    /**
     * 添加歌曲到队列
     * @param audio
     */
    @Override
    public void addAudio(String audio) {
        AudioController.getInstance().addAudio(new Gson().fromJson(audio, AudioBean.class));
    }

    /**
     * 暂停播放器
     */
    @Override
    public void pauseAudio() {
        AudioController.getInstance().pause();
    }

    /**
     * 恢复播放器
     */
    @Override
    public void resumeAudio() {
        AudioController.getInstance().pause();
    }
}
