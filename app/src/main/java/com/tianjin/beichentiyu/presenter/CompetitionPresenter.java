package com.tianjin.beichentiyu.presenter;

import com.heitong.frame.base.presenter.BasePresenterImpl;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.CompetitionContract;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/21 11:59
 * E-Mail Address：673236072@qq.com
 */
public class CompetitionPresenter extends BasePresenterImpl<CompetitionContract.View> implements CompetitionContract.Presenter{
    private String type = "3";  //类型： 1 体育新闻，2 平台公告, 3体育赛事
    private String seeType = "1";   //值为“1” 按点击量从大到小排序

    @Override
    public void loadData(int pageNo) {
        ApiManager.getBusinessService().getArticleList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                type,seeType,"",pageNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArticleListBean>() {
                    @Override
                    protected void onSuccees(ArticleListBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            mView.addData(bean);
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        mView.finishRefresh();
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    @Override
    public void detachView() {

    }
}
