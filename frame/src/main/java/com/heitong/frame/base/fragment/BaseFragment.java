package com.heitong.frame.base.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.ImmersionBar;
import com.heitong.frame.R;

/**
 * Created by 黑瞳 on 2019/7/23 21:24
 * E-Mail Address：673236072@qq.com
 */
public abstract class BaseFragment extends Fragment {
    protected View mView;
    protected Toolbar toolbar;
    protected View statusBarView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = createView(inflater,container);
        statusBarView = mView.findViewById(R.id.status_bar_view);
        toolbar = mView.findViewById(R.id.toolbar);
        fitsLayoutOverlap();
        onCreate();
        initData();
        bindView();
        bindEvent();
        firstRequest();
        return mView;
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap();
    }
    private void fitsLayoutOverlap() {
        if (statusBarView != null) {
            ImmersionBar.setStatusBarView(this, statusBarView);
        } else {
            ImmersionBar.setTitleBar(this, toolbar);
        }
    }
    /**
     * 加载布局
     */
    protected abstract View createView(LayoutInflater inflater, ViewGroup container);
    /**
     * 创建
     */
    protected void onCreate() {}

    /**
     * 数据初始化
     */
    protected void initData() {}

    /**
     * 控件绑定
     */
    protected void bindView() {}

    /**
     * 事件触发绑定
     */
    protected void bindEvent() {}

    /**
     * 首次逻辑操作
     */
    protected void firstRequest() {}
}
