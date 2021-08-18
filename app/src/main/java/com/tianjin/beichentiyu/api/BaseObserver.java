package com.tianjin.beichentiyu.api;

import android.accounts.NetworkErrorException;
import android.util.Log;

import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.manager.AccountManager;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 黑瞳 on 2019/10/17 23:05
 * E-Mail Address：673236072@qq.com
 */
public abstract class BaseObserver<T> implements Observer<T> {

    //开始
    @Override
    public void onSubscribe(Disposable d) {

    }

    //获取数据
    @Override
    public void onNext(T tBaseEntity) {
        try {
            //账号失效跳到登录页
            if(tBaseEntity instanceof BaseRespBean){
                BaseRespBean bean = (BaseRespBean) tBaseEntity;
                if(bean.isLoginFailure()){
                    AccountManager.getInstance().loggedOut();
                }
            }
            onSuccees(tBaseEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //失败
    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);  //网络错误
            } else {
                onFailure(e, false);
            }
            Log.e("http","请求失败"+e.toString());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //结束
    @Override
    public void onComplete() {
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccees(T t) throws Exception;


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;


}
