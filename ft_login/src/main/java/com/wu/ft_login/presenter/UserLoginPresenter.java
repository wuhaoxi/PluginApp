package com.wu.ft_login.presenter;

import com.google.gson.Gson;
import com.wu.ft_login.api.MockData;
import com.wu.ft_login.api.RequestCenter;
import com.wu.ft_login.inter.IUserLoginPresenter;
import com.wu.ft_login.inter.IUserLoginView;
import com.wu.ft_login.manager.UserManager;
import com.wu.lib_base.ft_login.model.LoginEvent;
import com.wu.lib_base.ft_login.model.user.User;
import com.wu.lib_network.okhttp.response.listener.DisposeDataListener;

import org.greenrobot.eventbus.EventBus;

/**
 * @author wuhaoxuan
 * @description 登录页面对应的Presenter
 */
public class UserLoginPresenter implements IUserLoginPresenter, DisposeDataListener {

    private IUserLoginView mIView;

    public UserLoginPresenter(IUserLoginView mIView) {
        this.mIView = mIView;
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
        //发送登录Event
        EventBus.getDefault().post(new LoginEvent());
        mIView.finishActivity();
    }

    @Override
    public void onFailure(Object reasonObj) {
        mIView.hideLoadingView();
        onSuccess(new Gson().fromJson(MockData.LOGIN_DATA, User.class));
        mIView.showLoginFailedView();
    }


}
