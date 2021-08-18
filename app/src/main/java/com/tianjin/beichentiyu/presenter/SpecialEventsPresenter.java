package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ActivityPageBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.SpecialEventsContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpecialEventsPresenter  extends BasePresenterImpl<SpecialEventsContract.View> implements SpecialEventsContract.Presenter {
    @Override
    public void loadData(int pageNo) {
        ApiManager.getBusinessService().getActivityPage(AccountManager.getInstance().getAccount(),
                AccountManager.getInstance().getNonstr(),"",
                pageNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ActivityPageBean>() {
                    @Override
                    protected void onSuccees(ActivityPageBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            mView.addData(bean);
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    @Override
    public void detachView() {

    }
}
