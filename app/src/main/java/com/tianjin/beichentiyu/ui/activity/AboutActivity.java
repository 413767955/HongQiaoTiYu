package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wjy on 2019/12/21
 * E-Mail Address: 673236072@qq.com
 * des:关于我们
 **/
public class AboutActivity extends BaseActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,AboutActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_des)
    TextView mTvDes;
    @BindView(R.id.lin_net)
    LinearLayout mLinNet;
    @BindView(R.id.tv_net)
    TextView mTvNet;
    @BindView(R.id.lin_phone)
    LinearLayout mLinPhone;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.wv)
    WebView mWebView;
    @BindView(R.id.lin_protocol)
    LinearLayout mLinProtocol;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_about;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        WebSettings webSettings = mWebView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mLinNet.setOnClickListener(v -> {

        });
        mLinPhone.setOnClickListener(v -> {
            AccountCommonUtil.actionCall(this,"10086");
        });
        mLinProtocol.setOnClickListener(v ->{
            ProtocolActivity.toActivity(this);
        });
    }
    private LodingDialog lodingDialog;
    @Override
    protected void firstRequest() {
/*        lodingDialog = new LodingDialog(this, "获取数据中...");
        lodingDialog.show();
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getArtileMsg(tel, nonstr, "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArtileMsgBean>() {
                    @Override
                    protected void onSuccees(ArtileMsgBean artileMsgBean) throws Exception {
                        lodingDialog.dismiss();
                        if (artileMsgBean.isSuccessful()){
                            mWebView.loadUrl(artileMsgBean.getShowArtUrl());
                        }else {
                            ToastUtil.showToast(artileMsgBean.getMsg());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });*/
    }

}
