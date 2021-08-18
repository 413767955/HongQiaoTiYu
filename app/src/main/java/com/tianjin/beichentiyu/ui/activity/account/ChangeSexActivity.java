package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.widget.TransparentToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 修改性别
 */
public class ChangeSexActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,ChangeSexActivity.class));
    }

    @BindView(R.id.toolbar)
    TransparentToolbar mToolbar;


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_change_sex;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        super.bindView( );
        ButterKnife.bind(this);
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }
}
