package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.SmsCodeManager;
import com.tianjin.beichentiyu.presenter.RegisterPresenter;
import com.tianjin.beichentiyu.presenter.contract.RegisterContract;
import com.tianjin.beichentiyu.ui.activity.ProtocolActivity;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.EditTextInputFilterUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/5 14:22
 * E-Mail Address：673236072@qq.com
 */
public class RegisterActivity extends BaseMvpActivity<RegisterContract.Presenter> implements RegisterContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,RegisterActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    //手机号
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    //验证码
    @BindView(R.id.et_sms)
    EditText mEtSms;
    //密码
    @BindView(R.id.et_password)
    EditText mEtPassword;
    //重复密码
    @BindView(R.id.et_password2)
    EditText mEtPassword2;
    //注册
    @BindView(R.id.tv_register)
    TextView mTvRegister;
    //立即登陆
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    private LodingDialog lodingDialog;

    @BindView(R.id.tv_agreement)
    TextView mTvAgreement;

    @BindView(R.id.checkbox)
    CheckBox mCheckbox;

    @Override
    protected RegisterContract.Presenter initPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_register;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this,"注册中...");

        InputFilter[] inputFilters = {EditTextInputFilterUtil.getLengthFilter(), EditTextInputFilterUtil.getTextTypeFilter()};
        mEtPassword.setFilters(inputFilters);
        mEtPassword2.setFilters(inputFilters);
        agreement();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mTvLogin.setOnClickListener(v -> finish());
        mTvRegister.setOnClickListener(v -> register());
        mTvGetCode.setOnClickListener(v -> sendSmsCode());
    }

    /**
     * 注册
     */
    private void register(){
        String phone = mEtPhone.getText().toString();
        String code = mEtSms.getText().toString();
        String pwd1 = mEtPassword.getText().toString();
        String pwd2 = mEtPassword2.getText().toString();
        if(!AccountCommonUtil.checkPhoneAndPwdAndCode(phone,code,pwd1,pwd2)){
            return;
        }
        if (!mCheckbox.isChecked()){
            ToastUtil.showToast("请先确认阅读《用户协议与隐私政策》");
            return;
        }
        mPresenter.register(phone, pwd1, code);
        lodingDialog.show();
    }
    @Override
    public void registerSuccess() {
        ToastUtil.showToast("注册成功");
        lodingDialog.dismiss();
        finish();
    }

    @Override
    public void registerError(String msg) {
        lodingDialog.dismiss();
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
        if(SmsCodeManager.sendRegisterSmsCode(mEtPhone.getText().toString())){
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
    /**
     * 使用富文本展示
     * 设置超链接事件
     */
    private void agreement() {
        //tv_agreement
        SpannableString mSpannableString;
        String content = "我已阅读并同意《用户协议与隐私政策》。";
        mSpannableString = new SpannableString(content);
        mSpannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ProtocolActivity.toActivity(RegisterActivity.this);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); //去除下划线

            }
        }, 7, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mSpannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 7, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
/*        mSpannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //StartIntentActivity.StartWebviewActivity(LoginActivity.this, Constants.SERVICE);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); //去除下划线

            }
        }, 14, 19, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mSpannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 14, 19, Spanned.SPAN_INCLUSIVE_INCLUSIVE);*/

        mTvAgreement.setMovementMethod(LinkMovementMethod.getInstance());//设置点击效果
        mTvAgreement.setText(mSpannableString);
    }
}
