package com.wu.ft_loading.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.wu.ft_loading.R;
import com.wu.lib_base.ft_home.HomePluginConfig;
import com.wu.lib_common_ui.base.constant.Constant;
import com.wu.lib_common_ui.base.plugin.PluginBaseActivity;
import com.wu.lib_plugin_manager.PluginManager;
import com.wu.lib_pullalive.app.AliveJobService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class LoadingActivity extends PluginBaseActivity {

    private static final String TAG = "LoadingActivity";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Intent intent = RePlugin.createIntent(HomePluginConfig.PLUGIN_NAME, HomePluginConfig.PAGE.PAGE_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            RePlugin.startActivity(LoadingActivity.this, intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_layout);
        pullAliveService();
        if (hasPermission(Constant.WRITE_READ_EXTERNAL_PERMISSION)) {
            doSDCardPermission();
        } else {
            requestPermission(Constant.WRITE_READ_EXTERNAL_CODE, Constant.WRITE_READ_EXTERNAL_PERMISSION);
        }
    }

    private void pullAliveService() {
        AliveJobService.start(this);
    }

    @Override
    public void doSDCardPermission() {
        mHandler.sendEmptyMessageDelayed(0, 3000);
        // TODO
        PluginManager.getInstance().requestPluginConfigData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
