package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.FieldEqueListBean;
import com.tianjin.beichentiyu.bean.FieldImgListBean;
import com.tianjin.beichentiyu.bean.FieldMsgBean;

import java.util.List;

public class VenuesInfoContract {
    public interface View extends IView {

        void addData(FieldMsgBean bean);

        void addImg(List<FieldImgListBean.ListBean> imgList);

        void addEquipment(FieldEqueListBean bran);
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
         * @param type
         */
        void getFieldEqueList(String id,String type);
    }
}
