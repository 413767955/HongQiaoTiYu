package com.tianjin.beichentiyu.ui.fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.GlideApp;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.heitong.frame.base.fragment.BaseFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.MemBean;
import com.tianjin.beichentiyu.bean.MemberDoOperationBean;
import com.tianjin.beichentiyu.bean.MemberMsgBean;
import com.tianjin.beichentiyu.bean.MyMenuTitleBean;
import com.tianjin.beichentiyu.dialog.LoggedOutDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.ui.activity.AboutActivity;
import com.tianjin.beichentiyu.ui.activity.MyCollectionActivity;
import com.tianjin.beichentiyu.ui.activity.MyWalletActivity;
import com.tianjin.beichentiyu.ui.activity.account.ChangePasswordSelectActivity;
import com.tianjin.beichentiyu.ui.activity.account.UserInfoCompleteActivity;
import com.tianjin.beichentiyu.ui.activity.my.BodySideAppointmentActivity;
import com.tianjin.beichentiyu.ui.activity.my.IntelligentVenuesActivity;
import com.tianjin.beichentiyu.ui.activity.my.MaintenanceRecordActivity;
import com.tianjin.beichentiyu.ui.activity.my.RepairRecordActivity;
import com.tianjin.beichentiyu.ui.activity.my.SignUpHistoryActivity;
import com.tianjin.beichentiyu.ui.activity.my.VenueBookingActivity;
import com.tianjin.beichentiyu.widget.MyCustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/21 14:35
 * E-Mail Address：673236072@qq.com
 * 我的
 */
public class MainMyFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    MyCustomToolbar mToolbar;
    //头像
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    //昵称
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btn_account_data)
    LinearLayout btn_account_data;
    @BindView(R.id.lin_my_collection)
    LinearLayout mLinMyCollection;
    @BindView(R.id.lin_venue_booking)
    LinearLayout mLinVenueBooking;
    @BindView(R.id.lin_body_side_appointment)
    LinearLayout mLinBodySideAppointment;

    @BindView(R.id.tv_collection)
    TextView mTvCollection;
    @BindView(R.id.tv_booking)
    TextView mTvBooking;
    @BindView(R.id.tv_appointment)
    TextView mTvAppointment;

    private BaseAdapter adapter;

    private List<MyMenuTitleBean> listBean;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this, mView);
        initRecyclerView();

    }

    @Override
    protected void bindEvent() {
        mToolbar.setRightOnClick(v -> new LoggedOutDialog(getContext()).show());
        btn_account_data.setOnClickListener(v -> {
            UserInfoCompleteActivity.toChangeInfoActivity(getContext());
        });
        mLinMyCollection.setOnClickListener(v -> IntelligentVenuesActivity.toActivity(getContext()));
        mLinVenueBooking.setOnClickListener(v -> VenueBookingActivity.toActivity(getContext()));
        mLinBodySideAppointment.setOnClickListener(v -> BodySideAppointmentActivity.toActivity(getContext()));
    }


    private void initRecyclerView() {
        listBean = new ArrayList<>();
        //listBean.add(new MyMenuTitleBean(R.mipmap.icon_wallet, "我的钱包", AccountManager.getInstance().getMemBean().getMoney()));
        listBean.add(new MyMenuTitleBean(R.mipmap.icon_collection, "我的收藏",""));
        listBean.add(new MyMenuTitleBean(R.mipmap.icon_home_tag, "报名历史", ""));
        listBean.add(new MyMenuTitleBean(R.mipmap.screen_icon, "报修记录", ""));
        listBean.add(new MyMenuTitleBean(R.mipmap.icon_home_tag, "维修记录", ""));
        listBean.add(new MyMenuTitleBean(R.mipmap.icon_pwd, "修改密码", ""));
        listBean.add(new MyMenuTitleBean(R.mipmap.icon_home_tag, "个人资料", ""));
        listBean.add(new MyMenuTitleBean(R.mipmap.icon_about, "关于我们", ""));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BaseAdapter<MyMenuTitleBean>(R.layout.item_my_menu, listBean) {
            @Override
            protected void convert(BaseViewHolder holder, MyMenuTitleBean item) {
                holder.setBackgroundResource(R.id.image, item.getIcon());
                holder.setTvText(R.id.tv_title, item.getTitle());
                //holder.setTvText(R.id.tv_num, item.getNum());
            }
        };
        adapter.setItemClickListner(position -> {
            onItemClick(position);
        });
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
        loadMemberMsg();
        loadMemberDoOperation();
    }

    public void updateData() {
        MemBean memBean = AccountManager.getInstance().getMemBean();
        //设置头像
        if (!TextUtils.isEmpty(memBean.getHeadImg())) {
            //GlideApp.with(getContext()).load(memBean.getHeadImg()).transition(new DrawableTransitionOptions().crossFade()).into(mProfileImage);
            GlideApp.with(getContext()).load(memBean.getHeadImg()).into(mProfileImage);
        }
        //设置昵称
        mTvNickName.setText(memBean.getNickName());
        //设置余额
        listBean.get(0).setNum(memBean.getMoney());
        adapter.notifyDataSetChanged();
        MemberDoOperationBean memberDoOperationBean = AccountManager.getInstance().getMemberDoOperationBean();
        if(memberDoOperationBean != null){
            mTvCollection.setText(memberDoOperationBean.getCollectNum());
            mTvBooking.setText(memberDoOperationBean.getFieldAppointmentNum());
            mTvAppointment.setText(memberDoOperationBean.getPhyAppointmentNum());
        }else{
            mTvCollection.setText("0");
            mTvBooking.setText("0");
            mTvAppointment.setText("0");
        }
    }

    /**
     * 菜单监听
     *
     * @param position
     */
    public void onItemClick(int position) {
        switch (position) {
//            case 0:
//                MyWalletActivity.toActivity(getContext());
//                break;
            case 0:
                MyCollectionActivity.toActivity(getContext());
                break;
            case 1:
                SignUpHistoryActivity.toActivity(getContext());
                break;
            case 2:
                RepairRecordActivity.toActivity(getContext());
                break;
            case 3:
                MaintenanceRecordActivity.toActivity(getContext());
                //ChangePasswordSelectActivity.toActivity(getContext());
                break;
            case 4:
                ChangePasswordSelectActivity.toActivity(getContext());
                break;
            case 5:
                UserInfoCompleteActivity.toChangeInfoActivity(getContext());
                break;
            case 6:
                AboutActivity.toActivity(getContext());
                break;
        }
    }

    public void loadMemberMsg() {
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getAccountService().getMemberMsg(tel,nonstr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberMsgBean>() {
                    @Override
                    protected void onSuccees(MemberMsgBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            AccountManager.getInstance().setMemBean(bean.getMem());
                            updateData();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }

    public void loadMemberDoOperation() {
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getAccountService().getMemberDoOperation(tel,nonstr)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberDoOperationBean>() {
                    @Override
                    protected void onSuccees(MemberDoOperationBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            AccountManager.getInstance().setMemberDoOperationBean(bean);
                            updateData();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                    }
                });
    }
}
