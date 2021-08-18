package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.manager.SmsCodeManager;
import com.tianjin.beichentiyu.presenter.ChangePasswordPresenter;
import com.tianjin.beichentiyu.presenter.contract.ChangePasswordContract;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.EditTextInputFilterUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/5 14:38
 * E-Mail Address：673236072@qq.com
 * 忘记密码
 */
public class ForgetPasswordActivity extends BaseMvpActivity<ChangePasswordContract.Presenter> implements ChangePasswordContract.View {
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, ForgetPasswordActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.et_sms)
    EditText mEtCode;
    @BindView(R.id.et_password)
    EditText mEtPwd;
    @BindView(R.id.tv_ok)
    TextView mTvOk;

    @Override
    protected ChangePasswordContract.Presenter initPresenter() {
        return new ChangePasswordPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        InputFilter[] inputFilters = {EditTextInputFilterUtil.getLengthFilter(), EditTextInputFilterUtil.getTextTypeFilter()};
        mEtPwd.setFilters(inputFilters);
    }

    @Override
    protected void firstRequest() {
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mTvOk.setOnClickListener(v -> updatePwd());
        mTvGetCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                ToastUtil.showToast("请输入手机号");
                return;
            }
            sendSmsCode();

        });

    }

    /**
     * 验证码修改密码
     */
    private void updatePwd() {
        String newPwd = mEtPwd.getText().toString();
        String code = mEtCode.getText().toString();
        if(!AccountCommonUtil.checkPwdAndCode(newPwd,code)){
            return;
        }
        mPresenter.updateSmsPwd(mEtPhone.getText().toString(), newPwd, code);
    }

    @Override
    public void success() {
        ToastUtil.showToast("修改密码成功");
        finish();
    }

    @Override
    public void failure(String msg) {
        ToastUtil.showToast(msg);
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
        if(SmsCodeManager.sendChangePasswordSmsCode(mEtPhone.getText().toString())){
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
