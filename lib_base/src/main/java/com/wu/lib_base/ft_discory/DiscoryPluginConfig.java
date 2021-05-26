package com.wu.lib_base.ft_discory;

/**
 * discory插件信息类
 */
public class DiscoryPluginConfig {

    public static final String PLUGIN_NAME = "ft_discory"; //插件名

    public static final String PACKAGE_NAME = "com.wu.ft_discory"; //插件包名

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface"; //interface名

    /**
     * 插件对外暴露的所有页面
     */
    public static class PAGE {

        public static final String PAGE_DISCORY = PACKAGE_NAME + ".view.DiscoryFragment"; //路由名

    }

    /**
     * 插件对外暴露的所有Action
     */
    public static class ACTION {

        public static final String KEY_DATA = PACKAGE_NAME + ".key.data";

    }



}
