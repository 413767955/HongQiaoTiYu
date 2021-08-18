package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.City;
import com.tianjin.beichentiyu.bean.Province;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.CityUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 黑瞳 on 2019/9/26 20:38
 * E-Mail Address：673236072@qq.com
 * 城市选择 - 搜索
 */
public class LocationSearchActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivityForResult(Activity activity,int requestCode){
        activity.startActivityForResult(new Intent(activity,LocationSearchActivity.class),requestCode);
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;

    @BindView(R.id.rv_location)
    RecyclerView mRvLocation;

    @BindView(R.id.et_search)
    EditText mEtSearch;


    private BaseAdapter mAdapter;
    private List<Province> mList;
    private Handler handler = new Handler();
    private Runnable run;
    private long searchTime = 0;
    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_location_search;
    }

    @Override
    protected void initData() {
        mList = CityUtil.getProvince(getBaseContext());
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initLocationRv();
        initSearch();
        searchCity("");
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v ->finish());
    }

    public void initSearch(){
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                searchCity(s.toString().trim());
            }
        });
    }

    public void searchCity(String text){
        handler.removeCallbacks(run);
        run = () -> {
            new Thread(() -> {
                List<City> cityList = new ArrayList<>();
                if(mList != null){
                    if(TextUtils.isEmpty(text)){
                        for(Province province:mList){
                            for(City city:province.getCities()){
                                cityList.add(city);
                            }
                        }
                    }else {
                        for (Province province : mList) {
                            if (TextUtils.equals(province.getAreaName(),text)
                                    || TextUtils.equals(province.getAreaName().replace("省",""),text)) {
                                cityList.addAll(province.getCities());
                            } else {
                                for (City city : province.getCities()) {
                                    if (!TextUtils.isEmpty(city.getAreaName())
                                            && city.getAreaName().contains(text)) {
                                        cityList.add(city);
                                    }
                                }
                            }
                        }
                    }
                    try {
                        mRvLocation.post(() -> {
                            mAdapter.setData(cityList);
                        });
                    }catch (Exception e){}
                }
            }).start();
        };
        long delayMillis = SystemClock.elapsedRealtime() - searchTime;
        searchTime = SystemClock.elapsedRealtime();
        if(delayMillis > 1000){
            handler.post(run);
        }else{
            handler.postDelayed(run,1000 - delayMillis);
        }

    }
    public void initLocationRv(){
         mAdapter = new BaseAdapter<City>(R.layout.item_location,null) {
            @Override
            protected void convert(BaseViewHolder holder, City item) {
                holder.setTvText(R.id.tv_name,item.getAreaName());
            }
        };
        mAdapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<City>)(data, position) -> {
            selectedCity(data);
        });
        mRvLocation.setLayoutManager(new LinearLayoutManager(this));
        mRvLocation.setAdapter(mAdapter);
    }
    /**
     * 选中城市
     * @param city
     */
    public void selectedCity(City city){
        AccountManager.getInstance().setSelectCity(city);
        setResult(RESULT_OK);
        finish();
    }
}
