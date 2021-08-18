package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.AppIndexBean;
import com.tianjin.beichentiyu.bean.PhysicalBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.FilterWindowManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/23 20:34
 * E-Mail Address：673236072@qq.com
 * 智能体侧
 */
public class NoopsycheFiancoActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,NoopsycheFiancoActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rpv)
    RollPagerView mRollViewPager;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;

    private StaticPagerAdapter mStaticPagerAdapter;
    private List<AppIndexBean.ListBean> mImgList;

    private BaseAdapter mDataAdapter;
    private List<PhysicalBean.ListBean> mDataList = new ArrayList<>();
    private int pageNo = 1;

    private FilterWindowManager filterManager;
    String proId = "";
    String cityId = "";
    String disId = "";
    String strId = "";
    String brandName = "";
    String subscribeState = "";

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_noopsyche_fianco;
    }

    @Override
    protected void firstRequest() {
        //加载轮播图
        loadAppIndexList();
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRollPagerView();
        initRefreshLayout();
        initDataRv();
        filterManager = new FilterWindowManager(drawerLayout);
        filterManager.setCallback((proId, cityId, disId, strId, brandName, subscribeState,region) -> {
            this.proId = proId;
            this.cityId = cityId;
            this.disId = disId;
            this.strId = strId;
            this.brandName = brandName;
            this.subscribeState = subscribeState;

            mDataList.clear();
            pageNo = 1;
            loadData();
        });
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }

    private void initRollPagerView(){
        mStaticPagerAdapter = new StaticPagerAdapter(){
            @Override
            public View getView(ViewGroup container, int position) {
                AppIndexBean.ListBean bean = mImgList.get(position);
                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.FIT_XY);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                GlideApp.with(container.getContext())
                        .load(bean.getImgUrl())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(view);
                return view;
            }

            @Override
            public int getCount() {
                return mImgList == null ? 0 : mImgList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }
        };
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(3000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(mStaticPagerAdapter);
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
                    pageNo++;
                    loadData();
                })
                .autoRefresh();
    }

    /**
     *  初始化列表
     */
    private void initDataRv(){
        mDataAdapter = new BaseAdapter<PhysicalBean.ListBean>(R.layout.item_my_reservation,mDataList) {
            @Override
            protected void convert(BaseViewHolder holder, PhysicalBean.ListBean item) {
                PhysicalBean.ListBean.MapBean mapBean = item.getMap();
                ImageView ivIcon = holder.getView(R.id.iv_icon);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvAddress = holder.getView(R.id.tv_address);
                TextView tvTime = holder.getView(R.id.tv_time);

                if(mapBean != null) {
                    if(!TextUtils.isEmpty(mapBean.getBase_img())) {
                        GlideApp.with(ivIcon.getContext()).load(mapBean.getBase_img()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(ivIcon);
                    }
                    tvTitle.setText(mapBean.getPhy_name());
                    tvAddress.setText(mapBean.getAddress());
                    tvTime.setText(mapBean.getOpen_time());
                }else{
                    tvTitle.setText("");
                    tvAddress.setText("");
                    tvTime.setText("");
                }
            }
        };
        mDataAdapter.setItemClickListner(position -> {
            NoopsycheFiancoInfoActivity.toActivity(this,mDataList.get(position).getMap().getId());
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mDataAdapter);
    }

    /**
     * 加载轮播图
     */
    public void loadAppIndexList(){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getAppIndexList(tel, nonstr, "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AppIndexBean>() {
                    @Override
                    protected void onSuccees(AppIndexBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mImgList = bean.getList();
                            mStaticPagerAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    /**
     * 加载列表数据
     */
    public void loadData(){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().showPhysicalPage(tel, nonstr, pageNo,
                proId,
                cityId,
                disId,
                strId,
                brandName,
                subscribeState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PhysicalBean>() {
                    @Override
                    protected void onSuccees(PhysicalBean bean) throws Exception {
                        refreshLayout.finishRefresh(true);
                        refreshLayout.finishLoadMore(true, true);
                        if (bean.isSuccessful()) {
                            addData(bean.getList());
                        }else if (bean.getCode().equals("77777")){
                            ToastUtil.showToast(bean.getMsg());
                            refreshLayout.finishLoadMore(true, false);
                            if (mDataList.isEmpty()) {
                                refreshLayout.showEmpty();
                            } else {
                                refreshLayout.hideEmpty();
                            }
                        }else{
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        refreshLayout.finishRefresh(true);
                    }
                });
    }
    public void addData(List<PhysicalBean.ListBean> list) {
        if (list.size() > 0) {
            mDataList.addAll(list);
            mDataAdapter.setData(mDataList);
            refreshLayout.finishRefresh(true);
        } else {
            refreshLayout.finishLoadMore(true, false);
            if (mDataList.isEmpty()) {
                refreshLayout.showEmpty();
            } else {
                refreshLayout.hideEmpty();
            }
            return;
        }
    }
}
