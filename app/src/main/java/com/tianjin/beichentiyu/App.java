package com.tianjin.beichentiyu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.heitong.frame.base.BaseApplication;
import com.tencent.bugly.crashreport.CrashReport;
import com.tianjin.beichentiyu.manager.LocationManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 黑瞳 on 2019/9/21 11:56
 * E-Mail Address：673236072@qq.com
 */
public class App extends BaseApplication {
    private static App app;
    private List<Activity> activityList = new LinkedList<>();
    private Activity curActivity;

    public static App get() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        LocationManager.getInstance().startLocation();
        initLifecycle();
        CrashReport.initCrashReport(getApplicationContext());
    }


    private void initLifecycle() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i("justh", "onActivityCreated");
                activityList.add(activity);
                curActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.w("justh", "onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.e("justh", "onActivityResumed");
                curActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i("justh", "onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i("justh", "onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i("justh", "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activityList.remove(activity);
            }
        });
    }

    public Activity getCurActivity() {
        return curActivity;
    }

    /**
     * 关闭处传入外的所有页面
     *
     * @param activity
     */
    public void finishAllActivity(Activity activity) {
        for (Activity act : activityList) {
            if (act != activity) {
                act.finish();
            }
        }
    }
}
