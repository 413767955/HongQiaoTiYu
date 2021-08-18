package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.LogTaskMsg;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 巡检任务详情
 * 年度维修统计任务详情
 */
public class MaintenanceTaskInfoActivity extends BaseMvpActivity<BaseContract.Presenter> {
    private int TYPE_CODE;
    public static int INSPECTION_CODE = 1;//巡检维修
    public static int STATISTICS_CODE = 2;//维修统计
    public static void toActivity(Context context,String logId,int type_code){
        Intent intent = new Intent(context,MaintenanceTaskInfoActivity.class);
        intent.putExtra("logId",logId);
        intent.putExtra("type_code",type_code);
        ((Activity)context).startActivityForResult(intent,InspectionMaintenanceActivity.REQUEST_CODE);
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.iv_img)
    ImageView mIvImg;//场馆图片
    @BindView(R.id.tv_name)
    TextView mTvName;//场馆名称
    @BindView(R.id.tv_address)
    TextView mTvAddress;//地址
    @BindView(R.id.tv_task_type)
    TextView mTvTaskType;
    @BindView(R.id.tv_time)
    TextView mTime;
    @BindView(R.id.iv_task_img)
    ImageView mIvTaskImg;
    @BindView(R.id.iv_task_img2)
    ImageView mIvTaskImg2;
    @BindView(R.id.btn_complete)
    Button mBtnComplete;//完工按钮

    private String logId = "";
    private LogTaskMsg msgBean = new LogTaskMsg();


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_maintenance_task_info;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        super.bindView( );
        ButterKnife.bind(this);
        Intent intent = getIntent();
        logId = intent.getStringExtra("logId");
        TYPE_CODE = intent.getIntExtra("type_code",-1);
        if (TYPE_CODE == STATISTICS_CODE){//如果是维修统计列表进入的，就不显示完工按钮
            mBtnComplete.setVisibility(View.GONE);
        }else {
            mBtnComplete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mBtnComplete.setOnClickListener(v ->
                CompleteActivity.toActivity(this,msgBean.getLogT().getLogId()));
    }

    @Override
    protected void firstRequest() {
        getMemLogTaskMsg();
    }

    private void getMemLogTaskMsg(){
        ApiManager.getBusinessService().getLogTaskMsg(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),logId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LogTaskMsg>() {
                    @Override
                    protected void onSuccees(LogTaskMsg bean) throws Exception {
                        if (bean.isSuccessful()){
                            setView(bean);
                            msgBean = bean;
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
    private void setView(LogTaskMsg bean){
        GlideApp.with(this)
                .load(bean.getField().getBaseImg())
                .placeholder(R.drawable.icon_field_err)
                .error(R.drawable.icon_field_err)
                .into(mIvImg);
        mTvName.setText(bean.getField().getFieldName());
        mTvAddress.setText(bean.getField().getAddress());
        switch (bean.getLogT().getTaskType()){
            case "1":
                mTvTaskType.setText("维修任务");
                break;
            case "2":
                mTvTaskType.setText("安装任务");
                break;
            case "3":
                mTvTaskType.setText("巡检任务");
                break;
        }

        if (bean.getLogT().getBuildTime().length() >= 10){
            String time = bean.getLogT().getBuildTime().substring(0, 10);
            mTime.setText(time);
        }else {
            mTime.setText(bean.getLogT().getBuildTime());
        }
        if (bean.getLogT().getShowImgOne() != null && !TextUtils.isEmpty(bean.getLogT().getShowImgOne())) {
            mIvTaskImg.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(bean.getLogR().getRepairImgOne()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(mIvTaskImg);
        }else {
            mIvTaskImg.setVisibility(View.GONE);
        }
        if (bean.getLogT().getShowImgTwo() != null && !TextUtils.isEmpty(bean.getLogT().getShowImgTwo())) {
            mIvTaskImg2.setVisibility(View.VISIBLE);
            GlideApp.with(this).load(bean.getLogR().getRepairImgTwo()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(mIvTaskImg2);
        }else {
            mIvTaskImg2.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == InspectionMaintenanceActivity.REQUEST_CODE){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
