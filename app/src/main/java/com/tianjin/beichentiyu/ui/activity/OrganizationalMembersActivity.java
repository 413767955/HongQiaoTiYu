package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;

public class OrganizationalMembersActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,OrganizationalMembersActivity.class));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_organizational_members;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {

    }
}
