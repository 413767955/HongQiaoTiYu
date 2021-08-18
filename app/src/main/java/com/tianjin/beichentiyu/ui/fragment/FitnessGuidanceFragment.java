package com.tianjin.beichentiyu.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.ui.activity.GuidanceVideoActivity;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/19 15:38
 * E-Mail Address：673236072@qq.com
 * 赛事活动 - 健身指导
 */
public class FitnessGuidanceFragment extends BaseFragment {
    public static FitnessGuidanceFragment newInstance(){
        FitnessGuidanceFragment fragment = new FitnessGuidanceFragment();
        return fragment;
    }

    private List<ArticleListBean.ListBean> mList = new ArrayList<>();
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private int totalPage = -1;
    private int pageNo = 0;
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_fitness_guidance, container, false);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this,mView);
        initRv();
        initRefreshLayout();
    }
    private BaseAdapter mAdapter;
    private void initRv(){
        mAdapter = new BaseAdapter<ArticleListBean.ListBean>(R.layout.item_fitness_guidance,mList) {
            @Override
            protected void convert(BaseViewHolder holder, ArticleListBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getTitle());
                //holder.setTvText(R.id.tv_content,item.get());
                holder.setTvText(R.id.tv_seeNum,item.getSeeNum()+"次播放");
                ImageView ivCover = holder.getView(R.id.iv_cover);
                GlideApp.with(getContext())
                        .asBitmap()
                        .load(item.getImgUrl())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivCover);
            }
        };
        mAdapter.setItemClickListner(position -> {
            //AccountCommonUtil.openBrowserBase64(getContext(),mList.get(position).getContent());
            GuidanceVideoActivity.toActivity(getContext(),mList.get(position).getAId());
            //赛事详情
            //CompetitionInfoActivity.toActivity(getContext(), Constant.Information.SSXQ,mList.get(position).getAId());
        });
        StaggeredGridLayoutManager layoutManager =  new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvContent.setLayoutManager(layoutManager);
        mRvContent.setAdapter(mAdapter);

    }

    public void addData(ArticleListBean bean) {
        totalPage = bean.getTotalPage();
        pageNo = bean.getPageNo();
        mList.addAll(bean.getList());
        mAdapter.setData(mList);
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true,pageNo < totalPage);
        if(mList.size() == 0){
            refreshLayout.showEmpty();
        }else{
            refreshLayout.hideEmpty();
        }
    }
    /**
     * 加载数据
     */
    private void loadData(){
        pageNo++;
        if(totalPage != -1 && pageNo > totalPage){
            ToastUtil.showToast("最后一页了");
            refreshLayout.finishLoadMore(true,false);
            return;
        }
        loadData(pageNo);
    }
    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(getContext()))
                .setFooterView(new FooterView(getContext()))
                .setOnRefreshListener(() -> {//下拉刷新
                    mList.clear();
                    loadData(1);
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }

    public void loadData(int pageNo) {
        ApiManager.getBusinessService().getArticleList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                "5","1","",pageNo,"")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArticleListBean>() {
                    @Override
                    protected void onSuccees(ArticleListBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            addData(bean);
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
}

