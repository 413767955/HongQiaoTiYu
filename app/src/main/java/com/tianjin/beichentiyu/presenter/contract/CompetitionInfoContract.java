package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.ArtileMsgBean;


public class CompetitionInfoContract {

    public interface View extends IView {
        /**
         * 添加数据
         * @param bean
         */
        void addData(ArtileMsgBean bean);
    }

    public interface Presenter extends IPresenter {
        void loadArtileMsg(String aId);
    }
}
