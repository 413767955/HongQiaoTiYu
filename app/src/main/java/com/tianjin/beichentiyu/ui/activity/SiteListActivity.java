package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;
import com.tianjin.beichentiyu.manager.FilterWindowManager;
import com.tianjin.beichentiyu.presenter.SiteListPresenter;
import com.tianjin.beichentiyu.presenter.contract.SiteListContract;
import com.tianjin.beichentiyu.utils.DistanceUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/3 13:30
 * E-Mail Address：673236072@qq.com
 * 场地列表
 */
public class SiteListActivity  extends BaseMvpActivity<SiteListContract.Presenter> implements SiteListContract.View {
    private String smartType;//智能健身传参
    private String type = "";//场地设施传参
    private int pageNo = 1;

    /**
     *  如果是场地设施传参type；
     *  如果是智能健身则传参smartType
     * @param context
     * @param titleType     标题
     * @param type          区分：如果是场地设施，传参
     * @param smartType     区分：如果是智能健身，传参
     */
    public static void toActivity(Context context,String titleType,String type,String smartType){
        Intent intent = new Intent(context,SiteListActivity.class);
        intent.putExtra("titleType",titleType);
        intent.putExtra("smartType",smartType);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    private String titleType;

    private BaseAdapter mAdapter;
    private List<NearFieldPageBean.ListBean> mList = new ArrayList<>();

    private FilterWindowManager filterManager;
    String proId = "";
    String cityId = "";
    String disId = "";
    String strId = "";
    String brandName = "";
    String subscribeState = "";
    @Override
    protected SiteListContract.Presenter initPresenter() {
        return new SiteListPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_site_list;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRvContent();
        titleType = getIntent().getStringExtra("titleType");
        smartType = getIntent().getStringExtra("smartType");
        type = getIntent().getStringExtra("type");
        initRefreshLayout();

        filterManager = new FilterWindowManager(drawerLayout);
        filterManager.setCallback((proId, cityId, disId, strId, brandName, subscribeState,region) -> {
            this.proId = proId;
            this.cityId = cityId;
            this.disId = disId;
            this.strId = strId;
            this.brandName = brandName;
            this.subscribeState = subscribeState;

            mList.clear();
            pageNo = 1;
            mPresenter.nearFieldPage(pageNo,type,smartType,
                    proId,
                    cityId,
                    disId,
                    strId,
                    brandName,
                    subscribeState);
        });
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setMyTitle(titleType);
    }

    @Override
    protected void firstRequest() {
        //mPresenter.nearFieldPage(pageNo,type,smartType);
    }

    private void initRvContent(){
        mAdapter = new BaseAdapter<NearFieldPageBean.ListBean>(R.layout.item_venue_list,mList){
            @Override
            protected void convert(BaseViewHolder holder, NearFieldPageBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getMap().getField_name());
                holder.setTvText(R.id.tv_address,item.getMap().getAddress());
                holder.setTvText(R.id.tv_distance,DistanceUtil.distanceUtil(item.getDistance()));
                if ("1".equals(item.getMap().getSubscribe_state())){
                    holder.setVisibility(R.id.tv_reserve, View.VISIBLE);
                }else {
                    holder.setVisibility(R.id.tv_reserve, View.GONE);
                }

                GlideApp.with(mContext).load(item.getMap().getBase_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView) holder.getView(R.id.iv_img));
            }
        };
        mAdapter.setItemClickListner(position -> {
            SiteInfoActivity.toActivity(this,mList.get(position).getMap().getId());
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
                    LogUtils.e("刷新");
                    mList.clear();
                    pageNo = 1;
                    mPresenter.nearFieldPage(pageNo,type,smartType,
                            proId,
                            cityId,
                            disId,
                            strId,
                            brandName,
                            subscribeState);
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }
    /**
     * 加载数据
     */
    private void loadData(){
        pageNo++;
        LogUtils.e(pageNo);
        mPresenter.nearFieldPage(pageNo,type,smartType,
                proId,
                cityId,
                disId,
                strId,
                brandName,
                subscribeState);
    }

    @Override
    public void addData(List<NearFieldPageBean.ListBean> list) {
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
}
