package com.tianjin.beichentiyu.bean;

import java.io.Serializable;
import java.util.List;

public class FieldMsgBean extends BaseRespBean {

    /**
     * distance : 1467530.1353600824
     * suppleList : [{"id":"","key_name":"","key_value":"","field_id":"","state":""}]
     * field : {"address":"普东新苑","areaNum":"","baseImg":null,"buildTime":"","cId":23,"cityId":2,"content":"","disId":31,"fieldName":"普东新苑","id":38,"lat":39.19505,"lng":117.206764,"mindState":"0","openTime":"","payState":"1","proId":2,"state":"0","strId":4,"subscribePrice":null,"subscribeState":"0","tel":"","traffic":"","typeId":3,"vId":38}
     * collectState : 1
     */

    private String distance;
    private FieldBean field;
    private String collectState;
    private List<SuppleListBean> suppleList;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public FieldBean getField() {
        return field;
    }

    public void setField(FieldBean field) {
        this.field = field;
    }

    public String getCollectState() {
        return collectState;
    }

    public void setCollectState(String collectState) {
        this.collectState = collectState;
    }

    public List<SuppleListBean> getSuppleList() {
        return suppleList;
    }

    public void setSuppleList(List<SuppleListBean> suppleList) {
        this.suppleList = suppleList;
    }

    public static class FieldBean implements Serializable {
        /**
         * address : 普东新苑
         * areaNum :
         * baseImg : null
         * buildTime :
         * cId : 23
         * cityId : 2
         * content :
         * disId : 31
         * fieldName : 普东新苑
         * id : 38
         * lat : 39.19505
         * lng : 117.206764
         * mindState : 0
         * openTime :
         * payState : 1
         * proId : 2
         * state : 0
         * strId : 4
         * subscribePrice : null
         * subscribeState : 0
         * tel :
         * traffic :
         * typeId : 3
         * vId : 38
         */

        private String address;
        private String areaNum;
        private String baseImg;
        private String buildTime;
        private String cId;
        private String cityId;
        private String content;
        private String disId;
        private String fieldName;
        private String id;
        private double lat;
        private double lng;
        private String mindState;
        private String openTime;
        private String payState;
        private String proId;
        private String state;
        private String strId;
        private String subscribePrice;
        private String subscribeState;
        private String tel;
        private String traffic;
        private String typeId;
        private String vId;

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

        public String getBuildTime() {
            return buildTime;
        }

        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        public String getCId() {
            return cId;
        }

        public void setCId(String cId) {
            this.cId = cId;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDisId() {
            return disId;
        }

        public void setDisId(String disId) {
            this.disId = disId;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
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

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStrId() {
            return strId;
        }

        public void setStrId(String strId) {
            this.strId = strId;
        }

        public String getBaseImg() {
            return baseImg;
        }

        public void setBaseImg(String baseImg) {
            this.baseImg = baseImg;
        }

        public String getcId() {
            return cId;
        }

        public void setcId(String cId) {
            this.cId = cId;
        }

        public String getSubscribePrice() {
            return subscribePrice;
        }

        public void setSubscribePrice(String subscribePrice) {
            this.subscribePrice = subscribePrice;
        }

        public String getvId() {
            return vId;
        }

        public void setvId(String vId) {
            this.vId = vId;
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

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getVId() {
            return vId;
        }

        public void setVId(String vId) {
            this.vId = vId;
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
