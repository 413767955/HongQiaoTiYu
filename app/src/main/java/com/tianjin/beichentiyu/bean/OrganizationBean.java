package com.tianjin.beichentiyu.bean;

import java.util.List;

public class OrganizationBean extends BaseRespBean{

    /**
     * pageNo : 1
     * list : [{"serialVersionUID":1,"orgId":"1","orgName":"足球协会","orgLogo":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1108709759,2393227950&fm=26&gp=0.jpg","proId":2,"cityId":2,"buildTime":null,"mainMan":"统一规化","bigOrg":"天津体育总局","committee":"主席、副主席","message":"福州市足球协会地址设在海峡西岸经济区政治、经济、文化、科研中心，福建省会福州，福建 福州 东升路１７号（法定代表人：陈华俤），联系人是陈华俤，主要经营 培训 、竞赛和接待活动等，于1989年1月1日在福州工商局登记注册挂牌成立，单位注册资本3（万元），在职员工10名","tel":"3445124","type":"1"}]
     */

    private String pageNo;
    private List<ListBean> list;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * serialVersionUID : 1
         * orgId : 1
         * orgName : 足球协会
         * orgLogo : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1108709759,2393227950&fm=26&gp=0.jpg
         * proId : 2
         * cityId : 2
         * buildTime : null
         * mainMan : 统一规化
         * bigOrg : 天津体育总局
         * committee : 主席、副主席
         * message : 福州市足球协会地址设在海峡西岸经济区政治、经济、文化、科研中心，福建省会福州，福建 福州 东升路１７号（法定代表人：陈华俤），联系人是陈华俤，主要经营 培训 、竞赛和接待活动等，于1989年1月1日在福州工商局登记注册挂牌成立，单位注册资本3（万元），在职员工10名
         * tel : 3445124
         * type : 1
         */

        private int serialVersionUID;
        private String orgId;
        private String orgName;
        private String orgLogo;
        private int proId;
        private int cityId;
        private Object buildTime;
        private String mainMan;
        private String bigOrg;
        private String committee;
        private String message;
        private String tel;
        private String type;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgLogo() {
            return orgLogo;
        }

        public void setOrgLogo(String orgLogo) {
            this.orgLogo = orgLogo;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public Object getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(Object buildTime) {
            this.buildTime = buildTime;
        }

        public String getMainMan() {
            return mainMan;
        }

        public void setMainMan(String mainMan) {
            this.mainMan = mainMan;
        }

        public String getBigOrg() {
            return bigOrg;
        }

        public void setBigOrg(String bigOrg) {
            this.bigOrg = bigOrg;
        }

        public String getCommittee() {
            return committee;
        }

        public void setCommittee(String committee) {
            this.committee = committee;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
