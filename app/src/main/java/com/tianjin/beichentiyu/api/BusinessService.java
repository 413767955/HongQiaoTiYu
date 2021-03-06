package com.tianjin.beichentiyu.api;

import com.tianjin.beichentiyu.bean.ActivityMsgBean;
import com.tianjin.beichentiyu.bean.ActivityPageBean;
import com.tianjin.beichentiyu.bean.AppIndexBean;
import com.tianjin.beichentiyu.bean.AppointmentBean;
import com.tianjin.beichentiyu.bean.AppointmentDateBean;
import com.tianjin.beichentiyu.bean.AppointmentPhysicalBean;
import com.tianjin.beichentiyu.bean.AreaTaskBean;
import com.tianjin.beichentiyu.bean.ArticleListBean;
import com.tianjin.beichentiyu.bean.ArtileMsgBean;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.FieldAppointListBean;
import com.tianjin.beichentiyu.bean.FieldEqueListBean;
import com.tianjin.beichentiyu.bean.FieldImgListBean;
import com.tianjin.beichentiyu.bean.FieldListByDisIdBean;
import com.tianjin.beichentiyu.bean.FieldMsgBean;
import com.tianjin.beichentiyu.bean.IntellectEqueListBean;
import com.tianjin.beichentiyu.bean.IntellectImgListBean;
import com.tianjin.beichentiyu.bean.IntellectMsgBean;
import com.tianjin.beichentiyu.bean.LogTaskMsg;
import com.tianjin.beichentiyu.bean.MemActPayBean;
import com.tianjin.beichentiyu.bean.MemberAreaMsgBean;
import com.tianjin.beichentiyu.bean.MemberCollectBean;
import com.tianjin.beichentiyu.bean.NearFieldPageBean;
import com.tianjin.beichentiyu.bean.NearFieldPageHotBean;
import com.tianjin.beichentiyu.bean.NearIntellectPageBean;
import com.tianjin.beichentiyu.bean.OrganizationBean;
import com.tianjin.beichentiyu.bean.OrganizationDutiesBean;
import com.tianjin.beichentiyu.bean.OrganizationInfoBean;
import com.tianjin.beichentiyu.bean.PhysicalBean;
import com.tianjin.beichentiyu.bean.PhysicalImgList;
import com.tianjin.beichentiyu.bean.PhysicalInfoBean;
import com.tianjin.beichentiyu.bean.RepairRecordBean;
import com.tianjin.beichentiyu.bean.SearchSiteBean;
import com.tianjin.beichentiyu.bean.TaskBean;
import com.tianjin.beichentiyu.bean.TaskTotalMsgBean;
import com.tianjin.beichentiyu.bean.UploadImageResultsBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by ?????? on 2019/10/17 21:36
 * E-Mail Address???673236072@qq.com
 */
public interface BusinessService {
    /**
     * ????????????
     *
     * @param imgPath
     * @return
     */
    @Multipart
    @POST("appSixEdge/addImg")
    Observable<UploadImageResultsBean> uploadImg(@Part MultipartBody.Part imgPath);

