package com.tianjin.beichentiyu.ui.activity.my;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.AppointmentBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.DateUtils;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 个人中心  场馆预约
 */
public class VenueBookingActivity extends BaseActivity{
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,VenueBookingActivity.class));
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_date)
    RecyclerView mRvDate;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.tv_nodata_view)
    TextView mTvNodataView;

    private static final int DAY_NUM = 6;
    private BaseAdapter mDateAdapter;
    private BaseAdapter mDataAdapter;
    private List<Pair<String,String>> mDateList;
    private List<AppointmentBean.ListBean> mDataList;
    private int selectedDate = 0;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_venue_booking;
    }

    @Override
    protected void firstRequest() {
        loadData(mDateList.get(0).second);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initDateRv();
        initDataRv();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }

    /**
     * 初始化日期菜单
     */
    private void initDateRv(){
        mDateList = DateUtils.getDates(DAY_NUM);
        mDateAdapter = new BaseAdapter<Pair<String,String>>(R.layout.adapter_menu_date,mDateList) {
            @Override
            protected void convert(BaseViewHolder holder, Pair<String,String> item) {
                TextView tvWeek = holder.getView(R.id.tv_week);
                TextView tvDate = holder.getView(R.id.tv_date);
                View viewLine = holder.getView(R.id.view_line);
                tvWeek.setText(item.first);
                tvDate.setText(item.second.substring(5));
                if(selectedDate == holder.getLayoutPosition()){
                    tvWeek.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    tvWeek.setTextColor(getResources().getColor(R.color.color_161616));
                    tvDate.setTextColor(getResources().getColor(R.color.color_398DEE));
                    viewLine.setVisibility(View.VISIBLE);
                }else{
                    tvWeek.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    tvWeek.setTextColor(getResources().getColor(R.color.color_777777));
                    tvDate.setTextColor(getResources().getColor(R.color.color_777777));
                    viewLine.setVisibility(View.INVISIBLE);
                }
            }
        };
        mDateAdapter.setItemClickListner(position -> {
            if(selectedDate == position){
                return;
            }
            selectedDate = position;
            mDateAdapter.notifyDataSetChanged();
            Pair<String,String> item = mDateList.get(position);
            mTvNodataView.setVisibility(View.GONE);
            loadData(item.second);
        });
        mRvDate.setLayoutManager(new GridLayoutManager(this,DAY_NUM));
        mRvDate.setAdapter(mDateAdapter);
    }

    /**
     * 初始化列表
     */
    private void initDataRv(){
        mDataAdapter = new BaseAdapter<AppointmentBean.ListBean>(R.layout.adapter_venue_booking,mDataList) {
            @Override
            protected void convert(BaseViewHolder holder, AppointmentBean.ListBean item) {
                ImageView ivIcon = holder.getView(R.id.iv_icon);
                TextView tvTitle = holder.getView(R.id.tv_title);
                TextView tvTime = holder.getView(R.id.tv_time);
                tvTitle.setText(item.getField_name());
                tvTime.setText(item.getTime_slot());
                GlideApp.with(ivIcon.getContext()).load(item.getBase_img()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into(ivIcon);
            }
        };
        mRvData.setLayoutManager(new LinearLayoutManager(this));
        mRvData.setAdapter(mDataAdapter);
    }

    /**
     * 加载数据
     * @param date
     */
    private void loadData(String date){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getMemLogAppointmentList(tel, nonstr, date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AppointmentBean>() {
                    @Override
                    protected void onSuccees(AppointmentBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            if (bean.getList().size() > 0){
                                mTvNodataView.setVisibility(View.GONE);
                            }else {
                                mTvNodataView.setVisibility(View.VISIBLE);
                            }
                            mDataList = bean.getList();
                            mDataAdapter.setData(mDataList);
                        }else{
                            mDataList = null;
                            mDataAdapter.setData(null);
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        mDataList = null;
                        mDataAdapter.setData(null);
                    }
                });
    }

}
