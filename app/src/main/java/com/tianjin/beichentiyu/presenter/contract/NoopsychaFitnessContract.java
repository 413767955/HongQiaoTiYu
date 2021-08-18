package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;

/**
 * Created by 黑瞳 on 2019/9/21 11:59
 * E-Mail Address：673236072@qq.com
 */
public class NoopsychaFitnessContract {
    public interface View extends IView {

        void addData(NearFieldPageHotBean bean);
    }

    public interface Presenter extends IPresenter {
        void nearFieldPageHot(int pageNo,String type);
    }
}
