package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.fragment.BaseFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.adapter.TabFragmentPageAdapter;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.ui.fragment.ImTaskFragment;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/10 19:52
 * E-Mail Address：673236072@qq.com
 * 巡检维修 - 我的任务
 */
public class ImMyTaskActivity extends BaseMvpActivity<BaseContract.Presenter>{
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,ImMyTaskActivity.class));
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
        return R.layout.activity_im_my_task;
    }
    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initToolbar();
        initViewPager();
    }

    private void initToolbar(){
        mToolbar.setLeftOnClick(v -> finish());
    }
    private void initViewPager(){
        List<Pair<String,BaseFragment>> list = new ArrayList<>();
        list.add(new Pair<>("全部",ImTaskFragment.newInstance(ImTaskFragment.ALL_TYPE)));
        list.add(new Pair<>("进行中",ImTaskFragment.newInstance(ImTaskFragment.UNDERWAY_TYPE)));
        list.add(new Pair<>("已完成",ImTaskFragment.newInstance(ImTaskFragment.COMPLETED_TYPE)));

        mTabVp.setAdapter(new TabFragmentPageAdapter(getSupportFragmentManager(),list));
        mTabVp.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(mTabVp);
    }
}
