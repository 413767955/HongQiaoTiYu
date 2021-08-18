package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;

/**
 * Created by 黑瞳 on 2019/9/21 14:36
 * E-Mail Address：673236072@qq.com
 */
public class LoginContract {
    public interface View extends IView {
        /**
         * 登陆成功
         */
        void loginSuccess();

        /**
         * 登陆失败
         */
        void loginError(String msg);
    }

    public interface Presenter extends IPresenter {
        /**
         * 登陆
         * @param tel   账号
         * @param pwd   密码
         */
        void login(String tel,String pwd,double lat,double lng);
    }
}
