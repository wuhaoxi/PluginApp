package com.wu.lib_network.okhttp.response.listener;

/**
 * @author wuhx
 * @function 监听下载进度
 */
public interface DisposeDownloadListener extends DisposeDataListener {

    void onProgress(int progress);


}
