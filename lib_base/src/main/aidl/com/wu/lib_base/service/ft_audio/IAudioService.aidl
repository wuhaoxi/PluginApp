// IAudioService.aidl
package com.wu.lib_base.service.ft_audio;

// Declare any non-default types here with import statements

interface IAudioService {

    //添加歌曲到播放队列
    void addAudio(String audio);

    //暂停播放器
    void pauseAudio();

    //继续播放
    void resumeAudio();

}
