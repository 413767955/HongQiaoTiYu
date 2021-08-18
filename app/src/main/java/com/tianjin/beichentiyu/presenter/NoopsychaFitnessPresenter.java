package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.NoopsychaFitnessContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NoopsychaFitnessPresenter extends BasePresenterImpl<NoopsychaFitnessContract.View> implements NoopsychaFitnessContract.Presenter {

    private int getPageNo = 0;
    LodingDialog lodingDialog;
    @Override
    public void detachView() {

    }

    @Override
    public void nearFieldPageHot(int pageNo, String type) {
        getPageNo = pageNo;
        getNearFieldPageHot(true,getPageNo,type);
    }

    public void getNearFieldPageHot(boolean isPost,int pageNo, String type) {
        if (lodingDialog == null){
            lodingDialog = new LodingDialog(App.get().getCurActivity(),"加载中...");
            lodingDialog.show();
        }

        ApiManager.getBusinessService().getNearFieldPageHot(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                pageNo,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NearFieldPageHotBean>() {
                    @Override
                    protected void onSuccees(NearFieldPageHotBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            lodingDialog.dismiss();
                            mView.addData(bean);
                        }else if (bean.getCode().equals("77777")){
                            //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
                            if (isPost){
                                getPageNo++;
                                getNearFieldPageHot(false,getPageNo,type);
                            }else {
                                mView.addData(bean);
                                lodingDialog.dismiss();
                                ToastUtil.showToast(bean.getMsg());

                            }
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                            lodingDialog.dismiss();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }

}
