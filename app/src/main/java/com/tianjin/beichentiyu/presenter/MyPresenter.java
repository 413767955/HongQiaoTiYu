package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.MemberMsgBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.MyContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/21 11:59
 * E-Mail Address：673236072@qq.com
 */
public class MyPresenter extends BasePresenterImpl<MyContract.View> implements MyContract.Presenter{
    @Override
    public void getMemberMsg() {
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getAccountService().getMemberMsg(tel,nonstr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberMsgBean>() {
                    @Override
                    protected void onSuccees(MemberMsgBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            AccountManager.getInstance().setMemBean(bean.getMem());
                            mView.updateData();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
