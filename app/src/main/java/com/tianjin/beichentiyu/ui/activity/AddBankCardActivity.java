package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.MemberBankBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 添加银行卡
 * 修改银行卡
 */
public class AddBankCardActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static int ADD_BANK_CODE = 1;
    public static int CHANGE_BANK_CODE = 2;
    private int typeCode = -1;
    private MemberBankBean.ListBean bankInfo = new MemberBankBean.ListBean();
    public static void toActivityForResult(Context context, int typeCode) {
        Intent intent = new Intent(context,AddBankCardActivity.class);
        intent.putExtra("type",typeCode);
        ((Activity) context).startActivityForResult(intent, MyBankCardActivity.RESULT_CODE);
    }
    public static void toActivityForResult(Context context,int typeCode, MemberBankBean.ListBean bankInfo) {
        Intent intent = new Intent(context,AddBankCardActivity.class);
        intent.putExtra("type",typeCode);
        intent.putExtra("bankInfo",bankInfo);
        ((Activity) context).startActivityForResult(intent, MyBankCardActivity.RESULT_CODE);
    }

    //    @BindView(R.id.top_view)
//    View mTopView;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rl_chooseBank)
    RelativeLayout mRlChooseBank;
    @BindView(R.id.et_bankName)
    EditText mEtBankName;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.et_bankNum)
    EditText mEtBankNum;
    @BindView(R.id.et_cardName)
    EditText mEtCardName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        //ImmersionBar.setStatusBarView(this, mTopView);
        typeCode = getIntent().getIntExtra("type", -1);
        bankInfo = (MemberBankBean.ListBean) getIntent().getSerializableExtra("bankInfo");
        if (typeCode == 1) {
            mToolbar.setMyTitle("添加银行卡");
        } else if (typeCode == 2) {
            mToolbar.setMyTitle("修改银行卡");
            if (bankInfo != null) {
                mEtBankName.setText(bankInfo.getBankName());
                mEtBankNum.setText(bankInfo.getBankNo());
                mEtCardName.setText(bankInfo.getBankUser());
                mEtPhone.setText(bankInfo.getBankTel());
            }

        }
        mBtnSubmit.setOnClickListener(v -> submit());

    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {
            finish();
        });
        mTvProtocol.setOnClickListener(v -> ProtocolActivity.toActivity(this));

    }

//    String bankName = mEtBankName.getText().toString();
//    String bankNum = mEtBankNum.getText().toString();
//    String cardName = mEtCardName.getText().toString();
//    String phone = mEtPhone.getText().toString();
    private String bankName;
    private String bankNum;
    private String cardName ;
    private String phone ;

    private void submit() {
        bankName = mEtBankName.getText().toString();
        bankNum = mEtBankNum.getText().toString();
        cardName = mEtCardName.getText().toString();
        phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(bankName)) {
            ToastUtil.showToast("请填写银行名称");
            return;
        }
        if (TextUtils.isEmpty(bankNum)) {
            ToastUtil.showToast("请填写银行卡号");
            return;
        }
        if (TextUtils.isEmpty(cardName)) {
            ToastUtil.showToast("请填写持卡人姓名");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showToast("请填写预留手机号");
            return;
        }
        if (!mCheckbox.isChecked()) {
            ToastUtil.showToast("请确认用户服务协议");
            return;
        }

        if (typeCode == 1){
            addMemberBank();
        }else if (typeCode == 2){
            updateMemberBank();
        }

    }

    /**
     * 绑定银行卡
     */
    private void addMemberBank(){
        ApiManager.getWalletService().addMemberBank(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                bankName, bankNum, phone, cardName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            ToastUtil.showToast(bean.getMsg());
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    /**
     * 修改银行卡
     */
    private void updateMemberBank(){
        ApiManager.getWalletService().updateMemberBank(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
               bankInfo.getBankId()+"", bankName, bankNum, phone, cardName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            ToastUtil.showToast(bean.getMsg());
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

}
