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
import com.heitong.frame.base.fragment.BaseMvpFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.presenter.CompetitionPresenter;
import com.tianjin.beichentiyu.presenter.contract.CompetitionContract;
import com.tianjin.beichentiyu.ui.activity.CompetitionInfoActivity;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/10/19 15:38
 * E-Mail Address：673236072@qq.com
 * 赛事活动 - 赛事
 */
public class MainCompetitionFragment extends BaseMvpFragment<CompetitionContract.Presenter> implements CompetitionContract.View{
    public static MainCompetitionFragment newInstance(){
        MainCompetitionFragment fragment = new MainCompetitionFragment();
        return fragment;
    }

    //private List<CompetitionBean> mList;
    private List<ArticleListBean.ListBean> mList = new ArrayList<>();
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private int totalPage = -1;
    private int pageNo = 0;
    @Override
    protected CompetitionContract.Presenter initPresenter() {
        return new CompetitionPresenter();
    }
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_competition, container, false);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this,mView);
        initRv();
        initRefreshLayout();
    }
    private BaseAdapter mAdapter;
    private void initRv(){
        /*BaseAdapter mAdapter = new BaseAdapter<CompetitionBean>(R.layout.item_competition,mList) {
            @Override
            protected void convert(BaseViewHolder holder, final CompetitionBean item) {
                ImageView ivCover = holder.getView(R.id.iv_cover);
                if(item.getHeight()!=0){
                    ViewGroup.LayoutParams layoutParams = ivCover.getLayoutParams();
                    layoutParams.height = (int) item.getHeight();
                    ivCover.setLayoutParams(layoutParams);
                }
                int position = holder.getLayoutPosition();
                GlideApp.with(getContext())
                        .asBitmap()
                        .load(item.getImg())
                        .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                CompetitionBean bean = mList.get(position);
                                ViewGroup.LayoutParams layoutParams = ivCover.getLayoutParams();
                                bean.setWidth(ivCover.getMeasuredWidth());
                                bean.setHeight(bean.getWidth()/((float)resource.getWidth())*((float)resource.getHeight()));
                                layoutParams.height = (int) bean.getHeight();
                                ivCover.setLayoutParams(layoutParams);

                                if(bean == item){
                                    ivCover.setImageBitmap(resource);
                                }
                                return false;
                            }
                        })
                        .into(ivCover);//加载原图大
            }
        };*/
        mAdapter = new BaseAdapter<ArticleListBean.ListBean>(R.layout.item_competition,mList) {
            @Override
            protected void convert(BaseViewHolder holder, ArticleListBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getTitle());
                //holder.setTvText(R.id.tv_content,item.get());
                holder.setTvText(R.id.tv_seeNum,item.getSeeNum()+"次阅读");
                ImageView ivCover = holder.getView(R.id.iv_cover);
//                ViewGroup.LayoutParams params = ivCover.getLayoutParams();
//                params.height = params.height + new Random().nextInt(300);
//                ivCover.setLayoutParams(params);
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
            //赛事详情
            CompetitionInfoActivity.toActivity(getContext(), Constant.Information.SSXQ,mList.get(position).getAId());
        });
        StaggeredGridLayoutManager layoutManager =  new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvContent.setLayoutManager(layoutManager);
        mRvContent.setAdapter(mAdapter);

    }

    @Override
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
        mPresenter.loadData(pageNo);
    }
    /**
     * 初始化下拉刷新上拉加载
     */
    private void initRefreshLayout() {
        refreshLayout.setHeaderView(new HeaderView(getContext()))
                .setFooterView(new FooterView(getContext()))
                .setOnRefreshListener(() -> {//下拉刷新
                    mList.clear();
                    mPresenter.loadData(1);
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }
    public void finishRefresh(){
        refreshLayout.finishRefresh(true);
    }

}

