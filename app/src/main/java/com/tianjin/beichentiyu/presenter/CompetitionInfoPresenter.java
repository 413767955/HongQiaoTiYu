package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ArtileMsgBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.CompetitionInfoContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/21 11:59
 * E-Mail Address：673236072@qq.com
 */
public class CompetitionInfoPresenter extends BasePresenterImpl<CompetitionInfoContract.View> implements CompetitionInfoContract.Presenter{
    @Override
    public void detachView() {

    }

    @Override
    public void loadArtileMsg(String aId) {
        LodingDialog lodingDialog = new LodingDialog(App.get().getCurActivity(),"请稍后...");
        lodingDialog.show();
        ApiManager.getBusinessService().getArtileMsg(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),aId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArtileMsgBean>() {
                    @Override
                    protected void onSuccees(ArtileMsgBean artileMsgBean) throws Exception {
                        lodingDialog.dismiss();
                        if (artileMsgBean.isSuccessful()){
                            mView.addData(artileMsgBean);
                        }else {
                            ToastUtil.showToast(artileMsgBean.getMsg());
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
