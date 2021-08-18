package com.tianjin.beichentiyu.ui.activity.my;


import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
import com.tianjin.beichentiyu.bean.MemActPayBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.ui.activity.SpecialEventsInfoActivity;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 报名记录
 */
public class SignUpHistoryActivity extends BaseActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,SignUpHistoryActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private BaseAdapter adapter;
    private List<MemActPayBean.ListBean> dataList = new ArrayList<>();
    private int pageNo = 0;
    private int totlePage = 0;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_sign_up_history;
    }

    @Override
    protected void firstRequest() {
        //showMemActPay();
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
    private void initRv(){
        adapter = new BaseAdapter<MemActPayBean.ListBean>(R.layout.item_signup_history,dataList){
            @Override
            protected void convert(BaseViewHolder holder, MemActPayBean.ListBean item) {
                ImageView img = holder.getView(R.id.iv_cover);
                TextView title = holder.getView(R.id.tv_title);
                TextView address = holder.getView(R.id.tv_address);
                TextView time = holder.getView(R.id.tv_time);
                title.setText(item.getTitle());
                address.setText(item.getAddress());
                time.setText(item.getGen_time());
                GlideApp.with(img.getContext())
                        .load(item.getShow_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(img);

            }
        };
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(adapter);
        adapter.setItemClickListner(position -> {
            SpecialEventsInfoActivity.toActivity(SignUpHistoryActivity.this,dataList.get(position).getRel_id(),
                    SpecialEventsInfoActivity.SIGN_UP_CODE,dataList.get(position).getShowActivityUrl());
        });
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout(){
        refreshLayout.setHeaderView(new HeaderView(this))
                .setFooterView(new FooterView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    dataList.clear();
                    pageNo = 1;
                    showMemActPay();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }
    private void loadData(){
        if (totlePage > pageNo) {
            pageNo += 1;
            showMemActPay();
        }else {
            refreshLayout.finishLoadMore(true,false);
        }
    }

    private void showMemActPay(){
        String tel = AccountManager.getInstance().getAccount();
        String nonStr = AccountManager.getInstance().getNonstr();
        ApiManager.getBusinessService().showMemActPay(tel,nonStr,pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemActPayBean>() {
                    @Override
                    protected void onSuccees(MemActPayBean memActPayBean) throws Exception {
                        refreshLayout.finishRefresh(true);
                        if (memActPayBean.isSuccessful()){
                            totlePage = memActPayBean.getTotalPage();
                            if (memActPayBean.getList().size() > 0) {
                                dataList.addAll(memActPayBean.getList());
                                adapter.setData(dataList);
                                refreshLayout.hideEmpty();
                            }else {
                                if (pageNo == 1) {
                                    refreshLayout.showEmpty();
                                }
                            }
                            if (totlePage > pageNo){
                                refreshLayout.finishLoadMore(true,true);
                            }else {
                                refreshLayout.finishLoadMore(true,false);
                            }
                        }else {
                            ToastUtil.showToast(memActPayBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }
}
