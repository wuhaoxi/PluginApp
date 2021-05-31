package com.wu.lib_plugin_manager.api;

import com.wu.lib_network.okhttp.CommonOkHttpClient;
import com.wu.lib_network.okhttp.request.CommonRequest;
import com.wu.lib_network.okhttp.request.RequestParams;
import com.wu.lib_network.okhttp.response.listener.DisposeDataHandle;
import com.wu.lib_network.okhttp.response.listener.DisposeDataListener;
import com.wu.lib_plugin_manager.model.HomeConfigResponse;

/**
 * 请求中心
 */
public class RequestCenter {

    static class HttpConstants {

        private static final String ROOT_URL = "http://39.97.122.129";

        /**
         * 首页请求接口
         */
        private static String HOME_CONFIG = ROOT_URL + "/module_voice/home_config";

    }

    public static void getRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> cls) {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url, params), new DisposeDataHandle(listener, cls));
    }

    public static void requestConfigData(DisposeDataListener listener) {
        RequestCenter.getRequest(HttpConstants.HOME_CONFIG, null, listener, HomeConfigResponse.class);
    }


}
