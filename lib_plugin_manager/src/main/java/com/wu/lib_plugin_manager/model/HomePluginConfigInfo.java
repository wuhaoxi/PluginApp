package com.wu.lib_plugin_manager.model;

import java.io.Serializable;

public class HomePluginConfigInfo implements Serializable {

  public String mPluginName;

  //当前插件版本号
  public float mPluginVersionCode;

  //下载地址
  public String mPluginUrl;

  //本地保存的地址
  public String mLocalPath;


}
