package com.wu.voice.application;

import android.annotation.SuppressLint;
import android.util.Log;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginConfig;
import com.wu.lib_base.ft_discory.DiscoryPluginConfig;
import com.wu.lib_base.ft_friend.FriendPluginConfig;
import com.wu.lib_base.ft_home.HomePluginConfig;
import com.wu.lib_base.ft_loading.LoadingPluginConfig;
import com.wu.lib_base.ft_login.LoginPluginConfig;
import com.wu.lib_base.ft_mine.MinePluginConfig;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VoiceApplication extends RePluginApplication {

    private static final String TAG = VoiceApplication.class.getSimpleName();

    private static VoiceApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //视频SDK初始化
        //VideoHelper.init(this);
        //音视频SDK初始化
        //AudioHelper.init(this);
        //分享SDK初始化
        //ShareManager.init(this);
        //更新组件下载
        //UpdateHelper.init(this);

        preloadPlugin();
    }

    /**
     * 预加载关键插件，io线程中
     */
    @SuppressLint("CheckResult")
    private void preloadPlugin() {
        Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return RePlugin.preload(LoadingPluginConfig.PLUGIN_NAME) &&
                    RePlugin.preload(HomePluginConfig.PLUGIN_NAME) &&
                    RePlugin.preload(MinePluginConfig.PLUGIN_NAME) &&
                    RePlugin.preload(DiscoryPluginConfig.PLUGIN_NAME) &&
                    RePlugin.preload(FriendPluginConfig.PLUGIN_NAME);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean o) {
                        Log.d(TAG, "plugin preload success");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Log.e(TAG, "plugin preload has problem: " + throwable.getMessage());
                    }
                });
    }

    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig config = super.createConfig();
        //使插件能用宿主工程的类
        config.setUseHostClassIfNotFound(true);
        return config;
    }

    public static VoiceApplication getInstance() {
        return mApplication;
    }
}
