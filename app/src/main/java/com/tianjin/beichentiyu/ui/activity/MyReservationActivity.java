package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.MyReservationContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的预约
 */
public class MyReservationActivity extends BaseMvpActivity<MyReservationContract.Presenter> implements MyReservationContract.View {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,MyReservationActivity.class));
    }
    private RecyclerView date_recyclerView;
    private BaseAdapter dateAdapter;
    private List<String> dateList;

    private RecyclerView recyclerView;
    private BaseAdapter adapter;
    private List<String> list;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_reservation;
    }

    @Override
    protected MyReservationContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        date_recyclerView = findViewById(R.id.date_recyclerView);
        recyclerView = findViewById(R.id.recyclerView);
        initRecyclerView();

    }
    private void initRecyclerView(){
        dateList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            dateList.add(new String());
        }

        date_recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        dateAdapter = new BaseAdapter(R.layout.item_reservation_date,dateList){
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            protected void convert(BaseViewHolder holder, Object item) {

            }
        };
        date_recyclerView.setAdapter(dateAdapter);


        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new String());
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter(R.layout.item_my_reservation,list){
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            protected void convert(BaseViewHolder holder, Object item) {

            }
        };
        recyclerView.setAdapter(adapter);
    }

}
