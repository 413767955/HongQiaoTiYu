package com.tianjin.beichentiyu.ui.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.FieldEqueListBean;
import com.tianjin.beichentiyu.bean.FieldImgListBean;
import com.tianjin.beichentiyu.bean.FieldMsgBean;
import com.tianjin.beichentiyu.presenter.VenuesInfoPresenter;
import com.tianjin.beichentiyu.presenter.contract.VenuesInfoContract;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.DistanceUtil;
import com.tianjin.beichentiyu.widget.TransparentToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/3 13:30
 * E-Mail Address：673236072@qq.com
 * 场地详情
 */
public class SiteInfoActivity extends BaseMvpActivity<VenuesInfoContract.Presenter> implements VenuesInfoContract.View {

    public static void toActivity(Context context, String id) {
        Intent intent = new Intent(context, SiteInfoActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    TransparentToolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rpv)
    RollPagerView mRollViewPager;
    @BindView(R.id.tv_facility_repairs)
    TextView mTvFacilityRepairs;
    @BindView(R.id.tv_field_name)
    TextView tvFieldName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_ticket)
    TextView tvTicket;
    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_traffic)
    TextView tvTraffic;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distance)
    TextView tvDistance;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_phone)
    FloatingActionButton btnPhone;
    @BindView(R.id.btn_reserve)
    FloatingActionButton btnReserve;
    @BindView(R.id.deviceView)
    TextView mDeviceView;
    @BindView(R.id.tv_reserve)
    TextView mTvReserve;
    @BindView(R.id.rv_spare)
    RecyclerView rvSpare;

    private String getId;
    private StaticPagerAdapter staticPagerAdapter;
    private List<FieldImgListBean.ListBean> imgList;
    private List<FieldEqueListBean.ListBean> equipmentList = new ArrayList<>();//设备
    private FieldMsgBean fieldMsgBean = new FieldMsgBean();
    private String relId;//收藏对应主键id

    @Override
    protected VenuesInfoContract.Presenter initPresenter() {
        return new VenuesInfoPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_site_info;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initToolbar();
        //initAppBarLayout();
        initRollPagerView();
        getId = getIntent().getStringExtra("id");
        equipmentRV();
        spareRV();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mTvFacilityRepairs.setOnClickListener(v -> UploadFacilityInfoActivity.toActivity(this,fieldMsgBean.getField().getId()));
        mTvReserve.setOnClickListener(v -> StadiumReservationActivity.toActivity(this,fieldMsgBean.getField()));
        btnPhone.setOnClickListener(v -> {
            AccountCommonUtil.actionCall(this,fieldMsgBean.getField().getTel());
        });


    }

    @Override
    protected void firstRequest() {
        //场地详情
        mPresenter.getFieldMsg(getId);
        //场地图片
        mPresenter.getFieldImgList(getId);
        //场地器械
        //type  0 可用， 1 维修中
        mPresenter.getFieldEqueList(getId,"0");

    }

    private void initAppBarLayout() {
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            //初始verticalOffset为0，不能参与计算。
            if (verticalOffset == 0) {
                mToolbar.setTitleAlpha(0.0f);
            } else {
                //保留一位小数
                float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
                mToolbar.setTitleAlpha(alpha);
            }
        });
    }

    private void initToolbar() {
        mImmersionBar.statusBarColor(android.R.color.transparent).init();
    }

    private void initRollPagerView() {
        //设置适配器
        staticPagerAdapter = new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                FieldImgListBean.ListBean bean = imgList.get(position);
                GlideApp.with(SiteInfoActivity.this)
                        .asBitmap()
                        .load(bean.getImgUrl())
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into(view);
                return view;
            }

            @Override
            public int getCount() {
                if (imgList == null) {
                    return 0;
                }
                return imgList.size();
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
        mRollViewPager.setAdapter(staticPagerAdapter);
    }
    private BaseAdapter equipmentAdapter;
    /**
     * 设备list
     */
    private void equipmentRV(){
        equipmentAdapter = new BaseAdapter<FieldEqueListBean.ListBean>(R.layout.item_site_equipment,equipmentList) {
            @Override
            protected void convert(BaseViewHolder holder, FieldEqueListBean.ListBean item) {
                if (item.isShow()){
                    holder.getView(R.id.ll_data).setVisibility(View.VISIBLE);
                    holder.setTvText(R.id.tv_name,item.getBrand_name());
                    holder.setTvText(R.id.tv_num,item.getSize()+"");
                }else {
                    holder.getView(R.id.ll_data).setVisibility(View.GONE);
                }
                holder.setTvText(R.id.tv_title,item.getE_name());
                holder.setTvText(R.id.tv_brand_name,item.getBrand_name());
                if (item.getBuild_time().length() >= 7){
                    String time = item.getBuild_time().substring(0, 7);
                    holder.setTvText(R.id.tv_build_time,time);
                }else {
                    holder.setTvText(R.id.tv_build_time,item.getBuild_time());
                }
                ImageView ivCover = holder.getView(R.id.iv_cover);
                GlideApp.with(SiteInfoActivity.this)
                        .asBitmap()
                        .load(item.getE_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivCover);


            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(equipmentAdapter);
    }
    //显示距离下方的备用rv
    private BaseAdapter spareAdaptrt;
    private List<FieldMsgBean.SuppleListBean> suppleList;
    private void spareRV(){
        spareAdaptrt = new BaseAdapter<FieldMsgBean.SuppleListBean>(R.layout.item_spare_key_value,suppleList) {
            @Override
            protected void convert(BaseViewHolder holder, FieldMsgBean.SuppleListBean item) {
                holder.setTvText(R.id.key_name,item.getKey_name());
                holder.setTvText(R.id.key_value,item.getKey_value());
                holder.setBackgroundResource(R.id.image,R.mipmap.icon_spare);
            }
        };
        rvSpare.setLayoutManager(new LinearLayoutManager(this));
        rvSpare.setAdapter(spareAdaptrt);
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void addData(FieldMsgBean bean) {
        fieldMsgBean = bean;
        tvFieldName.setText(bean.getField().getFieldName());
        //tvNumber.setText();
        tvContent.setText(bean.getField().getContent());
        if ("0".equals(bean.getField().getPayState())){
            tvTicket.setText("是");
        }else {
            tvTicket.setText("否");
        }
        tvOpenTime.setText(bean.getField().getOpenTime());
        tvTraffic.setText(bean.getField().getTraffic());
        tvAddress.setText(bean.getField().getAddress());
        tvDistance.setText(DistanceUtil.distanceUtil(bean.getDistance()));

        //拨打电话按钮
        if ("".equals(bean.getField().getTel())){
            btnPhone.setVisibility(View.GONE);
        }else {
            btnPhone.setVisibility(View.VISIBLE);
        }
        //预约按钮
        if ("1".equals(bean.getField().getSubscribeState())){
            mTvReserve.setVisibility(View.VISIBLE);
        }else {
            mTvReserve.setVisibility(View.GONE);
        }

        relId = bean.getField().getId();

        boolean isCollection = TextUtils.equals("0",bean.getCollectState());
        mToolbar.initCollectionView(isCollection,relId, Constant.Information.CDXQ);

        if (bean.getSuppleList() != null && bean.getSuppleList().size() > 0 ){
            suppleList = new ArrayList<>();
            suppleList.addAll(bean.getSuppleList());
          /*  FieldMsgBean.SuppleListBean suppleBean;
            for (int i = 0; i < 5; i++) {
                suppleBean = new FieldMsgBean.SuppleListBean();
                suppleBean.setKey_name("name"+i);
                suppleBean.setKey_value("value"+i);
                suppleList.add(suppleBean);
            }*/
            spareAdaptrt.setData(suppleList);

        }
    }

    @Override
    public void addImg(List<FieldImgListBean.ListBean> imgList) {
        this.imgList = imgList;
        staticPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addEquipment(FieldEqueListBean bean) {
        if (bean.getList().size() > 0) {
            equipmentList.addAll(bean.getList());
            equipmentAdapter.notifyDataSetChanged();
            mDeviceView.setVisibility(View.VISIBLE);
            mTvFacilityRepairs.setVisibility(View.VISIBLE);
        }else {
            mDeviceView.setVisibility(View.GONE);
            mTvFacilityRepairs.setVisibility(View.GONE);
        }
    }
}
