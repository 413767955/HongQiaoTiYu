package com.heitong.frame.base.activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.heitong.frame.R;
import com.heitong.frame.base.adapter.ScreenAdapter;

/**
 * Created by 黑瞳 on 2019/7/23 20:45
 * E-Mail Address：673236072@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ImmersionBar mImmersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenAdapter.match(this,375);
        setContentView(getLayoutResID());
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.colorPrimary);
        mImmersionBar.statusBarDarkFont(true).init();
        onCreate();
        initData();
        bindView();
        bindEvent();
        firstRequest();
    }

    /**
     * 设置布局id
     * @return
     */
    protected abstract int getLayoutResID();
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

    /**
     * 设置 app 不随着系统字体的调整而变化
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config=new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }

}
