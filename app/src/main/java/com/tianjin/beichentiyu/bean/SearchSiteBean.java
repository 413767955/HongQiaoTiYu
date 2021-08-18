package com.tianjin.beichentiyu.bean;

import java.util.List;

public class SearchSiteBean extends BaseRespBean{

    /**
     * pageNo : 1
     * list : [{"distance":1460695.9,"map":{"pay_state":"0","type_name":"体育馆","build_time":"2000","open_time":"00","mind_state":"0","subscribe_price":"123.00","dis_id":"19","content":"","subscribe_state":"1","c_id":"null","tel":"123456","id":"2","state":"0","lat":"39.13166400","traffic":"地铁","area_num":"1236","address":"天津市和平区包头道5号","lng":"117.19481000","type_id":"1","field_name":"aaa","v_id":"null","pro_id":"2","str_id":"17","base_img":"http://47.111.177.130/img/six/p_15757001676780.jpg","city_id":"2"}}]
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
         * distance : 1460695.9
         * map : {"pay_state":"0","type_name":"体育馆","build_time":"2000","open_time":"00","mind_state":"0","subscribe_price":"123.00","dis_id":"19","content":"","subscribe_state":"1","c_id":"null","tel":"123456","id":"2","state":"0","lat":"39.13166400","traffic":"地铁","area_num":"1236","address":"天津市和平区包头道5号","lng":"117.19481000","type_id":"1","field_name":"aaa","v_id":"null","pro_id":"2","str_id":"17","base_img":"http://47.111.177.130/img/six/p_15757001676780.jpg","city_id":"2"}
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
             * type_name : 体育馆
             * build_time : 2000
             * open_time : 00
             * mind_state : 0
             * subscribe_price : 123.00
             * dis_id : 19
             * content :
             * subscribe_state : 1
             * c_id : null
             * tel : 123456
             * id : 2
             * state : 0
             * lat : 39.13166400
             * traffic : 地铁
             * area_num : 1236
             * address : 天津市和平区包头道5号
             * lng : 117.19481000
             * type_id : 1
             * field_name : aaa
             * v_id : null
             * pro_id : 2
             * str_id : 17
             * base_img : http://47.111.177.130/img/six/p_15757001676780.jpg
             * city_id : 2
             */

            private String pay_state;
            private String type_name;
            private String build_time;
            private String open_time;
            private String mind_state;
            private String subscribe_price;
            private String dis_id;
            private String content;
            private String subscribe_state;
            private String c_id;
            private String tel;
            private String id;
            private String state;
            private String lat;
            private String traffic;
            private String area_num;
            private String address;
            private String lng;
            private String type_id;
            private String field_name;
            private String v_id;
            private String pro_id;
            private String str_id;
            private String base_img;
            private String city_id;

            public String getPay_state() {
                return pay_state;
            }

            public void setPay_state(String pay_state) {
                this.pay_state = pay_state;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
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

            public String getSubscribe_state() {
                return subscribe_state;
            }

            public void setSubscribe_state(String subscribe_state) {
                this.subscribe_state = subscribe_state;
            }

            public String getC_id() {
                return c_id;
            }

            public void setC_id(String c_id) {
                this.c_id = c_id;
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

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getTraffic() {
                return traffic;
            }

            public void setTraffic(String traffic) {
                this.traffic = traffic;
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

            public String getField_name() {
                return field_name;
            }

            public void setField_name(String field_name) {
                this.field_name = field_name;
            }

            public String getV_id() {
                return v_id;
            }

            public void setV_id(String v_id) {
                this.v_id = v_id;
            }

            public String getPro_id() {
                return pro_id;
            }

            public void setPro_id(String pro_id) {
                this.pro_id = pro_id;
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

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }
        }
    }
}
