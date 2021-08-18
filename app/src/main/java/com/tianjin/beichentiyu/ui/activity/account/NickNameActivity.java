package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;

import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.MemBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NickNameActivity extends BaseActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,NickNameActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.et_nick_name)
    EditText mEtNickName;

    private LodingDialog lodingDialog;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_nick_name;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this,"修改昵称中...");
        mToolbar.setRightTv("保存",R.color.color_161616);
        mEtNickName.setText(AccountManager.getInstance().getMemBean().getNickName());
    }
    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> saveNickName());
    }

    /**
     * 保存昵称
     */
    private void saveNickName(){
        String name = mEtNickName.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastUtil.showToast("昵称不能为空!");
            return;
        }
        lodingDialog.show();
        MemBean memBean = AccountManager.getInstance().getMemBean();
        String tel = memBean.getTel();
        String nonstr = memBean.getNonstr();
        String nickName = name;
        ApiManager.getAccountService().finishMemNickName(tel,nonstr,nickName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        lodingDialog.dismiss();
                        if(baseRespBean.isSuccessful()){
                            AccountManager.getInstance().getMemBean().setNickName(nickName);
                            AccountManager.getInstance().saveMenBean();
                            finish();
                        }else{
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast("昵称修改失败，请检查网络!");
                    }
                });
    }
}
