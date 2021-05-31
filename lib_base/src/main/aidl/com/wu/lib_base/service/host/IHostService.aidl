// IHostService.aidl
package com.wu.lib_base.service.host;

interface IHostService {

    int getHostVersionCode();

    String getHostVersionName();

    String getHostBuildType();

    boolean isHostDebug();

    String getHostFlavor();

}
