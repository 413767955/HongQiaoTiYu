package com.tianjin.beichentiyu.manager;

import android.text.TextUtils;

import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SmsCodeManager {
    /**
     * 发送注册验证码
     * @param tel
     */
    public static boolean sendRegisterSmsCode(String tel){
        return sendSmsCode(tel,Constant.Sms.REGISTER);
    }
    /**
     * 发送登陆验证码
     * @param tel
     */
    public static boolean sendLoginSmsCode(String tel){
        return sendSmsCode(tel,Constant.Sms.LOGIN);
    }
    /**
     * 发送修改密码验证码
     * @param tel
     */
    public static boolean sendChangePasswordSmsCode(String tel){
        return sendSmsCode(tel,Constant.Sms.CHANGE_PASSWORD);
    }
    /**
     * 发送短信验证码
     * @param tel
     */
    public static boolean sendSmsCode(String tel,String type) {
        if(TextUtils.isEmpty(tel)){
            ToastUtil.showToast("手机号不能为空!");
            return false;
        } else if (!AccountCommonUtil.isPhoneLength(tel)) {
            ToastUtil.showToast("请输入正确的手机号");
            return false;
        }
        ApiManager.getAccountService().sendPhone(tel, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        if(!baseRespBean.isSuccessful()){
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast("发送失败,请检查网络!");
                    }
                });
        return true;
    }
}