    /**
     * ??????????????????
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getActivityPage")
    Observable<ActivityPageBean> getActivityPage(@Field("tel") String tel,
                                                 @Field("nonstr") String nonstr,
                                                 @Field("orgId") String orgId,
                                                 @Field("pageNo") int pageNo,
                                                 @Field("pageSize") String pageSize);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr  ?????????
     * @param type    ????????? 1 ???????????????2 ????????????, 3????????????, 4???????????????5????????????
     * @param seeType ?????????????????????????????????1??? ??????????????????????????????
     * @param pageNo  ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getArticleList")
    Observable<ArticleListBean> getArticleList(@Field("tel") String tel,
                                               @Field("nonstr") String nonstr,
                                               @Field("type") String type,
                                               @Field("seeType") String seeType,
                                               @Field("orgId") String orgId,
                                               @Field("pageNo") int pageNo,
                                               @Field("pageSize") String pageSize);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param aId    ???????????????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getArtileMsg")
    Observable<ArtileMsgBean> getArtileMsg(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("aId") String aId);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param id     ???????????????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getActivityMsg")
    Observable<ActivityMsgBean> getActivityMsg(@Field("tel") String tel,
                                               @Field("nonstr") String nonstr,
                                               @Field("id") String id);


    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param aId    ????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/userEnrollActivity")
    Observable<BaseRespBean> userEnrollActivity(@Field("tel") String tel,
                                                @Field("nonstr") String nonstr,
                                                @Field("aId") String aId);

    /**
     * ???????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @param type      ?????????????????????id???1 ????????????2 ???????????????3 ????????????4 ????????????????????? 5 ????????????
     *                  6 ????????????????????? 7 ??????????????? 8???????????????????????????9 ????????????
     * @param smartType ??????????????????????????????????????????1 ??????????????? 2 ???????????????3 ???????????????4???????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getNearFieldPage")
    Observable<NearFieldPageBean> getNearFieldPage(@Field("tel") String tel,
                                                   @Field("nonstr") String nonstr,
                                                   @Field("pageNo") int pageNo,
                                                   @Field("type") String type,
                                                   @Field("smartType") String smartType,
                                                   @Field("proId") String proId,
                                                   @Field("cityId") String cityId,
                                                   @Field("disId") String disId,
                                                   @Field("strId") String strId,
                                                   @Field("brandName") String brandName,
                                                   @Field("subscribeState") String subscribeState);

    /**
     * ???????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @param type   ?????????1 ???????????? 2 ??????????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getNearFieldPageHot")
    Observable<NearFieldPageHotBean> getNearFieldPageHot(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("pageNo") int pageNo,
                                                         @Field("type") String type);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param id     ????????????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldMsg")
    Observable<FieldMsgBean> getFieldMsg(@Field("tel") String tel,
                                         @Field("nonstr") String nonstr,
                                         @Field("id") String id);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldImgList")
    Observable<FieldImgListBean> getFieldImgList(@Field("tel") String tel,
                                                 @Field("nonstr") String nonstr,
                                                 @Field("id") String id);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param id     ????????????id
     * @param type   0 ????????? 1 ?????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldEqueList")
    Observable<FieldEqueListBean> getFieldEqueList(@Field("tel") String tel,
                                                   @Field("nonstr") String nonstr,
                                                   @Field("id") String id,
                                                   @Field("type") String type);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemSetRepairPage")
    Observable<RepairRecordBean> getMemSetRepairPage(@Field("tel") String tel,
                                                     @Field("nonstr") String nonstr,
                                                     @Field("pageNo") int pageNo);

    /**
     * ??????
     *
     * @param tel
     * @param nonstr
     * @param sfeId        ?????????????????????
     * @param repairImgOne ????????????1
     * @param repairImgTwo ????????????2
     * @param content      ????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/setLogRepair")
    Observable<BaseRespBean> setLogRepair(@Field("tel") String tel,
                                          @Field("nonstr") String nonstr,
                                          @Field("sfeId") String sfeId,
                                          @Field("repairImgOne") String repairImgOne,
                                          @Field("repairImgTwo") String repairImgTwo,
                                          @Field("content") String content);

    /**
     * ????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId    ????????????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldAppointList")
    Observable<FieldAppointListBean> getFieldAppointList(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("fId") String fId);

    /**
     * ???????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId    ????????????id
     * @param date   ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAllMemLogAppointment")
    Observable<AppointmentDateBean> getAllMemLogAppointment(@Field("tel") String tel,
                                                            @Field("nonstr") String nonstr,
                                                            @Field("fId") String fId,
                                                            @Field("date") String date);

    /**
     * ??????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId
     * @param date
     * @param aId    ????????????????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/memDoLogAppointment")
    Observable<BaseRespBean> memDoLogAppointment(@Field("tel") String tel,
                                                 @Field("nonstr") String nonstr,
                                                 @Field("fId") String fId,
                                                 @Field("date") String date,
                                                 @Field("aId") String aId);


    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param type   ?????????1?????? 2?????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getOrganizationList")
    Observable<OrganizationBean> getOrganizationList(@Field("tel") String tel,
                                                     @Field("nonstr") String nonstr,
                                                     @Field("type") String type);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param orgId  ??????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getOrganizationMsg")
    Observable<OrganizationInfoBean> getOrganizationMsg(@Field("tel") String tel,
                                                        @Field("nonstr") String nonstr,
                                                        @Field("orgId") String orgId);

    /**
     * ???????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param orgId  ??????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getOrganizationDutiesList")
    Observable<OrganizationDutiesBean> getOrganizationDutiesList(@Field("tel") String tel,
                                                                 @Field("nonstr") String nonstr,
                                                                 @Field("orgId") String orgId);

    /**
     * ???????????????
     *
     * @param tel
     * @param nonstr
     * @param type   1??????????????? 2???????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAppIndexList")
    Observable<AppIndexBean> getAppIndexList(@Field("tel") String tel,
                                             @Field("nonstr") String nonstr,
                                             @Field("type") String type);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemberCollectList")
    Observable<MemberCollectBean> getMemberCollectList(@Field("tel") String tel,
                                                       @Field("nonstr") String nonstr,
                                                       @Field("pageNo") int pageNo);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param date   ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogAppointmentList")
    Observable<AppointmentBean> getMemLogAppointmentList(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param date   ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogAppointmentPhysicalList")
    Observable<AppointmentPhysicalBean> getMemLogAppointmentPhysicalList(@Field("tel") String tel,
                                                                         @Field("nonstr") String nonstr,
                                                                         @Field("date") String date);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param pageNo ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/showPhysicalPage")
    Observable<PhysicalBean> showPhysicalPage(@Field("tel") String tel,
                                              @Field("nonstr") String nonstr,
                                              @Field("pageNo") int pageNo,
                                              @Field("proId") String proId,
                                              @Field("cityId") String cityId,
                                              @Field("disId") String disId,
                                              @Field("strId") String strId,
                                              @Field("brandName") String brandName,
                                              @Field("subscribeState") String subscribeState);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getPhysicalMsg")
    Observable<PhysicalInfoBean> getPhysicalMsg(@Field("tel") String tel,
                                                @Field("nonstr") String nonstr,
                                                @Field("id") String id);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getPhysicalImgList")
    Observable<PhysicalImgList> getPhysicalImgList(@Field("tel") String tel,
                                                   @Field("nonstr") String nonstr,
                                                   @Field("id") String id);

    /**
     * ????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId    ????????????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getPhysicalAppointList")
    Observable<FieldAppointListBean> getPhysicalAppointList(@Field("tel") String tel,
                                                            @Field("nonstr") String nonstr,
                                                            @Field("fId") int fId);

    /**
     * ???????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId    ????????????id
     * @param date   ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAllMemLogPhyAppointment")
    Observable<AppointmentDateBean> getAllMemLogPhyAppointment(@Field("tel") String tel,
                                                               @Field("nonstr") String nonstr,
                                                               @Field("fId") int fId,
                                                               @Field("date") String date);

    /**
     * ??????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId
     * @param date
     * @param aId    ????????????????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/memDoLogPhyAppointment")
    Observable<BaseRespBean> memDoLogPhyAppointment(@Field("tel") String tel,
                                                    @Field("nonstr") String nonstr,
                                                    @Field("fId") int fId,
                                                    @Field("date") String date,
                                                    @Field("aId") String aId);

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param type   1????????????  2????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogTaskList")
    Observable<TaskBean> getMemLogTaskList(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("type") int type,
                                           @Field("pageNo") int pageNo);

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param logId  ??????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getLogTaskMsg")
    Observable<LogTaskMsg> getLogTaskMsg(@Field("tel") String tel,
                                         @Field("nonstr") String nonstr,
                                         @Field("logId") String logId);

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param logId
     * @param state        0:?????????  1????????????/?????????  3.????????????
     * @param repairImgOne
     * @param repairImgTwo
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/toDoTheTaskResult")
    Observable<BaseRespBean> toDoTheTaskResult(@Field("tel") String tel,
                                               @Field("nonstr") String nonstr,
                                               @Field("logId") String logId,
                                               @Field("state") int state,
                                               @Field("taskImgOne") String repairImgOne,
                                               @Field("taskImgTwo") String repairImgTwo);


    /**
     * ????????????id??????????????????
     *
     * @param tel
     * @param nonstr
     * @param disId
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldListByDisId")
    Observable<FieldListByDisIdBean> getFieldListByDisId(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("disId") String disId);

    /**
     * ????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldListByName")
    Observable<SearchSiteBean> getFieldListByName(@Field("tel") String tel,
                                                  @Field("nonstr") String nonstr,
                                                  @Field("name") String name);

    /**
     * ??????????????????
     *
     * @param tel
     * @param nonstr
     * @param relId
     * @param type   1:?????????2??????????????????3????????????4???????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/doMemberCollect")
    Observable<BaseRespBean> doMemberCollect(@Field("tel") String tel,
                                             @Field("nonstr") String nonstr,
                                             @Field("relId") String relId,
                                             @Field("type") String type);

    /**
     * ????????????
     *
     * @param tel
     * @param nonstr
     * @param relId
     * @param type
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/delMemberCollect")
    Observable<BaseRespBean> delMemberCollect(@Field("tel") String tel,
                                              @Field("nonstr") String nonstr,
                                              @Field("relId") String relId,
                                              @Field("type") String type);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/showMemActPay")
    Observable<MemActPayBean> showMemActPay(@Field("tel") String tel,
                                            @Field("nonstr") String nonstr,
                                            @Field("pageNo") int pageNo);

    /**
     * ??????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param date
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogAppointmentIntellectList")
    Observable<AppointmentBean> getMemLogAppointmentIntellectList(@Field("tel") String tel,
                                                                  @Field("nonstr") String nonstr,
                                                                  @Field("date") String date);

    /**
     * ????????????????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId    ????????????id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getIntellectAppointList")
    Observable<FieldAppointListBean> getIntellectAppointList(@Field("tel") String tel,
                                                             @Field("nonstr") String nonstr,
                                                             @Field("fId") String fId);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @param type   ??????id???1.???????????? 2.???????????? 3.???????????? 4.???????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getNearIntellectPage")
    Observable<NearIntellectPageBean> getNearIntellectPage(@Field("tel") String tel,
                                                           @Field("nonstr") String nonstr,
                                                           @Field("pageNo") int pageNo,
                                                           @Field("type") String type,
                                                           @Field("proId") String proId,
                                                           @Field("cityId") String cityId,
                                                           @Field("disId") String disId,
                                                           @Field("strId") String strId,
                                                           @Field("brandName") String brandName,
                                                           @Field("subscribeState") String subscribeState);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getIntellectMsg")
    Observable<IntellectMsgBean> getIntellectMsg(@Field("tel") String tel,
                                                 @Field("nonstr") String nonstr,
                                                 @Field("id") String id);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getIntellectImgList")
    Observable<IntellectImgListBean> getIntellectImgList(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("id") String id);

    /**
     * ????????????????????????
     *
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getIntellectEqueList")
    Observable<IntellectEqueListBean> getIntellectEqueList(@Field("tel") String tel,
                                                           @Field("nonstr") String nonstr,
                                                           @Field("id") String id);

    /**
     * ???????????????????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId    ????????????id
     * @param date   ??????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAllMemLogAppointmentIntellect")
    Observable<AppointmentDateBean> getAllMemLogAppointmentIntellect(@Field("tel") String tel,
                                                                     @Field("nonstr") String nonstr,
                                                                     @Field("fId") String fId,
                                                                     @Field("date") String date);

    /**
     * ??????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param fId
     * @param date
     * @param aId    ????????????????????????
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/memDoLogAppointmentIntellect")
    Observable<BaseRespBean> memDoLogAppointmentIntellect(@Field("tel") String tel,
                                                          @Field("nonstr") String nonstr,
                                                          @Field("fId") String fId,
                                                          @Field("date") String date,
                                                          @Field("aId") String aId);

    /**
     * ????????????????????????????????????????????????
     *
     * @param tel
     * @param nonstr
     * @param proId
     * @param cityId
     * @param disId
     * @param streetId
     * @param yearTime ????????????2019
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getTaskTotalMsg")
    Observable<TaskTotalMsgBean> getTaskTotalMsg(@Field("tel") String tel,
                                                 @Field("nonstr") String nonstr,
                                                 @Field("proId") String proId,
                                                 @Field("cityId") String cityId,
                                                 @Field("disId") String disId,
                                                 @Field("streetId") String streetId,
                                                 @Field("yearTime") String yearTime);

    /**
     * ????????????????????????????????????
     * @param tel
     * @param nonstr
     * @param proId
     * @param cityId
     * @param disId
     * @param streetId
     * @param yearTime
     * @param state
     * @param pageNo
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAreaTaskPage")
    Observable<AreaTaskBean> getAreaTaskPage(@Field("tel") String tel,
                                             @Field("nonstr") String nonstr,
                                             @Field("proId") String proId,
                                             @Field("cityId") String cityId,
                                             @Field("disId") String disId,
                                             @Field("streetId") String streetId,
                                             @Field("yearTime") String yearTime,
                                             @Field("state") String state,
                                             @Field("pageNo") int pageNo);

    /**
     * ??????????????????????????????
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemberAreaMsg")
    Observable<MemberAreaMsgBean> getMemberAreaMsg(@Field("tel") String tel,
                                                   @Field("nonstr") String nonstr);
}
