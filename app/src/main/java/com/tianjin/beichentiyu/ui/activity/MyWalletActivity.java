package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.widget.TransparentToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View{
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, MyWalletActivity.class));
    }
    @BindView(R.id.top_view)
    View mTopView;
    @BindView(R.id.toolbar)
    TransparentToolbar mToolbar;
    @BindView(R.id.btn_cash_withdrawal)
    TextView btn_cash_withdrawal;
    @BindView(R.id.rl_bill)
    RelativeLayout rl_bill;
    @BindView(R.id.btn_bankcard)
    RelativeLayout btn_bankcard;
    @BindView(R.id.tv_money)//钱包余额
    TextView mTvMoney;


    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_wallet;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        mImmersionBar.statusBarColor(android.R.color.transparent).init();
        mImmersionBar.setStatusBarView(this, mTopView);
        //setData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        if (!TextUtils.isEmpty(AccountManager.getInstance().getMemBean().getMoney())) {
            mTvMoney.setText(AccountManager.getInstance().getMemBean().getMoney());
        }else {
            mTvMoney.setText("0.0");
        }
    }
    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> {
            RechargeActivity.toActivity(this);
        },"充值");
        //提现
        btn_cash_withdrawal.setOnClickListener(v ->{
            CashWithdrawalActivity.toActivity(this);
        });
        //收支明细
        rl_bill.setOnClickListener(v ->{
            //ExpenditureDetailsActivity.toActivity(this);
            BillDetailsActivity.toActivity(this);
        });
        //银行卡管理
        btn_bankcard.setOnClickListener(v -> {
            MyBankCardActivity.toActivity(this);
        });

       /* back.setOnClickListener(this);
        btn_recharge.setOnClickListener(this);
        btn_cash_withdrawal.setOnClickListener(this);
        btn_bill.setOnClickListener(this);
        btn_bankcard.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
        btn_pay_settings.setOnClickListener(this);*/
    }
}
