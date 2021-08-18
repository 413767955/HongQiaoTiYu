package com.tianjin.beichentiyu.bean;

import java.util.List;

public class NearFieldPageBean extends BaseRespBean {


    /**
     * code : 99999
     * pageNo : 1
     * list : [{"distance":6198604.1,"map":{"pay_state":"0","area_num":"50","address":"西大望路甲12号国家广告产业园","lng":"116.48014000","type_id":"1","build_time":"123","open_time":"123","mind_state":"0","subscribe_price":"50.00","dis_id":"1","content":"这个测试","field_name":"测试","pro_id":"1","subscribe_state":"0","tel":"1234567890","id":"18","state":"0","str_id":"1","base_img":"null","lat":"39.90932500","city_id":"1","traffic":"公交11路"}}]
     */

    private String pageNo;
    private List<ListBean> list;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * distance : 6198604.1
         * map : {"pay_state":"0","area_num":"50","address":"西大望路甲12号国家广告产业园","lng":"116.48014000","type_id":"1","build_time":"123","open_time":"123","mind_state":"0","subscribe_price":"50.00","dis_id":"1","content":"这个测试","field_name":"测试","pro_id":"1","subscribe_state":"0","tel":"1234567890","id":"18","state":"0","str_id":"1","base_img":"null","lat":"39.90932500","city_id":"1","traffic":"公交11路"}
         */

        private String distance;
        private MapBean map;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public MapBean getMap() {
            return map;
        }

        public void setMap(MapBean map) {
            this.map = map;
        }

        public static class MapBean {
            /**
             * pay_state : 0
             * area_num : 50
             * address : 西大望路甲12号国家广告产业园
             * lng : 116.48014000
             * type_id : 1
             * build_time : 123
             * open_time : 123
             * mind_state : 0
             * subscribe_price : 50.00
             * dis_id : 1
             * content : 这个测试
             * field_name : 测试
             * pro_id : 1
             * subscribe_state : 0
             * tel : 1234567890
             * id : 18
             * state : 0
             * str_id : 1
             * base_img : null
             * lat : 39.90932500
             * city_id : 1
             * traffic : 公交11路
             */

            private String pay_state;
            private String area_num;
            private String address;
            private String lng;
            private String type_id;
            private String build_time;
            private String open_time;
            private String mind_state;
            private String subscribe_price;
            private String dis_id;
            private String content;
            private String field_name;
            private String pro_id;
            private String subscribe_state;
            private String tel;
            private String id;
            private String state;
            private String str_id;
            private String base_img;
            private String lat;
            private String city_id;
            private String traffic;

            public String getPay_state() {
                return pay_state;
            }

            public void setPay_state(String pay_state) {
                this.pay_state = pay_state;
            }

            public String getArea_num() {
                return area_num;
            }

            public void setArea_num(String area_num) {
                this.area_num = area_num;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getBuild_time() {
                return build_time;
            }

            public void setBuild_time(String build_time) {
                this.build_time = build_time;
            }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }

            public String getMind_state() {
                return mind_state;
            }

            public void setMind_state(String mind_state) {
                this.mind_state = mind_state;
            }

            public String getSubscribe_price() {
                return subscribe_price;
            }

            public void setSubscribe_price(String subscribe_price) {
                this.subscribe_price = subscribe_price;
            }

            public String getDis_id() {
                return dis_id;
            }

            public void setDis_id(String dis_id) {
                this.dis_id = dis_id;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getField_name() {
                return field_name;
            }

            public void setField_name(String field_name) {
                this.field_name = field_name;
            }

            public String getPro_id() {
                return pro_id;
            }

            public void setPro_id(String pro_id) {
                this.pro_id = pro_id;
            }

            public String getSubscribe_state() {
                return subscribe_state;
            }

            public void setSubscribe_state(String subscribe_state) {
                this.subscribe_state = subscribe_state;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getStr_id() {
                return str_id;
            }

            public void setStr_id(String str_id) {
                this.str_id = str_id;
            }

            public String getBase_img() {
                return base_img;
            }

            public void setBase_img(String base_img) {
                this.base_img = base_img;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getTraffic() {
                return traffic;
            }

            public void setTraffic(String traffic) {
                this.traffic = traffic;
            }
        }
    }
}
