package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.MemberBankBean;
import com.tianjin.beichentiyu.dialog.TwoButtonDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.MyBankCardPresenter;
import com.tianjin.beichentiyu.presenter.contract.MyBankCardContract;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的银行卡
 */
public class MyBankCardActivity extends BaseMvpActivity<MyBankCardContract.Presenter> implements MyBankCardContract.View {
    public static int RESULT_CODE = 1;

    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, MyBankCardActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.btn_add_bank)
    RelativeLayout btnAddBank;
    @BindView(R.id.refresh_layout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private BaseAdapter mAdapter;
    private List<MemberBankBean.ListBean> mList;
    private MemberBankBean.ListBean bankInfo;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_bank_card;
    }

    @Override
    protected MyBankCardContract.Presenter initPresenter() {
        return new MyBankCardPresenter();
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);

        initRecyclerView();
        initRefreshLayout();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {
            finish();
        });
        btnAddBank.setOnClickListener(v -> {
            //添加银行卡
            AddBankCardActivity.toActivityForResult(this, AddBankCardActivity.ADD_BANK_CODE);
        });
    }

    private void initRecyclerView() {
        mAdapter = new BaseAdapter<MemberBankBean.ListBean>(R.layout.item_bank_card, mList) {
            @Override
            protected void convert(BaseViewHolder holder, MemberBankBean.ListBean item) {
                holder.setTvText(R.id.tv_name, item.getBankName());
                holder.setTvText(R.id.tv_card_number, item.getBankNo());
                holder.setTvText(R.id.tv_type, "");

                ImageView iv = holder.getView(R.id.iv_img);
                holder.getView(R.id.rl_del).setOnClickListener(v -> {

                    new TwoButtonDialog(MyBankCardActivity.this)
                            .setMsg("是否删除银行卡？")
                            .confirmClick("删除", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delMemberBank(mList.get(holder.getLayoutPosition()).getBankId() + "");
                                }
                            }).show();
                });
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setItemClickListner(new BaseAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //修改银行卡
                AddBankCardActivity.toActivityForResult(MyBankCardActivity.this, AddBankCardActivity.CHANGE_BANK_CODE, mList.get(position));
            }
        });
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    mPresenter.loadData();
                })
                .autoRefresh();
    }

    @Override
    public void addData(List<MemberBankBean.ListBean> list) {
        mList = list;
        mAdapter.setData(mList);
        if (mList.size() == 0) {
            refreshLayout.showEmpty();
        } else {
            refreshLayout.hideEmpty();
        }
        refreshLayout.finishRefresh(true);
    }

    private void delMemberBank(String bankId) {
        ApiManager.getWalletService().delMemberBank(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), bankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            ToastUtil.showToast(bean.getMsg());
                            refreshLayout.autoRefresh();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_CODE && resultCode == Activity.RESULT_OK) {
            refreshLayout.autoRefresh();
        }
    }
}
