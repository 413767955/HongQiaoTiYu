package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.presenter.NoopsychaFitnessPresenter;
import com.tianjin.beichentiyu.presenter.contract.NoopsychaFitnessContract;
import com.tianjin.beichentiyu.utils.DistanceUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 热门推荐列表
 * 智能健身页面或场馆页面推荐
 */
public class NearFieldPageHotActivity extends BaseMvpActivity<NoopsychaFitnessContract.Presenter> implements NoopsychaFitnessContract.View {
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, NearFieldPageHotActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;

    private int pageNo = 1;
    private String type = "2";//类型：1 场馆推荐 2 智能健身推荐

    private List<NearFieldPageHotBean.ListBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_near_field_page_hot;
    }

    @Override
    protected NoopsychaFitnessContract.Presenter initPresenter() {
        return new NoopsychaFitnessPresenter();
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRv();
        initRefreshLayout();

    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }

    BaseAdapter recommendAdapter;

    private void initRv() {
        recommendAdapter = new BaseAdapter<NearFieldPageHotBean.ListBean>(R.layout.item_noopsyche_footpath, mList) {
            @Override
            protected void convert(BaseViewHolder holder, NearFieldPageHotBean.ListBean item) {
                holder.setTvText(R.id.tv_name, item.getMap().getField_name());
                holder.setTvText(R.id.tv_address, item.getMap().getAddress());
                holder.setTvText(R.id.tv_journey, DistanceUtil.distanceUtil(item.getDistance()));

                GlideApp.with(NearFieldPageHotActivity.this)
                        .load(item.getMap().getBase_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView) holder.getView(R.id.iv_cover));
            }
        };
        mRvContent.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        recommendAdapter.setItemClickListner(position -> {
            SiteInfoActivity.toActivity(this, mList.get(position).getMap().getId());
        });
        mRvContent.setNestedScrollingEnabled(false);
        mRvContent.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvContent.setFocusable(false);
        mRvContent.setAdapter(recommendAdapter);

    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(this))
                .setFooterView(new FooterView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    mList.clear();
                    pageNo = 1;
                    mPresenter.nearFieldPageHot(pageNo, type);
                    refreshLayout.hideEmpty();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }

    /**
     * 加载数据
     */
    private void loadData() {
        refreshLayout.hideEmpty();
        pageNo++;
        mPresenter.nearFieldPageHot(pageNo, type);
    }

    @Override
    public void addData(NearFieldPageHotBean bean) {
        if (bean != null) {
            if (bean.getList() != null && bean.getList().size() > 0) {
                mList.addAll(bean.getList());
                recommendAdapter.setData(mList);
                refreshLayout.finishRefresh(true);
            } else {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(false, true);
                if (pageNo == 1) {
                    refreshLayout.showEmpty();
                } else {
                    refreshLayout.hideEmpty();
                }
                return;
            }
        } else {
            refreshLayout.showEmpty();
            refreshLayout.finishRefresh(true);
            refreshLayout.finishLoadMore(true, true);
        }
    }
}
