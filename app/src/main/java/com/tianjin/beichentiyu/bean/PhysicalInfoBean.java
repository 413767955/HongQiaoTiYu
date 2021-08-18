package com.tianjin.beichentiyu.bean;

import java.io.Serializable;

public class PhysicalInfoBean extends BaseRespBean{

    /**
     * sp : {"address":"杨桥西路155号","baseImg":"http://lin1.csttyg.com:1805/img/field/f_15317505738585.jpg","buildTime":"2018-06-16","cityId":1,"content":"测试中心","disId":1,"id":1,"lat":39.934826,"lng":116.4224,"openTime":"全天","phyName":"人民医院","proId":1,"state":"0","strId":1,"subscribePrice":45,"tel":"123456789"}
     */

    private SpBean sp;
    private String collectState;

    public String getCollectState() {
        return collectState;
    }

    public void setCollectState(String collectState) {
        this.collectState = collectState;
    }

    public SpBean getSp() {
        return sp;
    }

    public void setSp(SpBean sp) {
        this.sp = sp;
    }

    public static class SpBean implements Serializable {
        /**
         * address : 杨桥西路155号
         * baseImg : http://lin1.csttyg.com:1805/img/field/f_15317505738585.jpg
         * buildTime : 2018-06-16
         * cityId : 1
         * content : 测试中心
         * disId : 1
         * id : 1
         * lat : 39.934826
         * lng : 116.4224
         * openTime : 全天
         * phyName : 人民医院
         * proId : 1
         * state : 0
         * strId : 1
         * subscribePrice : 45
         * tel : 123456789
         */

        private String address;
        private String baseImg;
        private String buildTime;
        private int cityId;
        private String content;
        private int disId;
        private int id;
        private double lat;
        private double lng;
        private String openTime;
        private String phyName;
        private int proId;
        private String state;
        private int strId;
        private String subscribePrice;
        private String tel;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getPhyName() {
            return phyName;
        }

        public void setPhyName(String phyName) {
            this.phyName = phyName;
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

        public String getSubscribePrice() {
            return subscribePrice;
        }

        public void setSubscribePrice(String subscribePrice) {
            this.subscribePrice = subscribePrice;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
