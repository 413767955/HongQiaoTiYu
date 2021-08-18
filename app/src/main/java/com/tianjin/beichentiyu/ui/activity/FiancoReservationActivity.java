package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.AppointmentDateBean;
import com.tianjin.beichentiyu.bean.DateListBean;
import com.tianjin.beichentiyu.bean.FieldAppointListBean;
import com.tianjin.beichentiyu.bean.PhysicalInfoBean;
import com.tianjin.beichentiyu.dialog.TwoButtonDialog;
import com.tianjin.beichentiyu.presenter.FiancoPresenter;
import com.tianjin.beichentiyu.presenter.contract.FiancoContract;
import com.tianjin.beichentiyu.utils.DateUtils;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 智能体侧预定
 */
public class FiancoReservationActivity extends BaseMvpActivity<FiancoContract.Presenter> implements FiancoContract.View{

    public static void toActivity(Context context, PhysicalInfoBean.SpBean data) {
        Intent intent = new Intent(context, FiancoReservationActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.dateRV)
    RecyclerView mDateRv;
    @BindView(R.id.reservationRV)
    RecyclerView mReservationRv;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_open_time)
    TextView mTvOpenTime;
    @BindView(R.id.tv_reserve)
    TextView mReserve;
    @BindView(R.id.tv_price)
    TextView mTvPrice;

    private List<DateListBean> dateList = new ArrayList<>();
    private List<FieldAppointListBean.ListBean> mTimeList = new ArrayList<>();
    private String date;//日期
    private int daySelectPosition = 0; //选中的日期

    private BaseAdapter mTimeAdapter;
    private int timeSelectPosition = -1; //选中的时间
    private String timeId;//选中的时间id
    private List<AppointmentDateBean.ListBean> reserveDateList = new ArrayList<>();

    private PhysicalInfoBean.SpBean spBean;
    @Override
    protected void initData() {
        this.spBean = (PhysicalInfoBean.SpBean) getIntent().getSerializableExtra("data");
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_stadium_reservation;
    }

    @Override
    protected FiancoContract.Presenter initPresenter() {
        return new FiancoPresenter();
    }

    @Override
    protected void bindView() {
        super.bindView();
        ButterKnife.bind(this);
        mTvTitle.setText(spBean.getPhyName());
        mTvOpenTime.setText(spBean.getOpenTime());
        mTvPrice.setText(spBean.getSubscribePrice()+"元");
        initDateRv();
        initReservationRv();

    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mReserve.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(timeId)){
                new TwoButtonDialog(this)
                        .setMsg("确认预约")
                        .confirmClick("确认", v1 ->  {
                            mPresenter.memDoLogPhyAppointment(spBean.getId(),date,timeId);
                        }).show();

            }else {
                ToastUtil.showToast("预约时间有误");
            }

        });
    }

    @Override
    protected void firstRequest() {
        date = DateUtils.getDateString();
        mPresenter.getSubscribeTime(spBean.getId(), date);
    }

    /**
     * 日期和星期
     */
    private void initDateRv() {
        for (int i = 0; i < DateUtils.getSevendate().size(); i++) {
            dateList.add(new DateListBean(DateUtils.getSevendate().get(i), DateUtils.get7week(date).get(i)));
            LogUtils.e(dateList.get(i).getDate());
        }

        BaseAdapter dateAdapter = new BaseAdapter<DateListBean>(R.layout.item_reservation_date_week, dateList) {
            @Override
            protected void convert(BaseViewHolder holder, DateListBean item) {
                holder.setTvText(R.id.tv_rdw_date, item.getDate());
                holder.setTvText(R.id.tv_rdw_week, item.getWeek());
                CardView cardView = holder.getView(R.id.cv_lay);
                if (holder.getLayoutPosition() == daySelectPosition) {
                    //cardView.setBackgroundResource(R.color.color_3EB3FF);
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.color_3EB3FF));
                    holder.setTvTextColor(R.id.tv_rdw_date, R.color.color_ffffff);
                    holder.setTvTextColor(R.id.tv_rdw_week, R.color.color_ffffff);
                } else {
                    //cardView.setBackgroundResource(R.color.color_ffffff);
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.color_ffffff));
                    holder.setTvTextColor(R.id.tv_rdw_date, R.color.color_595959);
                    holder.setTvTextColor(R.id.tv_rdw_week, R.color.color_595959);
                }
            }
        };
        dateAdapter.setItemClickListner(position -> {
            daySelectPosition = position;
            dateAdapter.notifyDataSetChanged();
            date = dateList.get(position).getDate();
            LogUtils.e(date);
            mPresenter.getSubscribeTime(spBean.getId(), dateList.get(position).getDate());
            timeSelectPosition = -1;//初始化点击的时间下标
            timeId = "";//初始化点击的时间id
            //mPresenter.getFieldAppointList(fieldBean.getId());
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mDateRv.setLayoutManager(layoutManager);
        mDateRv.setAdapter(dateAdapter);

    }

    /**
     * 预约时间段
     */
    private void initReservationRv() {
        mTimeAdapter = new BaseAdapter<FieldAppointListBean.ListBean>(R.layout.item_reservation_choose, mTimeList) {
            @Override
            protected void convert(BaseViewHolder holder, FieldAppointListBean.ListBean item) {
                holder.setTvText(R.id.tv_time, item.getTimeSlot());
                ImageView ivState = holder.getView(R.id.img_state);

                if(item.isSubscribe()){//已被预订
                    ivState.setBackgroundResource(R.mipmap.select_red);
                }else {
                    if (holder.getLayoutPosition() == timeSelectPosition) {
                        ivState.setBackgroundResource(R.mipmap.select_blue);
                    } else {
                        ivState.setBackgroundResource(R.mipmap.select_gray);
                    }
                }
            }
        };
        mTimeAdapter.setItemClickListner(position -> {
            if (!mTimeList.get(position).isSubscribe()) {//未被预订
                timeSelectPosition = position;
                mTimeAdapter.notifyDataSetChanged();
                timeId = mTimeList.get(position).getLogId();
                LogUtils.e(timeId);
            }
        });
        mReservationRv.setLayoutManager(new LinearLayoutManager(this));
        mReservationRv.setAdapter(mTimeAdapter);
    }

    @Override
    public void reserveDate(List<FieldAppointListBean.ListBean> list) {
        mTimeList = list;
        mTimeAdapter.setData(mTimeList);
    }

    @Override
    public void reserveSuccess(boolean isReserve) {
        if (isReserve) {
            finish();
        }
    }

}
