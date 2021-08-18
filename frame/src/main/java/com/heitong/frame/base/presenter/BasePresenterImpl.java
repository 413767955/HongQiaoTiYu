package com.heitong.frame.base.presenter;

import androidx.annotation.NonNull;

import com.heitong.frame.base.presenter.impl.IPresenter;
import com.heitong.frame.base.presenter.impl.IView;

/**
 * Created by 黑瞳 on 2019/7/23 20:45
 * E-Mail Address：673236072@qq.com
 */
public abstract class BasePresenterImpl<T extends IView> implements IPresenter {
    protected T mView;

    @Override
    public void attachView(@NonNull IView iView) {
        mView = (T) iView;
    }

    @Override
    public void detachView() {

    }
}
