package com.tianjin.beichentiyu.api;

import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.BrandListBean;
import com.tianjin.beichentiyu.bean.DistrictListBean;
import com.tianjin.beichentiyu.bean.LoginBean;
import com.tianjin.beichentiyu.bean.MemberDoOperationBean;
import com.tianjin.beichentiyu.bean.MemberMsgBean;
import com.tianjin.beichentiyu.bean.StreetListBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 黑瞳 on 2019/10/17 21:36
 * E-Mail Address：673236072@qq.com
 */
public interface AccountService {

    /**
     * 登录
     * @param tel 账号
     * @param pwd 密码
     * @param lat 纬度
     * @param lng 经度
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/doMemLogIn")
    Observable<LoginBean> login(@Field("tel") String tel,
                                @Field("pwd") String pwd,
                                @Field("lat") Double lat,
                                @Field("lng") Double lng);


    /**
     * 登录
     * @param tel 账号
     * @param code 验证码
     * @param lat 纬度
     * @param lng 经度
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/doMemPhoneCodeLogIn")
    Observable<LoginBean> smsLogin(@Field("tel") String tel,
                                @Field("phoneCode") String code,
                                @Field("lat") Double lat,
                                @Field("lng") Double lng);

    /**
     * 注册
     * @param tel 手机号
     * @param pwd 密码
     * @param phoneCode 验证码
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/register")
    Observable<BaseRespBean> register( @Field("tel") String tel,
                           @Field("pwd") String pwd,
                           @Field("phoneCode") String phoneCode);

    /**
     * 发送短信
     * @param tel 账号
     * @param type 短信类型： 1 注册，2 登录，3 修改密码
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/sendPhoneMsg")
    Observable<BaseRespBean> sendPhone(@Field("tel") String tel,
                                       @Field("type") String type);


    /**
     * 提交现居地
     * @param tel       账号
     * @param nonstr    随机码
     * @param hometown      现居住地，对应的城市主键id
     * @param hometownName    城市名称
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/finishMemberHometown")
    Observable<BaseRespBean> finishMemberHometown( @Field("tel") String tel,
                                      @Field("nonstr") String nonstr,
                                      @Field("hometown") String hometown,
                                      @Field("hometownName") String hometownName);


    @FormUrlEncoded
    @POST("appSixEdge/setMemLivingPlace")
    Observable<BaseRespBean> setMemLivingPlace( @Field("tel") String tel,
                                                @Field("nonstr") String nonstr,
                                                @Field("livingPlace") String livingPlace,
                                                @Field("livingPlaceName") String livingPlaceName);

    /**
     * 提交性别
     * @param tel
     * @param nonstr
     * @param sex       性别:1 男，2 女
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/finishMemberSex")
    Observable<BaseRespBean> setFinishMemberSex( @Field("tel") String tel,
                           @Field("nonstr") String nonstr,
                           @Field("sex") String sex);


    /**
     * 出生年月
     * @param tel
     * @param nonstr
     * @param birthday       出生年月日
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/finishMemberBirthday")
    Observable<BaseRespBean> setFinishMemberBirthday( @Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("birthday") String birthday);

    /**
     * 修改密码 -- 旧密码修改
     * @param tel       账号（手机号）
     * @param nonstr    随机码
     * @param oldPwd    旧密码
     * @param newPwd    新密码
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/setNewPwd")
    Observable<BaseRespBean> updatePwd( @Field("tel") String tel,
                           @Field("nonstr") String nonstr,
                           @Field("oldPwd") String oldPwd,
                           @Field("newPwd") String newPwd);

    /**
     * 修改密码 -- 通过短信修改
     * @param tel       账号（手机号）
     * @param //nonstr    随机码
     * @param phoneCode    短信验证码
     * @param newPwd    新密码
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/setNewPwdByCode")
    Observable<BaseRespBean> updateSmsPwd( @Field("tel") String tel,
                            //@Field("nonstr") String nonstr,
                            @Field("phoneCode") String phoneCode,
                            @Field("newPwd") String newPwd);

    /**
     * 获取会员信息接口
     * @param tel       账号（手机号）
     * @param nonstr    随机码
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemberMsg")
    Observable<MemberMsgBean> getMemberMsg(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr);

    /**
     * 获得会员收藏预约数量接口
     * @param tel       账号（手机号）
     * @param nonstr    随机码
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemberDoOperation")
    Observable<MemberDoOperationBean> getMemberDoOperation(@Field("tel") String tel,
                                                           @Field("nonstr") String nonstr);

    /**
     * 修改昵称接口
     * @param tel       账号（手机号）
     * @param nonstr    随机码
     * @param nickName    昵称
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/finishMemNickName")
    Observable<BaseRespBean> finishMemNickName( @Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("nickName") String nickName);

    /**
     * 修改头像接口
     * @param tel       账号（手机号）
     * @param nonstr    随机码
     * @param headImg    头像
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/finishMemHeadImg")
    Observable<BaseRespBean> finishMemHeadImg( @Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("headImg") String headImg);

    /**
     * 获得县区接口
     * @param cityId
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getDistrictList")
    Observable<DistrictListBean> getDistrictList(@Field("cityId") String cityId);

    /**
     * 获得县区接口
     * @param disId
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getStreetList")
    Observable<StreetListBean> getStreetList(@Field("disId") String disId);

    /**
     * 品牌列表接口
     * @return
     */
    @POST("appSixEdge/getBrandList")
    Observable<BrandListBean> getBrandList();
}
