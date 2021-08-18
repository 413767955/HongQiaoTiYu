package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ActivityMsgBean;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.dialog.TwoButtonDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.ui.activity.my.SignUpHistoryActivity;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 活动详情
 * SpecialEventsInfoActivity
 * activity_special_events_info
 */
public class SpecialEventsInfoActivity extends BaseMvpActivity<BaseContract.Presenter> {
    private String aId;
    public static int ACTIVITY_CODE = 1;//活动
    public static int SIGN_UP_CODE = 2;//报名记录

    public static void toActivity(Context context, String aId, int code,String url) {
        Intent intent = new Intent(context, SpecialEventsInfoActivity.class);
        intent.putExtra("aId", aId);
        intent.putExtra("code", code);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.tv_title)
    TextView mTeTitle;
    @BindView(R.id.tv_see)
    TextView mSee;
    @BindView(R.id.iv_cover)
    ImageView mIvCover;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_address)
    TextView mTvAddress;//举办地点
    @BindView(R.id.tv_beginTime)
    TextView mTvBeginTime;//报名开始时间
    @BindView(R.id.tv_endTime)
    TextView mTvEndTime;//报名结束时间
    @BindView(R.id.btn_signup)
    Button mBtnSignup;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_num)
    TextView mTvNum;//查看次数
    @BindView(R.id.tv_tel)
    TextView mTvTel;//查看次数
    @BindView(R.id.webView)
    WebView mWebView;


    private int type_code;
    private String activityUrl;//活动网页地址
    private ActivityMsgBean bean = new ActivityMsgBean();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_special_events_info;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        super.bindView();
        ButterKnife.bind(this);
        aId = getIntent().getStringExtra("aId");
        type_code = getIntent().getIntExtra("code", -1);
        activityUrl = getIntent().getStringExtra("url");
        initToolbar();
        initSignup();
        initWebView();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mBtnSignup.setOnClickListener(v -> {
            new TwoButtonDialog(SpecialEventsInfoActivity.this)
                    .setMsg("确认报名？")
                    .confirmClick("确认", v1 -> {
                        //delMemberBank(mList.get(holder.getLayoutPosition()).getBankId() + "");
                        userEnrollActivity();
                    }).show();
        });

    }

    private void initToolbar() {
        mToolbar.setMyTitle("活动详情");
    }

    @Override
    protected void firstRequest() {
        getActivityMsg();
    }

    private void initWebView(){
        mWebView.loadUrl(activityUrl);
    }

    private void initSignup() {
        if (type_code == ACTIVITY_CODE) {
            mBtnSignup.setBackground(getDrawable(R.drawable.radius_3eb3ff_436eff_45));
        }else {
            mBtnSignup.setBackground(getDrawable(R.drawable.radius_solid_cccccc_45));
            mBtnSignup.setEnabled(false);
        }
    }

    public void addData(ActivityMsgBean bean) {
        this.bean = bean;
        mTeTitle.setText(bean.getSa().getTitle());
        mSee.setText(bean.getSa().getSeeNum() + "次阅读");
        mTvContent.setText(bean.getSa().getContent());
        if (!TextUtils.isEmpty(bean.getSa().getShowImg())) {
            GlideApp.with(SpecialEventsInfoActivity.this)
                    .load(bean.getSa().getShowImg())
                    .transition(new DrawableTransitionOptions().crossFade())
                    .into(mIvCover);
        }
        mTvAddress.setText(bean.getSa().getAddress());
        mTvBeginTime.setText(bean.getSa().getBeginTime());
        mTvEndTime.setText(bean.getSa().getEndTime());
        if (!TextUtils.isEmpty(bean.getSa().getPrice())) {
            if (Double.parseDouble(bean.getSa().getPrice()) > 0) {
                mTvPrice.setText(bean.getSa().getPrice());
            } else {
                mTvPrice.setText("免费");
            }
        }
        mTvNum.setText(bean.getSa().getNowMem() + "人");
        mTvTel.setText(bean.getSa().getTel());

    }

    //活动详情
    private void getActivityMsg() {
        ApiManager.getBusinessService().getActivityMsg(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), aId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ActivityMsgBean>() {
                    @Override
                    protected void onSuccees(ActivityMsgBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            addData(bean);
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

    //报名
    private void userEnrollActivity() {
        LodingDialog lodingDialog = new LodingDialog(this, "报名中...");
        lodingDialog.show();
        LogUtils.e(aId);
        ApiManager.getBusinessService().userEnrollActivity(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), aId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()){
                            ToastUtil.showToast("报名成功");
                            finish();
                            //跳转到报名历史页面
                            SignUpHistoryActivity.toActivity(SpecialEventsInfoActivity.this);
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }
}
