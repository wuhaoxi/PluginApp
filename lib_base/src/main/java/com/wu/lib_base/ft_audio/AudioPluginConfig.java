package com.wu.lib_base.ft_audio;

/**
 * Audio插件配置信息
 */
public class AudioPluginConfig {

    public static final String PLUGIN_NAME = "ft_audio"; //插件名

    public static final String PACKAGE_NAME = "com.wu.ft_audio"; //插件包名

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface"; //interface名

    /**
     * 插件对外暴露的所有页面
     */
    public static class PAGE {

        public static final String PAGE_MUSIC_BOTTOM = PACKAGE_NAME + ".mediaplayer.view.BottomMusicFragment"; //路由名

        public static final String PAGE_MUSIC = PACKAGE_NAME + ".mediaplayer.view.MusicPlayerActivity";

        public static final String PAGE_MUSIC_SERVICE = PACKAGE_NAME + ".mediaplayer.core.MusicService";
    }

    /**
     * 插件对外暴露的所有Action
     */
    public static class ACTION {

        public static final String KEY_ACTION_PLAY = PACKAGE_NAME + ".play";

    }

}
