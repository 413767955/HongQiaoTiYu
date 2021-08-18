package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.MemberBankBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.MyBankCardContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyBankCardPresenter extends BasePresenterImpl<MyBankCardContract.View> implements MyBankCardContract.Presenter{
    @Override
    public void loadData() {
        ApiManager.getWalletService().getMemberBankList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberBankBean>() {
                    @Override
                    protected void onSuccees(MemberBankBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            mView.addData(bean.getList());
                        }else{
                            mView.addData(null);
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.addData(null);
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

}
