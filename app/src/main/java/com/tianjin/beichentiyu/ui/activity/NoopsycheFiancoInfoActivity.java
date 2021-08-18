package com.tianjin.beichentiyu.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.PhysicalImgList;
import com.tianjin.beichentiyu.bean.PhysicalInfoBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.TransparentToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/14 19:45
 * E-Mail Address：673236072@qq.com
 * 智能体测 - 智能体测详情
 */
public class NoopsycheFiancoInfoActivity extends BaseMvpActivity<BaseContract.Presenter>{
    public static void toActivity(Context context, String id) {
        Intent intent = new Intent(context, NoopsycheFiancoInfoActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    TransparentToolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rpv)
    RollPagerView mRollViewPager;
//    @BindView(R.id.tv_facility_repairs)
//    TextView mTvFacilityRepairs;
    @BindView(R.id.tv_field_name)
    TextView tvFieldName;//场地名称
//    @BindView(R.id.tv_number)//浏览人数
//    TextView tvNumber;
    @BindView(R.id.tv_content)
    TextView tvContent;//介绍
    @BindView(R.id.tv_subscribePrice)
    TextView tvSubscribePrice;//预约价格
    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;//开放时间
//    @BindView(R.id.tv_traffic)
//    TextView tvTraffic;//交通
    @BindView(R.id.tv_address)
    TextView tvAddress;//位置
//    @BindView(R.id.tv_distance)
//    TextView tvDistance;//距离
    @BindView(R.id.btn_phone)
    FloatingActionButton btnPhone;
    @BindView(R.id.tv_reserve)
    TextView mTvReserve;

    private String id;

    private PhysicalInfoBean.SpBean data;
    private List<PhysicalImgList.ListBean> imgList = new ArrayList<>();
    @Override
    protected void initData() {
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_noopsyche_fianco_info;
    }
    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        mImmersionBar.statusBarColor(android.R.color.transparent).init();
        initRollPagerView();
    }

    @Override
    protected void firstRequest() {
        getPhysicalImgList();
        loadData();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
//        mTvSubscribe.setOnClickListener(v -> {
//            //StadiumReservationActivity.toActivity(this,id);
//        });
        btnPhone.setOnClickListener(v -> {
            AccountCommonUtil.actionCall(this,data.getTel());
        });
        mTvReserve.setOnClickListener(v -> {
            FiancoReservationActivity.toActivity(this,data);
        });
    }

    /**
     * 加载数据
     */
    public void loadData(){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getPhysicalMsg(tel, nonstr, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PhysicalInfoBean>() {
                    @Override
                    protected void onSuccees(PhysicalInfoBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            boolean isCollection = TextUtils.equals("0",bean.getCollectState());
                            mToolbar.initCollectionView(isCollection,id, Constant.Information.TCXQ);
                            initPhysicalInfo(bean.getSp());
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

    private void getPhysicalImgList(){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getPhysicalImgList(tel, nonstr, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PhysicalImgList>() {
                    @Override
                    protected void onSuccees(PhysicalImgList bean) throws Exception {
                        if (bean.isSuccessful()) {
                            imgList.addAll(bean.getList());
                            staticPagerAdapter.notifyDataSetChanged();
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

    @SuppressLint("RestrictedApi")
    private void initPhysicalInfo(PhysicalInfoBean.SpBean data){
        if(data == null){
            return;
        }
        this.data = data;

        tvFieldName.setText(data.getPhyName());
        tvContent.setText(data.getContent());
        tvOpenTime.setText(data.getOpenTime());
        tvAddress.setText(data.getAddress());
        //拨打电话按钮
        if ("".equals(data.getTel())){
            btnPhone.setVisibility(View.GONE);
        }else {
            btnPhone.setVisibility(View.VISIBLE);
        }
        tvSubscribePrice.setText(data.getSubscribePrice());
    }
    StaticPagerAdapter staticPagerAdapter;
    private void initRollPagerView() {
        //设置适配器
        staticPagerAdapter = new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                PhysicalImgList.ListBean bean = imgList.get(position);
                GlideApp.with(NoopsycheFiancoInfoActivity.this)
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
}
