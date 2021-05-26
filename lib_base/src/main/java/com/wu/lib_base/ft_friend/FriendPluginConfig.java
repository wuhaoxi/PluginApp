package com.wu.lib_base.ft_friend;

/**
 * friend插件信息类
 */
public class FriendPluginConfig {

    public static final String PLUGIN_NAME = "ft_friend"; //插件名

    public static final String PACKAGE_NAME = "com.wu.ft_friend"; //插件包名

    public static final String KEY_INTERFACE = PACKAGE_NAME + ".interface"; //interface名

    /**
     * 插件对外暴露的所有页面
     */
    public static class PAGE {

        public static final String PAGE_FRIEND = PACKAGE_NAME + ".view.FriendFragment"; //路由名

    }

    /**
     * 插件对外暴露的所有Action
     */
    public static class ACTION {

        public static final String KEY_DATA = PACKAGE_NAME + ".key.data";

    }



}
