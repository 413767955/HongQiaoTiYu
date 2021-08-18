package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ActivityPageBean;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.bean.OrganizationInfoBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.TransparentToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的组织
 */
public class OrganizationalInfoActivity extends BaseActivity {
    public static void toActivity(Context context, String orgId) {
        Intent intent = new Intent(context, OrganizationalInfoActivity.class);
        intent.putExtra("orgId", orgId);
        context.startActivity(intent);
    }

    private String orgId;

    @BindView(R.id.top_view)
    View mTopView;
    @BindView(R.id.toolbar)
    TransparentToolbar mToolbar;

    @BindView(R.id.iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.iv_phone)
    ImageView mIvPhone;

    @BindView(R.id.tv_msg1)
    TextView mTvMsg1;
    @BindView(R.id.tv_msg2)
    TextView mTvMsg2;
    @BindView(R.id.tv_msg3)
    TextView mTvMsg3;
    @BindView(R.id.tv_msg4)
    TextView mTvMsg4;

    @BindView(R.id.rl_information)
    RelativeLayout mRlInformation;
    @BindView(R.id.rv_information)
    RecyclerView mRvInformation;

    @BindView(R.id.rl_activity)
    RelativeLayout mRlActivity;
    @BindView(R.id.rv_activity)
    RecyclerView mRvActivity;
    @BindView(R.id.rv_spare)
    RecyclerView rvSpare;

    private List<ArticleListBean.ListBean> mInformationList;
    private BaseAdapter mInformationAdapter;

    private List<ActivityPageBean.ListBean> mActivityList;
    private BaseAdapter mActivityAdapter;

    private String proName;
    private String cityName;
    private OrganizationInfoBean.OrganBean organ;

    @Override
    protected void initData() {
        orgId = getIntent().getStringExtra("orgId");
    }

