package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.MemberBankBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.BankPicker;
import com.tianjin.beichentiyu.utils.PickerUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 提现
 */
public class CashWithdrawalActivity extends BaseMvpActivity{
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, CashWithdrawalActivity.class));
    }
    @BindView(R.id.top_view)
    View mTopView;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.back)
     RelativeLayout back;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv_choiceBank)
    TextView mTvChoiceBank;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_total)//全部提现
    TextView mTvTotal;

    private List<MemberBankBean.ListBean> bankList = new ArrayList<>();
    private MemberBankBean bankBean = new MemberBankBean();
    private String myMoney = "0";//我的余额
    private String bankId;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_cash_withdrawal;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        ImmersionBar.setStatusBarView(this,mTopView);
        setData();
    }

    private void setData(){
        myMoney = AccountManager.getInstance().getMemBean().getMoney();
        mTvMoney.setText("可提现余额"+myMoney+"元，");
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {finish();});

        btnConfirm.setOnClickListener(v -> confirm());
        mTvChoiceBank.setOnClickListener(v ->
                PickerUtil.choiceBank(this,bankBean).setOnOptionPickListener(new BankPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, MemberBankBean.ListBean item) {
                        LogUtils.e(item.getBankId());
                        bankId = item.getBankId()+"";
                        String bankNo = "";
                        if (item.getBankNo().length() > 5){
                            bankNo = item.getBankNo().substring(item.getBankNo().length()-4);
                        }
                        String text = item.getBankName() + "[" + bankNo+"]";
                        mTvChoiceBank.setText(text);
                    }
                })
        );
        mTvTotal.setOnClickListener(v -> {
            etMoney.setText(myMoney);
        });
    }

    @Override
    protected void firstRequest() {
        getMemberBankList();
    }

    private void confirm(){
        if (!TextUtils.isEmpty(etMoney.getText().toString())){
            doWithoutMoney(etMoney.getText().toString(),bankId);
        }else {
            ToastUtil.showToast("输入提现金额");
        }
    }

    private void doWithoutMoney(String money,String bankId){
        LodingDialog lodingDialog = new LodingDialog(this,"提现中...");
        lodingDialog.show();
        ApiManager.getWalletService().doWithoutMoney(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),money,bankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if(bean.isSuccessful()) {
                            ToastUtil.showToast(bean.getMsg());
                        }else{
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }
    public void getMemberBankList() {
        ApiManager.getWalletService().getMemberBankList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberBankBean>() {
                    @Override
                    protected void onSuccees(MemberBankBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            if (bean.getList().size() > 0) {
                                bankBean = bean;
                                String bankNo = "";
                                if (bean.getList().get(0).getBankNo().length() > 5) {
                                    bankNo = bean.getList().get(0).getBankNo().substring(bean.getList().get(0).getBankNo().length() - 4);
                                }
                                String text = bean.getList().get(0).getBankName() + "[" + bankNo + "]";
                                mTvChoiceBank.setText(text);
                                mTvChoiceBank.setEnabled(true);
                            }else {
                                mTvChoiceBank.setText("未添加银行卡");
                                mTvChoiceBank.setEnabled(false);
                            }
                        }else{
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
