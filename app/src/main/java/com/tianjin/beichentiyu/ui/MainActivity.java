package com.tianjin.beichentiyu.ui;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.MainContract;
import com.tianjin.beichentiyu.ui.activity.CommunityActivity;
import com.tianjin.beichentiyu.ui.fragment.MainHomeFragment;
import com.tianjin.beichentiyu.ui.fragment.MainMyFragment;

/**
 * Created by 黑瞳 on 2019/9/21 14:35
 * E-Mail Address：673236072@qq.com
 * 首页
 */
public class MainActivity extends BaseMvpActivity<MainContract.Presenter> implements MainContract.View {

    public static void toActivity(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }

    private MainHomeFragment homeFragment;
    private MainMyFragment myFragment;

    //首页
    private LinearLayout mLinHome;
    private ImageView mIvHome;
    private TextView mTvHome;

    //设备报修
    private LinearLayout mLinEquipment;
    private RelativeLayout mRelEquipment;

    private LinearLayout mLinMy;
    private ImageView mIvMy;
    private TextView mTvMy;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        mLinHome = findViewById(R.id.lin_home);
        mIvHome = findViewById(R.id.iv_home);
        mTvHome = findViewById(R.id.tv_home);
        mLinEquipment = findViewById(R.id.lin_equipment);
        mRelEquipment = findViewById(R.id.rel_equipment);
        mLinMy = findViewById(R.id.lin_my);
        mIvMy = findViewById(R.id.iv_my);
        mTvMy = findViewById(R.id.tv_my);
        selectedFragment(0);
    }

    @Override
    protected void bindEvent() {
        mLinHome.setOnClickListener(v -> {
            selectedFragment(0);
            ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(true).statusBarColor(R.color.colorPrimary).init();
        });
        mLinEquipment.setOnClickListener(v -> {
            CommunityActivity.toActivity(this);
            //setChoiceItem(1);
        });
        mRelEquipment.setOnClickListener(v -> {
            CommunityActivity.toActivity(this);
            //setChoiceItem(1);
        });
        mLinMy.setOnClickListener(v -> {
            selectedFragment(1);
            ImmersionBar.with(this).keyboardEnable(false).statusBarDarkFont(false).statusBarColor(android.R.color.transparent).init();

           // ImmersionBar.with(this).keyboardEnable(true).statusBarAlpha(0.0f).navigationBarAlpha(0.0f).init();
        });
    }

    /**
     * Fragment切换
     *
     * @param index
     */
    private void selectedFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new MainHomeFragment();
                    transaction.add(R.id.frame_layout, homeFragment);
                } else {
                    transaction.show(homeFragment);
                    homeFragment.onResume();
                }
                 break;
            case 1:
                if (myFragment == null) {
                    myFragment = new MainMyFragment();
                    transaction.add(R.id.frame_layout, myFragment);
                } else {
                    transaction.show(myFragment);
                    myFragment.onResume();
                }
                  break;
        }
        transaction.commit();
        setSelectedChoice(index);
    }
    /**
     * 隐藏片段
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
    }
    public void setSelectedChoice(int index){
        switch (index){
            case 0:
                mIvHome.setBackgroundResource(R.mipmap.icon_home_selected);
                mTvHome.setTextColor(getResources().getColor(R.color.color_menu_selected));
                mIvMy.setBackgroundResource(R.mipmap.icon_my);
                mTvMy.setTextColor(getResources().getColor(R.color.color_menu));
                break;
            case 1:
                mIvHome.setBackgroundResource(R.mipmap.icon_home);
                mTvHome.setTextColor(getResources().getColor(R.color.color_menu));
                mIvMy.setBackgroundResource(R.mipmap.icon_my_selected);
                mTvMy.setTextColor(getResources().getColor(R.color.color_menu_selected));
                break;
        }
    }

}
