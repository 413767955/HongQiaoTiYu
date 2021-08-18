package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.refresh.FooterView;
import com.donkingliang.refresh.HeaderView;
import com.donkingliang.refresh.RefreshLayout;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/28 22:57
 * E-Mail Address：673236072@qq.com
 * 热门推荐
 */
public class HotRecommendActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View{
    private static final String TYPE_KEY = "TYPE_KEY";
    public static void toActivity(Context context,int informationType){
        Intent intent = new Intent(context,HotRecommendActivity.class);
        intent.putExtra(TYPE_KEY,informationType);
        context.startActivity(intent);
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.rl_layout)
    RefreshLayout refreshLayout;

    private BaseAdapter mAdapter;
    private List<ArticleListBean.ListBean> mList = new ArrayList<>();
    private int totalPage = -1;
    private int pageNo = 0;

    private int informationType = Constant.Information.XWZX;
    private String seeType = "1";   //值为“1” 按点击量从大到小排序
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_hot_recommend;
    }

    @Override
    protected void initData() {
        informationType = getIntent().getIntExtra(TYPE_KEY,Constant.Information.XWZX);

    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        if (informationType == Constant.Information.XWZX){
            mToolbar.setMyTitle("新闻资讯");
        }else if (informationType == Constant.Information.PTGG){
            mToolbar.setMyTitle("公告");
        }
        initRefreshLayout();
        initRv();
    }
    @Override
    protected void bindEvent() {
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
                    //mPresenter.loadData(1);
                    getArticleList();
                })
                .setOnLoadMoreListener(() -> {//上拉加载
                    loadData();
                })
                .autoRefresh();
    }
    /**
     * 加载数据
     */
    private void loadData(){
        pageNo++;
        if(totalPage != -1 && pageNo > totalPage){
            refreshLayout.finishLoadMore(true,false);
            return;
        }
        getArticleList();
    }

    private void initRv(){

        mAdapter = new BaseAdapter<ArticleListBean.ListBean>(R.layout.item_information,mList){

            @Override
            protected void convert(BaseViewHolder holder, ArticleListBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getTitle());
                holder.setTvText(R.id.tv_read_num,item.getSeeNum()+"次阅读");
                if (item.getGenTime().length() >= 10){
                    String time = item.getGenTime().substring(0, 10);
                    holder.setTvText(R.id.tv_comment_num,time);
                }else {
                    holder.setTvText(R.id.tv_comment_num,item.getGenTime());
                }
//                holder.setTvText(R.id.tv_comment_num,item.getGenTime());
                GlideApp.with(HotRecommendActivity.this)
                        .load(item.getImgUrl())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView) holder.getView(R.id.iv_cover));
            }
        };
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mAdapter);
        mAdapter.setItemClickListner(position ->
                CompetitionInfoActivity.toActivity(HotRecommendActivity.this,
                        informationType,
                        mList.get(position).getAId())
        );
    }

    /**
     * 获取新闻列表
     */
    private void getArticleList(){
        ApiManager.getBusinessService().getArticleList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                getType(),seeType,"",pageNo,"")
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

    /**
     * 类型： 1 体育新闻，2 平台公告, 3体育赛事
     * @return
     */
    public String getType(){
        switch (informationType){
            case Constant.Information.XWZX:
                return "1";
            case Constant.Information.PTGG:
                return "2";
            case Constant.Information.SSXQ:
                return "3";
        }
        return "1";
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
}
