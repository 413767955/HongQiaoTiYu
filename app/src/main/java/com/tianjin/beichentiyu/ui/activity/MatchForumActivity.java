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
import com.tianjin.beichentiyu.ui.fragment.SpecialEventsFragment;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/9/23 20:34
 * E-Mail Address：673236072@qq.com
 * 赛事活动
 */
public class MatchForumActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,MatchForumActivity.class));
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
        return R.layout.activity_match_forum;
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
        //list.add(new Pair<>("赛事资讯", MainCompetitionFragment.newInstance()));
        list.add(new Pair<>("活动报名", SpecialEventsFragment.newInstance()));
        //list.add(new Pair<>("健身指导", FitnessGuidanceFragment.newInstance()));

        mTabVp.setAdapter(new TabFragmentPageAdapter(getSupportFragmentManager(),list));
        mTabVp.setOffscreenPageLimit(2);
        mTab.setupWithViewPager(mTabVp);
        mTab.setTabTextColors(getResources().getColor(R.color.color_777777),getResources().getColor(R.color.color_161616));

    }

}
