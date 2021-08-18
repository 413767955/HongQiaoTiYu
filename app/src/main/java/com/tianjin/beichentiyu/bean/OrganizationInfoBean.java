package com.tianjin.beichentiyu.bean;

import java.util.List;

public class OrganizationInfoBean extends BaseRespBean{

    /**
     * suppleList : [{"id":"","key_name":"","key_value":"","field_id":"","state":""}]
     * organ : {"bigOrg":"天津体育总局","buildTime":"2019-11-08T01:27:22.000","cityId":2,"committee":"主席、副主席","mainMan":"漂流运动","message":"2010年5月1日（劳动节）由陈胜杰发起，最初以汕头五人组为雏形建立而成。以汕头市区为活动的中心，建立起潮阳，澄海，潮州等分队，现有会员多达200余人，主要以青年为主，但不乏儿童及中年人，并以较快的速度不断发展。轮滑运动有100多年的历史，而漂移板运动进入我国才短短几年，特别是潮汕地区，漂移板运动的发展滞后于全国，我们从零开始，我们为漂移板运动在国内的发展不断努力着。我们的口号是：闪漂，闪漂，得意的漂！","orgId":"2","orgLogo":"https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1799130906,3133933929&fm=26&gp=0.jpg","orgName":"闪漂俱乐部","proId":2,"tel":"13480980993","type":"2"}
     */

    private OrganBean organ;
    private String proName;
    private String cityName;
    private List<SuppleListBean> suppleList;//备用合集

    public OrganBean getOrgan() {
        return organ;
    }

    public void setOrgan(OrganBean organ) {
        this.organ = organ;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<SuppleListBean> getSuppleList() {
        return suppleList;
    }

    public void setSuppleList(List<SuppleListBean> suppleList) {
        this.suppleList = suppleList;
    }

    public static class OrganBean {
        /**
         * bigOrg : 天津体育总局
         * buildTime : 2019-11-08T01:27:22.000
         * cityId : 2
         * committee : 主席、副主席
         * mainMan : 漂流运动
         * message : 2010年5月1日（劳动节）由陈胜杰发起，最初以汕头五人组为雏形建立而成。以汕头市区为活动的中心，建立起潮阳，澄海，潮州等分队，现有会员多达200余人，主要以青年为主，但不乏儿童及中年人，并以较快的速度不断发展。轮滑运动有100多年的历史，而漂移板运动进入我国才短短几年，特别是潮汕地区，漂移板运动的发展滞后于全国，我们从零开始，我们为漂移板运动在国内的发展不断努力着。我们的口号是：闪漂，闪漂，得意的漂！
         * orgId : 2
         * orgLogo : https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1799130906,3133933929&fm=26&gp=0.jpg
         * orgName : 闪漂俱乐部
         * proId : 2
         * tel : 13480980993
         * type : 2
         */

        private String bigOrg;
        private String buildTime;
        private int cityId;
        private String committee;
        private String mainMan;
        private String message;
        private String orgId;
        private String orgLogo;
        private String orgName;
        private int proId;
        private String tel;
        private String type;

        public String getBigOrg() {
            return bigOrg;
        }

        public void setBigOrg(String bigOrg) {
            this.bigOrg = bigOrg;
        }

        public String getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCommittee() {
            return committee;
        }

        public void setCommittee(String committee) {
            this.committee = committee;
        }

        public String getMainMan() {
            return mainMan;
        }

        public void setMainMan(String mainMan) {
            this.mainMan = mainMan;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgLogo() {
            return orgLogo;
        }

        public void setOrgLogo(String orgLogo) {
            this.orgLogo = orgLogo;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
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
    public static class SuppleListBean {
        /**
         * id :
         * key_name :
         * key_value :
         * field_id :
         * state :
         */

        private String id;
        private String key_name;
        private String key_value;
        private String field_id;
        private String state;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey_name() {
            return key_name;
        }

        public void setKey_name(String key_name) {
            this.key_name = key_name;
        }

        public String getKey_value() {
            return key_value;
        }

        public void setKey_value(String key_value) {
            this.key_value = key_value;
        }

        public String getField_id() {
            return field_id;
        }

        public void setField_id(String field_id) {
            this.field_id = field_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
