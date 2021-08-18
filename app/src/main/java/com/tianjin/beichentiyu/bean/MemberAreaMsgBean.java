package com.tianjin.beichentiyu.bean;

public class MemberAreaMsgBean extends BaseRespBean {

    /**
     * roleMsg : 省级权限：天津市
     * list : {"areaId":2,"genTime":"2020-05-26T16:59:52.000","id":2,"mId":"156084764834715","type":"1"}
     */

    private String roleMsg;
    private ListBean list;

    public String getRoleMsg() {
        return roleMsg;
    }

    public void setRoleMsg(String roleMsg) {
        this.roleMsg = roleMsg;
    }

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * areaId : 2
         * genTime : 2020-05-26T16:59:52.000
         * id : 2
         * mId : 156084764834715
         * type : 1
         */

        private String areaId;
        private String genTime;
        private String id;
        private String mId;
        private String type;

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMId() {
            return mId;
        }

        public void setMId(String mId) {
            this.mId = mId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
