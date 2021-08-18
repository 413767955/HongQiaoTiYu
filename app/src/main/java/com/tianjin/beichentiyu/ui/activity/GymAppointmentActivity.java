package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.activity.BaseActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.AppointmentDateBean;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.DateListBean;
import com.tianjin.beichentiyu.bean.FieldAppointListBean;
import com.tianjin.beichentiyu.bean.IntellectMsgBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.dialog.TwoButtonDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.DateUtils;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 智能健身房预约
 */
public class GymAppointmentActivity extends BaseActivity {
    private IntellectMsgBean.FieldBean fieldBean;

    public static void toActivity(Context context, IntellectMsgBean.FieldBean fieldBean) {
        Intent intent = new Intent(context, GymAppointmentActivity.class);
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
        return R.layout.activity_gym_appointment;
    }


    @Override
    protected void bindView() {
        super.bindView();
        ButterKnife.bind(this);
        fieldBean = (IntellectMsgBean.FieldBean) getIntent().getSerializableExtra("fieldBean");
        mTvTitle.setText(fieldBean.getFieldName());
        mTvOpenTime.setText(fieldBean.getOpenTime());
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
                            memDoLogAppointmentIntellect(fieldBean.getId(),date,timeId);
                        }).show();

            }else {
                ToastUtil.showToast("预约时间有误");
            }

        });
    }

    @Override
    protected void firstRequest() {
        date = DateUtils.getDateString();
        getSubscribeTime(fieldBean.getId(),date);
    }

    private void getSubscribeTime(String fId,String date){
        getIntellectAppointList(fId,date);
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
            LogUtils.e(dateList.get(position).getDate());
            getSubscribeTime(fieldBean.getId(),dateList.get(position).getDate());
            timeSelectPosition = -1;//初始化点击的时间下标
            timeId = "";//初始化点击的时间id
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

    private void getIntellectAppointList(String fId,String date){
        ApiManager.getBusinessService().getIntellectAppointList(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),fId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldAppointListBean>() {
                    @Override
                    protected void onSuccees(FieldAppointListBean bean) throws Exception {
                        if (bean.isSuccessful()){
                            mTimeList = bean.getList();
                            mTimeAdapter.setData(mTimeList);
                            //getAllMemLogAppointmentIntellect(fId,date);
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


    private void memDoLogAppointmentIntellect(String fId, String date, String aId){
        LodingDialog lodingDialog = new LodingDialog(App.get().getCurActivity(),"加载中...");
        lodingDialog.show();
        ApiManager.getBusinessService().memDoLogAppointmentIntellect(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),fId,date,aId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()){
                            ToastUtil.showToast(bean.getMsg());
                            finish();
                        }else {
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
