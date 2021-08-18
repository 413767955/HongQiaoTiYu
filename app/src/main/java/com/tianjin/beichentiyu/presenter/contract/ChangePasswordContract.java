package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;

public class ChangePasswordContract {
    public interface View extends IView {
        void success();

        void failure(String msg);
    }

    public interface Presenter extends IPresenter {
        /**
         * 旧密码修改密码
         * @param oldPwd
         * @param newPwd
         */
        void updatePwd(String tel,String oldPwd,String newPwd);

        /**
         * 短信修改密码
         * @param newPwd
         */
        void updateSmsPwd(String tel,String newPwd,String code);
        /**
         * 发送短信验证码
         * @param tel
         */
        void sendSmsCode(String tel);
    }}
