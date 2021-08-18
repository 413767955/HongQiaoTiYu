package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;

/**
 * Created by 黑瞳 on 2019/9/21 14:37
 * E-Mail Address：673236072@qq.com
 */
public class MyContract {
    public interface View extends IView {
        void updateData();
    }

    public interface Presenter extends IPresenter {
        void getMemberMsg();
    }
}
