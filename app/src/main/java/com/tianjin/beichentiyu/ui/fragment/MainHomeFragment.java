package com.tianjin.beichentiyu.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.heitong.frame.GlideApp;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.heitong.frame.base.fragment.BaseMvpFragment;
import com.sunfusheng.marqueeview.MarqueeView;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.bean.HomeMeunBean;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.LocationManager;
import com.tianjin.beichentiyu.presenter.HomePresenter;
import com.tianjin.beichentiyu.presenter.contract.HomeContract;
import com.tianjin.beichentiyu.ui.activity.HotRecommendActivity;
import com.tianjin.beichentiyu.ui.activity.InspectionMaintenanceActivity;
import com.tianjin.beichentiyu.ui.activity.LocationActivity;
import com.tianjin.beichentiyu.ui.activity.MatchForumActivity;
import com.tianjin.beichentiyu.ui.activity.NoopsycheFiancoActivity;
import com.tianjin.beichentiyu.ui.activity.NoopsycheFitnessActivity;
import com.tianjin.beichentiyu.ui.activity.SiteFacilityActivity;
import com.tianjin.beichentiyu.ui.activity.SiteInfoActivity;
import com.tianjin.beichentiyu.ui.activity.SiteListActivity;
import com.tianjin.beichentiyu.ui.activity.SportsOrganizationActivity;
import com.tianjin.beichentiyu.ui.activity.account.UserInfoCompleteActivity;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.DistanceUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.HomeCustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/9/21 14:35
 * E-Mail Address：673236072@qq.com
 * 首页
 */
public class MainHomeFragment extends BaseMvpFragment<HomeContract.Presenter> implements HomeContract.View {
    @BindView(R.id.toolbar)
    HomeCustomToolbar mToolbar;
    @BindView(R.id.rel_hot)
    RelativeLayout mRelHot;
    @BindView(R.id.rv_home_menu)
    RecyclerView mRvHomeMeun;
    @BindView(R.id.rv_information)
    RecyclerView mRvInformation;

    @BindView(R.id.marqueeView)
    MarqueeView mMarqueeView;
    @BindView(R.id.tv_nodata_view)
    TextView mTvNodataView;

    private BaseAdapter mAdapter;
    private List<ArticleListBean.ListBean> mList = new ArrayList<>();
    private int pageNo = 0;
    private String type = "";

