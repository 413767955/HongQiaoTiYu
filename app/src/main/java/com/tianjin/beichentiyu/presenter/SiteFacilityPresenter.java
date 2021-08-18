package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.SiteFacilityContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SiteFacilityPresenter extends BasePresenterImpl<SiteFacilityContract.View> implements SiteFacilityContract.Presenter {

    private int getPageNo = 0;

    @Override
    public void nearFieldPage(int pageNo, String type, String smartType) {
        getPageNo = pageNo;
        getNearFieldPage(true, getPageNo, type, smartType);
    }

    public void getNearFieldPage(boolean isPost, int pageNo, String type, String smartType) {
        ApiManager.getBusinessService().getNearFieldPage(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), pageNo, type, smartType,"","","","","","")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NearFieldPageBean>() {
                    @Override
                    protected void onSuccees(NearFieldPageBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mView.addData(bean);
                        } else if (bean.getCode() == "77777") {
                            if (isPost) {
                                getPageNo++;
                                getNearFieldPage(false, getPageNo, type, smartType);
                            } else {
                                ToastUtil.showToast(bean.getMsg());
                            }
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    private int hotPageNo = 0;

    @Override
    public void nearFieldPageHot(int pageNo, String type) {
        hotPageNo = pageNo;
        getNearFieldPageHot(true, hotPageNo, type);
    }

    public void getNearFieldPageHot(boolean isPost, int pageNo, String type) {
        ApiManager.getBusinessService().getNearFieldPageHot(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                pageNo, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NearFieldPageHotBean>() {
                    @Override
                    protected void onSuccees(NearFieldPageHotBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            mView.addHotData(bean);
                        } else if (bean.getCode().equals("77777")) {
                            //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
                            if (isPost) {
                                hotPageNo++;
                                getNearFieldPageHot(false, hotPageNo, type);
                            } else {
                                //ToastUtil.showToast(bean.getMsg());
                                mView.addHotData(bean);

                            }
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
