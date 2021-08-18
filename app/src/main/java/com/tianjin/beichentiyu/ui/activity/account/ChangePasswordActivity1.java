package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.ChangePasswordPresenter;
import com.tianjin.beichentiyu.presenter.contract.ChangePasswordContract;
import com.tianjin.beichentiyu.utils.AccountCommonUtil;
import com.tianjin.beichentiyu.utils.EditTextInputFilterUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/5 14:38
 * E-Mail Address：673236072@qq.com
 * 修改密码 - 旧密码修改
 */
public class ChangePasswordActivity1 extends BaseMvpActivity<ChangePasswordContract.Presenter> implements ChangePasswordContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,ChangePasswordActivity1.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.et_old_pwd)
    EditText mEtOldPwd;
    @BindView(R.id.et_new_pwd1)
    EditText mEtNewPwd1;
    @BindView(R.id.et_new_pwd2)
    EditText mEtNewPwd2;
    @BindView(R.id.tv_save)
    TextView mTvSave;
    @Override
    protected ChangePasswordContract.Presenter initPresenter() {
        return new ChangePasswordPresenter();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_change_password1;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        InputFilter[] inputFilters = {EditTextInputFilterUtil.getLengthFilter(), EditTextInputFilterUtil.getTextTypeFilter()};
        mEtOldPwd.setFilters(inputFilters);
        mEtNewPwd1.setFilters(inputFilters);
        mEtNewPwd2.setFilters(inputFilters);
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mTvSave.setOnClickListener(v -> updatePwd());
    }

    /**
     * 修改密码
     */
    private void updatePwd(){
        String oldPwd = mEtOldPwd.getText().toString();
        String newPwd1 = mEtNewPwd1.getText().toString();
        String newPwd2 = mEtNewPwd2.getText().toString();
        if(!AccountCommonUtil.checkUpdatePwd(oldPwd,newPwd1,newPwd2)){
            return;
        }
        mPresenter.updatePwd(AccountManager.getInstance().getAccount(), oldPwd,newPwd1);
    }

    @Override
    public void success() {
        ToastUtil.showToast("修改密码成功");
        finish();
    }

    @Override
    public void failure(String msg) {
        ToastUtil.showToast(msg);
    }
}
