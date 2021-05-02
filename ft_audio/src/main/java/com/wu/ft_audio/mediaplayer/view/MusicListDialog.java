package com.wu.ft_audio.mediaplayer.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wu.ft_audio.R;
import com.wu.ft_audio.mediaplayer.core.AudioController;
import com.wu.ft_audio.mediaplayer.event.AudioLoadEvent;
import com.wu.ft_audio.mediaplayer.event.AudioPlayModeEvent;
import com.wu.ft_audio.mediaplayer.model.AudioBean;
import com.wu.ft_audio.mediaplayer.view.adapter.MusicListAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.wu.ft_audio.mediaplayer.core.AudioController.PlayMode.LOOP;
import static com.wu.ft_audio.mediaplayer.core.AudioController.PlayMode.RANDOM;
import static com.wu.ft_audio.mediaplayer.core.AudioController.PlayMode.REPEAT;

public class MusicListDialog extends BottomSheetDialog {

    private Context mContext;
    private DisplayMetrics dm;

    /**
     * view
     */
    private ImageView mTipView;
    private TextView mPlayModeView;
    private RecyclerView mRecyclerView;
    private MusicListAdapter mMusicListAdapter;

    /**
     * data
     */
    private ArrayList<AudioBean> mQueue; //播放队列
    private AudioBean mAudioBean; //当前正在播放歌曲
    private AudioController.PlayMode mPlayMode;

    public MusicListDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dm = mContext.getResources().getDisplayMetrics();
        setContentView(R.layout.dialog_bottom_sheet);
        initData();
        initView();
    }

    private void initData() {
        //当前播歌曲，用来初始化UI
        mQueue = AudioController.getInstance().getQueue();
        mAudioBean = AudioController.getInstance().getNowPlaying();
        mPlayMode = AudioController.getInstance().getPlayMode();
    }

    private void initView() {
        /**
         * 充满宽度，也要以在style中定义
         */
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = dm.widthPixels; //设置宽度
        dialogWindow.setAttributes(lp);

        setCancelable(true);
        setCanceledOnTouchOutside(true);

        mTipView = findViewById(R.id.mode_image_view);
        mPlayModeView = findViewById(R.id.mode_text_view);
        mPlayModeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mPlayMode) {
                    case LOOP:
                        AudioController.getInstance().setPlayMode(RANDOM);
                        break;
                    case RANDOM:
                        AudioController.getInstance().setPlayMode(REPEAT);
                        break;
                    case REPEAT:
                        AudioController.getInstance().setPlayMode(LOOP);
                        break;

                }

            }
        });
        //更新界面
        updatePlayModeView();
        //初始化recycler
        mRecyclerView = findViewById(R.id.recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mMusicListAdapter = new MusicListAdapter(mContext, mQueue, mAudioBean);
        mRecyclerView.setAdapter(mMusicListAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioLoadEvent(AudioLoadEvent event) {
        mAudioBean = event.audioBean;
        //更新列表
        updateList();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioPlayModeEvent(AudioPlayModeEvent event) {
        mPlayMode = event.mPlayMode;
        //更新播放模式
        updatePlayModeView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        EventBus.getDefault().unregister(this);
    }

    private void updatePlayModeView() {
        switch (mPlayMode) {
            case LOOP:
                mTipView.setImageResource(R.mipmap.loop);
                mPlayModeView.setText("列表循环");
                break;
            case RANDOM:
                mTipView.setImageResource(R.mipmap.random);
                mPlayModeView.setText("随机播放");
                break;
            case REPEAT:
                mTipView.setImageResource(R.mipmap.once);
                mPlayModeView.setText("单曲循环");
                break;
        }
    }

    private void updateList() {
        mMusicListAdapter.updateAdapter(mAudioBean);
    }

}
