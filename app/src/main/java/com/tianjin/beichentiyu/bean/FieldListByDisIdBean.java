package com.tianjin.beichentiyu.bean;

import java.util.List;

public class FieldListByDisIdBean extends BaseRespBean {

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
         * id : 1
         * fieldName : 荣溪园
         * buildTime : 2017
         * openTime : 全天
         * proId : 2
         * cityId : 2
         * disId : 31
         * strId : 7
         * cId : 5
         * vId : 7
         * address : 荣溪园
         * state : 0
         * content :
         * tel :
         * payState : 1
         * areaNum :
         * baseImg : http://47.111.154.197/img/six/p_15744757279408.jpg
         * typeId : 3
         * traffic :
         * lat : 39.217186
         * lng : 117.09503
         * subscribeState : 1
         * subscribePrice : 0.0
         * mindState : 0
         */

        private int serialVersionUID;
        private String id;
        private String fieldName;
        private String buildTime;
        private String openTime;
        private int proId;
        private int cityId;
        private String disId;
        private int strId;
        private int cId;
        private int vId;
        private String address;
        private String state;
        private String content;
        private String tel;
        private String payState;
        private String areaNum;
        private String baseImg;
        private int typeId;
        private String traffic;
        private double lat;
        private double lng;
        private String subscribeState;
        private double subscribePrice;
        private String mindState;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
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

        public String getDisId() {
            return disId;
        }

        public void setDisId(String disId) {
            this.disId = disId;
        }

        public int getStrId() {
            return strId;
        }

        public void setStrId(int strId) {
            this.strId = strId;
        }

        public int getCId() {
            return cId;
        }

        public void setCId(int cId) {
            this.cId = cId;
        }

        public int getVId() {
            return vId;
        }

        public void setVId(int vId) {
            this.vId = vId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPayState() {
            return payState;
        }

        public void setPayState(String payState) {
            this.payState = payState;
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

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getTraffic() {
            return traffic;
        }

        public void setTraffic(String traffic) {
            this.traffic = traffic;
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

        public String getSubscribeState() {
            return subscribeState;
        }

        public void setSubscribeState(String subscribeState) {
            this.subscribeState = subscribeState;
        }

        public double getSubscribePrice() {
            return subscribePrice;
        }

        public void setSubscribePrice(double subscribePrice) {
            this.subscribePrice = subscribePrice;
        }

        public String getMindState() {
            return mindState;
        }

        public void setMindState(String mindState) {
            this.mindState = mindState;
        }
    }
}
