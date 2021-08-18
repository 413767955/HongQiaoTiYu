package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.MemBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.PickerUtil;
import com.tianjin.beichentiyu.utils.TimeUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DatePicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BirthdayActivity extends BaseActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context, BirthdayActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rel_birthday)
    RelativeLayout mRelBirthday;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;

    private LodingDialog lodingDialog;
    //生日
    private String birthday;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_birthday;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this,"生日设置中...");
        mToolbar.setRightTv("保存",R.color.color_161616);
        birthday = AccountManager.getInstance().getMemBean().getBirthday();
        mTvBirthday.setText(birthday);
    }
    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> saveBirthday());
        //生日监听
        mRelBirthday.setOnClickListener(v -> {
            showBirthdaySetting();
        });
    }

    /**
     * 显示生日设置弹窗
     */
    private void showBirthdaySetting(){
        DatePicker datePicker = PickerUtil.datePickerYearMonthDay(this,TimeUtil.getDateString());
        datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                birthday = year+"/"+month+"/"+day;
                mTvBirthday.setText(birthday);
            }
        });
    }
    /**
     * 保存生日
     */
    private void saveBirthday(){
        if(TextUtils.isEmpty(birthday)){
            ToastUtil.showToast("请设置生日!");
            return;
        }
        lodingDialog.show();
        MemBean memBean = AccountManager.getInstance().getMemBean();
        String tel = memBean.getTel();
        String nonstr = memBean.getNonstr();
        ApiManager.getAccountService().setFinishMemberBirthday(tel,nonstr,birthday)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        lodingDialog.dismiss();
                        if(baseRespBean.isSuccessful()){
                            AccountManager.getInstance().getMemBean().setBirthday(birthday);
                            AccountManager.getInstance().saveMenBean();
                            finish();
                        }else{
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast("生日设置失败，请检查网络!");
                    }
                });
    }
}
