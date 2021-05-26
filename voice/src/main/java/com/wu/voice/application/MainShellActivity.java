package com.wu.voice.application;

import android.content.Intent;
import android.os.Bundle;

import com.qihoo360.replugin.RePlugin;
import com.wu.lib_base.ft_home.HomePluginConfig;
import com.wu.lib_base.ft_loading.LoadingPluginConfig;
import com.wu.lib_common_ui.base.BaseActivity;

/**
 * 壳Activity， 直接跳转到登录页面
 */
public class MainShellActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //直接启动loading actvity
        Intent intent = RePlugin.createIntent(LoadingPluginConfig.PLUGIN_NAME,
                LoadingPluginConfig.PAGE.PAGE_LOADING);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        RePlugin.startActivity(this, intent);
        finish();
    }
}
