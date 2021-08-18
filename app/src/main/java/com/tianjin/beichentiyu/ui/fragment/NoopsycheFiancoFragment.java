package com.tianjin.beichentiyu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.fragment.BaseMvpFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.NoopsycheFiancoCentreBean;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黑瞳 on 2019/9/23 22:13
 * E-Mail Address：673236072@qq.com
 * 智能体侧中心
 */
public class NoopsycheFiancoFragment extends BaseMvpFragment<BaseContract.Presenter> implements  BaseContract.View{
    public static final int INDOOR_TYPE = 0;//室内
    public static final int OUTDOOR_TYPE = 1;//室外

    private static final String TYPE_KEY = "TYPE_KEY";
    public static NoopsycheFiancoFragment newInstance(int type){
        NoopsycheFiancoFragment fragment = new NoopsycheFiancoFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY,type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private int type;
    private RecyclerView mRvContent;
    private BaseAdapter adapter;
    private List<NoopsycheFiancoCentreBean> mList;
    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getInt(TYPE_KEY,INDOOR_TYPE);
        }
    }

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_noopsyche_fianco, container, false);
    }

    @Override
    protected void bindView() {
        mRvContent = mView.findViewById(R.id.rv_content);

        initRv();
    }
    private void initRv(){
        mList = new ArrayList<>();
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
        mList.add(new NoopsycheFiancoCentreBean("","XXX智能体侧中心","天津市 南开区","周一至周日 全天","10108","10086"));
//        adapter = new BaseAdapter<Object>(R.layout.item_noopsyche_fianco,mList){
//            @Override
//            protected void convert(BaseViewHolder holder, Object item) {
//
//            }
//        };
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(adapter);
    }
}
