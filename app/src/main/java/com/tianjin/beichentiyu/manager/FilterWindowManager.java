package com.tianjin.beichentiyu.manager;

import android.animation.ObjectAnimator;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BrandListBean;
import com.tianjin.beichentiyu.bean.City;
import com.tianjin.beichentiyu.bean.DistrictListBean;
import com.tianjin.beichentiyu.bean.Province;
import com.tianjin.beichentiyu.bean.StreetListBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.utils.CityUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.entity.WheelItem;
import cn.qqtheme.framework.widget.WheelView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wjy on 2020/4/19
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class FilterWindowManager {
    private RelativeLayout mLayout;         //筛选界面
    private LinearLayout mMenuLayout;       //筛选菜单
    private LinearLayout mRegionLayout;     //地区界面
    private LinearLayout mMoreLayout;       //更多界面

    private DrawerLayout mDrawerLayout;

    private static final String DEFAULT_OPTION = "全部";
    private WheelView mWheelView1;
    private WheelView mWheelView2;
    private WheelView mWheelView3;
    private WheelView mWheelView4;

    private TextView mTvMenu1;
    private TextView mTvMenu2;
    private TextView mTvMenu3;
    private TextView mTvMenu4;
    private TextView mTvMenu5;

    private Map<Integer,List<WheelItem>> datas = new HashMap<>();
    private Integer[] ids = {0,0,0,0};

    private static final int INDEX_PROVINCE = 0;    //省份
    private static final int INDEX_CITY = 1;        //城市
    private static final int INDEX_COUNTY = 2;      //县区
    private static final int INDEX_STREE = 3;       //街道

    private LinearLayout mLayBrand;
    private RecyclerView mRvBrand;
    private BaseAdapter mBrandAdapter;
    private List<BrandListBean.ListBean> mBrandList;
    private int brandPosition = -1;

    private TextView mTvSubscribeOk;
    private TextView mTvSubscribeNo;

    private BrandListBean.ListBean brand;
    private String subscribeState = "";

    private boolean isShowRegion;   //显示地区界面
    private boolean isShowMore;     //显示更多界面

    private LodingDialog lodingDialog;

    private Callback callback;

    public FilterWindowManager(DrawerLayout mDrawerLayout) {
        this.mDrawerLayout = mDrawerLayout;
        this.mLayout = mDrawerLayout.findViewById(R.id.rel_filter);

        initMenu();             //初始化菜单
        initRegion();           //初始化地区
        initDrawerLayout();     //初始化更多筛选
    }
    public FilterWindowManager(RelativeLayout view) {
        this.mLayout = view;

        initRegion();           //初始化地区
    }

    private void initMenu(){
        this.mTvMenu1 = mLayout.findViewById(R.id.tv_menu1);
        this.mTvMenu2 = mLayout.findViewById(R.id.tv_menu2);
        this.mTvMenu3 = mLayout.findViewById(R.id.tv_menu3);
        this.mTvMenu4 = mLayout.findViewById(R.id.tv_menu4);
        this.mTvMenu5 = mLayout.findViewById(R.id.tv_menu5);

        View.OnClickListener onClickListener = v -> {
            showRegion(!isShowRegion);
        };
        this.mLayout.findViewById(R.id.rel_menu1).setOnClickListener(onClickListener);
        this.mLayout.findViewById(R.id.rel_menu2).setOnClickListener(onClickListener);
        this.mLayout.findViewById(R.id.rel_menu3).setOnClickListener(onClickListener);
        this.mLayout.findViewById(R.id.rel_menu4).setOnClickListener(onClickListener);
        this.mLayout.findViewById(R.id.rel_menu5).setOnClickListener(v -> {
            showRegion(false);
            loadBrand();
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        });
    }
    private void initRegion(){
        this.mRegionLayout = mLayout.findViewById(R.id.lin_region);
        this.mWheelView1 = mLayout.findViewById(R.id.wheel1);
        this.mWheelView2 = mLayout.findViewById(R.id.wheel2);
        this.mWheelView3 = mLayout.findViewById(R.id.wheel3);
        this.mWheelView4 = mLayout.findViewById(R.id.wheel4);
        initFilter(true,true,true,true);
        this.mRegionLayout.post(() -> {
            this.mRegionLayout.setTranslationY(-this.mRegionLayout.getHeight());
        });

        this.mLayout.findViewById(R.id.tv_reset).setOnClickListener(v -> {
            initFilter(true,true,true,true);
        });
        this.mLayout.findViewById(R.id.tv_complete).setOnClickListener(v -> {
            if (callback != null) {
                String brandName = "";
                if (brand != null) {
                    brandName = brand.getBrandImg();
                }

                Pair<String,String> data1 = getSelectedData(INDEX_PROVINCE,ids[INDEX_PROVINCE]);
                Pair<String,String> data2 = getSelectedData(INDEX_CITY,ids[INDEX_CITY]);
                Pair<String,String> data3 = getSelectedData(INDEX_COUNTY,ids[INDEX_COUNTY]);
                Pair<String,String> data4 = getSelectedData(INDEX_STREE,ids[INDEX_STREE]);

                String region = "";
                if(ids[0] != 0){
                    region += data1.second;
                }
                if(ids[1] != 0){
                    region += data2.second;
                }
                if(ids[2] != 0){
                    region += data3.second;
                }
                if(ids[3] != 0){
                    region += data4.second;
                }
                callback.onComplete(data1.first, data2.first, data3.first, data4.first, brandName, subscribeState,region);
                if(mTvMenu1 != null) {
                    mTvMenu1.setText(data1.second);
                    mTvMenu2.setText(data2.second);
                    mTvMenu3.setText(data3.second);
                    mTvMenu4.setText(data4.second);
                }
            }
            showRegion(false);
        });

        mWheelView1.setOnItemSelectListener(index -> {
            ids[INDEX_PROVINCE] = index;
            if(index == 0){
                initFilter(false,true,true,true);
            }else{
                List<WheelItem> list = datas.get(INDEX_PROVINCE);
                if(list != null && list.get(index) instanceof Province){
                    Province data = (Province) list.get(index);
                    initCity(data.getCities());
                }
            }
        });

        mWheelView2.setOnItemSelectListener(index -> {
            ids[INDEX_CITY] = index;
            if(index == 0){
                initFilter(false,false,true,true);
            }else{
                List<WheelItem> list = datas.get(INDEX_CITY);
                if(list != null && list.get(index) instanceof City){
                    City data = (City) list.get(index);
                    loadCounty(data);
                }
            }
        });

        mWheelView3.setOnItemSelectListener(index -> {
            ids[INDEX_COUNTY] = index;
            if(index == 0){
                initFilter(false,false,false,true);
            }else{
                List<WheelItem> list = datas.get(INDEX_COUNTY);
                if(list != null && list.get(index) instanceof DistrictListBean.ListBean){
                    DistrictListBean.ListBean data = (DistrictListBean.ListBean) list.get(index);
                    loadStree(data);
                }
            }
        });

        mWheelView4.setOnItemSelectListener(index -> {
            ids[INDEX_STREE] = index;
        });
    }

    private void initDrawerLayout(){
        this.mLayBrand = mDrawerLayout.findViewById(R.id.lay_brand);
        this.mRvBrand = mDrawerLayout.findViewById(R.id.rv_brand);

        this.mTvSubscribeOk = mDrawerLayout.findViewById(R.id.tv_subscribe_ok);
        this.mTvSubscribeNo = mDrawerLayout.findViewById(R.id.tv_subscribe_no);
        updateChecked(mTvSubscribeOk, false);
        updateChecked(mTvSubscribeNo, false);

        mDrawerLayout.findViewById(R.id.tv_resetx).setOnClickListener(v -> {
            brand = null;
            brandPosition = -1;
            subscribeState = "";
            if (mBrandAdapter != null) {
                mBrandAdapter.notifyDataSetChanged();
            }
            updateChecked(mTvSubscribeOk, false);
            updateChecked(mTvSubscribeNo, false);
        });
        mDrawerLayout.findViewById(R.id.tv_completex).setOnClickListener(v -> {
            if (callback != null) {
                String brandName = "";
                if (brand != null) {
                    brandName = brand.getBrandImg();
                }
                Pair<String,String> data1 = getSelectedData(INDEX_PROVINCE,ids[INDEX_PROVINCE]);
                Pair<String,String> data2 = getSelectedData(INDEX_CITY,ids[INDEX_CITY]);
                Pair<String,String> data3 = getSelectedData(INDEX_COUNTY,ids[INDEX_COUNTY]);
                Pair<String,String> data4 = getSelectedData(INDEX_STREE,ids[INDEX_STREE]);

                callback.onComplete(data1.first, data2.first, data3.first, data4.first, brandName, subscribeState,"");
            }
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        });
        initSubscribe();
    }
    /**
     * 初始化筛选
     * @param initPor
     * @param initCity
     * @param initDis
     * @param initStr
     */
    private void initFilter(boolean initPor,boolean initCity,boolean initDis,boolean initStr){
        if(initPor) initPro();
        if(initCity) initCity(null);
        if(initDis) initCounty(null);
        if(initStr) initStree(null);
    }

    private Pair<String,String> getSelectedData(int dataIndex,int idIndex){
        String id = "";
        String name = "";
        switch (dataIndex){
            case INDEX_PROVINCE:
                if(idIndex == 0){
                    id = "";
                    name = "省份";
                }else{
                    List<WheelItem> list = datas.get(INDEX_PROVINCE);
                    if(list != null && list.get(idIndex) instanceof Province){
                        Province data = (Province) list.get(idIndex);
                        id = data.getAreaId();
                        name = data.getAreaName();
                    }
                }
                break;
            case INDEX_CITY:
                if(idIndex == 0){
                    id = "";
                    name = "城市";
                }else{
                    List<WheelItem> list = datas.get(INDEX_CITY);
                    if(list != null && list.get(idIndex) instanceof City){
                        City data = (City) list.get(idIndex);
                        id = data.getAreaId();
                        name = data.getAreaName();
                    }
                }
                break;
            case INDEX_COUNTY:
                if(idIndex == 0){
                    id = "";
                    name = "县区";
                }else{
                    List<WheelItem> list = datas.get(INDEX_COUNTY);
                    if(list != null && list.get(idIndex) instanceof DistrictListBean.ListBean){
                        DistrictListBean.ListBean data = (DistrictListBean.ListBean) list.get(idIndex);
                        id = data.getDistrictId();
                        name = data.getDistrictName();
                    }
                }
                break;
            case INDEX_STREE:
                if(idIndex == 0){
                    id = "";
                    name = "街道";
                }else{
                    List<WheelItem> list = datas.get(INDEX_STREE);
                    if(list != null && list.get(idIndex) instanceof StreetListBean.ListBean){
                        StreetListBean.ListBean data = (StreetListBean.ListBean) list.get(idIndex);
                        id = data.getStreetId();
                        name = data.getStreetName();
                    }
                }
                break;
        }
        return new Pair(id,name);
    }

    private void initWheelView(WheelView wheelView,List<?> list,int index){
        wheelView.setItems(list,index);
        wheelView.setCycleDisable(true);
        wheelView.setOffset(5);
        wheelView.setTextSize(16);
        wheelView.setUseWeight(true);
        wheelView.setTextSizeAutoFit(true);
        WheelView.DividerConfig dividerConfig = new WheelView.DividerConfig();
        dividerConfig.setRatio(WheelView.DividerConfig.FILL);
    }
    public void showRegion(boolean isShowRegion){
        this.isShowRegion = isShowRegion;
        if(isShowRegion){
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRegionLayout,
                    "translationY",
                    mRegionLayout.getTranslationY(),
                    0);
            objectAnimator.setDuration(300);
            objectAnimator.start();
        }else{
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRegionLayout,
                    "translationY",
                    mRegionLayout.getTranslationY(),
                    -mRegionLayout.getHeight());
            objectAnimator.setDuration(300);
            objectAnimator.start();
        }
    }
    /**
     * 打开筛选页面
     */
    public void openDrawer() {
        loadBrand();
    }

    /**
     * 初始化订阅
     */
    private void initSubscribe() {
        mTvSubscribeOk.setOnClickListener(v -> {
            if ("1".equals(subscribeState)) {
                subscribeState = "";
                updateChecked(mTvSubscribeOk, false);
            } else {
                subscribeState = "1";
                updateChecked(mTvSubscribeNo, false);
                updateChecked(mTvSubscribeOk, true);
            }
        });
        mTvSubscribeNo.setOnClickListener(v -> {
            if ("0".equals(subscribeState)) {
                subscribeState = "";
                updateChecked(mTvSubscribeNo, false);
            } else {
                subscribeState = "0";
                updateChecked(mTvSubscribeOk, false);
                updateChecked(mTvSubscribeNo, true);
            }
        });
    }

    /**
     * 加载省份
     */
    private void initPro() {
        List<WheelItem> list = new ArrayList<>();
        list.add(new Province(DEFAULT_OPTION));
        list.addAll(CityUtil.getProvince(mLayout.getContext()));
        datas.put(INDEX_PROVINCE, list);
        ids[INDEX_PROVINCE] = 0;
        initWheelView(mWheelView1,
                datas.get(INDEX_PROVINCE),
                ids[INDEX_PROVINCE]);

    }

    /**
     * 加载城市
     */
    private void initCity(List<City> cities) {
        List<WheelItem> list = new ArrayList<>();
        list.add(new City(DEFAULT_OPTION));
        if(cities != null) {
            list.addAll(cities);
        }
        datas.put(INDEX_CITY, list);
        ids[INDEX_CITY] = 0;
        initWheelView(mWheelView2,
                datas.get(INDEX_CITY),
                ids[INDEX_CITY]);
    }

    /**
     * 加载县区
     *
     * @param city
     */
    private void loadCounty(City city) {
        if (city == null) {
            initCounty(null);
            return;
        }
        lodingDialog = new LodingDialog(mLayout.getContext(), "获取中...");
        lodingDialog.show();
        ApiManager.getAccountService().getDistrictList(city.getAreaId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DistrictListBean>() {
                    @Override
                    protected void onSuccees(DistrictListBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            initCounty(bean.getList());
                        } else {
                            initCounty(null);
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        initCounty(null);
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }

    /**
     * 初始化县区
     *
     */
    private void initCounty(List<DistrictListBean.ListBean> diss) {
        List<WheelItem> list = new ArrayList<>();
        list.add(new DistrictListBean.ListBean(DEFAULT_OPTION));
        if(diss != null){
            list.addAll(diss);
        }
        datas.put(INDEX_COUNTY, list);
        ids[INDEX_COUNTY] = 0;
        initWheelView(mWheelView3,
                datas.get(INDEX_COUNTY),
                ids[INDEX_COUNTY]);

    }

    /**
     * 加载街道
     */
    private void loadStree(DistrictListBean.ListBean county) {
        if (county == null) {
            initStree(null);
            return;
        }
        lodingDialog = new LodingDialog(mLayout.getContext(), "获取中...");
        lodingDialog.show();
        ApiManager.getAccountService().getStreetList(county.getDistrictId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<StreetListBean>() {
                    @Override
                    protected void onSuccees(StreetListBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            initStree(bean.getList());
                        } else {
                            initStree(null);
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        initStree(null);
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }

    /**
     * 初始化街道
     */
    private void initStree(List<StreetListBean.ListBean> strs) {
        List<WheelItem> list = new ArrayList<>();
        list.add(new StreetListBean.ListBean(DEFAULT_OPTION));
        if(strs != null){
            list.addAll(strs);
        }
        datas.put(INDEX_STREE, list);
        ids[INDEX_STREE] = 0;
        initWheelView(mWheelView4,
                datas.get(INDEX_STREE),
                ids[INDEX_STREE]);
    }


    /**
     * 加载品牌
     */
    private void loadBrand() {
        if (mBrandList != null) {
            return;
        }
        lodingDialog = new LodingDialog(mLayout.getContext(), "获取中...");
        lodingDialog.show();
        ApiManager.getAccountService().getBrandList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BrandListBean>() {
                    @Override
                    protected void onSuccees(BrandListBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            initBrand(bean.getList());
                        } else {
                            initBrand(null);
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        initBrand(null);
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }

    /**
     * 初始品牌
     *
     * @param list
     */
    private void initBrand(List<BrandListBean.ListBean> list) {
        mBrandList = list;
        if (mBrandAdapter == null) {
            mBrandAdapter = new BaseAdapter<BrandListBean.ListBean>(R.layout.item_drawer, mBrandList) {
                @Override
                protected void convert(BaseViewHolder holder, BrandListBean.ListBean item) {
                    TextView textView = holder.getView(R.id.tv_name);
                    updateChecked(textView, brandPosition == holder.getLayoutPosition());
                    holder.setTvText(R.id.tv_name, item.getBrandName());
                }
            };
            mBrandAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<BrandListBean.ListBean>) (data, position) -> {
                if (brandPosition == position) {
                    brandPosition = -1;
                    brand = null;
                } else {
                    brandPosition = position;
                    brand = data;
                }
                mBrandAdapter.notifyDataSetChanged();
            });
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
            mRvBrand.setLayoutManager(layoutManager);
            mRvBrand.setNestedScrollingEnabled(false);
            mRvBrand.setHasFixedSize(true);
            //解决数据加载完成后, 没有停留在顶部的问题
            mRvBrand.setFocusable(false);
            mRvBrand.setAdapter(mBrandAdapter);
        } else {
            mBrandAdapter.setData(mBrandList);
        }
        mLayBrand.setVisibility(mBrandList == null || mBrandList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void updateChecked(TextView view, boolean isChecked) {
        if (isChecked) {
            view.setBackgroundResource(R.drawable.bg_drawer_check_radius);
        } else {
            view.setBackgroundResource(R.drawable.bg_drawer_radius);
        }
    }

    public interface Callback {
        void onComplete(String proId,
                        String cityId,
                        String disId,
                        String strId,
                        String brandName,
                        String subscribeState,
                        String region);
    }
}
