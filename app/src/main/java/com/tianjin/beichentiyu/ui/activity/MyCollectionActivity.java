package com.tianjin.beichentiyu.ui.activity;

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
import com.tianjin.beichentiyu.bean.MemberCollectBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/10 19:52
 * E-Mail Address：673236072@qq.com
 * 我的收藏
 */
public class MyCollectionActivity extends BaseActivity {
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, MyCollectionActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private BaseAdapter mDataAdapter;
    private List<MemberCollectBean.ListBean> mDataList = new ArrayList<>();
    private int pageNo = 1;
    private int totalPage = -1;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initDataRv();
        initRefreshLayout();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(this))
                .setFooterView(new FooterView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    mDataList.clear();
                    pageNo = 1;
                    loadData();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }

    /**
     * 初始化列表
     */
    private void initDataRv() {
        mDataAdapter = new BaseAdapter<MemberCollectBean.ListBean>(R.layout.adapter_collection, mDataList) {
            @Override
            protected void convert(BaseViewHolder holder, MemberCollectBean.ListBean item) {
                ImageView ivIcon = holder.getView(R.id.iv_icon);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvTime = holder.getView(R.id.tv_time);
                tvTitle.setText(item.getShowName());
                tvTime.setText(item.getGenTime());
                GlideApp.with(ivIcon.getContext()).load(item.getShowImg()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(ivIcon);
                switch (item.getType()) {
                    case "1":
                        holder.setTvText(R.id.tv_type,"收藏："+"场馆");
                        break;
                    case "2":
                        holder.setTvText(R.id.tv_type,"收藏："+"体测中心");
                        break;
                    case "3":
                        holder.setTvText(R.id.tv_type,"收藏："+"资讯");
                        break;
                    case "4":
                        holder.setTvText(R.id.tv_type,"收藏："+"赛事");
                        break;
                    case "5":
                        holder.setTvText(R.id.tv_type,"收藏："+"智能健身");
                        break;
                }
            }
        };
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mDataAdapter);
        mDataAdapter.setItemClickListner(position -> {
            //类型：1 场馆， 2 体测中心， 3 文章， 4 赛事新闻
            String relId = mDataList.get(position).getRelId();
            switch (mDataList.get(position).getType()) {
                case "1":
                    SiteInfoActivity.toActivity(this,relId);
                    break;
                case "2":
                    NoopsycheFiancoInfoActivity.toActivity(this,relId);
                    break;
                case "3":
                    CompetitionInfoActivity.toActivity(this, Constant.Information.PTGG,relId);
                    break;
                case "4":
                    CompetitionInfoActivity.toActivity(this, Constant.Information.SSXQ,relId);
                    break;
                case "5":
                    //CompetitionInfoActivity.toActivity(this, Constant.Information.SSXQ,relId);
                    SmartFitnessInfoActivity.toActivity(this,relId,Constant.Information.ZNJS+"");
                    break;
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadData() {
        LodingDialog lodingDialog = new LodingDialog(this, "加载中...");
        lodingDialog.show();
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getMemberCollectList(tel, nonstr, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberCollectBean>() {
                    @Override
                    protected void onSuccees(MemberCollectBean bean) throws Exception {
                        lodingDialog.dismiss();
                        refreshLayout.finishRefresh(true);
                        refreshLayout.finishLoadMore(true, true);
                        if (bean.isSuccessful()) {
                            mDataList.addAll(bean.getList());
                            mDataAdapter.setData(mDataList);
                            totalPage = bean.getTotalPage();
                            if (mDataList != null && !mDataList.isEmpty()) {
                                refreshLayout.hideEmpty();
                            } else {
                                refreshLayout.showEmpty();
                            }
                            if (totalPage > pageNo) {
                                pageNo++;
                            } else {
                                refreshLayout.finishLoadMore(true, false);
                            }
                        } else {
                            mDataList = null;
                            mDataAdapter.setData(null);
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        mDataList = null;
                        mDataAdapter.setData(null);
                        lodingDialog.dismiss();
                        refreshLayout.finishRefresh(true);
                    }
                });
    }
}
