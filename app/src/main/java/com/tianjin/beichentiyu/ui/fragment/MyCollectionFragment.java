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
 * Created by 黑瞳 on 2019/10/10 19:24
 * E-Mail Address：673236072@qq.com
 * 我的收藏
 */
public class MyCollectionFragment extends BaseMvpFragment<BaseContract.Presenter> {
    public static final int FIELD_TYPE = 0;//场地
    public static final int MATCH_TYPE = 1;//赛事
    public static final int ACTIVITY_TYPE = 2;//活动
    public static final int ASSOCIATION_TYPE = 3;//协会
    public static final int CLUB_TYPE = 4;//俱乐部

    private static final String TYPE_KEY = "TYPE_KEY";
    public static MyCollectionFragment newInstance(int type){
        MyCollectionFragment fragment = new MyCollectionFragment();
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
        return inflater.inflate(R.layout.fragment_my_collection, container, false);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getInt(TYPE_KEY,FIELD_TYPE);
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
        mRvContent.setAdapter(new BaseAdapter<String>(R.layout.item_my_reservation,mList) {
            @Override
            protected void convert(BaseViewHolder holder, String item) {

            }
        });
    }
}
