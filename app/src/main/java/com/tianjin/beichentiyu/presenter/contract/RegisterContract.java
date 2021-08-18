package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;

/**
 * Created by 黑瞳 on 2019/9/21 14:36
 * E-Mail Address：673236072@qq.com
 */
public class RegisterContract {
    public interface View extends IView {
        /**
         * 注册成功
         */
        void registerSuccess();

        /**
         * 注册失败
         */
        void registerError(String msg);
    }

    public interface Presenter extends IPresenter {
        /**
         * 登陆
         * @param tel   账号
         * @param pwd   密码
         * @param phoneCode   验证码
         */
        void register(String tel, String pwd,String phoneCode);
    }
}
