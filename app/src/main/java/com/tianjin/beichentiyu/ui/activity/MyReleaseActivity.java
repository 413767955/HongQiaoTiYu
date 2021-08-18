package com.tianjin.beichentiyu.ui.activity;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.presenter.contract.MyReleaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的发布
 */
public class MyReleaseActivity extends BaseMvpActivity<MyReleaseContract.Presenter> implements MyReleaseContract.View {
    private RecyclerView recyclerView;
    private BaseAdapter adapter;
    private List<String> list;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_release;
    }

    @Override
    protected MyReleaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected void bindView() {
        recyclerView = findViewById(R.id.recyclerView);

        initRecyclerView();
    }

    @Override
    protected void bindEvent() {

    }

    private void initRecyclerView(){
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new String());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseAdapter<String>(R.layout.item_my_release,list){
            @Override
            protected void convert(BaseViewHolder holder, String item) {

            }

        };
        recyclerView.setAdapter(adapter);

    }
}
