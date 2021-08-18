package com.tianjin.beichentiyu.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.LoginBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.LoginContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{
    @Override
    public void login(String tel, String pwd,double lat,double lng) {
        LogUtils.e("lat="+lat,"lng="+lng);
        ApiManager.getAccountService().login(tel, pwd,lat,lng)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LoginBean>() {
                    @Override
                    protected void onSuccees(LoginBean loginBean) throws Exception {
                        if(loginBean.isSuccessful()) {
                            AccountManager.getInstance().setMemBean(loginBean.getMem());
                            mView.loginSuccess();
                        }else{
                            mView.loginError(loginBean.getMsg());
                        }
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.loginError(e.toString());
                    }
                });
    }

    @Override
    public void detachView() {

    }
}
