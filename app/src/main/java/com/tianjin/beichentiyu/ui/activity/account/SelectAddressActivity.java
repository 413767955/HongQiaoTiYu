package com.tianjin.beichentiyu.ui.activity.account;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.City;
import com.tianjin.beichentiyu.bean.MemBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.utils.PickerUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.AddressPicker;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 选择地址
 */
public class SelectAddressActivity extends BaseActivity {
    public static void toActicity(Context context){
        context.startActivity(new Intent(context,SelectAddressActivity.class));
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rel_address)
    RelativeLayout mRelAddress;
    @BindView(R.id.tv_address)
    TextView mTvAddress;

    private String livingPlace;
    private String livingPlaceName;
    private LodingDialog lodingDialog;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        lodingDialog = new LodingDialog(this,"地区设置中...");
        mToolbar.setRightTv("保存",R.color.color_161616);
        livingPlace = AccountManager.getInstance().getMemBean().getLivingPlace();
        livingPlaceName = AccountManager.getInstance().getMemBean().getLivingPlaceName();
        mTvAddress.setText(livingPlaceName);
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mToolbar.setRightOnClick(v -> saveAddress());
        mRelAddress.setOnClickListener(v -> showAddressDialog());
    }

    /**
     * 显示地区选择弹窗
     */
    private void showAddressDialog(){
        AddressPicker picker = PickerUtil.region(SelectAddressActivity.this);
        picker.setOnAddressPickListener((province, city, county) -> {
            livingPlace = String.valueOf(city.getId());
            livingPlaceName = city.getName();
            mTvAddress.setText(livingPlaceName);
            LogUtils.e(String.valueOf(city.getId()));
            LogUtils.e(city.getName());
        });
    }

    /**
     * 保存地区
     */
    private void saveAddress(){
        if(TextUtils.isEmpty(livingPlace) || TextUtils.isEmpty(livingPlaceName)){
            ToastUtil.showToast("请设置地区!");
            return;
        }
        lodingDialog.show();
        MemBean memBean = AccountManager.getInstance().getMemBean();
        String tel = memBean.getTel();
        String nonstr = memBean.getNonstr();
        ApiManager.getAccountService().setMemLivingPlace(tel,nonstr,livingPlace,livingPlaceName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        lodingDialog.dismiss();
                        if(baseRespBean.isSuccessful()){
                            AccountManager.getInstance().getMemBean().setLivingPlace(livingPlace);
                            AccountManager.getInstance().getMemBean().setLivingPlaceName(livingPlaceName);
                            AccountManager.getInstance().saveMenBean();
                            AccountManager.getInstance().setSelectCity(new City(livingPlace,livingPlaceName));
                            finish();
                        }else{
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast("地区设置失败，请检查网络!");
                    }
                });
    }
}

