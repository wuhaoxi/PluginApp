package com.wu.ft_audio.mediaplayer.core;

import com.wu.ft_audio.exception.AudioQueueEmptyException;
import com.wu.ft_audio.mediaplayer.db.GreenDaoHelper;
import com.wu.ft_audio.mediaplayer.event.AudioCompleteEvent;
import com.wu.ft_audio.mediaplayer.event.AudioErrorEvent;
import com.wu.ft_audio.mediaplayer.event.AudioFavouriteEvent;
import com.wu.ft_audio.mediaplayer.event.AudioPlayModeEvent;
import com.wu.ft_audio.mediaplayer.model.AudioBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

/**
 * 控制播放逻辑类
 */
public class AudioController {

    /**
     * 播放方式
     */
    public enum PlayMode {
        /**
         * 列表循环
         */
        LOOP,
        /**
         * 随机
         */
        RANDOM,
        /**
         * 单曲循环
         */
        REPEAT
    }

    private AudioPlayer mAudioPlayer; //核心播放器
    //播放队列,不能为空,不设置主动抛错
    private ArrayList<AudioBean> mQueue = new ArrayList<>(); //歌曲队列
    private int mQueueIndex = 0; //当前播放歌曲索引
    private PlayMode mPlayMode = PlayMode.LOOP; // 循环模式

    private AudioController() {
        EventBus.getDefault().register(this);
        mAudioPlayer = new AudioPlayer();
    }

    /**
     * 获取播放器当前状态
     * @return
     */
    private CustomMediaPlayer.Status getStatus() {
        return mAudioPlayer.getStatus();
    }

    private AudioBean getNextPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex + 1) % mQueue.size();
                return getPlaying(mQueueIndex);
            case RANDOM:
                mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                return getPlaying(mQueueIndex);
            case REPEAT:
                return getPlaying(mQueueIndex);

        }
        return null;
    }

    private AudioBean getPreviousPlaying() {
        switch (mPlayMode) {
            case LOOP:
                mQueueIndex = (mQueueIndex - 1) % mQueue.size();
                return getPlaying(mQueueIndex);
            case RANDOM:
                mQueueIndex = new Random().nextInt(mQueue.size()) % mQueue.size();
                return getPlaying(mQueueIndex);
            case REPEAT:
                return getPlaying(mQueueIndex);
        }
        return null;
    }

    private AudioBean getPlaying(int index) {
        if (mQueue != null && !mQueue.isEmpty() && index >= 0 && index < mQueue.size()) {
            return mQueue.get(index);
        } else {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列.");
        }
    }

    public static AudioController getInstance() {
        return AudioController.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static AudioController instance = new AudioController();
    }

    private void load(AudioBean audioBean) {
        mAudioPlayer.load(audioBean);
    }

    public ArrayList<AudioBean> getQueue() {
        return mQueue == null ? new ArrayList<AudioBean>() : mQueue;
    }

    public void setQueue(ArrayList<AudioBean> queue) {
        this.setQueue(queue, 0);
    }

    public void setQueue(ArrayList<AudioBean> queue, int queueIndex) {
        mQueue.addAll(queue);
        mQueueIndex = queueIndex;
    }

    private void addCustomAudio(int index, AudioBean bean) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空，请先设置播放队列");
        }
        mQueue.add(index, bean);
    }

    private int queryAudio(AudioBean bean) {
        return mQueue.indexOf(bean);
    }

    /**
     * 添加单一歌曲到指定位置
     *
     * @param bean
     */
    public void addAudio(AudioBean bean) {
        this.addAudio(0, bean);
    }

    public void addAudio(int index, AudioBean bean) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空");
        }
        int query = queryAudio(bean);
        if (query <= -1) {
            //没有添加过
            addCustomAudio(index, bean);
            setPlayIndex(index);
        } else {
            AudioBean currentBean = getNowPlaying();
            if (!currentBean.id.equals(bean.id)) {
                //已经添加过且不在播放中
                setPlayIndex(query);
            }
        }
    }

    /**
     * 对外提供设置播放模式
     *
     * @param playMode
     */
    public void setPlayMode(PlayMode playMode) {
        this.mPlayMode = playMode;
        //还要对外发送切换事件，更新UI
        EventBus.getDefault().post(new AudioPlayModeEvent(mPlayMode));
    }

    public PlayMode getPlayMode() {
        return mPlayMode;
    }

    public int getQueueIndex() {
        return mQueueIndex;
    }

    /**
     * 添加/移除到收藏
     */
    public void changeFavourite() {
        if (null != GreenDaoHelper.selectFavourite(getNowPlaying())) {
            //已收藏，移除
            GreenDaoHelper.removeFavourite(getNowPlaying());
            EventBus.getDefault().post(new AudioFavouriteEvent(false));
        } else {
            //未收藏，添加收藏
            GreenDaoHelper.addFavourite(getNowPlaying());
            EventBus.getDefault().post(new AudioFavouriteEvent(true));
        }
    }

    public void setPlayIndex(int index) {
        if (mQueue == null) {
            throw new AudioQueueEmptyException("当前播放队列为空,请先设置播放队列;");
        }
        mQueueIndex = index;
        play();
    }

    public int getPlayIndex() {
        return mQueueIndex;
    }

    /**
     * 对外提供的获取当前歌曲信息
     *
     * @return
     */
    public AudioBean getNowPlaying() {
       return getPlaying(mQueueIndex);
    }

    /**
     * 加载当前index歌曲
     */
    public void play() {
        AudioBean bean = getPlaying(mQueueIndex);
        load(bean);
    }

    public void pause() {
        mAudioPlayer.pause();
    }

    public void resume() {
        mAudioPlayer.resume();
    }

    public void release() {
        mAudioPlayer.release();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 加载next index歌曲
     */
    public void next() {
        AudioBean bean = getNextPlaying();
        load(bean);
    }

    /**
     * 加载previous index歌曲
     */
    public void previous() {
        AudioBean bean = getPreviousPlaying();
        load(bean);
    }

    /**
     * 对外提供获取当前播放时间
     */
    public int getNowPlayTime() {
        return mAudioPlayer.getCurrentPosition();
    }

    /**
     * 对外提供获取总播放时间
     */
    public int getTotalPlayTime() {
        return mAudioPlayer.getCurrentPosition();
    }

    /**
     * 自动切换播放/暂停
     */
    public void playOrPause() {
        if (isStartState()) {
            pause();
        } else if (isPauseState()){
            resume();
        }
    }

    /**
     * 对外提供是否播放中状态
     *
     * @return
     */
    public boolean isStartState() {
        return CustomMediaPlayer.Status.STARTED == getStatus();
    }

    /**
     * 对外提供是否暂停状态
     * @return
     */
    public boolean isPauseState() {
        return CustomMediaPlayer.Status.PAUSED == getStatus();
    }

    //播放完毕事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioCompleteEvent(AudioCompleteEvent event) {
        next();
    }

    //播放出错事件处理
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAudioErrorEvent(AudioErrorEvent event) {
        next();
    }

}
