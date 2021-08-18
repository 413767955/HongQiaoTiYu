package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.google.android.material.tabs.TabLayout;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ExpenditureDetailsBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 账单明细列表
 */
public class BillDetailsActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,BillDetailsActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tab)
    TabLayout mTab;

    private BaseAdapter adapter;
    private String mTitles[] = {"全部","提现", "充值"};
    private int type = 0;   // 初始化 0为全部，1为提现，2为充值
    private int pageNo = 1;
    private List<ExpenditureDetailsBean.ListBean> mList = new ArrayList<>();
    private List<ExpenditureDetailsBean.ListBean> typeList = new ArrayList<>();
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_bill_details;
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        tab();
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {finish();});
    }

    private void getMemberLogMoneyPage(int pageNo){
        ApiManager.getWalletService().getMemberLogMoneyPage(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ExpenditureDetailsBean>() {
                    @Override
                    protected void onSuccees(ExpenditureDetailsBean bean) throws Exception {
                        mRefreshLayout.finishRefresh(true);
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
                        mRefreshLayout.finishRefresh(true);
                    }
                });
    }
    private void setTypeList(){
        if (type == 0){
            for (int i = 0; i < mList.size(); i++) {
                typeList.add(mList.get(i));
            }
        }else if (type == 1){
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getType().equals("1")){//提现
                    typeList.add(mList.get(i));
                }
            }
        }else if (type == 2){
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getType().equals("2")){//充值
                    typeList.add(mList.get(i));
                }
            }
        }
        adapter.setData(typeList);
    }
    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        mRefreshLayout.setHeaderView(new HeaderView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    loadData();
                })
                .autoRefresh();
    }
    private void initRecyclerView(){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //adapter = new RevenueMallAdapter(this,dataList);
        adapter = new BaseAdapter<ExpenditureDetailsBean.ListBean>(R.layout.item_expenditure_details, typeList) {
            @Override
            protected void convert(BaseViewHolder holder, ExpenditureDetailsBean.ListBean item) {
                //holder.setTvText(R.id.tv_type,)
                holder.setTvText(R.id.tv_time,item.getGen_time());
                if (item.getType().equals("1")){
                    holder.setTvText(R.id.tv_type,"提现");
                    holder.setTvText(R.id.tv_money,"-"+item.getMoney());
                }else if (item.getType().equals("2")){
                    holder.setTvText(R.id.tv_type,"充值");
                    holder.setTvText(R.id.tv_money,"+"+item.getMoney());
                }
            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.setItemClickListner(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ExpenditureDetailsActivity.toActivity(BillDetailsActivity.this,typeList.get(position));
            }
        });

    }
    private void tab(){
        for (int i = 0; i < mTitles.length; i++) {
            mTab.addTab(mTab.newTab().setText(mTitles[i]));
        }
        mTab.setTabTextColors(getResources().getColor(R.color.color_777777),getResources().getColor(R.color.color_161616));
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                type = tab.getPosition();
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

    private void loadData(){
        pageNo = 1;
        mList.clear();
        typeList.clear();
        getMemberLogMoneyPage(pageNo);
    }


}
