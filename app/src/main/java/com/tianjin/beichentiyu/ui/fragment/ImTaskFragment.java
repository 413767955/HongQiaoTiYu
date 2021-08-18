package com.tianjin.beichentiyu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.heitong.frame.base.fragment.BaseFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.TaskBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.ui.activity.MaintenanceTaskInfoActivity;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/10 19:24
 * E-Mail Address：673236072@qq.com
 * 巡检维修 - 临时任务 全部&进行中&已完成
 */
public class ImTaskFragment extends BaseFragment {
    public static final int TEMPORARY_TYPE = 1;//临时任务
    public static final int QUARTER_TYPE = 2;//季度任务
    public static final int ALL_TYPE = 2;//全部
    public static final int UNDERWAY_TYPE = 3;//进行中
    public static final int COMPLETED_TYPE = 4;//已完成

    private static final String TYPE_KEY = "TYPE_KEY";
    public static ImTaskFragment newInstance(int type){
        ImTaskFragment fragment = new ImTaskFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY,type);
        fragment.setArguments(bundle);
        return fragment;
    }
    private int type;

    @BindView(R.id.rl_layout)
    public RefreshLayout refreshLayout;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    private BaseAdapter mDataAdapter;
    private List<TaskBean.ListBean> mDataList = new ArrayList<>();
    private int pageNo = 1;

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_im_task, container, false);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getInt(TYPE_KEY,TEMPORARY_TYPE);
        }
    }

    @Override
    protected void bindEvent() {
        ButterKnife.bind(this,mView);
        initRefreshLayout();
        initDataRv();
    }


    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(getContext()))
                .setFooterView(new FooterView(getContext()))
                .setOnRefreshListener(() -> {//下拉刷新
                    mDataList.clear();
                    pageNo = 1;
                    loadData();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    pageNo++;
                    loadData();
                })
                .autoRefresh();
    }

    private void initDataRv(){
        mDataAdapter = new BaseAdapter<TaskBean.ListBean>(R.layout.item_im_task,mDataList){
            @Override
            protected void convert(BaseViewHolder holder, TaskBean.ListBean item) {
                holder.setTvText(R.id.tv_name,item.getField_name());
                holder.setTvText(R.id.tv_address,item.getField_address());

                switch (item.getTask_type()){
                    case "1":
                        holder.setTvText(R.id.tv_task_type,"维修任务");
                        break;
                    case "2":
                        holder.setTvText(R.id.tv_task_type,"安装任务");
                        break;
                    case "3":
                        holder.setTvText(R.id.tv_task_type,"巡检任务");
                        break;
                }
                GlideApp.with(getContext()).load(item.getBase_img()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into((ImageView) holder.getView(R.id.iv_cover));

            }
        };
        mDataAdapter.setItemClickListner(position -> {
            MaintenanceTaskInfoActivity.toActivity(getContext(),mDataList.get(position).getLog_id(),MaintenanceTaskInfoActivity.INSPECTION_CODE);
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.setAdapter(mDataAdapter);
    }


    public void loadData(){
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getMemLogTaskList(tel, nonstr, type,pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<TaskBean>() {
                    @Override
                    protected void onSuccees(TaskBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            addData(bean.getList());
                        }else{
                            refreshLayout.finishRefresh(true);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        refreshLayout.finishRefresh(true);
                    }
                });
    }
    public void addData(List<TaskBean.ListBean> list) {
        refreshLayout.finishRefresh(true);
        if (list != null && list.size() > 0) {
            mDataList.addAll(list);
            mDataAdapter.setData(mDataList);
            refreshLayout.finishLoadMore(true,true);
        } else {
            refreshLayout.finishLoadMore(true, false);
            if (mDataList.isEmpty()) {
                refreshLayout.showEmpty();
            } else {
                refreshLayout.hideEmpty();
            }
            return;
        }
    }

    /**
     * 提交结果页上传反馈成功后返回来刷新列表
     */
    public void callback(){
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

}
