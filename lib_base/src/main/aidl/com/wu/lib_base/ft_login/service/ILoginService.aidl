package com.wu.lib_base.ft_login.service;

import com.wu.lib_base.ft_login.model.user.User;

interface ILoginService {

    //是否登录
    boolean hasLogin();

    void removeUser();

    User getUserInfo();

}
