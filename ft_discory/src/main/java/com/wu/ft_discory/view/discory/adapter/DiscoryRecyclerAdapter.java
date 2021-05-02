package com.wu.ft_discory.view.discory.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wu.ft_discory.R;
import com.wu.lib_base.model.discory.RecommandBodyValue;
import com.wu.lib_common_ui.recyclerview.MultiItemTypeAdapter;
import com.wu.lib_common_ui.recyclerview.base.ItemViewDelegate;
import com.wu.lib_common_ui.recyclerview.base.ViewHolder;
import com.wu.lib_image_loader.app.ImageLoaderManager;

import java.util.List;

public class DiscoryRecyclerAdapter extends MultiItemTypeAdapter {

    public static final int PICTURE_TYPE = 0x01; //图片类型
    public static final int VIDEO_TYPE = 0x02; //视频类型
    public static final int AD_TYPE = 0x03; //广告类型

    public DiscoryRecyclerAdapter(Context context, List datas) {
        super(context, datas);
        addItemViewDelegate(PICTURE_TYPE, new PictureItemDelegate());
        addItemViewDelegate(VIDEO_TYPE, new VideoItemDelegate());
        addItemViewDelegate(AD_TYPE, new AdItemDelegate());
    }

    private class PictureItemDelegate implements ItemViewDelegate<RecommandBodyValue> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_discory_list_picture_layout;
        }

        @Override
        public boolean isForViewType(RecommandBodyValue item, int position) {
            return item.type == DiscoryRecyclerAdapter.PICTURE_TYPE;
        }

        @Override
        public void convert(ViewHolder holder, RecommandBodyValue recommandBodyValue, int position) {
            TextView titleView = holder.getView(R.id.title_view);
            if (TextUtils.isEmpty(recommandBodyValue.title)) {
                titleView.setVisibility(View.GONE);
            } else {
                titleView.setVisibility(View.VISIBLE);
                titleView.setText(recommandBodyValue.title);
            }
            holder.setText(R.id.name_view, recommandBodyValue.text);
            holder.setText(R.id.play_view, recommandBodyValue.play);
            holder.setText(R.id.time_view, recommandBodyValue.time);
            holder.setText(R.id.zan_view, recommandBodyValue.zan);
            holder.setText(R.id.message_view, recommandBodyValue.msg);
            ImageView logo = holder.getView(R.id.logo_view);
            ImageLoaderManager.getInstance().displayImageForView(logo, recommandBodyValue.logo);
            ImageView avatar = holder.getView(R.id.author_view);
            ImageLoaderManager.getInstance().displayImageForCircle(avatar, recommandBodyValue.avatr);
        }
    }

    /**
     * 广告类型item
     */
    private class AdItemDelegate implements ItemViewDelegate<RecommandBodyValue> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_discory_list_ad_layout;
        }

        @Override
        public boolean isForViewType(RecommandBodyValue item, int position) {
            return item.type == DiscoryRecyclerAdapter.AD_TYPE;
        }

        @Override
        public void convert(ViewHolder holder, RecommandBodyValue recommandBodyValue, int position) {

        }
    }
    /**
     * 广告类型item
     */
    private class VideoItemDelegate implements ItemViewDelegate<RecommandBodyValue> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.item_discory_list_video_layout;
        }

        @Override
        public boolean isForViewType(RecommandBodyValue item, int position) {
            return item.type == DiscoryRecyclerAdapter.VIDEO_TYPE;
        }

        @Override
        public void convert(ViewHolder holder, RecommandBodyValue recommandBodyValue, int position) {

        }
    }



}
