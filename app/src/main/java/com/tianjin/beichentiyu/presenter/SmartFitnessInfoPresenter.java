package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.IntellectEqueListBean;
import com.tianjin.beichentiyu.bean.IntellectImgListBean;
import com.tianjin.beichentiyu.bean.IntellectMsgBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.SmartFitnessInfoContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SmartFitnessInfoPresenter extends BasePresenterImpl<SmartFitnessInfoContract.View> implements SmartFitnessInfoContract.Presenter {
    @Override
    public void getFieldMsg(String id) {
        ApiManager.getBusinessService().getIntellectMsg(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IntellectMsgBean>() {
                    @Override
                    protected void onSuccees(IntellectMsgBean bean) throws Exception {
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
        ApiManager.getBusinessService().getIntellectImgList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IntellectImgListBean>() {
                    @Override
                    protected void onSuccees(IntellectImgListBean bean) throws Exception {
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
    public void getFieldEqueList(String id) {
        ApiManager.getBusinessService().getIntellectEqueList(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IntellectEqueListBean>() {
                    @Override
                    protected void onSuccees(IntellectEqueListBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            List<IntellectEqueListBean.ListBean> list =  bean.getList();
                            if (list != null && list.size() > 0) {
                                // Map<类型, 列表>
                                Map<String, List<IntellectEqueListBean.ListBean>> mapMap = new HashMap<>();
                                for (int i = 0; i < list.size(); i++) {
                                    IntellectEqueListBean.ListBean listBean = list.get(i);
                                    List<IntellectEqueListBean.ListBean> mList = mapMap.get(listBean.getBrand_name());
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
                                for (List<IntellectEqueListBean.ListBean> mList : mapMap.values()){
                                    mList.get(0).setSize(mList.size());
                                    list.addAll(mList);
                                }

                            }
                            mView.addEquipment(bean);
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
}