    @Override
    protected HomeContract.Presenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void firstRequest() {
        //体育新闻
        //mPresenter.loadData(pageNo, "1");
        //公告
        mPresenter.loadData(pageNo, "2");

        getNearFieldPageHot(true,hotPageNo);

    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this, mView);
        initToolbar();
        initHomeMenu();
        initInformationRv();
        //gonggao();
    }

    @Override
    protected void bindEvent() {

        //公告
        mMarqueeView.setOnItemClickListener((position, textView) -> HotRecommendActivity.toActivity(getContext(), Constant.Information.PTGG));
        //新闻资讯
        mRelHot.setOnClickListener(v -> {
            //HotRecommendActivity.toActivity(getContext(), Constant.Information.XWZX);
            SiteListActivity.toActivity(getContext(),"全部场馆","","");
        });
    }

    /**
     * 设置标题栏
     */
    private void initToolbar() {
        mToolbar.setLocation(AccountManager.getInstance().getCityName());
        mToolbar.setLeftOnClick(v ->
                LocationActivity.toActivity(getContext())
        );
        mToolbar.setRightOnClick(v -> {
            UserInfoCompleteActivity.toChangeInfoActivity(getContext());
        });
    }

    List<String> notices = new ArrayList<>();


    private void initHomeMenu() {
        List<HomeMeunBean> list = new ArrayList<>();
        list.add(new HomeMeunBean("智能健身", R.drawable.ic_home_menu_znjs));
        list.add(new HomeMeunBean("智能体测", R.drawable.ic_home_menu_zntc));
        list.add(new HomeMeunBean("场地设施", R.drawable.ic_home_menu_cdss));
        list.add(new HomeMeunBean("巡检维修", R.drawable.ic_home_menu_xjwx));
        list.add(new HomeMeunBean("体育组织", R.drawable.ic_home_menu_tyzz));
        list.add(new HomeMeunBean("赛事活动", R.drawable.ic_home_menu_sshd));
        BaseAdapter menuAdapter = new BaseAdapter<HomeMeunBean>(R.layout.item_home_menu, list) {
            @Override
            protected void convert(BaseViewHolder holder, HomeMeunBean item) {
                holder.setTvText(R.id.tv_name, item.getName());
                holder.setBackgroundResource(R.id.iv_icon, item.getIcon());
            }
        };
        menuAdapter.setItemClickListner(position -> {
            onHomeMenuClick(position);
        });
        mRvHomeMeun.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRvHomeMeun.setAdapter(menuAdapter);

    }

    /**
     * 菜单监听
     *
     * @param position
     */
    public void onHomeMenuClick(int position) {
        switch (position) {
            case 0://智能健身
                NoopsycheFitnessActivity.toActivity(getContext());
                break;
            case 1://智能体测
                NoopsycheFiancoActivity.toActivity(getContext());
                break;
            case 2://场地设施
                SiteFacilityActivity.toActivity(getContext());
                break;
            case 3://巡检维修
                InspectionMaintenanceActivity.toActivity(getContext());
                //MaintenanceTaskInfoActivity.toActivity(getContext());
                break;
            case 4://体育组织
                SportsOrganizationActivity.toActivity(getContext());
                break;
            case 5://赛事活动
                MatchForumActivity.toActivity(getContext());
                break;
        }
    }

    /*private void initInformationRv() {
        mAdapter = new BaseAdapter<ArticleListBean.ListBean>(R.layout.item_information, mList) {
            @Override
            protected void convert(BaseViewHolder holder, ArticleListBean.ListBean item) {
                holder.setTvText(R.id.tv_title, item.getTitle());
                holder.setTvText(R.id.tv_read_num, item.getSeeNum() + "次阅读");
                if (item.getGenTime().length() >= 10) {
                    String time = item.getGenTime().substring(0, 10);
                    holder.setTvText(R.id.tv_comment_num, time);
                } else {
                    holder.setTvText(R.id.tv_comment_num, item.getGenTime());
                }
                //holder.setTvText(R.id.tv_comment_num,item.getGenTime());
                GlideApp.with(getContext())
                        .load(item.getImgUrl())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView) holder.getView(R.id.iv_cover));

            }
        };
        mRvInformation.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setItemClickListner(position -> {
            CompetitionInfoActivity.toActivity(getContext(), Constant.Information.SYXW, mList.get(position).getAId());
            //SpecialEventsInfoActivity.toActivity(getContext(),"1");
        });
        mRvInformation.setNestedScrollingEnabled(false);
        mRvInformation.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvInformation.setFocusable(false);
        mRvInformation.setAdapter(mAdapter);
    }*/

    public void addData(ArticleListBean bean) {
//        pageNo = bean.getPageNo();
//        mList.addAll(bean.getList());
//        mAdapter.setData(mList);
    }
    private List<NearFieldPageHotBean.ListBean> hotList = new ArrayList<>();
    public void addHotData(NearFieldPageHotBean bean) {
        if (bean.getList() != null && !bean.getList().isEmpty()) {
            hotList.addAll(bean.getList());
            recommendAdapter.setData(hotList);
            mTvNodataView.setVisibility(View.GONE);
        } else {
            mTvNodataView.setVisibility(View.VISIBLE);
        }
    }
    private BaseAdapter recommendAdapter;
    private void initInformationRv(){
        recommendAdapter = new BaseAdapter<NearFieldPageHotBean.ListBean>(R.layout.item_noopsyche_footpath,hotList){
            @Override
            protected void convert(BaseViewHolder holder, NearFieldPageHotBean.ListBean item) {
                holder.setTvText(R.id.tv_name,item.getMap().getField_name());
                holder.setTvText(R.id.tv_address,item.getMap().getAddress());
                holder.setTvText(R.id.tv_journey, DistanceUtil.distanceUtil(item.getDistance()));

                GlideApp.with(getContext())
                        .load(item.getMap().getBase_img())
                        .placeholder(R.drawable.icon_field_err)
                        .error(R.drawable.icon_field_err)
                        .into((ImageView)holder.getView(R.id.iv_cover));
            }
        };
        mRvInformation.setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        recommendAdapter.setItemClickListner(position -> {
            SiteInfoActivity.toActivity(getContext(), hotList.get(position).getMap().getId());
        });
        mRvInformation.setNestedScrollingEnabled(false);
        mRvInformation.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvInformation.setFocusable(false);
        mRvInformation.setAdapter(recommendAdapter);

    }

    @Override
    public void noticeData(ArticleListBean bean) {
        for (int i = 0; i < bean.getList().size(); i++) {
            notices.add(bean.getList().get(i).getTitle());
        }
        mMarqueeView.startWithList(notices);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.getInstance().getCity() != null) {
            mToolbar.setLocation(AccountManager.getInstance().getCurrentCity().getAreaName());
        } else {
            String cityName = LocationManager.getInstance().getCity();
            mToolbar.setLocation(cityName);
            AccountManager.getInstance().setLocationCity(cityName);
        }
    }

    private int hotPageNo = 1;

    public void getNearFieldPageHot(boolean isPost, int pageNo) {
        ApiManager.getBusinessService().getNearFieldPageHot(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                pageNo, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<NearFieldPageHotBean>() {
                    @Override
                    protected void onSuccees(NearFieldPageHotBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            addHotData(bean);
                        } else if (bean.getCode().equals("77777")) {
                            //如果返回code=77777，增加页面再请求,如果还是77777，提示附近没有场地
                            if (isPost) {
                                hotPageNo++;
                                getNearFieldPageHot(false, hotPageNo);
                            } else {
                                ToastUtil.showToast(bean.getMsg());
                                addHotData(bean);

                            }
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

}
