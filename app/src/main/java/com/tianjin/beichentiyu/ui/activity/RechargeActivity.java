package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.gyf.immersionbar.ImmersionBar;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.AliRechargeBean;
import com.tianjin.beichentiyu.dialog.BottomDialogFr;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.dialog.OneButtonDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.PayResult;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 充值
 */
public class RechargeActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, RechargeActivity.class));
    }

    @BindView(R.id.top_view)
    View mTopView;
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_payType)
    TextView mTvPayType;
    @BindView(R.id.btn_recharge)
    Button mBtnRecharge;
    @BindView(R.id.et_money)
    EditText mEtMoney;

    private int payType = 0;
    private int CODE_ALIPAY = 1;
    private String orderInfo;
    private static final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    //Result result = new Result((String) msg.obj);
//            Toast.makeText(RechargeActivity.this, payResult.getResult(),
//                    Toast.LENGTH_LONG).show();
//            LogUtils.e(payResult.getResult());
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(RechargeActivity.this, ("充值成功" + payResult), true);
                        AccountManager.getInstance().getMemBean().setMoney(money);
                        AccountManager.getInstance().saveMenBean();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(RechargeActivity.this, "充值失败" + payResult, false);
                    }
            }
        }
    };

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_recharge;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        ImmersionBar.setStatusBarView(this, mTopView);

    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {
            finish();
        });
        mTvPayType.setOnClickListener(view ->
                payType());

        mBtnRecharge.setOnClickListener(v -> {
            if (payType != 0) {
                doMemRecharge();
            } else {
                ToastUtil.showToast("请选择支付方式");
            }
        });
    }

    private void payType() {
        BottomDialogFr bottomDialogFr = new BottomDialogFr();
        bottomDialogFr.show(getSupportFragmentManager(), "DF");
        bottomDialogFr.aliPayClick(view -> {
            payType = CODE_ALIPAY;
            mTvPayType.setText("支付宝");
        });
    }

    private LodingDialog lodingDialog;
    String money;

    private void doMemRecharge() {

        money = mEtMoney.getText().toString();
        if (TextUtils.isEmpty(money)) {
            ToastUtil.showToast("请输入充值金额");
            return;
        }
        if (Float.valueOf(money) <= 0) {
            ToastUtil.showToast("充值金额必须大与0");
            return;
        }
        lodingDialog = new LodingDialog(this, "");
        lodingDialog.show();
        ApiManager.getWalletService().doMemRecharge(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), money)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AliRechargeBean>() {
                    @Override
                    protected void onSuccees(AliRechargeBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            orderInfo = bean.getStr();
                            payTask();

                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                    }
                });
    }

    private void payTask() {
        Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(RechargeActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void showAlert(Context ctx, String info, boolean isSuccess) {
        new OneButtonDialog(ctx)
                .setMsg(info)
                .confirmClick("确认", v -> {
                            if (isSuccess) {
                                finish();
                            }
                        }
                )
                .show();
    }
}
