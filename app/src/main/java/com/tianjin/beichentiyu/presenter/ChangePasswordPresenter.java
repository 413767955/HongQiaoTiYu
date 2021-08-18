package com.tianjin.beichentiyu.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.ChangePasswordContract;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordPresenter extends BasePresenterImpl<ChangePasswordContract.View> implements ChangePasswordContract.Presenter{
    /**
     * 旧密码修改
     * @param tel
     * @param oldPwd
     * @param newPwd
     */
    @Override
    public void updatePwd(String tel, String oldPwd, String newPwd) {
        String nonstr = AccountManager.getInstance().getNonstr();
        ApiManager.getAccountService().updatePwd(tel,nonstr, oldPwd,newPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            mView.success();
                        }else{
                            mView.failure(bean.getMsg());
                        }
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.failure("修改密码失败，请检查网络!");
                    }
                });
    }

    /**
     * 短信修改密码
     * @param tel
     * @param newPwd
     * @param code
     */
    @Override
    public void updateSmsPwd(String tel, String newPwd, String code) {
        //String nonstr = AccountManager.getInstance().getNonstr();
       // ApiManager.getAccountService().updateSmsPwd(tel,nonstr, code,newPwd)
        ApiManager.getAccountService().updateSmsPwd(tel, code,newPwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            mView.success();
                        }else{
                            LogUtils.e(bean.getMsg());
                            mView.failure(bean.getMsg());
                        }
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.failure("修改密码失败，请检查网络!");
                    }
                });
    }

    /**
     * 发送短信验证码
     * @param tel
     */
    @Override
    public void sendSmsCode(String tel) {
        ApiManager.getAccountService().sendPhone(tel, Constant.Sms.CHANGE_PASSWORD)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        if(!baseRespBean.isSuccessful()){
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast("发送失败,请检查网络!");
                    }
                });
    }

    @Override
    public void detachView() {

    }
}