    @Override
    protected void firstRequest() {
        getOrganizationMsg();
        loadInformationData();
        loadActivityData();
    }


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_organizational_info;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        mImmersionBar.statusBarColor(android.R.color.transparent).init();
        mImmersionBar.setStatusBarView(this, mTopView);
        initInformationRv();
        initActivityRv();
        spareRV();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setMyTitleColor(getResources().getColor(R.color.color_ffffff));
        mToolbar.setBackWhite();
        mToolbar.setLeftOnClick(v -> finish());
        mIvPhone.setOnClickListener(v -> {
            if (organ != null && !TextUtils.isEmpty(organ.getTel())) {
                AccountCommonUtil.actionCall(this, organ.getTel());
            }
        });
        mRlInformation.setOnClickListener(v -> {
            OrgInformationActivity.toActivity(this, orgId);
        });
        mRlActivity.setOnClickListener(v -> {
            OrgActivityActivity.toActivity(this, orgId);
        });
    }


    /**
     * 初始化组织详情
     */
    private void initOrgInfo() {
        if (organ == null) {
            return;
        }
        if (!TextUtils.isEmpty(organ.getOrgLogo())) {
            GlideApp.with(OrganizationalInfoActivity.this)
                    .load(organ.getOrgLogo())
                    .into(mIvHead);
                    //.transition(new DrawableTransitionOptions().crossFade())

        }
        mTvName.setText(organ.getOrgName());
        mTvLocation.setText(proName + " " + cityName);
        mTvMsg1.setText(organ.getBuildTime());
        mTvMsg2.setText(organ.getMainMan());
        mTvMsg3.setText(organ.getBigOrg());
        mTvMsg4.setText(organ.getCommittee());
        if (TextUtils.isEmpty(organ.getTel())) {
            mIvPhone.setVisibility(View.GONE);
        } else {
            mIvPhone.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取组织详情
     */
    private void getOrganizationMsg() {
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getOrganizationMsg(tel, nonstr, orgId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OrganizationInfoBean>() {
                    @Override
                    protected void onSuccees(OrganizationInfoBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            organ = bean.getOrgan();
                            proName = bean.getProName();
                            cityName = bean.getCityName();
                            initOrgInfo();
                            if (bean.getSuppleList() != null && bean.getSuppleList().size() > 0) {
                                suppleList = new ArrayList<>();
                                suppleList.addAll(bean.getSuppleList());
                                //模拟数据
                                /*OrganizationInfoBean.SuppleListBean suppleBean;
                                for (int i = 0; i < 5; i++) {
                                    suppleBean = new OrganizationInfoBean.SuppleListBean();
                                    suppleBean.setKey_name("name" + i);
                                    suppleBean.setKey_value("value" + i);
                                    suppleList.add(suppleBean);
                                }*/
                                spareAdaptrt.setData(suppleList);

                            }
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    //显示组织详情下方的备用rv
    private BaseAdapter spareAdaptrt;
    private List<OrganizationInfoBean.SuppleListBean> suppleList;
    private void spareRV() {
        spareAdaptrt = new BaseAdapter<OrganizationInfoBean.SuppleListBean>(R.layout.item_spare_key_value, suppleList) {
            @Override
            protected void convert(BaseViewHolder holder, OrganizationInfoBean.SuppleListBean item) {
                holder.setTvText(R.id.key_name, item.getKey_name());
                holder.setTvText(R.id.key_value, item.getKey_value());
                holder.setBackgroundResource(R.id.image,R.mipmap.icon_spare_82a6ce);

            }
        };
        rvSpare.setLayoutManager(new LinearLayoutManager(this));
        rvSpare.setAdapter(spareAdaptrt);
    }

    /**
     * 初始化文章
     */
    private void initInformationRv() {
        mRvInformation.setLayoutManager(new LinearLayoutManager(this));
        mInformationAdapter = new BaseAdapter<ArticleListBean.ListBean>(R.layout.item_oi_information, mInformationList) {
            @Override
            protected void convert(BaseViewHolder holder, ArticleListBean.ListBean item) {
                holder.setTvText(R.id.tv_title, item.getTitle());
                holder.setTvText(R.id.tv_read_num, item.getSeeNum() + "次阅读");
                holder.setTvText(R.id.tv_comment_num, item.getGenTime());
                GlideApp.with(OrganizationalInfoActivity.this)
                        .load(item.getImgUrl())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView) holder.getView(R.id.iv_cover));
            }
        };
        mInformationAdapter.setItemClickListner(position -> {
            CompetitionInfoActivity.toActivity(OrganizationalInfoActivity.this,
                    Constant.Information.XWZX,
                    mInformationList.get(position).getAId());
        });
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvInformation.setFocusable(false);
        mRvInformation.setAdapter(mInformationAdapter);
    }



    /**
     * 加载文章数据
     */
    public void loadInformationData() {
        //LogUtils.e(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),orgId);
        String type = "3";//文章
        ApiManager.getBusinessService().getArticleList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                type, "1", orgId, 1, "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArticleListBean>() {
                    @Override
                    protected void onSuccees(ArticleListBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mInformationList = bean.getList();
                            mInformationAdapter.setData(mInformationList);
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    /**
     * 初始化活动
     */
    private void initActivityRv() {
        mRvActivity.setLayoutManager(new LinearLayoutManager(this));
        mActivityAdapter = new BaseAdapter<ActivityPageBean.ListBean>(R.layout.item_special_events, mActivityList) {
            @Override
            protected void convert(BaseViewHolder holder, ActivityPageBean.ListBean item) {
                holder.setTvText(R.id.tv_title, item.getTitle());
                holder.setTvText(R.id.tv_msg, item.getMsg());
                holder.setTvText(R.id.tv_num, "已报名" + item.getNow_mem() + "人");
                if (TextUtils.isEmpty(item.getPrice()) || "0".equals(item.getPrice())) {
                    holder.getView(R.id.tv_price_start).setVisibility(View.GONE);
                    holder.getView(R.id.tv_price_end).setVisibility(View.GONE);
                    holder.setTvText(R.id.tv_price, "免费");
                } else {
                    holder.getView(R.id.tv_price_start).setVisibility(View.VISIBLE);
                    holder.getView(R.id.tv_price_end).setVisibility(View.VISIBLE);
                    holder.setTvText(R.id.tv_price, item.getPrice());
                }
                ImageView ivCover = holder.getView(R.id.iv_cover);
                GlideApp.with(OrganizationalInfoActivity.this)
                        .asBitmap()
                        .load(item.getShow_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivCover);
            }
        };
        mActivityAdapter.setItemClickListner(position -> {
            SpecialEventsInfoActivity.toActivity(OrganizationalInfoActivity.this, mActivityList.get(position).getA_id(),
                    SpecialEventsInfoActivity.ACTIVITY_CODE,mActivityList.get(position).getShowActivityUrl());
        });
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvActivity.setFocusable(false);
        mRvActivity.setAdapter(mActivityAdapter);
    }

    /**
     * 加载文章数据
     */
    public void loadActivityData() {
        ApiManager.getBusinessService().getActivityPage(AccountManager.getInstance().getAccount(),
                AccountManager.getInstance().getNonstr(), orgId,
                1, "5")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ActivityPageBean>() {
                    @Override
                    protected void onSuccees(ActivityPageBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mActivityList = bean.getList();
                            mActivityAdapter.setData(mActivityList);
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
