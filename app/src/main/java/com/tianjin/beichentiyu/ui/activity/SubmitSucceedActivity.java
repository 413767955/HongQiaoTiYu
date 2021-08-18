package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/3 16:55
 * E-Mail Address：673236072@qq.com
 * 提交成功
 */
public class SubmitSucceedActivity  extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,SubmitSucceedActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_back)
    TextView tvBack;

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_submit_succeed;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        mToolbar.setLeftOnClick(v -> finish());
    }

    @Override
    protected void bindEvent() {
        tvBack.setOnClickListener(v -> finish());
    }
}
