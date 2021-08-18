package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我要入驻 完善信息
 */
public class EntryActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,EntryActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_entry;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void bindEvent() {
        mToolbar.setRightTv("取消",R.color.color_398DEE);
        mToolbar.setRightOnClick(v ->{
            finish();
        });
    }
}
