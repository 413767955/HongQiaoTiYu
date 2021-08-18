package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.AreaTaskBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wjy on 2020/5/24
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class AreaTaskActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;
    String title;
    String proId;
    String cityId;
    String disId;
    String streetId;
    String yearTime;
    String state;
    private int pageNo = 0;
    private int totalPage = -1;


    private BaseAdapter mAdapter;
    private List<AreaTaskBean.ListBean> mList = new ArrayList<>();

    public static void toActivity(Activity activity,
                                  String title,
                                  String proId,
                                  String cityId,
                                  String disId,
                                  String streetId,
                                  String yearTime,
                                  String state) {
        Intent intent = new Intent(activity, AreaTaskActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("proId", proId);
        intent.putExtra("cityId", cityId);
        intent.putExtra("disId", disId);
        intent.putExtra("streetId", streetId);
        intent.putExtra("yearTime", yearTime);
        intent.putExtra("state", state);
        activity.startActivity(intent);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            proId = intent.getStringExtra("proId");
            cityId = intent.getStringExtra("cityId");
            disId = intent.getStringExtra("disId");
            streetId = intent.getStringExtra("streetId");
            yearTime = intent.getStringExtra("yearTime");
            state = intent.getStringExtra("state");
        }
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_area_task;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRvContent();
        initRefreshLayout();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setMyTitle(title);
        mToolbar.setLeftOnClick(v -> finish());
    }

    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(this))
                .setFooterView(new FooterView(this))
                .setOnRefreshListener(() -> {//下拉刷新
                    mList.clear();
                    pageNo = 1;
                    getAreaTaskPage();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }

    private void initRvContent() {
        mAdapter = new BaseAdapter<AreaTaskBean.ListBean>(R.layout.item_im_task, mList) {
            @Override
            protected void convert(BaseViewHolder holder, AreaTaskBean.ListBean item) {
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
                GlideApp.with(AreaTaskActivity.this).load(item.getBase_img()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into((ImageView) holder.getView(R.id.iv_cover));


            }
        };
        mAdapter.setItemClickListner(position -> {
            MaintenanceTaskInfoActivity.toActivity(AreaTaskActivity.this,mList.get(position).getLog_id(),
                    MaintenanceTaskInfoActivity.STATISTICS_CODE);
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mAdapter);
    }

    private void loadData() {
        pageNo++;
        if(totalPage != -1 && pageNo > totalPage){
            refreshLayout.finishLoadMore(true,false);
            return;
        }
        getAreaTaskPage();
    }

    public void addData(List<AreaTaskBean.ListBean> list) {
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true, pageNo < totalPage);
        if (list != null && !list.isEmpty()) {
            mList.addAll(list);
        }

        if (mList != null && !mList.isEmpty()) {
            mAdapter.setData(mList);
            refreshLayout.hideEmpty();
        } else {
            refreshLayout.showEmpty();
        }
    }

    private void getAreaTaskPage() {
        ApiManager.getBusinessService().getAreaTaskPage(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                proId, cityId, disId, streetId, yearTime, state, pageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<AreaTaskBean>() {
                    @Override
                    protected void onSuccees(AreaTaskBean data) throws Exception {
                        if (data.isSuccessful()) {
                            totalPage = data.getTotalPage();
                            addData(data.getList());
                        } else {
                            ToastUtil.showToast(data.getMsg());
                            addData(null);
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        addData(null);
                    }
                });
    }
}
