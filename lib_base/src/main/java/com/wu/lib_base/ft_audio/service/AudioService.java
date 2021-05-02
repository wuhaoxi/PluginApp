package com.wu.lib_base.ft_audio.service;

import android.app.Activity;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.wu.lib_base.ft_audio.model.CommonAudioBean;

import java.util.ArrayList;

/**
 * @author wuhaoxuan
 * 音乐模块对外接口
 */
public interface AudioService extends IProvider {

    public void addAudio(Activity activity, CommonAudioBean bean);

    public void pauseAudio();

    public void resumeAudio();

    //外部启动MusicService方法
    public void startMusicService(ArrayList<CommonAudioBean> audios);



}
