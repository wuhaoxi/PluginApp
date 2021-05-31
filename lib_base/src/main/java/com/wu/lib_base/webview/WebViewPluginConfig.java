package com.wu.lib_base.webview;

/**
 * 网页容器插件
 */
public class WebViewPluginConfig {
  //插件包名
  private final static String PACKAGE_NAME = "com.wu.lib_webview";
  //插件名
  public static String PLUGIN_NAME = "com.wu.lib_webview";
  //插件对外提供方法binder
  public static String KEY_INTERFACE = PACKAGE_NAME + ".interface";

  /**
   * 插件对外暴露页面
   */
  public static class PAGE {
    public static final String PAGE_WEBVIEW = PACKAGE_NAME + ".activity.AdBrowserActivity";
  }

  /**
   * 插件所有Action
   */
  public static class ACTION {
    public static String KEY_DATA = PACKAGE_NAME + ".key.data";
    public static String KEY_URL = "url";
  }
}
