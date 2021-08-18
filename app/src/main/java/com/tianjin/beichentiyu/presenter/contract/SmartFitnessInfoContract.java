package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.IntellectEqueListBean;
import com.tianjin.beichentiyu.bean.IntellectImgListBean;
import com.tianjin.beichentiyu.bean.IntellectMsgBean;

import java.util.List;

public class SmartFitnessInfoContract {
    public interface View extends IView {

        void addData(IntellectMsgBean bean);

        void addImg(List<IntellectImgListBean.ListBean> imgList);

        void addEquipment(IntellectEqueListBean bran);
    }

    public interface Presenter extends IPresenter {
        /**
         * 场地详情
         * @param id
         */
        void getFieldMsg(String id);

        /**
         * 场地图片
         * @param id
         */
        void getFieldImgList(String id);

        /**
         * 场地器械
         * @param id
         */
        void getFieldEqueList(String id);
    }
}
