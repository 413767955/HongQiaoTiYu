package com.tianjin.beichentiyu.manager;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by wjy on 2020/4/19
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class FilterManager {
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLayPro;
    private RecyclerView mRvPro;
    private BaseAdapter mProAdapter;
    private List<Province> mProList;
    private int proPosition = -1;

    private LinearLayout mLayCity;
    private RecyclerView mRvCity;
    private BaseAdapter mCityAdapter;
    private List<City> mCityList;
    private int cityPosition = -1;

    private LinearLayout mLayCounty;
    private RecyclerView mRvCounty;
    private BaseAdapter mCountyAdapter;
    private List<DistrictListBean.ListBean> mCountyList;
    private int countPosition = -1;

    private LinearLayout mLayStree;
    private RecyclerView mRvStree;
    private BaseAdapter mStreeAdapter;
    private List<StreetListBean.ListBean> mStreeList;
    private int streePosition = -1;

    private LinearLayout mLayBrand;
    private RecyclerView mRvBrand;
    private BaseAdapter mBrandAdapter;
    private List<BrandListBean.ListBean> mBrandList;
    private int brandPosition = -1;

    private TextView mTvSubscribeOk;
    private TextView mTvSubscribeNo;

    private Province province;
    private City city;
    private DistrictListBean.ListBean county;
    private StreetListBean.ListBean stree;
    private BrandListBean.ListBean brand;
    String subscribeState = "";


    private LodingDialog lodingDialog;

    private Callback callback;

    public FilterManager(DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        this.mLayPro = mDrawerLayout.findViewById(R.id.lay_pro);
        this.mRvPro = mDrawerLayout.findViewById(R.id.rv_pro);

        this.mLayCity = mDrawerLayout.findViewById(R.id.lay_city);
        this.mRvCity = mDrawerLayout.findViewById(R.id.rv_city);

        this.mLayCounty = mDrawerLayout.findViewById(R.id.lay_county);
        this.mRvCounty = mDrawerLayout.findViewById(R.id.rv_county);

        this.mLayStree = mDrawerLayout.findViewById(R.id.lay_stree);
        this.mRvStree = mDrawerLayout.findViewById(R.id.rv_stree);

        this.mLayBrand = mDrawerLayout.findViewById(R.id.lay_brand);
        this.mRvBrand = mDrawerLayout.findViewById(R.id.rv_brand);

        this.mTvSubscribeOk = mDrawerLayout.findViewById(R.id.tv_subscribe_ok);
        this.mTvSubscribeNo = mDrawerLayout.findViewById(R.id.tv_subscribe_no);


        mDrawerLayout.findViewById(R.id.tv_reset).setOnClickListener(v -> {
            mLayCounty.setVisibility(View.GONE);
            mLayStree.setVisibility(View.GONE);
            mLayCity.setVisibility(View.GONE);
            province = null;
            city = null;
            county = null;
            stree = null;
            brand = null;
            subscribeState = "";
            proPosition = -1;
            cityPosition = -1;
            streePosition = -1;
            brandPosition = -1;
            if (mProAdapter != null) {
                mProAdapter.notifyDataSetChanged();
            }
            if (mBrandAdapter != null) {
                mBrandAdapter.notifyDataSetChanged();
            }
            updateChecked(mTvSubscribeOk, false);
            updateChecked(mTvSubscribeNo, false);
        });
        mDrawerLayout.findViewById(R.id.tv_complete).setOnClickListener(v -> {
            if (callback != null) {
                String proId = "";
                String cityId = "";
                String disId = "";
                String strId = "";
                String brandName = "";
                if (province != null) {
                    proId = province.getAreaId();
                }
                if (city != null) {
                    cityId = city.getAreaId();
                }
                if (county != null) {
                    strId = county.getDistrictId();
                }
                if (brand != null) {
                    brandName = brand.getBrandImg();
                }
                callback.onComplete(proId, cityId, disId, strId, brandName, subscribeState);
            }
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        });


        initSubscribe();
    }

    /**
     * 打开筛选页面
     */
    public void openDrawer() {
        loadBrand();
        loadPro();
        mDrawerLayout.openDrawer(Gravity.RIGHT);
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
    private void loadPro() {
        if (mProAdapter == null) {
            mProList = CityUtil.getProvince(mDrawerLayout.getContext());
            mProAdapter = new BaseAdapter<Province>(R.layout.item_drawer, mProList) {
                @Override
                protected void convert(BaseViewHolder holder, Province item) {
                    TextView textView = holder.getView(R.id.tv_name);
                    updateChecked(textView, proPosition == holder.getLayoutPosition());
                    holder.setTvText(R.id.tv_name, item.getAreaName());
                }
            };
            mProAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<Province>) (data, position) -> {
                if (proPosition == position) {
                    proPosition = -1;
                    province = null;
                    loadCitie(null);
                } else {

                    proPosition = position;
                    province = data;
                    loadCitie(data.getCities());
                }
                mProAdapter.notifyDataSetChanged();
            });
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL);
            mRvPro.setLayoutManager(layoutManager);
            mRvPro.setNestedScrollingEnabled(false);
            mRvPro.setHasFixedSize(true);
            //解决数据加载完成后, 没有停留在顶部的问题
            mRvPro.setFocusable(false);
            mRvPro.setAdapter(mProAdapter);
        }
    }

    /**
     * 加载城市
     */
    private void loadCitie(List<City> cities) {
        mCityList = cities;
        cityPosition = -1;
        if (mCityAdapter == null) {
            mCityAdapter = new BaseAdapter<City>(R.layout.item_drawer, mCityList) {
                @Override
                protected void convert(BaseViewHolder holder, City item) {
                    TextView textView = holder.getView(R.id.tv_name);
                    updateChecked(textView, cityPosition == holder.getLayoutPosition());
                    holder.setTvText(R.id.tv_name, item.getAreaName());
                }
            };
            mCityAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<City>) (data, position) -> {
                if (cityPosition == position) {
                    cityPosition = -1;
                    city = null;
                    loadCounty(null);
                } else {
                    cityPosition = position;
                    city = data;
                    loadCounty(data);
                }
                mCityAdapter.notifyDataSetChanged();
            });
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
            mRvCity.setLayoutManager(layoutManager);
            mRvCity.setNestedScrollingEnabled(false);
            mRvCity.setHasFixedSize(true);
            //解决数据加载完成后, 没有停留在顶部的问题
            mRvCity.setFocusable(false);
            mRvCity.setAdapter(mCityAdapter);
        } else {
            mCityAdapter.setData(mCityList);
        }
        city = null;
        county = null;
        stree = null;
        mLayCounty.setVisibility(View.GONE);
        mLayStree.setVisibility(View.GONE);
        mLayCity.setVisibility(mCityList == null || mCityList.size() == 0 ? View.GONE : View.VISIBLE);
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
        lodingDialog = new LodingDialog(mDrawerLayout.getContext(), "获取中...");
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
     * @param list
     */
    private void initCounty(List<DistrictListBean.ListBean> list) {
        mCountyList = list;
        countPosition = -1;
        if (mCountyAdapter == null) {
            mCountyAdapter = new BaseAdapter<DistrictListBean.ListBean>(R.layout.item_drawer, mCountyList) {
                @Override
                protected void convert(BaseViewHolder holder, DistrictListBean.ListBean item) {
                    TextView textView = holder.getView(R.id.tv_name);
                    updateChecked(textView, countPosition == holder.getLayoutPosition());
                    holder.setTvText(R.id.tv_name, item.getDistrictName());
                }
            };
            mCountyAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<DistrictListBean.ListBean>) (data, position) -> {
                if (countPosition == position) {
                    countPosition = -1;
                    county = null;
                    loadStree(null);
                } else {
                    countPosition = position;
                    county = data;
                    loadStree(county);
                }
                mCountyAdapter.notifyDataSetChanged();

            });
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
            mRvCounty.setLayoutManager(layoutManager);
            mRvCounty.setNestedScrollingEnabled(false);
            mRvCounty.setHasFixedSize(true);
            //解决数据加载完成后, 没有停留在顶部的问题
            mRvCounty.setFocusable(false);
            mRvCounty.setAdapter(mCountyAdapter);
        } else {
            mCountyAdapter.setData(mCountyList);
        }
        county = null;
        stree = null;
        mLayStree.setVisibility(View.GONE);
        mLayCounty.setVisibility(mCountyList == null || mCountyList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    /**
     * 加载街道
     */
    private void loadStree(DistrictListBean.ListBean county) {
        if (county == null) {
            initStree(null);
            return;
        }
        lodingDialog = new LodingDialog(mDrawerLayout.getContext(), "获取中...");
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
     *
     * @param list
     */
    private void initStree(List<StreetListBean.ListBean> list) {
        mStreeList = list;
        streePosition = -1;
        if (mStreeAdapter == null) {
            mStreeAdapter = new BaseAdapter<StreetListBean.ListBean>(R.layout.item_drawer, mStreeList) {
                @Override
                protected void convert(BaseViewHolder holder, StreetListBean.ListBean item) {
                    TextView textView = holder.getView(R.id.tv_name);
                    updateChecked(textView, streePosition == holder.getLayoutPosition());
                    holder.setTvText(R.id.tv_name, item.getStreetName());
                }
            };
            mStreeAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<StreetListBean.ListBean>) (data, position) -> {
                if (streePosition == position) {
                    streePosition = -1;
                    stree = null;
                } else {
                    streePosition = position;
                    stree = data;
                }
                mStreeAdapter.notifyDataSetChanged();
            });
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
            mRvStree.setLayoutManager(layoutManager);
            mRvStree.setNestedScrollingEnabled(false);
            mRvStree.setHasFixedSize(true);
            //解决数据加载完成后, 没有停留在顶部的问题
            mRvStree.setFocusable(false);
            mRvStree.setAdapter(mStreeAdapter);
        } else {
            mStreeAdapter.setData(mStreeList);
        }
        stree = null;
        mLayStree.setVisibility(mStreeList == null || mStreeList.size() == 0 ? View.GONE : View.VISIBLE);
    }


    /**
     * 加载品牌
     */
    private void loadBrand() {
        if (mBrandList != null) {
            return;
        }
        lodingDialog = new LodingDialog(mDrawerLayout.getContext(), "获取中...");
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
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
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
            view.setBackground(null);
        }
    }

    public interface Callback {
        void onComplete(String proId,
                        String cityId,
                        String disId,
                        String strId,
                        String brandName,
                        String subscribeState);
    }
}
