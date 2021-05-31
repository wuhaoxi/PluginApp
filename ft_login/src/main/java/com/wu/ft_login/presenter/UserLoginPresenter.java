package com.wu.ft_login.presenter;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.wu.ft_login.api.MockData;
import com.wu.ft_login.api.RequestCenter;
import com.wu.ft_login.inter.IUserLoginPresenter;
import com.wu.ft_login.inter.IUserLoginView;
import com.wu.ft_login.manager.UserManager;
import com.wu.lib_base.service.ft_login.LoginPluginConfig;
import com.wu.lib_base.service.ft_login.model.user.User;
import com.wu.lib_network.okhttp.response.listener.DisposeDataListener;

/**
 * @author wuhaoxuan
 * @description 登录页面对应的Presenter
 */
public class UserLoginPresenter implements IUserLoginPresenter, DisposeDataListener {

    private IUserLoginView mIView;
    private Context mContext;

    public UserLoginPresenter(IUserLoginView iView, Context context) {
        this.mIView = iView;
        this.mContext = context;
    }

    @Override
    public void login(String username, String password) {
        mIView.showLoadingView();
        RequestCenter.login(this);
    }

    @Override
    public void onSuccess(Object responseObj) {
        mIView.hideLoadingView();
        User user = (User) responseObj;
        UserManager.getInstance().setUser(user);
        //发送登录登录广播
        sendUserBroadcast(user);
        mIView.finishActivity();
    }

    @Override
    public void onFailure(Object reasonObj) {
        mIView.hideLoadingView();
        onSuccess(new Gson().fromJson(MockData.LOGIN_DATA, User.class));
        mIView.showLoginFailedView();
    }


    private void sendUserBroadcast(User user) {
        Intent intent = new Intent();
        intent.setAction(LoginPluginConfig.ACTION.LOGIN_SUCCESS_ACTION);
        intent.putExtra(LoginPluginConfig.ACTION.KEY_DATA, new Gson().toJson(user));
        mContext.sendBroadcast(intent);
    }


}
