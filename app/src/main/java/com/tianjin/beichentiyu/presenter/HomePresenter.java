package com.tianjin.beichentiyu.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.HomeContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/21 11:59
 * E-Mail Address：673236072@qq.com
 */
public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements HomeContract.Presenter{
    //private String type = "1";  //类型： 1 体育新闻，2 平台公告, 3体育赛事
    private String seeType = "1";   //值为“1” 按点击量从大到小排序
    @Override
    public void loadData(int pageNo,String type) {
        LogUtils.e(AccountManager.getInstance().getAccount()+"----"+AccountManager.getInstance().getNonstr());
        ApiManager.getBusinessService().getArticleList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                type,seeType,"",pageNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArticleListBean>() {
                    @Override
                    protected void onSuccees(ArticleListBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            if (type.equals("1")) {
                                mView.addData(bean);
                            }else if (type.equals("2")){
                                mView.noticeData(bean);
                            }
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
/*        ApiManager.getBusinessService().getArticleList1(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                type,seeType,"",pageNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArticleBase64Bean>() {
                    @Override
                    protected void onSuccees(ArticleBase64Bean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            if (type.equals("1")) {
                                mView.addData1(bean);
                            }else if (type.equals("2")){
                                mView.addData1(bean);
                            }
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });*/
    }
}
