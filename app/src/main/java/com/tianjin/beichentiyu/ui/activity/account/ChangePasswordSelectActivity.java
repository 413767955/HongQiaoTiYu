package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.widget.RelativeLayout;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/10 20:56
 * E-Mail Address：673236072@qq.com
 * 修改密码 - 选择修改方式
 */
public class ChangePasswordSelectActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,ChangePasswordSelectActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rel_way1)
    RelativeLayout mRelWay1;
    @BindView(R.id.rel_way2)
    RelativeLayout mRelWay2;
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_change_password_select;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mRelWay1.setOnClickListener(v -> ChangePasswordActivity1.toActivity(this));
        mRelWay2.setOnClickListener(v -> ChangePasswordActivity2.toActivity(this));
    }
}

