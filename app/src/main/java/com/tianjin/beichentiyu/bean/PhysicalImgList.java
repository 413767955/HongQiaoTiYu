package com.tianjin.beichentiyu.bean;

import java.util.List;

public class PhysicalImgList extends BaseRespBean{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * serialVersionUID : 1
         * logId : 157276804831947
         * pId : 2
         * imgUrl : http://47.111.177.130/img/six/p_15727680482364.jpg
         * gradeNum : 1
         * state : 0
         */

        private String serialVersionUID;
        private String logId;
        private String pId;
        private String imgUrl;
        private String gradeNum;
        private String state;

        public String getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(String serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getGradeNum() {
            return gradeNum;
        }

        public void setGradeNum(String gradeNum) {
            this.gradeNum = gradeNum;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
