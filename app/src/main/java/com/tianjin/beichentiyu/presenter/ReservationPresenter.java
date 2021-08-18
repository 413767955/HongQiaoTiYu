package com.tianjin.beichentiyu.presenter;

import android.text.TextUtils;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.AppointmentDateBean;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.FieldAppointListBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.ReservationContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReservationPresenter extends BasePresenterImpl<ReservationContract.View> implements ReservationContract.Presenter {
    //基础预约列表
    private List<FieldAppointListBean.ListBean> falList;
    @Override
    public void getSubscribeTime(String fId, String date) {
        if(falList == null){
            getFieldAppointList(fId,date);
        }else {
            getAllMemLogAppointment(fId,date);
        }
    }
    /**
     * 获得场馆的预约时间段列表
     * @param fId
     */
    private void getFieldAppointList(String fId,String date) {
        ApiManager.getBusinessService().getFieldAppointList(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),fId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldAppointListBean>() {
                    @Override
                    protected void onSuccees(FieldAppointListBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            falList = bean.getList();
                            getAllMemLogAppointment(fId,date);
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

    /**
     * 获得指定场馆某日的预约记录
     * @param fId
     * @param date
     */
    public void getAllMemLogAppointment(String fId, String date) {
        if(falList == null){
            ToastUtil.showToast(R.string.request_failure);
            return;
        }
        ApiManager.getBusinessService().getAllMemLogAppointment(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), fId, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AppointmentDateBean>() {
                    @Override
                    protected void onSuccees(AppointmentDateBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            processingData(bean.getList());
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

    /**
     * 处理数据
     * 将已预约列表合并到基础列表
     * @param list
     */
    private void processingData(List<AppointmentDateBean.ListBean> list){
        for(FieldAppointListBean.ListBean bean :falList){
            bean.setSubscribe(false);
            for(AppointmentDateBean.ListBean aBean:list){
                if(TextUtils.equals(bean.getLogId(),aBean.getLog_id())){
                    bean.setSubscribe(true);
                }
            }
        }
        mView.reserveDate(falList);
    }

    /**
     * 场馆预约操作
     * @param fId
     * @param date      预约日期，格式 yyyy-mm-dd
     * @param aId       预约时间段表主键
     */
    @Override
    public void memDoLogAppointment(String fId, String date, String aId) {
        LodingDialog lodingDialog = new LodingDialog(App.get().getCurActivity(),"加载中...");
        lodingDialog.show();
        ApiManager.getBusinessService().memDoLogAppointment(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),fId,date,aId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()){
                            ToastUtil.showToast(bean.getMsg());
                            mView.reserveSuccess(true);
                        }else {
                            ToastUtil.showToast(bean.getMsg());
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
