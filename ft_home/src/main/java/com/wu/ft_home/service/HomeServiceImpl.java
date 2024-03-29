package com.wu.ft_home.service;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.wu.ft_home.view.HomeActivity;
import com.wu.lib_base.ft_home.service.HomeService;

/**
 * @author wuhaoxuan
 * @description 首页模块对外接口实现类
 */
@Route(path = "/home/home_service")
public class HomeServiceImpl implements HomeService {

    @Override
    public void startHomeActivity(Context context) {
        //cHomeActivity.start(context);
    }

    @Override
    public void init(Context context) {
        Log.i(HomeServiceImpl.class.getSimpleName(), "init()");
    }
}
