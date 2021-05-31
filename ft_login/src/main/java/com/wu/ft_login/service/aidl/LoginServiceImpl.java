package com.wu.ft_login.service.aidl;

import com.wu.ft_login.manager.UserManager;
import com.wu.lib_base.service.ft_login.ILoginService;
import com.wu.lib_base.service.ft_login.model.user.User;

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
