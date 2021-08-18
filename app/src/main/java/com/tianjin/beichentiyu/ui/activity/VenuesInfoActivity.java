package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.AppBarLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.FieldEqueListBean;
import com.tianjin.beichentiyu.bean.FieldImgListBean;
import com.tianjin.beichentiyu.bean.FieldMsgBean;
import com.tianjin.beichentiyu.presenter.VenuesInfoPresenter;
import com.tianjin.beichentiyu.presenter.contract.VenuesInfoContract;
import com.tianjin.beichentiyu.widget.TransparentToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 场馆详情
 * 未使用
 */
public class VenuesInfoActivity extends BaseMvpActivity<VenuesInfoContract.Presenter> implements VenuesInfoContract.View {

    public static void toActivity(Context context, String id) {
        Intent intent = new Intent(context, VenuesInfoActivity.class);
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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;

    private String getId;
    private StaticPagerAdapter staticPagerAdapter;
    private List<FieldImgListBean.ListBean> imgList;

    @Override
    protected VenuesInfoContract.Presenter initPresenter() {
        return new VenuesInfoPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_venues_info;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initToolbar();
        initRollPagerView();
        getId = getIntent().getStringExtra("id");
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
       // mTvFacilityRepairs.setOnClickListener(v -> StadiumReservationActivity.toActivity(this,0));
    }

    @Override
    protected void firstRequest() {
        mPresenter.getFieldMsg(getId);
        mPresenter.getFieldImgList(getId);

    }

    private void initToolbar() {
        mImmersionBar.statusBarColor(android.R.color.transparent).init();
    }

    private void initRollPagerView() {
        staticPagerAdapter = new StaticPagerAdapter() {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                FieldImgListBean.ListBean bean = imgList.get(position);
                GlideApp.with(VenuesInfoActivity.this)
                        .asBitmap()
                        .load(bean.getImgUrl())
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
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

    @Override
    public void addData(FieldMsgBean bean) {
        tvName.setText(bean.getField().getFieldName());
        tvAddress.setText(bean.getField().getAddress());
    }

    @Override
    public void addImg(List<FieldImgListBean.ListBean> imgList) {
        this.imgList = imgList;
        staticPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void addEquipment(FieldEqueListBean bran) {

    }

}
