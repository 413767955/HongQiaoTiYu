package com.tianjin.beichentiyu.bean;

import java.util.List;

public class MemActPayBean extends BaseRespBean {

    /**
     * pageNo : 1
     * totalPage : 1
     * list : [{"log_id":"157355210750975","place_name":"金牛山公园","appointment_time":"2019-11-12","address":"杨桥西路155号","gen_time":"2019-11-12 17:48:27.0","rel_id":"157352590833018","type":"1","title":"null","province_name":"福建省","city_name":"福州市","show_img":"null","price":"0.00","tel":"123456789","m_id":"1","time_slot":"8:00-8:30"},{"log_id":"2","place_name":"null","appointment_time":"null","address":"null","gen_time":"2019-06-16 01:54:44.0","rel_id":"2","type":"1","title":"测试活动2","province_name":"null","city_name":"null","show_img":"http://47.111.177.130/img/six/123.jpg","price":"21.00","tel":"null","m_id":"1","time_slot":"null"},{"log_id":"1","place_name":"null","appointment_time":"null","address":"null","gen_time":"2019-06-16 01:52:30.0","rel_id":"1","type":"1","title":"测试活动","province_name":"null","city_name":"null","show_img":"http://47.111.177.130/img/six/123.jpg","price":"20.00","tel":"null","m_id":"1","time_slot":"null"}]
     */

    private int pageNo;
    private int totalPage;
    private List<ListBean> list;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * log_id : 157355210750975
         * place_name : 金牛山公园
         * appointment_time : 2019-11-12
         * address : 杨桥西路155号
         * gen_time : 2019-11-12 17:48:27.0
         * rel_id : 157352590833018
         * type : 1
         * title : null
         * province_name : 福建省
         * city_name : 福州市
         * show_img : null
         * price : 0.00
         * tel : 123456789
         * m_id : 1
         * time_slot : 8:00-8:30
         */

        private String log_id;
        private String place_name;
        private String appointment_time;
        private String address;
        private String gen_time;
        private String rel_id;
        private String type;
        private String title;
        private String province_name;
        private String city_name;
        private String show_img;
        private String price;
        private String tel;
        private String m_id;
        private String time_slot;
        private String showActivityUrl;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getPlace_name() {
            return place_name;
        }

        public void setPlace_name(String place_name) {
            this.place_name = place_name;
        }

        public String getAppointment_time() {
            return appointment_time;
        }

        public void setAppointment_time(String appointment_time) {
            this.appointment_time = appointment_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGen_time() {
            return gen_time;
        }

        public void setGen_time(String gen_time) {
            this.gen_time = gen_time;
        }

        public String getRel_id() {
            return rel_id;
        }

        public void setRel_id(String rel_id) {
            this.rel_id = rel_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getShow_img() {
            return show_img;
        }

        public void setShow_img(String show_img) {
            this.show_img = show_img;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getTime_slot() {
            return time_slot;
        }

        public void setTime_slot(String time_slot) {
            this.time_slot = time_slot;
        }

        public String getShowActivityUrl() {
            return showActivityUrl;
        }

        public void setShowActivityUrl(String showActivityUrl) {
            this.showActivityUrl = showActivityUrl;
        }
    }
}
