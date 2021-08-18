package com.tianjin.beichentiyu.bean;

import java.util.List;

public class PhysicalBean extends BaseRespBean{

    /**
     * pageNo : 1
     * list : [{"distance":128857.5,"map":{"address":"杨桥西路155号","lng":"119.28719800","build_time":"2018-06-16","open_time":"全天","subscribe_price":"0.00","dis_id":"1039","content":"公共区域","pro_id":"13","tel":"123456789","id":"2","state":"0","str_id":"1","phy_name":"金牛山公园1","base_img":"http://47.111.177.130/img/six/p_15727680482364.jpg","lat":"26.08650700","city_id":"115"}}]
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
         * distance : 128857.5
         * map : {"address":"杨桥西路155号","lng":"119.28719800","build_time":"2018-06-16","open_time":"全天","subscribe_price":"0.00","dis_id":"1039","content":"公共区域","pro_id":"13","tel":"123456789","id":"2","state":"0","str_id":"1","phy_name":"金牛山公园1","base_img":"http://47.111.177.130/img/six/p_15727680482364.jpg","lat":"26.08650700","city_id":"115"}
         */

        private double distance;
        private MapBean map;

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
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
             * address : 杨桥西路155号
             * lng : 119.28719800
             * build_time : 2018-06-16
             * open_time : 全天
             * subscribe_price : 0.00
             * dis_id : 1039
             * content : 公共区域
             * pro_id : 13
             * tel : 123456789
             * id : 2
             * state : 0
             * str_id : 1
             * phy_name : 金牛山公园1
             * base_img : http://47.111.177.130/img/six/p_15727680482364.jpg
             * lat : 26.08650700
             * city_id : 115
             */

            private String address;
            private String lng;
            private String build_time;
            private String open_time;
            private String subscribe_price;
            private String dis_id;
            private String content;
            private String pro_id;
            private String tel;
            private String id;
            private String state;
            private String str_id;
            private String phy_name;
            private String base_img;
            private String lat;
            private String city_id;

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

            public String getPro_id() {
                return pro_id;
            }

            public void setPro_id(String pro_id) {
                this.pro_id = pro_id;
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

            public String getPhy_name() {
                return phy_name;
            }

            public void setPhy_name(String phy_name) {
                this.phy_name = phy_name;
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
        }
    }
}
