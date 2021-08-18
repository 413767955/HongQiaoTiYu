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
import com.tianjin.beichentiyu.bean.FieldMsgBean;
import com.tianjin.beichentiyu.dialog.TwoButtonDialog;
import com.tianjin.beichentiyu.presenter.ReservationPresenter;
import com.tianjin.beichentiyu.presenter.contract.ReservationContract;
import com.tianjin.beichentiyu.utils.DateUtils;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 体育场馆预定
 */
public class StadiumReservationActivity extends BaseMvpActivity<ReservationContract.Presenter> implements ReservationContract.View {
    private int fId = -1;//主键id
    private FieldMsgBean.FieldBean fieldBean;

    public static void toActivity(Context context, FieldMsgBean.FieldBean fieldBean) {
        Intent intent = new Intent(context, StadiumReservationActivity.class);
        intent.putExtra("fieldBean", fieldBean);
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

    //
    private List<DateListBean> dateList = new ArrayList<>();
    private List<FieldAppointListBean.ListBean> mTimeList = new ArrayList<>();
    private String date;//日期
    private int daySelectPosition = 0; //选中的日期

    private BaseAdapter mTimeAdapter;
    private int timeSelectPosition = -1; //选中的时间
    private String timeId;//选中的时间id
    private List<AppointmentDateBean.ListBean> reserveDateList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_fianco_reservation;
    }

    @Override
    protected ReservationContract.Presenter initPresenter() {
        return new ReservationPresenter();
    }

    @Override
    protected void bindView() {
        super.bindView();
        ButterKnife.bind(this);
        //fId = getIntent().getIntExtra("id",-1);
        fieldBean = (FieldMsgBean.FieldBean) getIntent().getSerializableExtra("fieldBean");
        mTvTitle.setText(fieldBean.getFieldName());
        mTvOpenTime.setText(fieldBean.getOpenTime());
        initDateRv();
        initReservationRv();

    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mReserve.setOnClickListener(v -> {
            //LogUtils.e(date + "----------" + timeId);
            if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(timeId)){
                new TwoButtonDialog(this)
                        .setMsg("确认预约")
                        .confirmClick("确认", v1 ->  {
                            //memDoLogAppointmentIntellect(fieldBean.getId(),date,timeId);
                            mPresenter.memDoLogAppointment(fieldBean.getId(),date,timeId);
                        }).show();

            }else {
                ToastUtil.showToast("预约时间有误");
            }
        });
    }

    @Override
    protected void firstRequest() {
        date = DateUtils.getDateString();
        LogUtils.e(fieldBean.getId());
        mPresenter.getSubscribeTime(fieldBean.getId(), date);
    }

    /**
     * 日期和星期
     */
    private void initDateRv() {
        for (int i = 0; i < DateUtils.getSevendate().size(); i++) {
            dateList.add(new DateListBean(DateUtils.getSevendate().get(i), DateUtils.get7week(date).get(i)));
        }

        BaseAdapter dateAdapter = new BaseAdapter<DateListBean>(R.layout.item_reservation_date_week, dateList) {
            @Override
            protected void convert(BaseViewHolder holder, DateListBean item) {

                holder.setTvText(R.id.tv_rdw_date, item.getDate().substring(5));
                holder.setTvText(R.id.tv_rdw_week, item.getWeek());
                CardView cardView = holder.getView(R.id.cv_lay);
                if (holder.getLayoutPosition() == daySelectPosition) {
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.color_3EB3FF));
                    holder.setTvTextColor(R.id.tv_rdw_date, R.color.color_ffffff);
                    holder.setTvTextColor(R.id.tv_rdw_week, R.color.color_ffffff);
                } else {
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
            mPresenter.getSubscribeTime(fieldBean.getId(), dateList.get(position).getDate());
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
