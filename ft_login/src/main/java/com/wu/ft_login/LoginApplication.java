package com.wu.ft_login;

import android.app.Application;

import com.qihoo360.replugin.RePlugin;
import com.wu.ft_login.service.aidl.LoginServiceImpl;
import com.wu.lib_base.ft_login.LoginPluginConfig;

/**
 * 登录application入口
 */
public class LoginApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //注册AIDL的具体实现
        RePlugin.registerPluginBinder(LoginPluginConfig.KEY_INTERFACE, new LoginServiceImpl());
    }
}
