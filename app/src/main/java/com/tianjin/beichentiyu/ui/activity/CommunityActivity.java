package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.activity.BaseActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.City;
import com.tianjin.beichentiyu.bean.DistrictListBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CommunityToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/19 14:15
 * E-Mail Address：673236072@qq.com
 * 设备报修
 */
public class CommunityActivity extends BaseActivity {
    public static void toActivity(Context context) {
        context.startActivity(new Intent(context, CommunityActivity.class));
    }

    @BindView(R.id.toolbar)
    CommunityToolbar mToolbar;

    @BindView(R.id.rv_community)
    RecyclerView mRvCommunity;
    @BindView(R.id.tv_city_name)
    TextView mTvCityName;

    private List<DistrictListBean.ListBean> mList = new ArrayList<>();
    private BaseAdapter mAdapter;
    private City mCity;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_community;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initCommunityRv();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setSearchOnClick(v -> {
            LocationActivity.toActivity(this);
        });
        mTvCityName.setOnClickListener(v -> {
            LocationActivity.toActivity(this);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDistrictList(AccountManager.getInstance().getCurrentCity());
    }

    public void initCommunityRv() {
        mAdapter = new BaseAdapter<DistrictListBean.ListBean>(R.layout.item_community2, mList) {
            @Override
            protected void convert(BaseViewHolder holder, DistrictListBean.ListBean item) {
                holder.setTvText(R.id.tv_name, item.getDistrictName());
                if (holder.getLayoutPosition() % 2 != 0) {
                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.color_ffffff));
                } else {
                    holder.itemView.setBackgroundColor(Color.parseColor("#f9f9ff"));
                }
            }
        };
        mAdapter.setItemClickListner(position -> {
            RegionalSiteActivity.toActivity(this,mList.get(position).getDistrictId());
        });
        mRvCommunity.setLayoutManager(new LinearLayoutManager(this));
        mRvCommunity.setAdapter(mAdapter);
    }


    /**
     * 获取县城
     * @param city
     */
    private void loadDistrictList(City city) {
        if(city == null || city == mCity){
            return;
        }
        mCity = city;
        mTvCityName.setText(mCity.getAreaName());
        LodingDialog lodingDialog = new LodingDialog(this, "获取中...");
        lodingDialog.show();
        ApiManager.getAccountService().getDistrictList(mCity.getAreaId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DistrictListBean>() {
                    @Override
                    protected void onSuccees(DistrictListBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            mList = bean.getList();
                            mAdapter.setData(mList);
                        } else {
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
}

