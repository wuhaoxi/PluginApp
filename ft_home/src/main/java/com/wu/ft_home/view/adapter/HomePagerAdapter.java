package com.wu.ft_home.view.adapter;


import android.content.Context;

import com.qihoo360.replugin.RePlugin;
import com.wu.ft_home.model.CHANNEL;
import com.wu.lib_base.ft_discory.DiscoryPluginConfig;
import com.wu.lib_base.ft_friend.FriendPluginConfig;
import com.wu.lib_base.ft_mine.MinePluginConfig;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomePagerAdapter extends FragmentPagerAdapter {

    private CHANNEL[] mList;

    public HomePagerAdapter(FragmentManager fm, CHANNEL[] datas) {
        super(fm);
        mList = datas;
    }

    @Override
    public Fragment getItem(int position) {
        int type = mList[position].getValue();
        switch (type) {
            case CHANNEL.MINE_ID:
                return getCorrertFragment(MinePluginConfig.PLUGIN_NAME, MinePluginConfig.PAGE.PAGE_MINE);
            case CHANNEL.DISCORY_ID:
                return getCorrertFragment(DiscoryPluginConfig.PLUGIN_NAME, DiscoryPluginConfig.PAGE.PAGE_DISCORY);
            case CHANNEL.FRIEND_ID:
                return getCorrertFragment(FriendPluginConfig.PLUGIN_NAME, FriendPluginConfig.PAGE.PAGE_FRIEND);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.length;
    }

    /**
     * 获取插件中的mineFragment
     * @return
     */
    private Fragment getCorrertFragment(String pluginName, String pageClass) {
        Fragment fragment = null;
        //拿到插件Context
        Context pluginContext = RePlugin.fetchContext(pluginName);
        if (pluginContext != null) {
            //获取插件的ClassLoader
            ClassLoader classLoader = RePlugin.fetchClassLoader(pluginName);
            try {
                fragment = classLoader.loadClass(pageClass)
                        .asSubclass(Fragment.class)
                        .newInstance();
            } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fragment;
    }
}
