package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.fragment.BaseFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.adapter.TabFragmentPageAdapter;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.ui.fragment.ImTaskFragment;
import com.tianjin.beichentiyu.ui.fragment.QuarterTaskFragment;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/9/23 20:34
 * E-Mail Address：673236072@qq.com
 * 巡检维修
 */
public class InspectionMaintenanceActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static int REQUEST_CODE = 401;
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,InspectionMaintenanceActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.tab_vp)
    ViewPager mTabVp;
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_inspection_maintenance;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initToolbar();
        initViewPager();
    }

    private void initToolbar(){
        //mToolbar.setRightTv("我的任务",R.color.color_398DEE);
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> {
            ImMyTaskActivity.toActivity(this);
        });
    }
    Pair<String, BaseFragment> temporaryTaskFragment;
    Pair<String, BaseFragment> quarterTaskFragment;
    private void initViewPager(){
        List<Pair<String,BaseFragment>> list = new ArrayList<>();
        temporaryTaskFragment = new Pair<>("临时任务",ImTaskFragment.newInstance(ImTaskFragment.TEMPORARY_TYPE));
        quarterTaskFragment = new Pair<>("季度任务",QuarterTaskFragment.newInstance(ImTaskFragment.QUARTER_TYPE));
        list.add(temporaryTaskFragment);
        list.add(quarterTaskFragment);

        mTabVp.setAdapter(new TabFragmentPageAdapter(getSupportFragmentManager(),list));
        mTabVp.setOffscreenPageLimit(2);
        mTab.setupWithViewPager(mTabVp);
        mTab.setTabTextColors(getResources().getColor(R.color.color_777777),getResources().getColor(R.color.color_161616));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_CODE){
                if (temporaryTaskFragment != null ){
                    ((ImTaskFragment)temporaryTaskFragment.second).callback();
                }
                if (quarterTaskFragment != null ){
                    ((QuarterTaskFragment)quarterTaskFragment.second).callback();
                }
            }
        }
    }
}
