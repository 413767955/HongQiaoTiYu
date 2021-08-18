package com.heitong.frame.base.fragment;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;
import com.heitong.frame.utils.PMUtil;

/**
 * Created by 黑瞳 on 2019/7/23 21:26
 * E-Mail Address：673236072@qq.com
 */
public abstract class BaseMvpFragment <P extends IPresenter> extends BaseFragment{
    protected P mPresenter;
    @Override
    protected void onCreate() {
        attachView();
    }
    /**
     * P层绑定   若无则返回null;
     */
    protected abstract P initPresenter();
    /**
     * P层绑定V层
     */
    private void attachView() {
        mPresenter = initPresenter();
        if(mPresenter!=null && this instanceof IView){
            mPresenter.attachView((IView) this);
        }
    }
}
