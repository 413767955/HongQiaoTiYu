package com.heitong.frame.base.presenter.impl;

import androidx.annotation.NonNull;
/**
 * Created by 黑瞳 on 2019/7/23 20:45
 * E-Mail Address：673236072@qq.com
 */
public interface IPresenter {
    /**
     * 注入View，使之能够与View相互响应
     */
    void attachView(@NonNull IView iView);

    /**
     * 释放资源，如果使用了网络请求 可以在此执行IModel.cancelRequest()
     */
    void detachView();
}
