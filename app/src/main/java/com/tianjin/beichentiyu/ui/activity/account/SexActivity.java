package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

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

public class SexActivity extends BaseActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context, SexActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    //男
    @BindView(R.id.rel_boy)
    RelativeLayout mRelBoy;
    @BindView(R.id.rb_boy)
    RadioButton mRbBoy;
    //女
    @BindView(R.id.rel_girl)
    RelativeLayout mRelGirl;
    @BindView(R.id.rb_girl)
    RadioButton mRbGirl;

    private LodingDialog lodingDialog;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_sex;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this,"性别设置中...");
        mToolbar.setRightTv("保存",R.color.color_161616);

        MemBean memBean = AccountManager.getInstance().getMemBean();
        if("1".equals(memBean.getSex())){
            mRbBoy.setChecked(true);
        }else  if("2".equals(memBean.getSex())){
            mRbGirl.setChecked(true);
        }
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> saveSex());
        mRelBoy.setOnClickListener(v -> {
            mRbBoy.setChecked(true);
            mRbGirl.setChecked(false);
        });
        mRbBoy.setOnClickListener(v -> {
            mRbBoy.setChecked(true);
            mRbGirl.setChecked(false);
        });
        mRelGirl.setOnClickListener(v -> {
            mRbBoy.setChecked(false);
            mRbGirl.setChecked(true);
        });
        mRbGirl.setOnClickListener(v -> {
            mRbBoy.setChecked(false);
            mRbGirl.setChecked(true);
        });
    }

    /**
     * 保存昵称
     */
    private void saveSex(){
        String sex = "";
        if(mRbBoy.isChecked()){
            sex = "1";
        }else if(mRbGirl.isChecked()){
            sex = "2";
        }else{
            ToastUtil.showToast("请选择性别!");
            return;
        }
        lodingDialog.show();
        String findSex = sex;
        MemBean memBean = AccountManager.getInstance().getMemBean();
        String tel = memBean.getTel();
        String nonstr = memBean.getNonstr();
        ApiManager.getAccountService().setFinishMemberSex(tel,nonstr,sex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        lodingDialog.dismiss();
                        if(baseRespBean.isSuccessful()){
                            AccountManager.getInstance().getMemBean().setSex(findSex);
                            AccountManager.getInstance().saveMenBean();
                            finish();
                        }else{
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast("性别设置失败，请检查网络!");
                    }
                });
    }
}
