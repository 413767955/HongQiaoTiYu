package com.tianjin.beichentiyu.ui.activity.my;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.MemberAreaMsgBean;
import com.tianjin.beichentiyu.bean.TaskTotalMsgBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.FilterWindowManager;
import com.tianjin.beichentiyu.ui.activity.AreaTaskActivity;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;
import com.tianjin.beichentiyu.widget.YearPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 维修统计
 */
public class MaintenanceRecordActivity extends BaseActivity implements View.OnClickListener {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,MaintenanceRecordActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_total)
    TextView mTvTotal;
    @BindView(R.id.tv_complete_total)
    TextView mTvCompleteTotal;
    @BindView(R.id.tv_undone_total)
    TextView mTvUndoneTotal;
    @BindView(R.id.rel_filter)
    RelativeLayout mRelFilter;
    @BindView(R.id.tv_start_time)
    TextView mTvStartTime;
    @BindView(R.id.btn_inquire)
    Button mBtnInquire;
    @BindView(R.id.tv_authority)
    TextView mTvAuthority;


    private FilterWindowManager filterManager;
    String proId = "";
    String cityId = "";
    String disId = "";
    String strId = "";
    String startTime = "";

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_maintenance_record;
    }
    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        filterManager = new FilterWindowManager(mRelFilter);
        filterManager.setCallback((proId, cityId, disId, strId, brandName, subscribeState,region) -> {
            this.proId = proId;
            this.cityId = cityId;
            this.disId = disId;
            this.strId = strId;
            mTvAddress.setText(region);
        });
    }
    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mTvAddress.setOnClickListener(this);
        mTvStartTime.setOnClickListener(this);
        mTvTotal.setOnClickListener(this);
        mTvCompleteTotal.setOnClickListener(this);
        mTvUndoneTotal.setOnClickListener(this);
        mBtnInquire.setOnClickListener(this);
    }

    @Override
    protected void firstRequest() {
        getMemberAreaMsg();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_address:
                filterManager.showRegion(true);
                break;
            case R.id.tv_start_time:
                YearPopupWindow yearPopupWindow = new YearPopupWindow(this,startTime);
                yearPopupWindow.setListener((data, position) -> {
                    startTime = data;
                    mTvStartTime.setText(startTime);
                });
                yearPopupWindow.showAsDropDown(mTvStartTime);
                yearPopupWindow.update();
                break;
            case R.id.tv_total:
                //所有任务
                AreaTaskActivity.toActivity(this,"全部任务",proId,cityId,disId,strId,startTime,"");
                break;
            case R.id.tv_complete_total:
                //已完成
                AreaTaskActivity.toActivity(this,"已完成统计",proId,cityId,disId,strId,startTime,"1");
                break;
            case R.id.tv_undone_total:
                //未完成
                AreaTaskActivity.toActivity(this,"未完成统计",proId,cityId,disId,strId,startTime,"0");
                break;
            case R.id.btn_inquire:
                getTaskTotalMsg();
                break;
        }
    }

    private void getTaskTotalMsg(){
        LodingDialog lodingDialog = new LodingDialog(App.get().getCurActivity(),"");
        lodingDialog.show();
        ApiManager.getBusinessService().getTaskTotalMsg(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),
                proId,cityId,disId,strId,startTime)
                . subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskTotalMsgBean>() {
                    @Override
                    protected void onSuccees(TaskTotalMsgBean taskTotalMsgBean) throws Exception {
                        lodingDialog.dismiss();
                        if(taskTotalMsgBean.isSuccessful()){
                            mTvTotal.setText(taskTotalMsgBean.getTotalNum());
                            mTvCompleteTotal.setText(taskTotalMsgBean.getFinishNum());
                            mTvUndoneTotal.setText(taskTotalMsgBean.getNotNum());
                        }else {
                            ToastUtil.showToast(taskTotalMsgBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }
    private void getMemberAreaMsg(){
        LodingDialog lodingDialog = new LodingDialog(App.get().getCurActivity(),"");
        lodingDialog.show();
        ApiManager.getBusinessService().getMemberAreaMsg(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberAreaMsgBean>() {
                    @Override
                    protected void onSuccees(MemberAreaMsgBean data) throws Exception {
                        lodingDialog.dismiss();
                        if (data.isSuccessful()){
                            mTvAuthority.setText(data.getRoleMsg());
                            //getTaskTotalMsg();
                        }else {
                            ToastUtil.showToast(data.getMsg());
                            finish();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

}
