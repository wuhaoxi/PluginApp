package com.wu.ft_loading.view.loading;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.wu.ft_loading.R;
import com.wu.lib_base.ft_home.service.impl.HomeImpl;
import com.wu.lib_common_ui.base.BaseActivity;
import com.wu.lib_common_ui.base.constant.Constant;
import com.wu.lib_pullalive.app.AliveJobService;

public class LoadingActivity extends BaseActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            HomeImpl.getInstance().startHomeActivity(LoadingActivity.this);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
