package com.wu.ft_login.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wu.ft_login.R;
import com.wu.ft_login.inter.IUserLoginPresenter;
import com.wu.ft_login.inter.IUserLoginView;
import com.wu.ft_login.presenter.UserLoginPresenter;
import com.wu.lib_common_ui.base.plugin.PluginBaseActivity;

import androidx.annotation.Nullable;

/**
 * @author wuhaoxuan
 * @description 登录界面
 */
public class LoginActivity extends PluginBaseActivity implements IUserLoginView {

    private IUserLoginPresenter mIUserLoginPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        //初始化P层
        mIUserLoginPresenter = new UserLoginPresenter(this, this);
        findViewById(R.id.login_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIUserLoginPresenter.login(getUserName(), getPassword());
            }
        });
    }

    @Override public String getUserName() {
        return "13538442355";
    }

    @Override public String getPassword() {
        return "556677wu";
    }

    @Override public void showLoadingView() {
        //显示加载中UI
    }

    @Override public void hideLoadingView() {
        //隐藏加载布局
    }

    @Override public void showLoginFailedView() {
        //登陆失败处理
    }

    @Override public void finishActivity() {
        finish();
    }
}
