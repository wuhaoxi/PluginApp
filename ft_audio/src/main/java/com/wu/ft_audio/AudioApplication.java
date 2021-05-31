package com.wu.ft_audio;

import android.app.Application;

import com.qihoo360.replugin.RePlugin;
import com.wu.ft_audio.app.AudioHelper;
import com.wu.ft_audio.app.service.aidl.AudioServiceImpl;
import com.wu.lib_base.service.ft_audio.AudioPluginConfig;
import com.wu.lib_share.share.ShareManager;

public class AudioApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AudioHelper.init(this);
        ShareManager.init(this);

        /**
         * 提供AIDL接口
         */
        RePlugin.registerPluginBinder(AudioPluginConfig.KEY_INTERFACE, new AudioServiceImpl());
    }
}
