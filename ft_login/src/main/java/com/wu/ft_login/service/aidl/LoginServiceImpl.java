package com.wu.ft_login.service.aidl;

import android.os.RemoteException;

import com.wu.ft_login.manager.UserManager;
import com.wu.lib_base.ft_login.model.user.User;
import com.wu.lib_base.ft_login.service.ILoginService;

/**
 * ILoginServie aidl具体实现
 */
public class LoginServiceImpl extends ILoginService.Stub {

    @Override
    public boolean hasLogin() {
        return UserManager.getInstance().hasLogined();
    }

    @Override
    public void removeUser() {
        UserManager.getInstance().removeUser();
    }

    @Override
    public User getUserInfo() {
        return UserManager.getInstance().getUser();
    }
}
