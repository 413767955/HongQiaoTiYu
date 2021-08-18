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
 * Created by 黑瞳 on 2019/10/17 21:36
 * E-Mail Address：673236072@qq.com
 */
public interface BusinessService {
    /**
     * 上传图片
     *
     * @param imgPath
     * @return
     */
    @Multipart
    @POST("appSixEdge/addImg")
    Observable<UploadImageResultsBean> uploadImg(@Part MultipartBody.Part imgPath);

    /**
     * 活动列表接口
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
     * 文章列表
     *
     * @param tel
     * @param nonstr  随机码
     * @param type    类型： 1 体育新闻，2 平台公告, 3体育赛事, 4我的组织，5健身指导
     * @param seeType 排序：如果传入，值为“1” 按点击量从大到小排序
     * @param pageNo  页码
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
     * 文章详情
     *
     * @param tel
     * @param nonstr
     * @param aId    文章的主键id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getArtileMsg")
    Observable<ArtileMsgBean> getArtileMsg(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("aId") String aId);

    /**
     * 活动详情
     *
     * @param tel
     * @param nonstr
     * @param id     活动的主键id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getActivityMsg")
    Observable<ActivityMsgBean> getActivityMsg(@Field("tel") String tel,
                                               @Field("nonstr") String nonstr,
                                               @Field("id") String id);


    /**
     * 活动报名
     *
     * @param tel
     * @param nonstr
     * @param aId    活动主键
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/userEnrollActivity")
    Observable<BaseRespBean> userEnrollActivity(@Field("tel") String tel,
                                                @Field("nonstr") String nonstr,
                                                @Field("aId") String aId);

    /**
     * 场馆和智能健身列表
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @param type      场馆传参：类型id：1 体育馆，2 体育公园，3 健身园，4 街道文体中心， 5 游泳池，
     *                  6 社区健身中心， 7 笼式足球， 8笼式多功能运动场，9 其他场地
     * @param smartType 智能健身传参：只能场馆类型：1 智能步道， 2 智能球场，3 智能路径，4智能健身房
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
     * 智能健身页面或场馆页面推荐
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @param type   类型：1 场馆推荐 2 智能健身推荐
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getNearFieldPageHot")
    Observable<NearFieldPageHotBean> getNearFieldPageHot(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("pageNo") int pageNo,
                                                         @Field("type") String type);

    /**
     * 场地详情
     *
     * @param tel
     * @param nonstr
     * @param id     场地主键id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldMsg")
    Observable<FieldMsgBean> getFieldMsg(@Field("tel") String tel,
                                         @Field("nonstr") String nonstr,
                                         @Field("id") String id);

    /**
     * 场地图片
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
     * 场地器械
     *
     * @param tel
     * @param nonstr
     * @param id     场地主键id
     * @param type   0 可用， 1 维修中
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldEqueList")
    Observable<FieldEqueListBean> getFieldEqueList(@Field("tel") String tel,
                                                   @Field("nonstr") String nonstr,
                                                   @Field("id") String id,
                                                   @Field("type") String type);

    /**
     * 报修记录
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
     * 报修
     *
     * @param tel
     * @param nonstr
     * @param sfeId        场地器械表主键
     * @param repairImgOne 损坏图片1
     * @param repairImgTwo 损坏图片2
     * @param content      损坏描述
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
     * 获得场馆的预约时间段列表
     *
     * @param tel
     * @param nonstr
     * @param fId    场馆主键id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getFieldAppointList")
    Observable<FieldAppointListBean> getFieldAppointList(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("fId") String fId);

    /**
     * 获得指定场馆某日的预约记录
     *
     * @param tel
     * @param nonstr
     * @param fId    场馆主键id
     * @param date   日期
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAllMemLogAppointment")
    Observable<AppointmentDateBean> getAllMemLogAppointment(@Field("tel") String tel,
                                                            @Field("nonstr") String nonstr,
                                                            @Field("fId") String fId,
                                                            @Field("date") String date);

    /**
     * 场馆预约操作
     *
     * @param tel
     * @param nonstr
     * @param fId
     * @param date
     * @param aId    预约时间段表主键
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
     * 获得体育组织列表
     *
     * @param tel
     * @param nonstr
     * @param type   类型：1协会 2俱乐部
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getOrganizationList")
    Observable<OrganizationBean> getOrganizationList(@Field("tel") String tel,
                                                     @Field("nonstr") String nonstr,
                                                     @Field("type") String type);

    /**
     * 获得体育组织详情
     *
     * @param tel
     * @param nonstr
     * @param orgId  组织id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getOrganizationMsg")
    Observable<OrganizationInfoBean> getOrganizationMsg(@Field("tel") String tel,
                                                        @Field("nonstr") String nonstr,
                                                        @Field("orgId") String orgId);

    /**
     * 获得体育组织的全部人员名单
     *
     * @param tel
     * @param nonstr
     * @param orgId  组织id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getOrganizationDutiesList")
    Observable<OrganizationDutiesBean> getOrganizationDutiesList(@Field("tel") String tel,
                                                                 @Field("nonstr") String nonstr,
                                                                 @Field("orgId") String orgId);

    /**
     * 获取轮播图
     *
     * @param tel
     * @param nonstr
     * @param type   1：智能健身 2：智能体侧
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAppIndexList")
    Observable<AppIndexBean> getAppIndexList(@Field("tel") String tel,
                                             @Field("nonstr") String nonstr,
                                             @Field("type") String type);

    /**
     * 获取收藏列表记录
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
     * 获取场馆预约记录
     *
     * @param tel
     * @param nonstr
     * @param date   时间
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogAppointmentList")
    Observable<AppointmentBean> getMemLogAppointmentList(@Field("tel") String tel,
                                                         @Field("nonstr") String nonstr,
                                                         @Field("date") String date);

    /**
     * 获取体侧预约记录
     *
     * @param tel
     * @param nonstr
     * @param date   时间
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogAppointmentPhysicalList")
    Observable<AppointmentPhysicalBean> getMemLogAppointmentPhysicalList(@Field("tel") String tel,
                                                                         @Field("nonstr") String nonstr,
                                                                         @Field("date") String date);

    /**
     * 获取体侧中心列表
     *
     * @param tel
     * @param nonstr
     * @param pageNo 页数
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
     * 获取体测中心详情
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
     * 体测中心图片接口
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
     * 获得场馆的预约时间段列表
     *
     * @param tel
     * @param nonstr
     * @param fId    场馆主键id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getPhysicalAppointList")
    Observable<FieldAppointListBean> getPhysicalAppointList(@Field("tel") String tel,
                                                            @Field("nonstr") String nonstr,
                                                            @Field("fId") int fId);

    /**
     * 获得指定场馆某日的预约记录
     *
     * @param tel
     * @param nonstr
     * @param fId    场馆主键id
     * @param date   日期
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAllMemLogPhyAppointment")
    Observable<AppointmentDateBean> getAllMemLogPhyAppointment(@Field("tel") String tel,
                                                               @Field("nonstr") String nonstr,
                                                               @Field("fId") int fId,
                                                               @Field("date") String date);

    /**
     * 场馆预约操作
     *
     * @param tel
     * @param nonstr
     * @param fId
     * @param date
     * @param aId    预约时间段表主键
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
     * 获得任务列表接口（包含临时任务和季度任务）
     *
     * @param tel
     * @param nonstr
     * @param type   1临时任务  2季度任务
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemLogTaskList")
    Observable<TaskBean> getMemLogTaskList(@Field("tel") String tel,
                                           @Field("nonstr") String nonstr,
                                           @Field("type") int type,
                                           @Field("pageNo") int pageNo);

    /**
     * 获得任务详情接口（包含临时任务和季度任务）
     *
     * @param tel
     * @param nonstr
     * @param logId  任务id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getLogTaskMsg")
    Observable<LogTaskMsg> getLogTaskMsg(@Field("tel") String tel,
                                         @Field("nonstr") String nonstr,
                                         @Field("logId") String logId);

    /**
     * 任务结果反馈接口（包含临时任务和季度任务）
     *
     * @param tel
     * @param nonstr
     * @param logId
     * @param state        0:未操作  1：已修复/已巡检  3.报废处理
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
     * 根据区域id获得场馆列表
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
     * 根据场馆名称获得场馆列表
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
     * 收藏操作接口
     *
     * @param tel
     * @param nonstr
     * @param relId
     * @param type   1:场馆；2：体侧中心；3：文章；4：赛事新闻
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/doMemberCollect")
    Observable<BaseRespBean> doMemberCollect(@Field("tel") String tel,
                                             @Field("nonstr") String nonstr,
                                             @Field("relId") String relId,
                                             @Field("type") String type);

    /**
     * 取消收藏
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
     * 会员报名列表接口
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
     * 智能场馆预约记录接口
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
     * 获得智能健身场馆的预约时间段列表
     *
     * @param tel
     * @param nonstr
     * @param fId    场馆主键id
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getIntellectAppointList")
    Observable<FieldAppointListBean> getIntellectAppointList(@Field("tel") String tel,
                                                             @Field("nonstr") String nonstr,
                                                             @Field("fId") String fId);

    /**
     * 智能健身列表接口
     *
     * @param tel
     * @param nonstr
     * @param pageNo
     * @param type   类型id：1.智能步道 2.智能球场 3.智能路径 4.智能健身房
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
     * 智能健身详情接口
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
     * 智能健身图片接口
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
     * 场馆器械列表接口
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
     * 获得指定智能健身场馆某日的预约记录
     *
     * @param tel
     * @param nonstr
     * @param fId    场馆主键id
     * @param date   日期
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getAllMemLogAppointmentIntellect")
    Observable<AppointmentDateBean> getAllMemLogAppointmentIntellect(@Field("tel") String tel,
                                                                     @Field("nonstr") String nonstr,
                                                                     @Field("fId") String fId,
                                                                     @Field("date") String date);

    /**
     * 智能健身场馆预约操作
     *
     * @param tel
     * @param nonstr
     * @param fId
     * @param date
     * @param aId    预约时间段表主键
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
     * 筛选获得区域年份任务统计信息接口
     *
     * @param tel
     * @param nonstr
     * @param proId
     * @param cityId
     * @param disId
     * @param streetId
     * @param yearTime 年份，如2019
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
     * 获得区域年度任务列表接口
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
     * 获得会员区域权限接口
     * @param tel
     * @param nonstr
     * @return
     */
    @FormUrlEncoded
    @POST("appSixEdge/getMemberAreaMsg")
    Observable<MemberAreaMsgBean> getMemberAreaMsg(@Field("tel") String tel,
                                                   @Field("nonstr") String nonstr);
}
