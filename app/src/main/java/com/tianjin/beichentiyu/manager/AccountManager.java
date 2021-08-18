package com.tianjin.beichentiyu.manager;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.tianjin.beichentiyu.App;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.City;
import com.tianjin.beichentiyu.bean.MemBean;
import com.tianjin.beichentiyu.bean.MemberDoOperationBean;
import com.tianjin.beichentiyu.bean.Province;
import com.tianjin.beichentiyu.ui.activity.account.LoginActivity;
import com.tianjin.beichentiyu.utils.CityUtil;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.utils.SpUtil;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountManager {
    private static AccountManager instance;
    public static AccountManager getInstance(){
        if(instance == null){
            synchronized (AccountManager.class){
                if(instance == null){
                    instance = new AccountManager();
                }
            }
        }
        return instance;
    }

    private AccountManager(){
        initUesrInfo();
    }

    private MemBean memBean;
    private MemberDoOperationBean memberDoOperationBean;
    private String homeCity;
    private String livingCity;

    private City city;
    private String cityName;

    /**
     * 初始化用户信息
     */
    private void initUesrInfo(){
        initMenBean();
        initMemberOperation();
        homeCity = SpUtil.getString(Constant.Cache.HOMECITY,"");
        livingCity = SpUtil.getString(Constant.Cache.LIVINGCITY,"");
        String cityJson = SpUtil.getString(Constant.Cache.CITY,"");
        if(!TextUtils.isEmpty(cityJson)){
            city = new Gson().fromJson(cityJson,City.class);
            cityName = city.getAreaName();
        }
    }

    /**
     * 保存用户信息
     */
    public void saveUserInfo(){
        saveMenBean();
        saveMemberOperation();
        SpUtil.putString(Constant.Cache.HOMECITY,homeCity);
        SpUtil.putString(Constant.Cache.LIVINGCITY,livingCity);
    }

    /**
     * 是否已登录
     * @return
     */
    public boolean isLogin(){ return memBean != null;}

    /**
     * 信息是否完整
     * @return
     */
    public boolean isCompleteInformation(){
        if(memBean == null){
            LoginActivity.toActivity(App.get().getCurActivity());
            return false;
        }
        //未设置头像
        if(TextUtils.isEmpty(memBean.getHeadImg())){
            ToastUtil.showToast("请设置头像");
            return false;
        }
        //未设置昵称
        if(TextUtils.isEmpty(memBean.getNickName())){
            ToastUtil.showToast("请设置昵称");
            return false;
        }
        //未设置现居地
        if(TextUtils.isEmpty(memBean.getLivingPlace()) || TextUtils.isEmpty(memBean.getLivingPlaceName())){
            ToastUtil.showToast("请设置现居地");
            return false;
        }
        return true;
    }

    /**
     * 设置手动选择的城市
     * @param city
     */
    public void setSelectCity(City city){
        this.city = city;
        if(city == null){
            SpUtil.putString(Constant.Cache.CITY,"");
        }else{
            SpUtil.putString(Constant.Cache.CITY,new Gson().toJson(city));
            postCity(city);
        }
    }

    /**
     * 设置定位的城市
     * @param cityName
     */
    public void setLocationCity(String cityName){
        if(TextUtils.equals(this.cityName,cityName)) {
            return;
        }
        setSelectCity(null);
        List<Province> provinces =  CityUtil.getProvince(App.get().getApplicationContext());
        for(Province province:provinces){
            for(City city:province.getCities()){
                if(TextUtils.equals(cityName,city.getAreaName())){
                    postCity(city);
                    return;
                }
            }
        }
        ToastUtil.showToast("您所在地不在服务范围,请手动选择位置!");
    }

    public City getCurrentCity(){
        if(city != null){
            return city;
        }
        List<Province> provinces =  CityUtil.getProvince(App.get().getApplicationContext());
        if(TextUtils.isEmpty(cityName)) {
            for(Province province:provinces){
                for(City city:province.getCities()){
                    if(TextUtils.equals(cityName,city.getAreaName())){
                        return city;
                    }
                }
            }
        }
        if(memBean != null
                && !TextUtils.isEmpty(memBean.getLivingPlace())
                && !TextUtils.isEmpty(memBean.getLivingPlaceName())){
            return new City(memBean.getLivingPlace(),memBean.getLivingPlaceName());
        }
        ToastUtil.showToast("未找到用户所在地，请手动设置!");
        return provinces.get(0).getCities().get(0);
    }
    /**
     * 上传城市到所在地信息里
     * @param city
     */
    public void postCity(City city){
        if(city == null) {
            return;
        }
        cityName = city.getAreaName();
        String tel = memBean.getTel();
        String nonstr = memBean.getNonstr();
        ApiManager.getAccountService().setMemLivingPlace(tel,nonstr,city.getAreaId(),city.getAreaName())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        if(baseRespBean.isSuccessful()){
                            AccountManager.getInstance().getMemBean().setLivingPlace(city.getAreaId());
                            AccountManager.getInstance().getMemBean().setHometownName(city.getAreaName());
                            AccountManager.getInstance().saveMenBean();
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {}
                });
    }


    /**
     * 退出登陆
     */
    public void loggedOut(){
        AccountManager.getInstance().setMemBean(null);
        saveMenBean();
        LoginActivity.toActivity(App.get().getCurActivity());
    }

    /**
     * 初始化用户数据
     */
    public void initMenBean(){
        String memJson = SpUtil.getString(Constant.Cache.MEMDATA,"");
        if(!TextUtils.isEmpty(memJson)){
            try {
                memBean = new Gson().fromJson(memJson, MemBean.class);
            }catch (Exception e){
                e.printStackTrace();
                SpUtil.putString(Constant.Cache.MEMDATA,"");
            }
        }
    }

    /**
     * 保存用户数据
     */
    public void saveMenBean(){
        String memJson = "";
        if(memBean != null){
            memJson = new Gson().toJson(memBean);
        }
        SpUtil.putString(Constant.Cache.MEMDATA,memJson);
    }

    /**
     * 初始化预约数量信息
     */
    public void initMemberOperation(){
        String memJson = SpUtil.getString(Constant.Cache.MEMBER_DOOPERATION,"");
        if(!TextUtils.isEmpty(memJson)){
            try {
                memberDoOperationBean = new Gson().fromJson(memJson, MemberDoOperationBean.class);
            }catch (Exception e){
                e.printStackTrace();
                SpUtil.putString(Constant.Cache.MEMBER_DOOPERATION,"");
            }
        }
    }

    /**
     * 保存预约数量信息
     */
    public void saveMemberOperation(){
        String memJson = "";
        if(memberDoOperationBean != null){
            memJson = new Gson().toJson(memberDoOperationBean);
        }
        SpUtil.putString(Constant.Cache.MEMBER_DOOPERATION,memJson);
    }

    public String getNonstr() {
        return memBean.getNonstr();
    }

    public void setNonstr(String nonstr) {
        memBean.setNonstr(nonstr);
        saveMenBean();
    }

    public String getAccount() {
        return memBean.getTel();
    }

    public void setAccount(String account) {
        memBean.setTel(account);
        saveMenBean();
    }

    public MemberDoOperationBean getMemberDoOperationBean() {
        return memberDoOperationBean;
    }

    public void setMemberDoOperationBean(MemberDoOperationBean memberDoOperationBean) {
        this.memberDoOperationBean = memberDoOperationBean;
        saveMemberOperation();
    }

    public MemBean getMemBean() {
        return memBean;
    }

    public void setMemBean(MemBean memBean) {
        this.memBean = memBean;
        saveMenBean();
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getLivingCity() {
        return livingCity;
    }

    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
