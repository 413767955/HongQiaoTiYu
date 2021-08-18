package com.tianjin.beichentiyu.api;

import com.tianjin.beichentiyu.bean.AliRechargeBean;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.ExpenditureDetailsBean;
import com.tianjin.beichentiyu.bean.MemberBankBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WalletService {
    /**
     * 获得用户绑定的银行卡列表
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemberBankList")
    Observable<MemberBankBean> getMemberBankList(@Field("tel") String tel,
                                                 @Field("nonstr") String nonstr);

    /**
     * 绑定银行卡
     * @param tel
     * @param nonstr
     * @param bankName      银行名称
     * @param bankNo        银行卡号
     * @param bankTel       预留电话
     * @param bankUser      持卡人
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/addMemberBank")
    Observable<BaseRespBean> addMemberBank(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("bankName") String bankName,
                                           @Field("bankNo") String bankNo,
                                           @Field("bankTel") String bankTel,
                                           @Field("bankUser") String bankUser);

    /**
     * 修改银行卡
     * @param tel
     * @param nonstr
     * @param bankId        银行卡id
     * @param bankName
     * @param bankNo
     * @param bankTel
     * @param bankUser
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/updateMemberBank")
    Observable<BaseRespBean> updateMemberBank(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("bankId") String bankId,
                                           @Field("bankName") String bankName,
                                           @Field("bankNo") String bankNo,
                                           @Field("bankTel") String bankTel,
                                           @Field("bankUser") String bankUser);

    /**
     * 删除银行卡
     * @param tel
     * @param nonstr
     * @param bankId
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/delMemberBank")
    Observable<BaseRespBean> delMemberBank(@Field("tel") String tel,
                                              @Field("nonstr") String nonstr,
                                              @Field("bankId") String bankId);

    /**
     * 提现申请
     * @param tel
     * @param nonstr
     * @param money
     * @param bankId
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/doWithoutMoney")
    Observable<BaseRespBean> doWithoutMoney(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                            @Field("money") String money,
                                           @Field("bankId") String bankId);


    @FormUrlEncoded
    @POST("appSixEdge/getMemberLogMoneyPage")
    Observable<ExpenditureDetailsBean> getMemberLogMoneyPage(@Field("tel") String tel,
                                                             @Field("nonstr") String nonstr,
                                                             @Field("pageNo") int pageNo);


    @FormUrlEncoded
    @POST("appSixEdge/doMemRecharge")
    Observable<AliRechargeBean> doMemRecharge(@Field("tel") String tel,
                                              @Field("nonstr") String nonstr,
                                              @Field("money") String money);
}
