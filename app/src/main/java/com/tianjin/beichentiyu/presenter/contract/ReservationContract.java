package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.FieldAppointListBean;

import java.util.List;

public class ReservationContract {
    public interface View extends IView {

        void reserveDate(List<FieldAppointListBean.ListBean> list);

        void reserveSuccess(boolean isReserve);
    }

    public interface Presenter extends IPresenter {
        /**
         * 获得指定场馆某日的预约记录
         * @param fId
         */
        void getSubscribeTime(String fId ,String date);


        /**
         * 场馆预约操作
         * @param fId
         * @param date      预约日期，格式 yyyy-mm-dd
         * @param aId       预约时间段表主键
         */
        void memDoLogAppointment(String fId ,String date,String aId);
    }
}
