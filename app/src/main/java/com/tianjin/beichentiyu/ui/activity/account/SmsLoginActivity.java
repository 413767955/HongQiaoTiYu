package com.tianjin.beichentiyu.ui.activity.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.EditText;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.LoginBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.LocationManager;
import com.tianjin.beichentiyu.manager.SmsCodeManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.ui.MainActivity;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/5 13:52
 * E-Mail Address：673236072@qq.com
 * 验证码登陆
 */
public class SmsLoginActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivity(Activity activity,int requestCode){
        activity.startActivityForResult(new Intent(activity,SmsLoginActivity.class),requestCode);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_sms)
    EditText mEtSms;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private LodingDialog lodingDialog;
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_sms_login;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this,"登陆中...");
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mTvLogin.setOnClickListener(v -> smsLogin());
        mTvGetCode.setOnClickListener(v -> sendSmsCode());
    }

    private void smsLogin(){
        String phone = mEtPhone.getText().toString();
        String code = mEtSms.getText().toString();
        if(!AccountCommonUtil.checkPhoneAndCode(phone,code)){
            return;
        }
        double lat = LocationManager.getInstance().getLatitude();
        double lon = LocationManager.getInstance().getLongitude();
        if(lat == 0 || lon == 0){
            ToastUtil.showToast("未获取到位置信息!");
            return;
        }
        lodingDialog.show();
        ApiManager.getAccountService().smsLogin(phone, code,lat,lon)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    protected void onSuccees(LoginBean loginBean) throws Exception {
                        if(loginBean.isSuccessful()) {
                            AccountManager.getInstance().setMemBean(loginBean.getMem());
                            loginSuccess();
                        }else{
                            ToastUtil.showToast(loginBean.getMsg());
                        }
                        lodingDialog.dismiss();
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(e.toString());
                        lodingDialog.dismiss();
                    }
                });
    }

    /**
     * 登录成功
     */
    public void loginSuccess() {
        if(AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().isCompleteInformation()) {
                MainActivity.toActivity(this);
                ToastUtil.showToast("登录成功");
            } else {
                UserInfoCompleteActivity.toCompleteInfoActivity(this);
            }
            setResult(RESULT_OK);
            finish();
        }
    }


    private int time = 0;
    private Handler handler = new Handler();
    private Runnable run = () -> {
        time--;
        if (time <= 0) {
            stopTime();
            return;
        } else {
            startTime(time);
        }
    };
    /**
     * 发送短信验证码
     */
    private void sendSmsCode() {
        if (time != 0) {
            ToastUtil.showToast("" + time + "秒后再重试!");
            return;
        }
        if(SmsCodeManager.sendLoginSmsCode(mEtPhone.getText().toString())){
            startTime(60);
        }
    }
    /**
     * 开始倒计时
     *
     * @param time
     */
    private void startTime(int time) {
        this.time = time;
        mTvGetCode.setText("(" + this.time + "秒后重试)");
        handler.postDelayed(run, 1000);
    }

    /**
     * 停止倒计时
     */
    private void stopTime() {
        handler.removeCallbacks(run);
        mTvGetCode.setText("获取验证码");
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(run);
        super.onDestroy();
    }

}
