package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.City;
import com.tianjin.beichentiyu.bean.Province;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.LocationManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.CityUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/9/26 20:38
 * E-Mail Address：673236072@qq.com
 * 城市选择
 */
public class LocationActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context,LocationActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    @BindView(R.id.rv_location)
    RecyclerView mRvLocation;

    @BindView(R.id.rel_search)
    RelativeLayout mRelSearch;

    @BindView(R.id.rel_location)
    RelativeLayout mRelLocation;

    @BindView(R.id.tv_location_name)
    TextView mTvLocationName;

    private BaseAdapter mAdapter;
    private List<Province> mList;
    private int selectedCity = -1;
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_location;
    }

    @Override
    protected void initData() {
        mList = CityUtil.getProvince(getBaseContext());
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initLocationRv();
        mTvLocationName.setText(LocationManager.getInstance().getCity());
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> finish());
        mRelSearch.setOnClickListener(v -> LocationSearchActivity.toActivityForResult(this,1));
        mRelLocation.setOnClickListener(v -> {
            AccountManager.getInstance().setLocationCity(LocationManager.getInstance().getCity());
            finish();
        });
    }

    /**
     * 初始化省份列表
     */
    public void initLocationRv(){
         mAdapter = new BaseAdapter<Province>(R.layout.item_location,mList) {
            @Override
            protected void convert(BaseViewHolder holder, Province item) {
                holder.setTvText(R.id.tv_name,item.getAreaName());
                RecyclerView rvCity = holder.getView(R.id.rv_city);
                if(selectedCity == holder.getLayoutPosition()){
                    initCityRv(rvCity,item.getCities());
                    rvCity.setVisibility(View.VISIBLE);
                }else{
                    rvCity.setVisibility(View.GONE);
                }
            }
        };
        mAdapter.setItemClickListner(position -> {
            selectedCity = position;
            mAdapter.notifyDataSetChanged();
        });
        mRvLocation.setLayoutManager(new LinearLayoutManager(this));
        mRvLocation.setNestedScrollingEnabled(false);
        mRvLocation.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvLocation.setFocusable(false);
        mRvLocation.setAdapter(mAdapter);
    }

    /**
     * 初始化城市列表
     * @param rvCity
     * @param list
     */
    public void initCityRv(RecyclerView rvCity,List<City> list){
        if(rvCity == null){
            return;
       }
        BaseAdapter cityAdapter = null;
        if(rvCity.getTag() != null && rvCity.getTag() instanceof BaseAdapter){
            cityAdapter = (BaseAdapter) rvCity.getTag();
        }
        if(cityAdapter == null){
            cityAdapter = new BaseAdapter<City>(R.layout.item_location3,list) {
                @Override
                protected void convert(BaseViewHolder holder, City item) {
                    holder.setTvText(R.id.tv_name,item.getAreaName());
                }
            };
            cityAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<City>) (data, position) -> {
                selectedCity(data);
            });
            rvCity.setLayoutManager(new GridLayoutManager(getBaseContext(), 3));
            rvCity.setAdapter(cityAdapter);
            rvCity.setTag(cityAdapter);
        }else{
            cityAdapter.setData(list);
        }
    }

    /**
     * 选中城市
     * @param city
     */
    public void selectedCity(City city){
        AccountManager.getInstance().setSelectCity(city);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            finish();
        }
    }
}
