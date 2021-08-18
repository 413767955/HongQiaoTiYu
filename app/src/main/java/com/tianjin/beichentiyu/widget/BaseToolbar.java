package com.tianjin.beichentiyu.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseToolbar extends Toolbar {
    public BaseToolbar(Context context) {
        super(context);
    }

    public BaseToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageView getCollectionView(){
        return null;
    }

    private boolean isCollection;           //是否已收藏
    private String collectionId = "";       //收藏id
    private String collectionType = "";     //收藏类型  1:场馆；2：体侧中心；3：文章；4：赛事新闻
    private LodingDialog collectionLodingDialog;
    /**
     * 初始化收藏
     * @param isCollection
     * @param collectionId
     * @param type
     */
    public void initCollectionView(boolean isCollection,String collectionId,int type){
        this.isCollection = isCollection;
        this.collectionId = collectionId;
        switch (type){
            case Constant.Information.CDXQ://场地详情
                this.collectionType = "1";
                break;
            case Constant.Information.TCXQ://体测详情
                this.collectionType = "2";
                break;
            case Constant.Information.PTGG://平台公告
                this.collectionType = "3";
                break;
            case Constant.Information.SYXW://首页新闻
            case Constant.Information.XWZX://新闻资讯
            case Constant.Information.SSXQ://赛事详情
                this.collectionType = "4";
                break;
            case Constant.Information.ZNJS://智能健身
                this.collectionType = "5";
                break;
        }
        ImageView collectionView = getCollectionView();
        if(collectionView != null && !TextUtils.isEmpty(collectionId) && !TextUtils.isEmpty(collectionType)){
            updateCollectionStatic(collectionView,isCollection);
            collectionView.setVisibility(View.VISIBLE);
            collectionView.setOnClickListener(v -> {
                postCollection();
            });
        }
    }


    /**
     * 触发收藏
     */
    public void postCollection(){
        if(getCollectionView() == null || TextUtils.isEmpty(collectionId) || TextUtils.isEmpty(collectionType))
            return;

        if(isCollection) {//取消收藏
            collectionLodingDialog = new LodingDialog(getContext(),"取消收藏中...");
            collectionLodingDialog.show();
            ApiManager.getBusinessService().delMemberCollect(AccountManager.getInstance().getAccount(),
                    AccountManager.getInstance().getNonstr(),
                    collectionId,
                    collectionType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseRespBean>() {
                        @Override
                        protected void onSuccees(BaseRespBean bean) throws Exception {
                            if (bean.isSuccessful()) {
                                isCollection = false;
                                updateCollectionStatic(getCollectionView(), isCollection);
                            } else {
                                ToastUtil.showToast(bean.getMsg());
                            }
                            collectionLodingDialog.dismiss();
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            ToastUtil.showToast(R.string.request_failure);
                            collectionLodingDialog.dismiss();
                        }
                    });
        }else {//收藏
            collectionLodingDialog = new LodingDialog(getContext(),"收藏中...");
            collectionLodingDialog.show();
            ApiManager.getBusinessService().doMemberCollect(AccountManager.getInstance().getAccount(),
                    AccountManager.getInstance().getNonstr(),
                    collectionId,
                    collectionType)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseRespBean>() {
                        @Override
                        protected void onSuccees(BaseRespBean bean) throws Exception {
                            if (bean.isSuccessful()) {
                                isCollection = true;
                                updateCollectionStatic(getCollectionView(), isCollection);
                            } else {
                                ToastUtil.showToast(bean.getMsg());
                            }
                            collectionLodingDialog.dismiss();
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            ToastUtil.showToast(R.string.request_failure);
                            collectionLodingDialog.dismiss();
                        }
                    });
        }
    }


    /**
     * 更新收藏状态
     * @param collectionView
     * @param isCollection
     */
    public void updateCollectionStatic(ImageView collectionView,boolean isCollection){
        collectionView.setBackgroundResource(isCollection ? R.mipmap.icon_collection_on : R.mipmap.icon_collection_off);
    }
}
