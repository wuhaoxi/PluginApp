package com.wu.lib_plugin_manager;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.model.PluginInfo;
import com.wu.lib_network.okhttp.CommonOkHttpClient;
import com.wu.lib_network.okhttp.request.CommonRequest;
import com.wu.lib_network.okhttp.response.listener.DisposeDataHandle;
import com.wu.lib_network.okhttp.response.listener.DisposeDataListener;
import com.wu.lib_network.okhttp.utils.ResponseEntityToModule;
import com.wu.lib_plugin_manager.api.MockData;
import com.wu.lib_plugin_manager.api.RequestCenter;
import com.wu.lib_plugin_manager.model.HomeConfigResponse;
import com.wu.lib_plugin_manager.model.HomePluginConfigInfo;

import java.io.File;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class PluginManager {

    private static PluginManager mInstance = null;

    public static PluginManager getInstance() {
        if (mInstance == null) {
            synchronized (PluginManager.class) {
                if (mInstance == null) {
                    mInstance = new PluginManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 服务端插件配置信息
     */
    private HomeConfigResponse mHomeConfigResponse;

    /**
     * 拉取插件配置信息
     */
    public void requestPluginConfigData() {
        RequestCenter.requestConfigData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mHomeConfigResponse = (HomeConfigResponse) responseObj;
            }

            @Override
            public void onFailure(Object reasonObj) {
                //使用模拟数据
                onSuccess(ResponseEntityToModule.
                        parseJsonToModule(MockData.HOME_CONFIG_DATA, HomeConfigResponse.class));
            }
        });
    }

    /**
     * 获取宿主工程中的插件信息
     * @param pluginName
     * @return
     */
    public PluginInfo getPluginInfo(@NonNull final String pluginName) {
        return RePlugin.getPluginInfo(pluginName);
    }


    /**
     * 对外提供插件加载逻辑
     * @param pluginName
     * @return
     */
    public Observable<PluginInfo> loadPlugin(@NonNull final String pluginName) {
        return fetchPluginInfo(pluginName).flatMap(
                new Function<PluginInfo, ObservableSource<HomePluginConfigInfo>>() {
                    @Override
                    public ObservableSource<HomePluginConfigInfo> apply(PluginInfo pluginInfo) {
                        return pluginInfo == null ? getDownloadPluginInfo(pluginName) :
                                getUpdatePluginInfo(pluginName, pluginInfo);
                    }
                }
        ).flatMap(new Function<HomePluginConfigInfo, ObservableSource<File>>() {
            @Override
            public ObservableSource<File> apply(HomePluginConfigInfo homePluginConfigInfo) {
                return downloadPlugin(homePluginConfigInfo.mPluginUrl, homePluginConfigInfo.mLocalPath);
            }
        }).observeOn(Schedulers.io()).flatMap(new Function<File, ObservableSource<PluginInfo>>() {
            @Override
            public ObservableSource<PluginInfo> apply(File file) {
                //插件的安装
                return installPlugin(file.getAbsolutePath());
            }
        }).doOnNext(new Consumer<PluginInfo>() {
            @Override
            public void accept(PluginInfo pluginInfo) {
                //插件预加载
                if (pluginInfo != null) preloadPlugin(pluginInfo);
            }
        });
    }

    private Observable<HomePluginConfigInfo> getServerPluginInfo(@NonNull final String pluginName) {
        return Observable.fromCallable(new Callable<HomePluginConfigInfo>() {
            @Override
            public HomePluginConfigInfo call() {
                if (mHomeConfigResponse != null && mHomeConfigResponse.data != null &&
                        mHomeConfigResponse.data.list != null &&
                        mHomeConfigResponse.data.list.size() > 0) {
                    for (HomePluginConfigInfo info : mHomeConfigResponse.data.list) {
                        if (info.mPluginName.equals(pluginName)) {
                            return info;
                        }
                    }
                }
                return null;
            }
        });
    }

    //获取宿主工程中的插件信息，Observable装饰
    private Observable<PluginInfo> fetchPluginInfo(@NonNull final String pluginName) {
        return Observable.just(RePlugin.getPluginInfo(pluginName));
    }

    //下载插件
    private Observable<File> downloadPlugin(@NonNull final String pluginUrl,
                                            @NonNull final String filePath) {

        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(final ObservableEmitter<File> emitter) throws Exception {
                //文件下载
                CommonOkHttpClient.downloadFile(CommonRequest.createGetRequest(pluginUrl, null),
                        new DisposeDataHandle(new DisposeDataListener() {
                            @Override
                            public void onSuccess(Object o) {
                                File file = (File) o;
                                emitter.onNext(file);
                                emitter.onComplete();
                            }

                            @Override
                            public void onFailure(Object reasonObj) {
                                emitter.onError((Throwable) reasonObj);
                            }
                        }, filePath));
            }
        });
    }

    //安装插件
    private Observable<PluginInfo> installPlugin(@NonNull final String filePath) {
        return Observable.fromCallable(new Callable<PluginInfo>() {
            @Override
            public PluginInfo call() {
                return RePlugin.install(filePath);
            }
        });
    }

    //插件预加载
    private boolean preloadPlugin(@NonNull final PluginInfo info) {
        return RePlugin.preload(info);
    }

    private Observable<HomePluginConfigInfo> getDownloadPluginInfo(@NonNull final String pluginName) {
        return getServerPluginInfo(pluginName);
    }

    private Observable<HomePluginConfigInfo> getUpdatePluginInfo(@NonNull final String pluginName,
        @NonNull final PluginInfo info) {
        return getServerPluginInfo(pluginName).filter(new Predicate<HomePluginConfigInfo>() {
            @Override
            public boolean test(HomePluginConfigInfo homePluginConfigInfo) {
                return info.getVersionValue() < homePluginConfigInfo.mPluginVersionCode;
            }
        });
    }


}
