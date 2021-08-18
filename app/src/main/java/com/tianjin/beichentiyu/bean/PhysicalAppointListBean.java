package com.tianjin.beichentiyu.bean;

import java.util.List;

public class PhysicalAppointListBean extends BaseRespBean {

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
         * logId : 157375063502680
         * timeSlot : 8:30-9:00
         * state : 0
         * gradeNum : 1
         * genTime : 2019-11-15 00:57:15
         * pId : 2
         */

        private String serialVersionUID;
        private String logId;
        private String timeSlot;
        private String state;
        private String gradeNum;
        private String genTime;
        private String pId;

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

        public String getTimeSlot() {
            return timeSlot;
        }

        public void setTimeSlot(String timeSlot) {
            this.timeSlot = timeSlot;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getGradeNum() {
            return gradeNum;
        }

        public void setGradeNum(String gradeNum) {
            this.gradeNum = gradeNum;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }
    }
}
