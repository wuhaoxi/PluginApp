package com.wu.ft_audio.mediaplayer.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qihoo360.replugin.RePlugin;
import com.wu.ft_audio.R;
import com.wu.ft_audio.mediaplayer.core.AudioController;
import com.wu.ft_audio.mediaplayer.event.AudioLoadEvent;
import com.wu.ft_audio.mediaplayer.event.AudioPauseEvent;
import com.wu.ft_audio.mediaplayer.event.AudioStartEvent;
import com.wu.ft_audio.mediaplayer.model.AudioBean;
import com.wu.lib_base.service.ft_audio.AudioPluginConfig;
import com.wu.lib_image_loader.app.ImageLoaderManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author wuhaoxuan
 * @description 播放器底部View
 */
public class BottomMusicView extends RelativeLayout {

    private Context mContext;

    /**
     * View
     */
    private ImageView mLeftView;
    private TextView mTitleView;
    private TextView mAlbumView;
    private ImageView mPlayView;
    private ImageView mRightView;

    /**
     * data
     */
    private AudioBean mAudioBean;


    public BottomMusicView(Context context) {
        this(context, null);
    }

    public BottomMusicView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomMusicView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.bottom_view, this);
        rootView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳到音乐播放Activity
                //MusicPlayerActivity.start((Activity)mContext);
                Intent intent = RePlugin.createIntent(AudioPluginConfig.PLUGIN_NAME,
                        AudioPluginConfig.PAGE.PAGE_MUSIC);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                RePlugin.startActivity(v.getContext(), intent);
            }
        });
        mLeftView = rootView.findViewById(R.id.album_view);
        //让左侧mLeftView不停旋转
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(mLeftView, View.ROTATION_X, 0f, 360);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);
        animator.start();

        mTitleView = rootView.findViewById(R.id.audio_name_view);
        mAlbumView = rootView.findViewById(R.id.audio_album_view);
        mPlayView = rootView.findViewById(R.id.play_view);
        mPlayView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理播放暂停事件
                AudioController.getInstance().playOrPause();
            }
        });
        mRightView = rootView.findViewById(R.id.show_list_view);
        mRightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示音乐列表对话框
                MusicListDialog dialog = new MusicListDialog(mContext);
                dialog.show();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioLoadEvent(AudioLoadEvent event) {
        //监听加载事件
        mAudioBean = event.audioBean;
        showLoadingView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioStartEvent(AudioStartEvent event) {
        showPlayView();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPauseEvent(AudioPauseEvent event) {
        showPauseView();
    }

    private void showLoadingView() {
        if (mAudioBean != null) {
            ImageLoaderManager.getInstance()
                    .displayImageForCircle(mLeftView, mAudioBean.albumPic);
            mTitleView.setText(mAudioBean.name);
            mAlbumView.setText(mAudioBean.album);
            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
        }
    }

    private void showPauseView() {
        if (mAudioBean != null) {
            mPlayView.setImageResource(R.mipmap.note_btn_play_white);
        }
    }

    private void showPlayView() {
        if (mAudioBean != null) {
            mPlayView.setImageResource(R.mipmap.note_btn_pause_white);
        }
    }

}
