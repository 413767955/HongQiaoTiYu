package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.ActivityPageBean;

public class SpecialEventsContract {
    public interface View extends IView {
        void addData(ActivityPageBean bean);
    }

    public interface Presenter extends IPresenter {
        void loadData(int pageNo);
    }
}
