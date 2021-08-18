package com.tianjin.beichentiyu.bean;

import com.google.gson.annotations.SerializedName;

public class LogTaskMsg extends BaseRespBean {


    /**
     * logT : {"bId":1,"buildTime":"2019-12-03T15:21:47.000","endTime":null,"fieldAddress":"荣溪园","fieldId":1,"genTime":"2019-12-03T15:21:41.000","logId":"1","mId":"1","msg":"检修","repairId":"1","showImgOne":null,"showImgTwo":null,"state":"0","taskType":"1","type":"1"}
     * field : {"address":"荣溪园","areaNum":"","baseImg":"http://47.111.154.197/img/six/p_15744757279408.jpg","buildTime":"2017","cId":5,"cityId":2,"content":"","disId":31,"fieldName":"荣溪园","id":1,"lat":39.217186,"lng":117.09503,"mindState":"0","openTime":"全天","payState":"1","proId":2,"state":"0","strId":7,"subscribePrice":0,"subscribeState":"1","tel":"","traffic":"","typeId":3,"vId":7}
     * logR : {"content":null,"doneTime":null,"eId":1,"fId":1,"genTime":"2018-07-16T23:43:14.000","logId":"1","repairImgOne":"http://lin1.csttyg.com:1805/img/reqair/r_15317557932536.jpg","repairImgTwo":null,"state":"0","userId":"1"}
     * eName : 跑步机
     */

    private LogTBean logT;
    private FieldBean field;
    private LogRBean logR;
    private String eName;

    public LogTBean getLogT() {
        return logT;
    }

    public void setLogT(LogTBean logT) {
        this.logT = logT;
    }

    public FieldBean getField() {
        return field;
    }

    public void setField(FieldBean field) {
        this.field = field;
    }

    public LogRBean getLogR() {
        return logR;
    }

    public void setLogR(LogRBean logR) {
        this.logR = logR;
    }

    public String getEName() {
        return eName;
    }

    public void setEName(String eName) {
        this.eName = eName;
    }

    public static class LogTBean {
        /**
         * bId : 1
         * buildTime : 2019-12-03T15:21:47.000
         * endTime : null
         * fieldAddress : 荣溪园
         * fieldId : 1
         * genTime : 2019-12-03T15:21:41.000
         * logId : 1
         * mId : 1
         * msg : 检修
         * repairId : 1
         * showImgOne : null
         * showImgTwo : null
         * state : 0
         * taskType : 1
         * type : 1
         */

        private String bId;
        private String buildTime;
        private Object endTime;
        private String fieldAddress;
        private String fieldId;
        private String genTime;
        private String logId;
        private String mId;
        @SerializedName("msg")
        private String msgX;
        private String repairId;
        private String showImgOne;
        private String showImgTwo;
        private String state;
        private String taskType;
        private String type;

        public String getBId() {
            return bId;
        }

        public void setBId(String bId) {
            this.bId = bId;
        }

        public String getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public String getFieldAddress() {
            return fieldAddress;
        }

        public void setFieldAddress(String fieldAddress) {
            this.fieldAddress = fieldAddress;
        }

        public String getFieldId() {
            return fieldId;
        }

        public void setFieldId(String fieldId) {
            this.fieldId = fieldId;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getMId() {
            return mId;
        }

        public void setMId(String mId) {
            this.mId = mId;
        }

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }

        public String getRepairId() {
            return repairId;
        }

        public void setRepairId(String repairId) {
            this.repairId = repairId;
        }

        public String getShowImgOne() {
            return showImgOne;
        }

        public void setShowImgOne(String showImgOne) {
            this.showImgOne = showImgOne;
        }

        public String getShowImgTwo() {
            return showImgTwo;
        }

        public void setShowImgTwo(String showImgTwo) {
            this.showImgTwo = showImgTwo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTaskType() {
            return taskType;
        }

        public void setTaskType(String taskType) {
            this.taskType = taskType;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class FieldBean {
        /**
         * address : 荣溪园
         * areaNum :
         * baseImg : http://47.111.154.197/img/six/p_15744757279408.jpg
         * buildTime : 2017
         * cId : 5
         * cityId : 2
         * content :
         * disId : 31
         * fieldName : 荣溪园
         * id : 1
         * lat : 39.217186
         * lng : 117.09503
         * mindState : 0
         * openTime : 全天
         * payState : 1
         * proId : 2
         * state : 0
         * strId : 7
         * subscribePrice : 0.0
         * subscribeState : 1
         * tel :
         * traffic :
         * typeId : 3
         * vId : 7
         */

        private String address;
        private String areaNum;
        private String baseImg;
        private String buildTime;
        private int cId;
        private int cityId;
        private String content;
        private int disId;
        private String fieldName;
        private int id;
        private double lat;
        private double lng;
        private String mindState;
        private String openTime;
        private String payState;
        private int proId;
        private String state;
        private int strId;
        private double subscribePrice;
        private String subscribeState;
        private String tel;
        private String traffic;
        private int typeId;
        private int vId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaNum() {
            return areaNum;
        }

        public void setAreaNum(String areaNum) {
            this.areaNum = areaNum;
        }

        public String getBaseImg() {
            return baseImg;
        }

        public void setBaseImg(String baseImg) {
            this.baseImg = baseImg;
        }

        public String getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        public int getCId() {
            return cId;
        }

        public void setCId(int cId) {
            this.cId = cId;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDisId() {
            return disId;
        }

        public void setDisId(int disId) {
            this.disId = disId;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public String getMindState() {
            return mindState;
        }

        public void setMindState(String mindState) {
            this.mindState = mindState;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getStrId() {
            return strId;
        }

        public void setStrId(int strId) {
            this.strId = strId;
        }

        public double getSubscribePrice() {
            return subscribePrice;
        }

        public void setSubscribePrice(double subscribePrice) {
            this.subscribePrice = subscribePrice;
        }

        public String getSubscribeState() {
            return subscribeState;
        }

        public void setSubscribeState(String subscribeState) {
            this.subscribeState = subscribeState;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getVId() {
            return vId;
        }

        public void setVId(int vId) {
            this.vId = vId;
        }
    }

    public static class LogRBean {
        /**
         * content : null
         * doneTime : null
         * eId : 1
         * fId : 1
         * genTime : 2018-07-16T23:43:14.000
         * logId : 1
         * repairImgOne : http://lin1.csttyg.com:1805/img/reqair/r_15317557932536.jpg
         * repairImgTwo : null
         * state : 0
         * userId : 1
         */

        private String content;
        private String doneTime;
        private int eId;
        private int fId;
        private String genTime;
        private String logId;
        private String repairImgOne;
        private Object repairImgTwo;
        private String state;
        private String userId;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDoneTime() {
            return doneTime;
        }

        public void setDoneTime(String doneTime) {
            this.doneTime = doneTime;
        }

        public int getEId() {
            return eId;
        }

        public void setEId(int eId) {
            this.eId = eId;
        }

        public int getFId() {
            return fId;
        }

        public void setFId(int fId) {
            this.fId = fId;
        }

        public String getGenTime() {
            return genTime;
        }

        public void setGenTime(String genTime) {
            this.genTime = genTime;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public String getRepairImgOne() {
            return repairImgOne;
        }

        public void setRepairImgOne(String repairImgOne) {
            this.repairImgOne = repairImgOne;
        }

        public Object getRepairImgTwo() {
            return repairImgTwo;
        }

        public void setRepairImgTwo(Object repairImgTwo) {
            this.repairImgTwo = repairImgTwo;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
