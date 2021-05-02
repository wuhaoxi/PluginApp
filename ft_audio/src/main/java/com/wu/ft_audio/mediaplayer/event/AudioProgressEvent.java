package com.wu.ft_audio.mediaplayer.event;

import com.wu.ft_audio.mediaplayer.core.CustomMediaPlayer;

public class AudioProgressEvent {

    public CustomMediaPlayer.Status mStatus;
    public int progress;
    public int maxLength;

    public AudioProgressEvent(CustomMediaPlayer.Status status, int progress, int maxLength) {
        this.mStatus = mStatus;
        this.progress = progress;
        this.maxLength = maxLength;
    }
}
