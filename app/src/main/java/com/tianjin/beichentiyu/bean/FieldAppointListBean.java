package com.tianjin.beichentiyu.bean;

import java.util.List;

public class FieldAppointListBean extends BaseRespBean{

    /**
     * code : 99999
     * list : [{"serialVersionUID":1,"logId":"157352589343886","timeSlot":"9:00-9:30","state":"0","gradeNum":1,"genTime":"2019-11-12 10:31:33","fId":1},{"serialVersionUID":1,"logId":"157352590833018","timeSlot":"8:00-8:30","state":"0","gradeNum":1,"genTime":"2019-11-12 10:31:48","fId":1}]
     */

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
         * logId : 157352589343886
         * timeSlot : 9:00-9:30
         * state : 0
         * gradeNum : 1
         * genTime : 2019-11-12 10:31:33
         * fId : 1
         */

        private int serialVersionUID;
        private String logId;
        private String timeSlot;
        private boolean subscribe;
        private int gradeNum;
        private String genTime;
        private int fId;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
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

        public boolean isSubscribe() {
            return subscribe;
        }

        public void setSubscribe(boolean subscribe) {
            this.subscribe = subscribe;
        }

        public int getGradeNum() {
            return gradeNum;
        }

        public void setGradeNum(int gradeNum) {
            this.gradeNum = gradeNum;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public int getFId() {
            return fId;
        }

        public void setFId(int fId) {
            this.fId = fId;
        }
    }
}
