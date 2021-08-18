package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.MemberBankBean;

import java.util.List;

public class MyBankCardContract {
    public interface View extends IView {
        void addData(List<MemberBankBean.ListBean> list);
    }

    public interface Presenter extends IPresenter {
        void loadData();
    }
}
