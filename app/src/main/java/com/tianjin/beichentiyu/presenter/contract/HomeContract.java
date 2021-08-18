package com.tianjin.beichentiyu.presenter.contract;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.tianjin.beichentiyu.bean.ArticleListBean;

/**
 * Created by 黑瞳 on 2019/9/21 11:59
 * E-Mail Address：673236072@qq.com
 */
public class HomeContract {
    public interface View extends IView {
        /**
         * 体育新闻
         * @param bean
         */
        void addData(ArticleListBean bean);
        //void addData1(ArticleBase64Bean bean);
        /**
         * 获取公告数据
         */
        void noticeData(ArticleListBean bean);
    }

    public interface Presenter extends IPresenter {
        void loadData(int pageNo,String type);
    }
}
