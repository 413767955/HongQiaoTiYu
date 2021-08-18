package com.tianjin.beichentiyu.api;


import com.tianjin.beichentiyu.utils.html.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 黑瞳 on 2019/10/17 21:10
 * E-Mail Address：673236072@qq.com
 */
public class ApiManager {
    //测试
    //private static String URL = "http://47.111.177.130/sixedge/";
    //private static String URL = "http://129.211.167.222/sixedge/";
    //六边体育正式
    //private static String URL = "http://123.57.138.191/sixedge/";
    //北辰体育正式
    //private static String URL = "http://47.99.124.188/beichengSport/";
    //红桥体育
    private static String URL = "http://39.107.115.68/beichengSport/";
    //南开体育
    //private static String URL = "http://39.107.122.205/beichengSport/";

    private static Retrofit instance;
    private static OkHttpClient mOkHttpClient;
    //数据接口
    private static ApiService apiService;
    //账户数据接口
    private static AccountService accountService;
    //业务逻辑接口
    private static BusinessService businessService;
    //钱包接口
    private static WalletService walletService;
    public static final int TIMEOUT = 60;
    public static Retrofit get() {
        if (instance == null) {
            synchronized (ApiManager.class) {
                if (instance == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                            .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                            .addInterceptor(new LoggingInterceptor())
                            .build();
                    instance = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            // 设置请求的域名
                            .baseUrl(URL)
                            // 设置解析转换工厂
                            .addConverterFactory( GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        return instance;
    }

    public static ApiService getApiService(){
        if(apiService == null) {
            apiService = get().create(ApiService.class);
        }
       return apiService;
    }

    public static AccountService getAccountService(){
        if(accountService == null) {
            accountService = get().create(AccountService.class);
        }
        return accountService;
    }

    public static BusinessService getBusinessService(){
        if(businessService == null) {
            businessService = get().create(BusinessService.class);
        }
        return businessService;
    }

    public static WalletService getWalletService(){
        if(walletService == null) {
            walletService = get().create(WalletService.class);
        }
        return walletService;
    }

    public static String getURL() {
        return URL;
    }
}
