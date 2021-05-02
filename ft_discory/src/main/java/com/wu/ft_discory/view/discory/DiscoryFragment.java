package com.wu.ft_discory.view.discory;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wu.ft_discory.R;
import com.wu.ft_discory.view.discory.adapter.DiscoryRecyclerAdapter;
import com.wu.lib_base.api.MockData;
import com.wu.lib_base.api.RequestCenter;
import com.wu.lib_base.model.discory.BaseRecommandModel;
import com.wu.lib_base.model.discory.BaseRecommandMoreModel;
import com.wu.lib_base.model.discory.RecommandBodyValue;
import com.wu.lib_common_ui.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.wu.lib_common_ui.recyclerview.wrapper.LoadMoreWrapper;
import com.wu.lib_network.okhttp.response.listener.DisposeDataListener;
import com.wu.lib_network.okhttp.utils.ResponseEntityToModule;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class DiscoryFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, LoadMoreWrapper.OnLoadMoreListener {

    private Context mContext;
    /**
     * UI
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private DiscoryRecyclerAdapter mAdapter;
    private HeaderAndFooterWrapper mHeaderWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;

    /**
     * Data
     */
    private BaseRecommandModel mRecommandData;
    private List<RecommandBodyValue> mDatas = new ArrayList<>();

    public static DiscoryFragment newInstance() {
        DiscoryFragment fragment = new DiscoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discory_layout, null);
        mSwipeRefreshLayout = rootView.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_red_light));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //发请求更新UI
        requestData();
    }

    //下拉刷新接口
    @Override
    public void onRefresh() {
        requestData();
    }

    //加载更多接口
    @Override
    public void onLoadMoreRequested() {
        loadMore();
    }

    //请求数据
    private void requestData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override public void onSuccess(Object responseObj) {
                mRecommandData = (BaseRecommandModel) responseObj;
                //更新UI
                updateView();
            }

            @Override public void onFailure(Object reasonObj) {
                //显示请求失败View,显示mock数据
                onSuccess(
                        ResponseEntityToModule.parseJsonToModule(MockData.HOME_DATA, BaseRecommandModel.class));
            }
        });
    }

    //更新UI
    private void updateView() {
        mSwipeRefreshLayout.setRefreshing(false); //停止刷新
        mDatas = mRecommandData.data.list;
        mAdapter = new DiscoryRecyclerAdapter(mContext, mDatas);
        //头部view初始化
        mHeaderWrapper = new HeaderAndFooterWrapper(mAdapter);
        DiscoryBannerView bannerView = new DiscoryBannerView(mContext, mRecommandData.data.head);
        mHeaderWrapper.addHeaderView(bannerView);
        DiscoryFunctionView functionView = new DiscoryFunctionView(mContext);
        mHeaderWrapper.addHeaderView(functionView);
        DiscoryRecommandView recommandView =
                new DiscoryRecommandView(mContext, mRecommandData.data.head);
        mHeaderWrapper.addHeaderView(recommandView);
        DiscoryNewView newView = new DiscoryNewView(mContext, mRecommandData.data.head);
        mHeaderWrapper.addHeaderView(newView);
        //加载更多初始化
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderWrapper);
        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(this);
        mRecyclerView.setAdapter(mLoadMoreWrapper);
    }

    private void loadMore() {
        RequestCenter.requestRecommandMore(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                BaseRecommandMoreModel moreData = (BaseRecommandMoreModel) responseObj;
                //追加数据到adapter中
                mDatas.addAll(moreData.data.list);
                mLoadMoreWrapper.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Object reasonObj) {
                //显示请求失败View,显示mock数据
                mLoadMoreWrapper.notifyDataSetChanged();
                onSuccess(ResponseEntityToModule.parseJsonToModule(MockData.HOME_MORE_DATA,
                        BaseRecommandMoreModel.class));
            }
        });
    }
}
