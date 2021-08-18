package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.bean.NoopsycheFootpathBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.NoopsychaFitnessPresenter;
import com.tianjin.beichentiyu.presenter.contract.NoopsychaFitnessContract;
import com.tianjin.beichentiyu.utils.Constant;
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
 * Created by 黑瞳 on 2019/9/23 20:34
 * E-Mail Address：673236072@qq.com
 * 智能健身
 */
public class NoopsycheFitnessActivity extends BaseMvpActivity<NoopsychaFitnessContract.Presenter> implements NoopsychaFitnessContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,NoopsycheFitnessActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rpv)
    RollPagerView mRollViewPager;

    @BindView(R.id.lin_znbd)
    LinearLayout mLinZnbd;
    @BindView(R.id.lin_znqc)
    LinearLayout mLinZnqc;
    @BindView(R.id.lin_znlj)
    LinearLayout mLinZnlj;
    @BindView(R.id.lin_znjsf)
    LinearLayout mLinZnjsf;

//    @BindView(R.id.rl_layout)
//    RefreshLayout refreshLayout;
//    @BindView(R.id.rv_content)
//    RecyclerView mRvContent;
    @BindView(R.id.tv_hot_all)
    TextView mTvHotAll;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_nodata_view)
    TextView mTvNodataView;

    private StaticPagerAdapter mStaticPagerAdapter;
    private List<AppIndexBean.ListBean> mImgList;

    private List<NoopsycheFootpathBean> mNfList;
    private int pageSize = 5;
    private int pageNo = 1;
    private List<NearFieldPageHotBean.ListBean> mList = new ArrayList<>();
    private BaseAdapter recommendAdapter;
    @Override
    protected NoopsychaFitnessContract.Presenter initPresenter() {
        return new NoopsychaFitnessPresenter();
    }

    private String type = "2";//类型：1 场馆推荐 2 智能健身推荐

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_noopsyche_fitness;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRv();
        initRollPagerView();
    }
    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mLinZnbd.setOnClickListener(v -> {
            SmartFitnessListActivity.toActivity(this,"智能步道", Constant.SmartFitness.ZNBD);//智能步道
        });
        mLinZnqc.setOnClickListener(v -> {
            SmartFitnessListActivity.toActivity(this,"智能球场",Constant.SmartFitness.ZNQC);//智能球场
        });
        mLinZnlj.setOnClickListener(v -> {
            SmartFitnessListActivity.toActivity(this,"智能路径",Constant.SmartFitness.ZNLJ);//智能路径
        });
        mLinZnjsf.setOnClickListener(v -> {
            SmartFitnessListActivity.toActivity(this,"智能健身房",Constant.SmartFitness.ZNJSF);//智能健身房
        });
        mTvHotAll.setOnClickListener(v -> {
            NearFieldPageHotActivity.toActivity(this);
        });
    }

    @Override
    protected void firstRequest() {
        mPresenter.nearFieldPageHot(pageNo,type);
        loadAppIndexList();
    }

    private void initRv(){
        recommendAdapter = new BaseAdapter<NearFieldPageHotBean.ListBean>(R.layout.item_noopsyche_footpath,mList){
            @Override
            protected void convert(BaseViewHolder holder, NearFieldPageHotBean.ListBean item) {
                holder.setTvText(R.id.tv_name,item.getMap().getField_name());
                holder.setTvText(R.id.tv_address,item.getMap().getAddress());
                holder.setTvText(R.id.tv_journey, DistanceUtil.distanceUtil(item.getDistance()));

                GlideApp.with(NoopsycheFitnessActivity.this)
                        .load(item.getMap().getBase_img())
                        .error(R.drawable.icon_field_err)
                        .placeholder(R.drawable.icon_field_err)
                        .into((ImageView)holder.getView(R.id.iv_cover));
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        recommendAdapter.setItemClickListner(position -> {
            //CompetitionInfoActivity.toActivity(this,CompetitionInfoActivity.INFORMATION_CODE,mList.get(position).getMap().getId());
            SiteInfoActivity.toActivity(this, mList.get(position).getMap().getId());

        });
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRecyclerView.setFocusable(false);
        mRecyclerView.setAdapter(recommendAdapter);

    }
    private void initRollPagerView(){
        mStaticPagerAdapter = new StaticPagerAdapter(){
            @Override
            public View getView(ViewGroup container, int position) {
                AppIndexBean.ListBean bean = mImgList.get(position);
                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                GlideApp.with(container.getContext()).load(bean.getImgUrl())
                        .placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(view);
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

    @Override
    public void addData(NearFieldPageHotBean bean) {
//        refreshLayout.finishRefresh(true);
//        refreshLayout.finishLoadMore(true,true);
        pageNo = bean.getPageNo();
        mList.addAll(bean.getList());
        recommendAdapter.setData(mList);
        if (mList != null) {
            if (mList.size() > 0) {
                //refreshLayout.showEmpty();
                mTvNodataView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
               // refreshLayout.hideEmpty();
                mTvNodataView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }else {
           // refreshLayout.showEmpty();
            mTvNodataView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }
    }
    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout(){
//        refreshLayout.setHeaderView(new HeaderView(this));
//        refreshLayout.setFooterView(new FooterView(this));
        //下拉刷新
//        refreshLayout.setOnRefreshListener(() -> {
//            mList.clear();
//            mPresenter.nearFieldPageHot(pageSize,type);
//        });
        //上拉加载
//        refreshLayout.setOnLoadMoreListener(() -> {
//            loadData();
//        });
        //自动加载
//        refreshLayout.autoRefresh();
    }
    /**
     * 加载轮播图
     */
    public void loadAppIndexList(){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getAppIndexList(tel, nonstr, "1")
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

}
