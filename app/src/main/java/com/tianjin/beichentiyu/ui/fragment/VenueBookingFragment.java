package com.tianjin.beichentiyu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.heitong.frame.base.fragment.BaseMvpFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 场馆预约
 */
public class VenueBookingFragment extends BaseMvpFragment<BaseContract.Presenter> {
    public static final int PENDING_PAYMENT_TYPE = 0;//待付款
    public static final int PAID_TYPE = 1;//已缴费
    public static final int LISTED_TYPE = 2;//已结单
    private static final String TYPE_KEY = "TYPE_KEY";
    public static VenueBookingFragment newInstance(int type){
        VenueBookingFragment fragment = new VenueBookingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY,type);
        fragment.setArguments(bundle);
        return fragment;

    }
    private int type;

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_venue_booking,container,false);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getInt(TYPE_KEY,PENDING_PAYMENT_TYPE);
        }
    }
    @Override
    protected void bindEvent() {
        ButterKnife.bind(this,mView);
        initRv();
    }


    private void initRv(){
        List<String> mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(new BaseAdapter<String>(R.layout.item_venue_booking,mList) {
            @Override
            protected void convert(BaseViewHolder holder, String item) {

            }
        });
    }
}
