package com.wu.lib_base.ft_loading;

/**
 * loading插件信息类
 */
public class LoadingPluginConfig {

    public static final String PLUGIN_NAME = "ft_loading"; //插件名

    public static final String PACKAGE_NAME = "com.wu.ft_loading"; //插件包名

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface"; //interface名

    /**
     * 插件对外暴露的所有页面
     */
    public static class PAGE {

        public static final String PAGE_LOADING = PACKAGE_NAME + ".view.LoadingActivity"; //interface名

    }

    /**
     * 插件对外暴露的所有Action
     */
    public static class ACTION {

        public static final String KEY_DATA = PACKAGE_NAME + ".key.data";

    }



}
