package com.wu.lib_base.service.ft_login.service.impl;

import android.content.Context;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wu.lib_base.service.ft_login.model.user.User;
import com.wu.lib_base.service.ft_login.service.LoginService;

/**
 * @author wuhaoxuan
 * 对LoginService包装，业务方直接调用，无需再自己初始化service类
 */
public class LoginImpl {

  @Autowired(name = "/login/login_service") protected LoginService mLoginService;

  private static LoginImpl mLoginImpl = null;

  public static LoginImpl getInstance() {
    if (mLoginImpl == null) {
      synchronized (LoginImpl.class) {
        if (mLoginImpl == null) {
          mLoginImpl = new LoginImpl();
        }
        return mLoginImpl;
      }
    }
    return mLoginImpl;
  }

  private LoginImpl() {
    //初始化LoginService
    ARouter.getInstance().inject(this);
  }

  public void login(Context context) {
    mLoginService.login(context);
  }

  public boolean hasLogin() {
    return mLoginService.hasLogin();
  }

  public void removeUser() {
    mLoginService.removeUser();
  }

  public User getUserInfo() {
    return mLoginService.getUserInfo();
  }
}
