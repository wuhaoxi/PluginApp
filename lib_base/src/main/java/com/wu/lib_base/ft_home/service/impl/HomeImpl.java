package com.wu.lib_base.ft_home.service.impl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wu.lib_base.ft_home.service.HomeService;

/**
 * @author wuhaoxuan
 * @description 对HomeService进行包装，使接口更方便
 */
public class HomeImpl {

    private static HomeImpl mHomeImpl = null;

    @Autowired(name = "/home/home_service")
    protected HomeService mHomeService;

    public static HomeImpl getInstance() {
        if (mHomeImpl == null) {
            synchronized (HomeImpl.class) {
                if (mHomeImpl == null) {
                    mHomeImpl = new HomeImpl();
                }
            }
        }
        return mHomeImpl;
    }

    private HomeImpl() {
        ARouter.getInstance().inject(this);
    }

    public void startHomeActivity(Context context) {
        mHomeService.startHomeActivity(context);
    }
}
