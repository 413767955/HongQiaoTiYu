package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.ExpenditureDetailsBean;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  支出明细
 *  收支明细
 */
public class ExpenditureDetailsActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivity(Context context,ExpenditureDetailsBean.ListBean infoBean){
        Intent intent = new Intent(context,ExpenditureDetailsActivity.class);
        intent.putExtra("infoBean",infoBean);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.extractView)
    LinearLayout extractView;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_num)
    TextView tvBankNum;
    @BindView(R.id.tv_card_name)
    TextView tvCardName;
    @BindView(R.id.tv_state)
    TextView tvState;
    //ll_rechargeView,tv_recharge_time，tv_pay_id，tv_recharge_state
    //tv_recharge_type
    @BindView(R.id.ll_rechargeView)
    LinearLayout llRechargeView;
    @BindView(R.id.tv_recharge_time)
    TextView tvRechargeTime;
    @BindView(R.id.tv_recharge_type)
    TextView tvRechargType;
    @BindView(R.id.tv_pay_id)
    TextView tvPayId;
    @BindView(R.id.tv_recharge_state)
    TextView tvRechargeState;


    private BaseAdapter adapter;
    private List<String> list;
    private List<ExpenditureDetailsBean.ListBean> mList = new ArrayList<>();
    private List<ExpenditureDetailsBean.ListBean> typeList = new ArrayList<>();
    private ExpenditureDetailsBean.ListBean infoBean ;
    private String mTitles[] = {"提现", "充值"};
    private int type = 1;   // 初始化 1为提现，2为充值
    private int pageNo = 1;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_expenditure_details;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        super.bindView( );
        ButterKnife.bind(this);
        infoBean = (ExpenditureDetailsBean.ListBean) getIntent().getSerializableExtra("infoBean");
        setView();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        if (infoBean.getType().equals("1")){
            mToolbar.setMyTitle("提现明细");
        }else if (infoBean.getType().equals("2")){
            mToolbar.setMyTitle("充值明细");
        }
    }

    private void setView(){
        if (infoBean != null){
            if (infoBean.getType().equals("1")) {
                extractView.setVisibility(View.VISIBLE);
                llRechargeView.setVisibility(View.GONE);
                tvMoney.setText(infoBean.getMoney());
                tvType.setText("提现");
                tvTime.setText(infoBean.getGen_time());
                tvBankName.setText(infoBean.getBank_name());
                tvBankNum.setText(infoBean.getBank_no());
                tvCardName.setText(infoBean.getBank_user());
                if (infoBean.getState().equals("0")){
                    tvState.setText("申请");
                }else if (infoBean.getState().equals("1")){
                    tvState.setText("到账");
                }else if (infoBean.getState().equals("2")){
                    tvState.setText("不同意");
                }
            }else if (infoBean.getType().equals("2")) {
                extractView.setVisibility(View.GONE);
                llRechargeView.setVisibility(View.VISIBLE);
                tvRechargType.setText("充值");
                tvMoney.setText(infoBean.getMoney());
                tvRechargeTime.setText(infoBean.getGen_time());
                tvTime.setText(infoBean.getGen_time());
                tvPayId.setText(infoBean.getPay_id());
                if (infoBean.getState().equals("0")){
                    tvRechargeState.setText("申请");
                }else if (infoBean.getState().equals("1")){
                    tvRechargeState.setText("到账");
                }else if (infoBean.getState().equals("2")){
                    tvRechargeState.setText("不同意");
                }
            }
        }
    }

/*    private void initRecyclerView(){
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new String());
        }


        mRefreshLayout.setHeaderView(new HeaderView(this));
        HeaderView headerView = new HeaderView(this);

        mRefreshLayout.setHeaderView(headerView);
        mRefreshLayout.autoRefresh();
        //设置尾部(加载更多)
        mRefreshLayout.setFooterView(new FooterView(this));

        //设置刷新监听，触发刷新时回调
        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 刷新回调
                //refresh();
                mRefreshLayout.finishRefresh(true);
                //refreshLayout.showEmpty();
            }
        });

        //设置上拉加载更多的监听，触发加载时回调。
        //RefreshLayout默认没有启用上拉加载更多的功能，如果设置了OnLoadMoreListener，则自动启用。
//        refreshLayout.setOnLoadMoreListener(new RefreshLayout.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                // 加载回调
//                //loadmore();
//                //mallScoreRecords();
//
//            }
//        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RevenueMallAdapter(this,dataList);
        adapter = new BaseAdapter<ExpenditureDetailsBean.ListBean>(R.layout.item_expenditure_details, typeList) {
            @Override
            protected void convert(BaseViewHolder holder, ExpenditureDetailsBean.ListBean item) {


            }
        };
        mRecyclerView.setAdapter(adapter);
    }
    private void tab(){
        for (int i = 0; i < mTitles.length; i++) {
            mTab.addTab(mTab.newTab().setText(mTitles[i]));
        }
        mTab.setTabTextColors(getResources().getColor(R.color.color_777777),getResources().getColor(R.color.color_161616));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition() + 1;
                LogUtils.e(type);
                typeList.clear();
                setTypeList();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getMemberLogMoneyPage(int pageNo){
        ApiManager.getWalletService().getMemberLogMoneyPage(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ExpenditureDetailsBean>() {
                    @Override
                    protected void onSuccees(ExpenditureDetailsBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            mList.addAll(bean.getList());
                            setTypeList();
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }
    private void setTypeList(){
        if (type == 1){
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getType().equals("1")){//提现
                    typeList.add(mList.get(i));
                }
            }
        }else {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getType().equals("2")){//充值
                    typeList.add(mList.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }*/

}
