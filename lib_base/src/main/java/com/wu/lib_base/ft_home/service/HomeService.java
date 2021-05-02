package com.wu.lib_base.ft_home.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * @author wuhaoxuan
 * @description 首页模块对外暴露的功能接口
 */
public interface HomeService extends IProvider {

    void startHomeActivity(Context context);
}
