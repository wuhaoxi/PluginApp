package com.wu.lib_base.service.ft_login;

import com.wu.lib_base.service.ft_login.model.user.User;

interface ILoginService {

    //是否登录
    boolean hasLogin();

    void removeUser();

    User getUserInfo();

}
