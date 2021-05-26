package com.wu.ft_home.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.loader.s.PluginServiceClient;
import com.wu.ft_home.R;
import com.wu.ft_home.model.CHANNEL;
import com.wu.ft_home.utils.Utils;
import com.wu.ft_home.view.adapter.HomePagerAdapter;
import com.wu.lib_base.ft_audio.AudioPluginConfig;
import com.wu.lib_base.ft_audio.model.CommonAudioBean;
import com.wu.lib_base.ft_audio.service.impl.AudioImpl;
import com.wu.lib_base.ft_home.HomePluginConfig;
import com.wu.lib_base.ft_login.LoginPluginConfig;
import com.wu.lib_base.ft_login.model.user.User;
import com.wu.lib_base.ft_login.service.ILoginService;
import com.wu.lib_common_ui.base.constant.Constant;
import com.wu.lib_common_ui.base.plugin.PluginBaseActivity;
import com.wu.lib_common_ui.pager_indictor.ScaleTransitionPagerTitleView;
import com.wu.lib_image_loader.app.ImageLoaderManager;
import com.wu.lib_update.app.UpdateHelper;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends PluginBaseActivity implements View.OnClickListener {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private static final CHANNEL[] CHANNELS =
            new CHANNEL[] {CHANNEL.MY, CHANNEL.DISCORY, CHANNEL.FRIEND};

    private UpdateReceiver mUpdateReceiver;
    private UserBroadcastReceiver mUserBroadcastReceiver;

    /**
     * View
     */
    private DrawerLayout mDrawerLayout;
    private View mToggleView;
    private View mSearchView;
    private ViewPager mViewPager;
    private HomePagerAdapter mAdapter;
    private View mDrawerQrcodeView;
    private View mDrawerShareView;
    private View unLogginLayout;
    private ImageView mPhotoView;

    private RelativeLayout mBottomContainer;

    /**
     * data
     */
    private ArrayList<CommonAudioBean> mLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerBroadcastReceiver();
        registerUserReceiver();
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initService();
    }

    private void initData() {
        mLists.add(new CommonAudioBean("100001", "http://sp-sycdn.kuwo.cn/resource/n2/85/58/433900159.mp3",
                "以你的名字喊我", "周杰伦", "七里香", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698076304&di=e6e99aa943b72ef57b97f0be3e0d2446&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fblog%2F201401%2F04%2F20140104170315_XdG38.jpeg",
                "4:30"));
        mLists.add(
                new CommonAudioBean("100002", "http://sq-sycdn.kuwo.cn/resource/n1/98/51/3777061809.mp3", "勇气",
                        "梁静茹", "勇气", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698193627&di=711751f16fefddbf4cbf71da7d8e6d66&imgtype=jpg&src=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D213168965%2C1040740194%26fm%3D214%26gp%3D0.jpg",
                        "4:40"));
        mLists.add(
                new CommonAudioBean("100003", "http://sp-sycdn.kuwo.cn/resource/n2/52/80/2933081485.mp3", "灿烂如你",
                        "汪峰", "春天里", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698239736&di=3433a1d95c589e31a36dd7b4c176d13a&imgtype=0&src=http%3A%2F%2Fpic.zdface.com%2Fupload%2F201051814737725.jpg",
                        "3:20"));
        mLists.add(
                new CommonAudioBean("100004", "http://sr-sycdn.kuwo.cn/resource/n2/33/25/2629654819.mp3", "小情歌",
                        "五月天", "小幸运", "电影《不能说的秘密》主题曲,尤其以最美的不是下雨天,是与你一起躲过雨的屋檐最为经典",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1559698289780&di=5146d48002250bf38acfb4c9b4bb6e4e&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20131220%2Fbki-20131220170401-1254350944.jpg",
                        "2:45"));

        //AudioImpl.getInstance().startMusicService(mLists);
    }

    //启动插件中的前台service
    private void initService() {
        Intent intent = RePlugin.createIntent(AudioPluginConfig.PLUGIN_NAME, AudioPluginConfig.PAGE.PAGE_MUSIC_SERVICE);
        intent.setAction(AudioPluginConfig.ACTION.KEY_ACTION_PLAY);
        startService(intent);
    }

    private void initView() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggleView = findViewById(R.id.toggle_view);
        mToggleView.setOnClickListener(this);
        mSearchView = findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        //初始化adpater
        mAdapter = new HomePagerAdapter(getSupportFragmentManager(), CHANNELS);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        initMagicIndicator();

        mDrawerQrcodeView = findViewById(R.id.home_qrcode);
        mDrawerQrcodeView.setOnClickListener(this);
        mDrawerShareView = findViewById(R.id.home_music);
        mDrawerShareView.setOnClickListener(this);
        findViewById(R.id.online_music_view).setOnClickListener(this);
        findViewById(R.id.check_update_view).setOnClickListener(this);

        //登录相关UI
        unLogginLayout = findViewById(R.id.unloggin_layout);
        unLogginLayout.setOnClickListener(this);
        mPhotoView = findViewById(R.id.avatr_view);
        findViewById(R.id.exit_layout).setOnClickListener(this);

        mBottomContainer = findViewById(R.id.bottom_view);
        //查找插件中的BottomMusicView
        Context pluginContext = RePlugin.fetchContext(AudioPluginConfig.PLUGIN_NAME);
        if (pluginContext != null) {
            ClassLoader pluginClassLoader = RePlugin.fetchClassLoader(AudioPluginConfig.PLUGIN_NAME);
            try {
                Fragment fragment = pluginClassLoader.loadClass(AudioPluginConfig.PAGE.PAGE_MUSIC_BOTTOM)
                        .asSubclass(Fragment.class)
                        .newInstance();
                getSupportFragmentManager().
                        beginTransaction().add(R.id.bottom_view, fragment).commit();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }


    }

    //初始化指示器
    private void initMagicIndicator() {
        MagicIndicator magicIndicator = findViewById(R.id.magic_indicator);
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() { return CHANNELS == null ? 0 : CHANNELS.length; }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(CHANNELS[index].getKey());
                simplePagerTitleView.setTextSize(19);
                simplePagerTitleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#333333"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                return 1.0f;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //退出不销毁task中activity
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unRegisterBroadcastReceiver();
        unRegisterUserReceiver();
    }


    @Override
    public void doCameraPermission() {
        ARouter.getInstance().build(Constant.Router.ROUTER_CAPTURE_ACTIVIYT).navigation();
    }

    private void goToMusic() {
        ARouter.getInstance().build(Constant.Router.ROUTER_MUSIC_ACTIVIYT).navigation();
    }

    private void gotoWebView(String url) {
        ARouter.getInstance()
                .build(Constant.Router.ROUTER_WEB_ACTIVIYT)
                .withString("url", url)
                .navigation();
    }

    //启动检查更新
    private void checkUpdate() {
        UpdateHelper.checkUpdate(this);
    }

    private void registerBroadcastReceiver() {
        if (mUpdateReceiver == null) {
            mUpdateReceiver = new UpdateReceiver();
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(mUpdateReceiver, new IntentFilter(UpdateHelper.UPDATE_ACTION));
        }
    }

    private void unRegisterBroadcastReceiver() {
        if (mUpdateReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mUpdateReceiver);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.exit_layout) {
            finish();
            System.exit(0);
        } else if (id == R.id.unloggin_layout) {
            //获取对应插件提供的IBinder
            IBinder binder =
                    RePlugin.fetchBinder(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.KEY_INTERFACE);
            if (binder == null) {
                return;
            }
            //拿到，进行强转
            ILoginService loginService = ILoginService.Stub.asInterface(binder);
            try {
                if (!loginService.hasLogin()) {
                    Intent intent = RePlugin.createIntent(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.PAGE.PAGE_LOGIN);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    RePlugin.startActivity(this, intent);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.toggle_view) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        } else if (id == R.id.home_qrcode) {
            if (hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)) {
                doCameraPermission();
            } else {
                requestPermission(Constant.HARDWEAR_CAMERA_CODE, Constant.HARDWEAR_CAMERA_PERMISSION);
            }
        } else if (id == R.id.home_music) {//shareFriend();
            goToMusic();
        } else if (id == R.id.online_music_view) {//跳到指定webactivity
            //跳到指定webactivity
            if (hasPermission(Constant.WRITE_READ_EXTERNAL_PERMISSION)) {
                doSDCardPermission();
            } else {
                requestPermission(Constant.WRITE_READ_EXTERNAL_CODE,
                        Constant.WRITE_READ_EXTERNAL_PERMISSION);
            }
        } else if (id == R.id.check_update_view) {
            checkUpdate();
        }
    }

    public class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //启动安装页面
            context.startActivity(
                    Utils.getInstallApkIntent(context, intent.getStringExtra(UpdateHelper.UPDATE_FILE_KEY)));
        }
    }

    private void registerUserReceiver() {
        if (mUserBroadcastReceiver == null) {
            mUserBroadcastReceiver = new UserBroadcastReceiver();
        }
        registerReceiver(mUserBroadcastReceiver,
                new IntentFilter(LoginPluginConfig.ACTION.LOGIN_SUCCESS_ACTION));
    }

    private void unRegisterUserReceiver() {
        if (mUserBroadcastReceiver != null) {
            unregisterReceiver(mUserBroadcastReceiver);
        }
    }

    private void updateLoginUI(String data) {
        unLogginLayout.setVisibility(View.GONE);
        mPhotoView.setVisibility(View.VISIBLE);
        ImageLoaderManager.getInstance()
                .displayImageForCircle(mPhotoView, new Gson().fromJson(data, User.class).data.photoUrl);

    }

    private class UserBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (LoginPluginConfig.ACTION.LOGIN_SUCCESS_ACTION.equals(action)) {
                updateLoginUI(intent.getStringExtra(LoginPluginConfig.ACTION.KEY_DATA));
            }
        }
    }
}
