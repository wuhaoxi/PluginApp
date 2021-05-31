package com.wu.voice.application.service;

import com.wu.lib_base.service.host.IHostService;
import com.wu.voice.BuildConfig;

/**
 * 宿主工程对外AIDL实现类
 */
public class IHostServiceImpl extends IHostService.Stub {

    /**
     * 宿主工程版本名
     * @return
     */
    @Override
    public int getHostVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 宿主工程版本号
     * @return
     */
    @Override
    public String getHostVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 宿主工程编译类型
     * @return
     */
    @Override
    public String getHostBuildType() {
        return BuildConfig.BUILD_TYPE;
    }

    /**
     * 是否debug类型
     * @return
     */
    @Override
    public boolean isHostDebug() {
        return false;
    }

    /**
     * 宿主工程渠道名
     * @return
     */
    @Override
    public String getHostFlavor() {
        return BuildConfig.FLAVOR;
    }
}
