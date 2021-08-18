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
import com.tianjin.beichentiyu.ui.fragment.AssociationFragment;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/9/23 20:34
 * E-Mail Address：673236072@qq.com
 * 体育组织
 */
public class SportsOrganizationActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,SportsOrganizationActivity.class));
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
        return R.layout.activity_sports_organization;
    }

    @Override
    protected void bindView() {
        super.bindView( );
        ButterKnife.bind(this);
        initViewPager();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());

    }

    private void initViewPager(){
        List<Pair<String,BaseFragment>> list = new ArrayList<>();
        list.add(new Pair<>("协会", AssociationFragment.newInstance(AssociationFragment.ASSOCIATION_TYPE)));
        list.add(new Pair<>("俱乐部",AssociationFragment.newInstance(AssociationFragment.CLUB_TYPE)));

        mTabVp.setAdapter(new TabFragmentPageAdapter(getSupportFragmentManager(),list));
        mTabVp.setOffscreenPageLimit(2);
        mTab.setupWithViewPager(mTabVp);
        mTab.setTabTextColors(getResources().getColor(R.color.color_777777),getResources().getColor(R.color.color_161616));
    }
}
