package com.tianjin.beichentiyu.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wjy on 2020/1/14
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class IntellectMsgBean extends BaseRespBean{


    /**
     * distance : 1.2306902060048373E7
     * field : {"address":"天津市和平区包头道5号","areaNum":"1236","baseImg":"http://47.111.177.130/img/six/p_15757001676780.jpg","buildTime":"2000","cId":null,"cityId":2,"content":"","disId":19,"fieldName":"aaa","id":2,"lat":39.131664,"lng":117.19481,"mindState":"0","openTime":"00","payState":"0","proId":2,"state":"0","strId":17,"subscribePrice":123,"subscribeState":"1","tel":"123456","traffic":"地铁","typeId":1,"vId":null}
     * collectState : 0
     * suppleList : [{"id":"","key_name":"","key_value":"","field_id":"","state":""}]
     */

    private String distance;
    private FieldBean field;
    private String collectState;
    private List<SuppleListBean> suppleList;//备用合集

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
         * address : 天津市和平区包头道5号
         * areaNum : 1236
         * baseImg : http://47.111.177.130/img/six/p_15757001676780.jpg
         * buildTime : 2000
         * cId : null
         * cityId : 2
         * content :
         * disId : 19
         * fieldName : aaa
         * id : 2
         * lat : 39.131664
         * lng : 117.19481
         * mindState : 0
         * openTime : 00
         * payState : 0
         * proId : 2
         * state : 0
         * strId : 17
         * subscribePrice : 123.0
         * subscribeState : 1
         * tel : 123456
         * traffic : 地铁
         * typeId : 1
         * vId : null
         */

        private String address;
        private String areaNum;
        private String baseImg;
        private String buildTime;
        private Object cId;
        private int cityId;
        private String content;
        private int disId;
        private String fieldName;
        private String id;
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
        private Object vId;

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

        public Object getCId() {
            return cId;
        }

        public void setCId(Object cId) {
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

        public Object getVId() {
            return vId;
        }

        public void setVId(Object vId) {
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
