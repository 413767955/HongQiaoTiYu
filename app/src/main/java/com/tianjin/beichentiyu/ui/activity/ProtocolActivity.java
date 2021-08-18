package com.tianjin.beichentiyu.ui.activity;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.widget.CustomToolbar;

/**
 * 隐私协议
 */
public class ProtocolActivity extends BaseActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,ProtocolActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.wv)
    WebView mWebView;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_protocol;
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
        mToolbar.setLeftOnClick(view -> finish());

    }

    @Override
    protected void firstRequest() {
        mWebView.loadUrl(ApiManager.getURL()+"appSixEdge/showPrivacyView");
    }
}
