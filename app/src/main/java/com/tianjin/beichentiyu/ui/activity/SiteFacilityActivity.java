package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.bean.SearchSiteBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.SiteFacilityPresenter;
import com.tianjin.beichentiyu.presenter.contract.SiteFacilityContract;
import com.tianjin.beichentiyu.utils.DistanceUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.SiteFacilityToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/23 20:34
 * E-Mail Address：673236072@qq.com
 * 场地设施
 */
public class SiteFacilityActivity extends BaseMvpActivity<SiteFacilityContract.Presenter> implements SiteFacilityContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,SiteFacilityActivity.class));
    }
    @BindView(R.id.toolbar)
    SiteFacilityToolbar mToolbar;
    @BindView(R.id.rv_menu)
    RecyclerView mRvMenu;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_search_view)
    RelativeLayout mRlSearchView;
    @BindView(R.id.rv_search)
    RecyclerView mRvSearch;
    @BindView(R.id.lin_look_all)
    LinearLayout mLinLookAll;
    @BindView(R.id.rel_card1)
    RelativeLayout mRelCard1;
    @BindView(R.id.rel_card2)
    RelativeLayout mRelCard2;
    @BindView(R.id.imageView1)
    ImageView mImageView1;
    @BindView(R.id.imageView2)
    ImageView mImageView2;
    @BindView(R.id.tv_title1)
    TextView mTvTitle1;
    @BindView(R.id.tv_title2)
    TextView mTvTitle2;
    @BindView(R.id.tv_dis1)
    TextView mTvDis1;
    @BindView(R.id.tv_dis2)
    TextView mTvDis2;
    @BindView(R.id.tv_open_time1)
    TextView mTvOpenTime1;
    @BindView(R.id.tv_open_time2)
    TextView mTvOpenTime2;
    @BindView(R.id.tv_address1)
    TextView mTvAddress1;
    @BindView(R.id.tv_address2)
    TextView mTvAddress2;
    @BindView(R.id.tv_nodata_view)
    TextView mTvNodataView;

    private String smartType = "";//智能健身传参
    private String type = "";//场地设施传参
    private int pageNo = 1;
    private int hotPage = 1;
    private String hotType = "1";//场馆热门推荐

    private BaseAdapter mSearchAdapter;
    private List<SearchSiteBean.ListBean> mSearchList = new ArrayList<>();
    @Override
    protected SiteFacilityContract.Presenter initPresenter() {
        return new SiteFacilityPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_site_facility;
    }

    @Override
    protected void bindView() {
        mImmersionBar.statusBarColor(R.color.color_436EFF).init();
        ButterKnife.bind(this);
        initRvMenu();
        //initRvContent();
        initRv();//热门推荐
    }

    @Override
    public void onBackPressed() {
        if(mRlSearchView.getVisibility() == View.VISIBLE){
            mRlSearchView.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {
            if(mRlSearchView.getVisibility() == View.VISIBLE){
                mRlSearchView.setVisibility(View.GONE);
            }else {
                finish();
            }
        });
        mToolbar.setSearchOnClick(text -> {
            loadSearchData(text);
        });
        mLinLookAll.setOnClickListener(v -> SiteListActivity.toActivity(this,"全部场馆","",""));
        mRelCard1.setOnClickListener(v -> {
            if (bean1 != null){
                SiteInfoActivity.toActivity(this, bean1.getMap().getId());
            }
        });
        mRelCard2.setOnClickListener(v ->{
            if (bean2 != null){
                SiteInfoActivity.toActivity(this, bean2.getMap().getId());
            }
        });
    }

    /**
     * 搜索
     */
    private void initSearch(){
        if(mSearchAdapter == null){
            mSearchAdapter = new BaseAdapter<SearchSiteBean.ListBean>(R.layout.item_venue_list,mSearchList){
                @Override
                protected void convert(BaseViewHolder holder, SearchSiteBean.ListBean item) {
                    holder.setTvText(R.id.tv_title,item.getMap().getField_name());
                    holder.setTvText(R.id.tv_address,item.getMap().getAddress());
                    holder.setTvText(R.id.tv_distance,DistanceUtil.distanceUtil(item.getDistance()));
                    //holder.setTvText(R.id.tv_distance,item.getDistance());
                    if ("1".equals(item.getMap().getSubscribe_state())){
                        holder.setVisibility(R.id.tv_reserve, View.VISIBLE);
                    }else {
                        holder.setVisibility(R.id.tv_reserve, View.GONE);
                    }

                    GlideApp.with(mContext).load(item.getMap().getBase_img()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into((ImageView) holder.getView(R.id.iv_img));
                }
            };
            mRvSearch.setLayoutManager(new LinearLayoutManager(this));
            mRvSearch.setAdapter(mSearchAdapter);
            mSearchAdapter.setItemClickListner(position -> {
                 SiteInfoActivity.toActivity(this,mSearchList.get(position).getMap().getId());
            });
        }
    }
    private LodingDialog lodingDialog;
    /**
     * 搜索场地列表
     * @param text
     */
    private void loadSearchData(String text){
        if(TextUtils.isEmpty(text)){
            return;
        }
        lodingDialog = new LodingDialog(this,"搜索中...");
        lodingDialog.show();
        initSearch();
        ApiManager.getBusinessService().getFieldListByName(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<SearchSiteBean>() {
                    @Override
                    protected void onSuccees(SearchSiteBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mSearchList.clear();
                            mSearchList.addAll(bean.getList());
                            mSearchAdapter.setData(mSearchList);
                            mRlSearchView.setVisibility(View.VISIBLE);
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                        lodingDialog.dismiss();
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }
    @Override
    protected void firstRequest() {
        mPresenter.nearFieldPage(pageNo,type,smartType);
        mPresenter.nearFieldPageHot(hotPage,hotType);

    }

    /**
     * 初始化菜单
     */
    private void initRvMenu(){
        List<Pair<String,Integer>> list = new ArrayList<>();
        list.add(new Pair<>("健身园",R.mipmap.icon_sf_jsy));
        list.add(new Pair<>("足篮排",R.mipmap.icon_sf_zlp));
        list.add(new Pair<>("乒羽网",R.mipmap.icon_sf_pyw));
        list.add(new Pair<>("健身锻炼",R.mipmap.icon_sf_jsdl));
        list.add(new Pair<>("休闲娱乐",R.mipmap.icon_sf_xxyl));
        list.add(new Pair<>("高危体育",R.mipmap.icon_sf_gwty));
        list.add(new Pair<>("竞技对抗",R.mipmap.icon_sf_jjdk));
        list.add(new Pair<>("健身步道",R.mipmap.icon_sf_jsbd));

       /* list.add(new Pair<>("街镇文体中心",R.mipmap.icon_sf4));
        list.add(new Pair<>("游泳馆",R.mipmap.icon_sf5));
        list.add(new Pair<>("社区健身中心",R.mipmap.icon_sf3));
        list.add(new Pair<>("笼式多功能运动场",R.mipmap.icon_sf8));*/
        list.add(new Pair<>("其他场地",R.mipmap.icon_sf_qtyd));
        list.add(new Pair<>("全部",R.mipmap.icon_sf10));
        BaseAdapter adapter = new BaseAdapter<Pair<String,Integer>>(R.layout.item_site_facility_menu,list) {
            @Override
            protected void convert(BaseViewHolder holder, Pair<String,Integer> item) {
                holder.setTvText(R.id.tv_name,item.first);
                holder.setBackgroundResource(R.id.iv_icon,item.second);
            }
        };
        adapter.setItemClickListner(position -> {
            if (position +1 == list.size()){
                //查看全部
                SiteListActivity.toActivity(this, list.get(position).first, "", "");
            }else {
                SiteListActivity.toActivity(this, list.get(position).first, (position + 1) + "", "");
            }
        });
        mRvMenu.setLayoutManager(new GridLayoutManager(this,5));
        mRvMenu.setAdapter(adapter);
    }

    private NearFieldPageBean.ListBean bean1;
    private NearFieldPageBean.ListBean bean2;
    @Override
    public void addData(NearFieldPageBean bean) {
        try {
            bean1 = bean.getList().get(0);
            bean2 = bean.getList().get(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        initBanner();
    }

    private List<NearFieldPageHotBean.ListBean> hotList = new ArrayList<>();

    /**
     * 猜你喜欢  列表
     * @param bean
     */
    @Override
    public void addHotData(NearFieldPageHotBean bean) {
        if (bean.getList() != null && !bean.getList().isEmpty()){
            hotList.addAll(bean.getList());
            recommendAdapter.setData(hotList);
            mTvNodataView.setVisibility(View.GONE);
        }else {
            mTvNodataView.setVisibility(View.VISIBLE);
        }


    }
    private BaseAdapter recommendAdapter;
    private void initRv(){
        recommendAdapter = new BaseAdapter<NearFieldPageHotBean.ListBean>(R.layout.item_noopsyche_footpath,hotList){
            @Override
            protected void convert(BaseViewHolder holder, NearFieldPageHotBean.ListBean item) {
                holder.setTvText(R.id.tv_name,item.getMap().getField_name());
                holder.setTvText(R.id.tv_address,item.getMap().getAddress());
                holder.setTvText(R.id.tv_journey,DistanceUtil.distanceUtil(item.getDistance()));

                GlideApp.with(SiteFacilityActivity.this)
                        .load(item.getMap().getBase_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView)holder.getView(R.id.iv_cover));
            }
        };
        mRvContent.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        recommendAdapter.setItemClickListner(position -> {
            SiteInfoActivity.toActivity(this, hotList.get(position).getMap().getId());

        });
        mRvContent.setNestedScrollingEnabled(false);
        mRvContent.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvContent.setFocusable(false);
        mRvContent.setAdapter(recommendAdapter);

    }

    /**
     * 设置两个场馆
     */
    private void initBanner(){
        if(bean1 != null){
            String url = bean1.getMap().getBase_img();
            if(!TextUtils.isEmpty(url)) {
                GlideApp.with(this)
                        .load(url)
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into(mImageView1);
            }
            mTvTitle1.setText(bean1.getMap().getField_name());
            mTvDis1.setText("距离: "+DistanceUtil.distanceUtil(bean1.getDistance()));
            mTvOpenTime1.setText("开放时间: "+bean1.getMap().getOpen_time());
            mTvAddress1.setText("地址: "+bean1.getMap().getAddress());
        }

        if(bean2 != null){
            String url = bean2.getMap().getBase_img();
            if(!TextUtils.isEmpty(url)) {
                GlideApp.with(this)
                        .load(url)
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into(mImageView2);
            }
            mTvTitle2.setText(bean2.getMap().getField_name());
            mTvDis2.setText("距离: "+DistanceUtil.distanceUtil(bean2.getDistance()));
            mTvOpenTime2.setText("开放时间: "+bean2.getMap().getOpen_time());
            mTvAddress2.setText("地址: "+bean2.getMap().getAddress());
        }else {
            mRelCard2.setVisibility(View.INVISIBLE);
        }
    }
}
