package com.heitong.frame.base;

import android.app.Application;
import android.content.SharedPreferences;

import com.heitong.frame.base.adapter.ScreenAdapter;

/**
 * Created by 黑瞳 on 2019/7/23 21:32
 * E-Mail Address：673236072@qq.com
 */
public class BaseApplication extends Application {
    private static BaseApplication instance;
    private SharedPreferences configPreferences;
    public static BaseApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdapter.setup(this);
        instance = this;
        configPreferences = getSharedPreferences("CONFIG", 0);
    }
    public SharedPreferences getConfigPreferences() {
        return configPreferences;
    }
}
