package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
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
import com.tianjin.beichentiyu.bean.NearIntellectPageBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.FilterWindowManager;
import com.tianjin.beichentiyu.utils.DistanceUtil;
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
 * 智能健身列表
 */
public class SmartFitnessListActivity extends BaseActivity{
    private String title;
    private String type = "";
    private int pageNo = 1;

    /**
     * @param context
     * @param title     标题
     * @param type          区分：如果是场地设施，传参
     */
    public static void toActivity(Context context,String title,String type){
        Intent intent = new Intent(context, SmartFitnessListActivity.class);
        intent.putExtra("title",title);
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


    private BaseAdapter mAdapter;
    private List<NearIntellectPageBean.ListBean> mList = new ArrayList<>();

    private FilterWindowManager filterManager;
    String proId = "";
    String cityId = "";
    String disId = "";
    String strId = "";
    String brandName = "";
    String subscribeState = "";
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_smart_fitness;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRvContent();
        title = getIntent().getStringExtra("title");
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
            loadData(true);
        });
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setMyTitle(title);
    }

    private void initRvContent(){
        mAdapter = new BaseAdapter<NearIntellectPageBean.ListBean>(R.layout.item_smart_fitness,mList){
            @Override
            protected void convert(BaseViewHolder holder, NearIntellectPageBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getMap().getField_name());
                holder.setTvText(R.id.tv_address,item.getMap().getAddress());
                holder.setTvText(R.id.tv_distance, DistanceUtil.distanceUtil(item.getDistance()+""));
                if ("1".equals(item.getMap().getSubscribe_state())){
                    holder.setVisibility(R.id.tv_reserve, View.VISIBLE);
                }else {
                    holder.setVisibility(R.id.tv_reserve, View.GONE);
                }

                GlideApp.with(mContext).load(item.getMap().getBase_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into((ImageView) holder.getView(R.id.iv_img));
            }
        };
        mAdapter.setItemClickListner(position -> {
            SmartFitnessInfoActivity.toActivity(this,mList.get(position).getMap().getId(),type);
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
                    loadData(true);
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
        loadData(true);
    }

    public void addData(List<NearIntellectPageBean.ListBean> list) {
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

    private void loadData(boolean isPost) {
        LogUtils.e(proId,cityId,disId,strId);
        ApiManager.getBusinessService().getNearIntellectPage(AccountManager.getInstance().getAccount(),
                AccountManager.getInstance().getNonstr(),
                pageNo,
                type,
                proId,
                cityId,
                disId,
                strId,
                brandName,
                subscribeState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NearIntellectPageBean>() {
                    @Override
                    protected void onSuccees(NearIntellectPageBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            addData(bean.getList());
                        } else if (bean.getCode().equals("77777") ){
                            //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
                            if(isPost) {
                                pageNo++;
                                loadData(false);
                            }else {
                                ToastUtil.showToast(bean.getMsg());
                                addData(bean.getList());
                            }
                        }else{
                            ToastUtil.showToast(bean.getMsg());
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
