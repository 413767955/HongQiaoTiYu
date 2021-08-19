package com.tianjin.beichentiyu.manager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.tianjin.beichentiyu.App;

public class LocationManager {
    private static LocationManager instance;
    public static LocationManager getInstance(){
        if(instance == null){
            synchronized (LocationManager.class){
                if(instance == null){
                    instance = new LocationManager();
                }
            }
        }
        return instance;
    }
    private LocationManager(){}
    private LocationClient mLocationClient = null;
    private BDAbstractLocationListener myListener;
    private BDLocation bdLocation;
    private double latitude;       //维度
    private double longitude;      //经度
    private String addr;         //详细地址信息
    private String country;      //国家
    private String province;     //省份
    private String city;         //城市
    private String district;     //区县
    private String street;       //街道信息

    public void startLocation() {
        if(mLocationClient != null) {
            mLocationClient.restart();
            return;
        }
        mLocationClient = new LocationClient(App.get());
        //声明LocationClient类
        myListener = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
                //以下只列举部分获取地址相关的结果信息
                //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
                bdLocation = location;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                addr = location.getAddrStr();    //获取详细地址信息
                country = location.getCountry();    //获取国家
                province = location.getProvince();    //获取省份
                city = location.getCity();    //获取城市
                district = location.getDistrict();    //获取区县
                street = location.getStreet();    //获取街道信息
            }
        };
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);
        option.setCoorType("bd0911");
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public BDLocation getBdLocation() {
        return bdLocation;
    }

    public void setBdLocation(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
