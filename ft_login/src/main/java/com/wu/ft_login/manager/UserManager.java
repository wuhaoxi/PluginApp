package com.wu.ft_login.manager;


import com.wu.lib_base.ft_login.model.user.User;

/**
 * @author wuhaoxuan
 * @description 单例管理登录用户信息
 */
public class UserManager {

    private static UserManager userManager = null;
    private User user = null;

    public static UserManager getInstance() {
        if (userManager == null) {
            synchronized (UserManager.class) {
                if (userManager == null) {
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    /**
     * init the user
     */
    public void setUser(User user) {

        this.user = user;
    }

    public boolean hasLogined() {

        return user == null ? false : true;
    }

    /**
     * has user info
     */
    public User getUser() {

        return this.user;
    }

    /**
     * remove the user info
     */
    public void removeUser() {

        this.user = null;
    }

}
