package com.tianjin.beichentiyu.manager;

import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.utils.ToastUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectManager {
    public static final void sendCollect(String rellId, String type, boolean isCollect, Callback collback) {
        String state = isCollect ? "0" : "1";
        if (isCollect) {
            ToastUtil.showToast("已收藏");
            return;
        } else {
        ApiManager.getBusinessService().doMemberCollect(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                rellId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            collback.collectState(isCollect);
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                            collback.error();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        collback.error();
                    }
                });
        }
    }

    public static void setCollectImg(ImageView img, boolean isCollect) {

        if (img == null) {
            return;
        }

        if (isCollect) {
            img.setBackgroundResource(R.mipmap.icon_collection_on);
            LogUtils.e(isCollect);
        } else {
            img.setBackgroundResource(R.mipmap.icon_collection_off);
        }
    }

    public interface Callback {

        void collectState(boolean isCollect);
        void error();
    }
}
