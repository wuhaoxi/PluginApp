package com.wu.ft_discory.view.discory.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.wu.lib_image_loader.app.ImageLoaderManager;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author wuhaoxuan
 */
public class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<String> mData;

    public BannerPagerAdapter(Context context, ArrayList<String> data) {
        this.mContext = context;
        this.mData = data;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView photoView = new ImageView(mContext);
        photoView.setScaleType(ImageView.ScaleType.FIT_XY);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(mContext, CourseDetailActivity.class);
                //mContext.startActivity(intent);
            }
        });

        ImageLoaderManager.getInstance().displayImageForView(photoView, mData.get(position));
        container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
