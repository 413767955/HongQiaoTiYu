package com.tianjin.beichentiyu.ui.activity.my;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.tianjin.beichentiyu.bean.RepairRecordBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 报修记录
 */
public class RepairRecordActivity extends BaseActivity {

    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, RepairRecordActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private BaseAdapter adapter;
    private List<RepairRecordBean.ListBean> dataList = new ArrayList<>();
    private int pageNo = 1;
    private int totlePage = 0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_repair_record;
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

    private void initRv() {
        adapter = new BaseAdapter<RepairRecordBean.ListBean>(R.layout.item_repair_record, dataList) {
            @Override
            protected void convert(BaseViewHolder holder, RepairRecordBean.ListBean item) {
                ImageView img = holder.getView(R.id.iv_cover);
                TextView title = holder.getView(R.id.tv_title);
                TextView address = holder.getView(R.id.tv_address);
                TextView eName = holder.getView(R.id.tv_eName);
                TextView content = holder.getView(R.id.tv_content);
                TextView state = holder.getView(R.id.tv_state);
                LinearLayout linTag = holder.getView(R.id.lin_tag);
                title.setText(item.getField_name());
                address.setText(item.getAddress());
                eName.setText(item.getE_name());
                content.setText(item.getContent());
                GlideApp.with(img.getContext())
                        .load(item.getBase_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(img);
                if (!TextUtils.isEmpty(item.getState())) {
                    if (TextUtils.equals(item.getState(), "0")) {
                        state.setText("待处理");
                        linTag.setBackground(getDrawable(R.drawable.round_tag));
                        state.setTextColor(getResources().getColor(R.color.color_777777));
                    } else {
                        state.setText("已处理");
                        linTag.setBackground(getDrawable(R.drawable.round_tag1));
                        state.setTextColor(getResources().getColor(R.color.color_398DEE));
                    }
                }
            }
        };
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(adapter);
        adapter.setItemClickListner(position -> {
            RepairInfoActivity.toActivity(RepairRecordActivity.this,dataList.get(position));
        });
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(this))
                .setFooterView(new FooterView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    dataList.clear();
                    pageNo = 1;
                    getMemSetRepairPage();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }
    private void loadData() {
        if (totlePage > pageNo) {
            pageNo += 1;
            getMemSetRepairPage();
        } else {
            refreshLayout.finishLoadMore(true, false);
        }
    }

    private void getMemSetRepairPage() {
        String tel = AccountManager.getInstance().getAccount();
        String nonStr = AccountManager.getInstance().getNonstr();
        ApiManager.getBusinessService().getMemSetRepairPage(tel, nonStr, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RepairRecordBean>() {
                    @Override
                    protected void onSuccees(RepairRecordBean bean) throws Exception {
                        refreshLayout.finishRefresh(true);
                        if (bean.isSuccessful()) {
                            totlePage = bean.getTotalPage();
                            if (bean.getList().size() > 0) {
                                dataList.addAll(bean.getList());
                                adapter.setData(dataList);
                                refreshLayout.hideEmpty();
                            } else {
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
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }
}
