package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;

public class SiteFacilityContract {
    public interface View extends IView {

        void addData(NearFieldPageBean bean);

        void addHotData(NearFieldPageHotBean bean);
        //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
        //void again(NearFieldPageBean bean);
    }

    public interface Presenter extends IPresenter {
        void nearFieldPage(int pageNo, String type, String smartType);

        void nearFieldPageHot(int pageNo, String type);
    }
}
