package com.tianjin.beichentiyu.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.heitong.frame.base.fragment.BaseMvpFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.ActivityPageBean;
import com.tianjin.beichentiyu.presenter.SpecialEventsPresenter;
import com.tianjin.beichentiyu.presenter.contract.SpecialEventsContract;
import com.tianjin.beichentiyu.ui.activity.SpecialEventsInfoActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/19 22:36
 * E-Mail Address：673236072@qq.com
 * 赛事活动 - 活动
 */
public class SpecialEventsFragment  extends BaseMvpFragment<SpecialEventsContract.Presenter> implements SpecialEventsContract.View{
    public static SpecialEventsFragment newInstance(){
        SpecialEventsFragment fragment = new SpecialEventsFragment();
        return fragment;
    }

    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    private BaseAdapter mAdapter;
    private List<ActivityPageBean.ListBean> mList = new ArrayList<>();
    private int totalPage = -1;
    private int pageNo = 0;
    @Override
    protected SpecialEventsContract.Presenter initPresenter() {
        return new SpecialEventsPresenter();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_special_events, container, false);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this,mView);
        initRv();
        initRefreshLayout();
    }
    /**
     * 加载数据
     */
    private void loadData(){
        pageNo++;
        if(totalPage != -1 && pageNo > totalPage){
            refreshLayout.finishLoadMore(true,false);
            return;
        }
        mPresenter.loadData(pageNo);
    }

    /**
     * 初始化列表
     */
    private void initRv(){
        mAdapter = new BaseAdapter<ActivityPageBean.ListBean>(R.layout.item_special_events,mList) {
            @Override
            protected void convert(BaseViewHolder holder, ActivityPageBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getTitle());
                holder.setTvText(R.id.tv_msg,item.getMsg());
                holder.setTvText(R.id.tv_num,"已报名"+item.getNow_mem() + "人");
                if(TextUtils.isEmpty(item.getPrice()) || "0".equals(item.getPrice())) {
                    holder.getView(R.id.tv_price_start).setVisibility(View.GONE);
                    holder.getView(R.id.tv_price_end).setVisibility(View.GONE);
                    holder.setTvText(R.id.tv_price, "免费");
                }else{
                    holder.getView(R.id.tv_price_start).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_price_end).setVisibility(View.VISIBLE);
                    holder.setTvText(R.id.tv_price, item.getPrice());
                }
                ImageView ivCover = holder.getView(R.id.iv_cover);
                GlideApp.with(getContext())
                        .asBitmap()
                        .load(item.getShow_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivCover);
            }
        };
        mAdapter.setItemClickListner(position -> {
                    SpecialEventsInfoActivity.toActivity(getContext(),mList.get(position).getA_id(),
                            SpecialEventsInfoActivity.ACTIVITY_CODE,mList.get(position).getShowActivityUrl());
                });
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout(){
        refreshLayout.setHeaderView(new HeaderView(getContext()))
                .setFooterView(new FooterView(getContext()))
                .setOnRefreshListener(() -> {//下拉刷新
                    mList.clear();
                    mPresenter.loadData(1);
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }
    @Override
    public void addData(ActivityPageBean bean) {
        totalPage = bean.getTotalPage();
        pageNo = bean.getPageNo();
        mList.addAll(bean.getList());
        mAdapter.setData(mList);
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true,pageNo < totalPage);
        if(mList.size() == 0){
            refreshLayout.showEmpty();
        }else{
            refreshLayout.hideEmpty();
        }
    }
}

