package com.tianjin.beichentiyu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewDialog extends Dialog {
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    @BindView(R.id.web_view)
    WebView mWebView;
    private String privacyURL;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private View.OnClickListener confirmClickListener;
    private View.OnClickListener cancelClickListener;
    private String btnConfirmStr;

    public WebViewDialog(@NonNull Context context, String privacyURL) {
        super(context);
        this.privacyURL = privacyURL;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_dialog);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        WebSettings webSettings = mWebView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        initView();
    }

    private void initView() {
        LogUtils.e(ApiManager.getURL() + privacyURL);
        mWebView.loadUrl(ApiManager.getURL() + privacyURL);
        mBtnCancel.setOnClickListener(v -> {
            cancelClickListener.onClick(v);
            dismiss();});
        mBtnConfirm.setOnClickListener(v -> {
            confirmClickListener.onClick(v);
            dismiss();
        });
        if (!TextUtils.isEmpty(btnConfirmStr)) {
            mBtnConfirm.setText(btnConfirmStr);
        }
        //downTime();
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    mProgressBar.setVisibility(View.GONE);
                }
                else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }

            }
        });

    }

    public WebViewDialog setBtnConfirmStr(String btnConfirmStr) {
        this.btnConfirmStr = btnConfirmStr;
        return this;
    }
    public WebViewDialog confirmClick(View.OnClickListener listener){
        this.confirmClickListener = listener;
        return this;
    }
    public WebViewDialog cancelClick(View.OnClickListener listener){
        this.cancelClickListener = listener;
        return this;
    }
}
