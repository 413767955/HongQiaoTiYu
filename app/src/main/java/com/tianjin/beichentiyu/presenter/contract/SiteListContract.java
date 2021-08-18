package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;

import java.util.List;

public class SiteListContract {
    public interface View extends IView {

        void addData(List<NearFieldPageBean.ListBean> list);

        //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
        //void again(NearFieldPageBean bean);
    }

    public interface Presenter extends IPresenter {
        void nearFieldPage(int pageNo,String type,String smartType,
                           String proId,
                           String cityId,
                           String disId,
                           String strId,
                           String brandName,
                           String subscribeState);


    }
}
