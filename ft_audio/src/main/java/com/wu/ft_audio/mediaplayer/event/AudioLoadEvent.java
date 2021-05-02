package com.wu.ft_audio.mediaplayer.event;

import com.wu.ft_audio.mediaplayer.model.AudioBean;

public class AudioLoadEvent {

    public AudioBean audioBean;

    public AudioLoadEvent(AudioBean audioBean) {
        this.audioBean = audioBean;
    }
}
