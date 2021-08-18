package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.ArticleListBean;

public class CompetitionContract {
    public interface View extends IView {
        void addData(ArticleListBean bean);
        void finishRefresh();
    }

    public interface Presenter extends IPresenter {
        void loadData(int pageNo);
    }
}
