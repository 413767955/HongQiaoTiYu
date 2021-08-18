package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.FieldEqueListBean;
import com.tianjin.beichentiyu.bean.FieldImgListBean;
import com.tianjin.beichentiyu.bean.FieldMsgBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.VenuesInfoContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VenuesInfoPresenter extends BasePresenterImpl<VenuesInfoContract.View> implements VenuesInfoContract.Presenter {

    @Override
    public void detachView() {

    }

    @Override
    public void getFieldMsg(String id) {
        ApiManager.getBusinessService().getFieldMsg(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldMsgBean>() {
                    @Override
                    protected void onSuccees(FieldMsgBean bean) throws Exception {
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
    public void getFieldImgList(String id) {
        ApiManager.getBusinessService().getFieldImgList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldImgListBean>() {
                    @Override
                    protected void onSuccees(FieldImgListBean bean) throws Exception {
                        if(bean.isSuccessful()){
                            mView.addImg(bean.getList());
                        }else{
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
    public void getFieldEqueList(String id, String type) {
        ApiManager.getBusinessService().getFieldEqueList(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),id,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldEqueListBean>() {
                    @Override
                    protected void onSuccees(FieldEqueListBean bean) throws Exception {

                        if (bean.isSuccessful()){
                            List<FieldEqueListBean.ListBean> list =  bean.getList();
                            if (list != null && list.size() > 0) {
                                // Map<类型, 列表>
                                Map<String, List<FieldEqueListBean.ListBean>> mapMap = new HashMap<>();
                                for (int i = 0; i < list.size(); i++) {
                                    FieldEqueListBean.ListBean listBean = list.get(i);
                                    List<FieldEqueListBean.ListBean> mList = mapMap.get(listBean.getBrand_name());
                                    if(mList == null){
                                        mList = new ArrayList<>();
                                        listBean.setShow(true);
                                    }else {
                                        listBean.setShow(false);
                                    }
                                    mList.add(listBean);
                                    mapMap.put(listBean.getBrand_name(),mList);
                                }
                                list.clear();
                                for (List<FieldEqueListBean.ListBean> mList : mapMap.values()){
                                    mList.get(0).setSize(mList.size());
                                    list.addAll(mList);
                                }

                            }
                            mView.addEquipment(bean);
                        }else{
                            ToastUtil.showToast(bean.getMsg());
                        }
                      /*  if (bean.isSuccessful()){
                            mView.addEquipment(bean);
                        }else{
                            ToastUtil.showToast(bean.getMsg());
                        }*/
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }
}
