package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ActivityPageBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/3 13:30
 * E-Mail Address：673236072@qq.com
 * 组织详情 - 最近活动
 */
public class OrgActivityActivity extends BaseActivity {
    private int pageNo = 1;
    private String orgId;

    public static void toActivity(Context context, String orgId) {
        Intent intent = new Intent(context, OrgActivityActivity.class);
        intent.putExtra("orgId", orgId);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private BaseAdapter mAdapter;
    private List<ActivityPageBean.ListBean> mList = new ArrayList<>();

    @Override
    protected void initData() {
        orgId = getIntent().getStringExtra("orgId");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_org_activity;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRvContent();
        initRefreshLayout();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }
    private void initRvContent(){
        mAdapter = new BaseAdapter<ActivityPageBean.ListBean>(R.layout.item_special_events,mList){
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
                GlideApp.with(OrgActivityActivity.this)
                        .asBitmap()
                        .load(item.getShow_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        //.dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivCover);
            }
        };
        mAdapter.setItemClickListner(position -> {
            SpecialEventsInfoActivity.toActivity(OrgActivityActivity.this,mList.get(position).getA_id(),
                    SpecialEventsInfoActivity.ACTIVITY_CODE,mList.get(position).getShowActivityUrl());
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mAdapter);
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout(){
        refreshLayout.setHeaderView(new HeaderView(this))
                .setFooterView(new FooterView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    mList.clear();
                    pageNo = 1;
                    loadData(pageNo);
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }


    public void addData(List<ActivityPageBean.ListBean> list) {
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true,true);
        if(list != null && !list.isEmpty()){
            mList.addAll(list);
        }

        if(mList != null && !mList.isEmpty()){
            mAdapter.setData(mList);
            refreshLayout.hideEmpty();
        }else{
            refreshLayout.showEmpty();
        }
    }
    /**
     * 加载数据
     */
    private void loadData(){
        pageNo++;
        loadData(pageNo);
    }
    private void loadData(int pageNo) {
        ApiManager.getBusinessService().getActivityPage(AccountManager.getInstance().getAccount(),
                AccountManager.getInstance().getNonstr(),orgId,
                pageNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ActivityPageBean>() {
                    @Override
                    protected void onSuccees(ActivityPageBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            addData(bean.getList());
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                            addData(null);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        addData(null);
                    }
                });
    }
}
