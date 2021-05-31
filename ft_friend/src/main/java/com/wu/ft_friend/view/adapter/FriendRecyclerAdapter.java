package com.wu.ft_friend.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.qihoo360.replugin.RePlugin;
import com.wu.ft_friend.R;
import com.wu.lib_base.model.friend.FriendBodyValue;
import com.wu.lib_base.service.ft_audio.AudioPluginConfig;
import com.wu.lib_base.service.ft_audio.IAudioService;
import com.wu.lib_base.service.ft_login.ILoginService;
import com.wu.lib_base.service.ft_login.LoginPluginConfig;
import com.wu.lib_common_ui.MultiImageViewLayout;
import com.wu.lib_common_ui.recyclerview.MultiItemTypeAdapter;
import com.wu.lib_common_ui.recyclerview.base.ItemViewDelegate;
import com.wu.lib_common_ui.recyclerview.base.ViewHolder;
import com.wu.lib_image_loader.app.ImageLoaderManager;
import com.wu.lib_video.videoplayer.core.VideoAdContext;

import java.util.List;

public class FriendRecyclerAdapter extends MultiItemTypeAdapter {


    public static final int MUSIC_TYPE = 0x01; //音乐类型
    public static final int VIDEO_TYPE = 0x02; //视频类型

    private Context mContext;

    public FriendRecyclerAdapter(Context context, List<FriendBodyValue> datas) {
        super(context, datas);
        mContext = context;
        addItemViewDelegate(MUSIC_TYPE, new MusicItemDelegate());
        addItemViewDelegate(VIDEO_TYPE, new VideoItemDelegate());
    }

    /**
     * 音乐类型item
     */
    private class MusicItemDelegate implements ItemViewDelegate<FriendBodyValue> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_friend_list_picture_layout;
        }

        @Override
        public boolean isForViewType(FriendBodyValue item, int position) {
            return item.type == FriendRecyclerAdapter.MUSIC_TYPE;
        }

        @Override
        public void convert(ViewHolder holder, final FriendBodyValue recommandBodyValue, int position) {
            //为viewholder绑定数据
            holder.setText(R.id.name_view, recommandBodyValue.name + " 分享单曲:");
            holder.setText(R.id.fansi_view, recommandBodyValue.fans + "粉丝");
            holder.setText(R.id.text_view, recommandBodyValue.text);
            holder.setText(R.id.zan_view, recommandBodyValue.zan);
            holder.setText(R.id.message_view, recommandBodyValue.msg);
            holder.setText(R.id.audio_name_view, recommandBodyValue.audioBean.name);
            holder.setText(R.id.audio_author_view, recommandBodyValue.audioBean.album);
            holder.setOnClickListener(R.id.album_layout, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IBinder audioBinder =
                            RePlugin.fetchBinder(AudioPluginConfig.PLUGIN_NAME, AudioPluginConfig.KEY_INTERFACE);
                    if (audioBinder == null) {
                        return;
                    }
                    //AIDL接口
                    IAudioService audioService = IAudioService.Stub.asInterface(audioBinder);
                    try {
                        audioService.addAudio(new Gson().toJson(recommandBodyValue.audioBean));
                        Intent intent = RePlugin.createIntent(AudioPluginConfig.PLUGIN_NAME,
                                AudioPluginConfig.PAGE.PAGE_MUSIC);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        RePlugin.startActivity(v.getContext(), intent);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.setOnClickListener(R.id.guanzhu_view, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IBinder b = RePlugin.fetchBinder(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.KEY_INTERFACE);
                    if (b == null) {
                        return;
                    }
                    ILoginService loginService = ILoginService.Stub.asInterface(b);
                    try {
                        //未登录，跳转到登录页面
                        if (!loginService.hasLogin()) {
                            Intent intent = RePlugin.createIntent(LoginPluginConfig.PLUGIN_NAME,
                                    LoginPluginConfig.PAGE.PAGE_LOGIN);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //在插件中启动非本插件内的activity必须有这个
                            RePlugin.startActivity(v.getContext(), intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ImageView avatar = holder.getView(R.id.photo_view);
            ImageLoaderManager.getInstance().displayImageForCircle(avatar, recommandBodyValue.avatr);
            ImageView albumPicView = holder.getView(R.id.album_view);
            ImageLoaderManager.getInstance()
                    .displayImageForView(albumPicView, recommandBodyValue.audioBean.albumPic);
            //多图自动布局
            MultiImageViewLayout imageViewLayout = holder.getView(R.id.image_layout);
            imageViewLayout.setList(recommandBodyValue.pics);

        }
    }

    /**
     * 视频类型item
     */
    public class VideoItemDelegate implements ItemViewDelegate<FriendBodyValue> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_friend_list_video_layout;
        }

        @Override
        public boolean isForViewType(FriendBodyValue item, int position) {
            return item.type == FriendRecyclerAdapter.VIDEO_TYPE;
        }

        @Override
        public void convert(ViewHolder holder, FriendBodyValue recommandBodyValue, int position) {
            RelativeLayout videoGroup = holder.getView(R.id.video_layout);
            VideoAdContext mAdsdkContext = new VideoAdContext(videoGroup, recommandBodyValue.videoUrl);
            holder.setText(R.id.fansi_view, recommandBodyValue.fans + "粉丝");
            holder.setText(R.id.name_view, recommandBodyValue.name + " 分享视频");
            holder.setText(R.id.text_view, recommandBodyValue.text);
            holder.setOnClickListener(R.id.guanzhu_view, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IBinder b = RePlugin.fetchBinder(LoginPluginConfig.PLUGIN_NAME, LoginPluginConfig.KEY_INTERFACE);
                    if (b == null) {
                        return;
                    }
                    ILoginService loginService = ILoginService.Stub.asInterface(b);
                    try {
                        //未登录，跳转到登录页面
                        if (!loginService.hasLogin()) {
                            Intent intent = RePlugin.createIntent(LoginPluginConfig.PLUGIN_NAME,
                                    LoginPluginConfig.PAGE.PAGE_LOGIN);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //在插件中启动非本插件内的activity必须有这个
                            RePlugin.startActivity(v.getContext(), intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ImageView avatar = holder.getView(R.id.photo_view);
            ImageLoaderManager.getInstance().displayImageForCircle(avatar, recommandBodyValue.avatr);
        }
    }

}
