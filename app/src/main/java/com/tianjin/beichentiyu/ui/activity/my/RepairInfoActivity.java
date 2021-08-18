package com.tianjin.beichentiyu.ui.activity.my;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.RepairRecordBean;
import com.tianjin.beichentiyu.widget.CustomToolbar;

/**
 * 报修记录详情
 */
public class RepairInfoActivity extends BaseActivity {

    public static void toActivity(Context context, RepairRecordBean.ListBean bean){
        Intent intent = new Intent(context,RepairInfoActivity.class);
        intent.putExtra("bean",bean);
        context.startActivity(intent);
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.iv_img)
    ImageView mIvImg;//场馆图片
    @BindView(R.id.tv_name)
    TextView mTvName;//场馆名称
    @BindView(R.id.tv_address)
    TextView mTvAddress;//地址
    @BindView(R.id.tv_time)
    TextView mTime;
    @BindView(R.id.tv_eName)
    TextView mTvEName;//器械名称
    @BindView(R.id.tv_content)
    TextView mTvContent;//损坏描述
    @BindView(R.id.tv_state)
    TextView mTvState;//报修状态
    @BindView(R.id.iv_repair_img1)
    ImageView mIvRepairImg;
    @BindView(R.id.iv_repair_img2)
    ImageView mIvRepairImg2;

    private RepairRecordBean.ListBean dataBean;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_repair_info;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        dataBean = (RepairRecordBean.ListBean) getIntent().getSerializableExtra("bean");
        inDataView();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }

    private void inDataView(){
        if (dataBean == null){
            return;
        }
        GlideApp.with(this)
                .load(dataBean.getBase_img())
                .placeholder(R.drawable.icon_field_err)
                .error(R.drawable.icon_field_err)
                .into(mIvImg);
        mTvName.setText(dataBean.getField_name());
        mTvAddress.setText(dataBean.getAddress());
        mTvEName.setText(dataBean.getE_name());
        mTvContent.setText(dataBean.getContent());
        mTime.setText(dataBean.getGen_time());
        if (!TextUtils.isEmpty(dataBean.getState())) {
            if ("0".equals(dataBean.getState())) {
                mTvState.setText("待处理");
            } else {
                mTvState.setText("已处理");
            }
        }
        if (dataBean.getRepair_img_one() != null && !TextUtils.isEmpty(dataBean.getRepair_img_one())) {
            mIvRepairImg .setVisibility(View.VISIBLE);
            GlideApp.with(this)
                    .load(dataBean.getRepair_img_one())
                    .placeholder(R.drawable.icon_field_err)
                    .error(R.drawable.icon_field_err)

                    .into(mIvRepairImg);
        }else {
            mIvRepairImg.setVisibility(View.GONE);
        }
        if (dataBean.getRepair_img_two() != null && !TextUtils.isEmpty(dataBean.getRepair_img_two())) {
            mIvRepairImg2.setVisibility(View.VISIBLE);
            GlideApp.with(this)
                    .load(dataBean.getRepair_img_two())
                    .placeholder(R.drawable.icon_field_err)
                    .error(R.drawable.icon_field_err)
                    .into(mIvRepairImg2);
        }else {
            mIvRepairImg2.setVisibility(View.GONE);
        }
    }
}
