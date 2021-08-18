package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.SiteListContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SiteListPresenter extends BasePresenterImpl<SiteListContract.View> implements SiteListContract.Presenter {
    private int getPageNo = 0;

    @Override
    public void nearFieldPage(int pageNo, String type, String smartType,
                              String proId,
                              String cityId,
                              String disId,
                              String strId,
                              String brandName,
                              String subscribeState) {
        getPageNo = pageNo;
        getNearFieldPage(true, getPageNo, type, smartType, proId,
                cityId,
                disId,
                strId,
                brandName,
                subscribeState);
    }

    private void getNearFieldPage(boolean isPost, int pageNo, String type, String smartType,
                                  String proId,
                                  String cityId,
                                  String disId,
                                  String strId,
                                  String brandName,
                                  String subscribeState) {
        ApiManager.getBusinessService().getNearFieldPage(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), pageNo, type, smartType,
                proId,
                cityId,
                disId,
                strId,
                brandName,
                subscribeState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NearFieldPageBean>() {
                    @Override
                    protected void onSuccees(NearFieldPageBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mView.addData(bean.getList());
                        } else if (bean.getCode().equals("77777")) {
                            //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
                            if (isPost) {
                                getPageNo++;
                                getNearFieldPage(false, getPageNo, type, smartType,proId,
                                        cityId,
                                        disId,
                                        strId,
                                        brandName,
                                        subscribeState);
                            } else {
                                ToastUtil.showToast(bean.getMsg());
                                mView.addData(bean.getList());
                            }
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        mView.addData(null);
                    }
                });
    }
}
