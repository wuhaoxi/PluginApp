package com.wu.ft_discory.view.discory;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wu.ft_discory.R;
import com.wu.lib_base.model.discory.RecommandFooterValue;
import com.wu.lib_base.model.discory.RecommandHeadValue;
import com.wu.lib_common_ui.recyclerview.CommonAdapter;
import com.wu.lib_common_ui.recyclerview.base.ViewHolder;
import com.wu.lib_image_loader.app.ImageLoaderManager;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DiscoryNewView extends RelativeLayout {

    private Context mContext;

    /**
     * UI
     */
    private RecyclerView mRecyclerView;

    /**
     * Data
     */
    private RecommandHeadValue mHeaderValue;

    public DiscoryNewView(Context context, RecommandHeadValue recommandHeadValue) {
        this(context, null, recommandHeadValue);
    }

    public DiscoryNewView(Context context, AttributeSet attrs,
                          RecommandHeadValue recommandHeadValue) {
        super(context, attrs);
        mContext = context;
        mHeaderValue = recommandHeadValue;
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_discory_head_recommand_layout, null);
        TextView titleView = rootView.findViewById(R.id.title_view);
        titleView.setText("新碟");
        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.setAdapter(new CommonAdapter<RecommandFooterValue>(mContext,
                R.layout.item_discory_head_recommand_recycler_layout, mHeaderValue.footer) {
            @Override
            protected void convert(ViewHolder holder, RecommandFooterValue value, int position) {
                holder.setText(R.id.text_view, value.info);
                ImageView imageView = holder.getView(R.id.image_view);
                ImageLoaderManager.getInstance().displayImageForView(imageView, value.imageUrl);
            }
        });
    }

}
