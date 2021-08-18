package com.tianjin.beichentiyu.bean;

import java.io.Serializable;
import java.util.List;

public class RepairRecordBean extends BaseRespBean {

    /**
     * pageNo : 1
     * totalPage : 1
     * list : [{"log_id":"157590433516293","e_name":"动感单车","address":"荣溪园","field_address":"null","gen_time":"2019-12-09 23:12:15.0","dis_id":"null","content":"哈哈哈哈哈","field_name":"荣溪园","fe_code":"null","pro_id":"null","user_id":"1","e_img":"http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg","fe_id":"null","e_id":"2","repair_img_two":"","f_id":"1","state":"0","repair_img_one":"http://47.111.177.130/img/six/p_15759043350870.jpg","base_img":"http://47.111.154.197/img/six/p_15744757279408.jpg","done_time":"null","city_id":"null"},{"log_id":"1","e_name":"跑步机","address":"荣溪园","field_address":"null","gen_time":"2018-07-16 23:43:14.0","dis_id":"null","content":"null","field_name":"荣溪园","fe_code":"null","pro_id":"null","user_id":"1","e_img":"http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg","fe_id":"null","e_id":"1","repair_img_two":"null","f_id":"1","state":"0","repair_img_one":"http://lin1.csttyg.com:1805/img/reqair/r_15317557932536.jpg","base_img":"http://47.111.154.197/img/six/p_15744757279408.jpg","done_time":"null","city_id":"null"},{"log_id":"2","e_name":"动感单车","address":"荣溪园","field_address":"null","gen_time":"2018-07-16 23:43:14.0","dis_id":"null","content":"null","field_name":"荣溪园","fe_code":"null","pro_id":"null","user_id":"1","e_img":"http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg","fe_id":"null","e_id":"2","repair_img_two":"null","f_id":"1","state":"0","repair_img_one":"http://lin1.csttyg.com:1805/img/reqair/r_15317557932536.jpg","base_img":"http://47.111.154.197/img/six/p_15744757279408.jpg","done_time":"null","city_id":"null"}]
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

    public static class ListBean implements Serializable {
        /**
         * log_id : 157590433516293
         * e_name : 动感单车
         * address : 荣溪园
         * field_address : null
         * gen_time : 2019-12-09 23:12:15.0
         * dis_id : null
         * content : 哈哈哈哈哈
         * field_name : 荣溪园
         * fe_code : null
         * pro_id : null
         * user_id : 1
         * e_img : http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg
         * fe_id : null
         * e_id : 2
         * repair_img_two :
         * f_id : 1
         * state : 0
         * repair_img_one : http://47.111.177.130/img/six/p_15759043350870.jpg
         * base_img : http://47.111.154.197/img/six/p_15744757279408.jpg
         * done_time : null
         * city_id : null
         */

        private String log_id;
        private String e_name;
        private String address;
        private String field_address;
        private String gen_time;
        private String dis_id;
        private String content;
        private String field_name;
        private String fe_code;
        private String pro_id;
        private String user_id;
        private String e_img;
        private String fe_id;
        private String e_id;
        private String repair_img_two;
        private String f_id;
        private String state;
        private String repair_img_one;
        private String base_img;
        private String done_time;
        private String city_id;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getE_name() {
            return e_name;
        }

        public void setE_name(String e_name) {
            this.e_name = e_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getField_address() {
            return field_address;
        }

        public void setField_address(String field_address) {
            this.field_address = field_address;
        }

        public String getGen_time() {
            return gen_time;
        }

        public void setGen_time(String gen_time) {
            this.gen_time = gen_time;
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

        public String getFe_code() {
            return fe_code;
        }

        public void setFe_code(String fe_code) {
            this.fe_code = fe_code;
        }

        public String getPro_id() {
            return pro_id;
        }

        public void setPro_id(String pro_id) {
            this.pro_id = pro_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getE_img() {
            return e_img;
        }

        public void setE_img(String e_img) {
            this.e_img = e_img;
        }

        public String getFe_id() {
            return fe_id;
        }

        public void setFe_id(String fe_id) {
            this.fe_id = fe_id;
        }

        public String getE_id() {
            return e_id;
        }

        public void setE_id(String e_id) {
            this.e_id = e_id;
        }

        public String getRepair_img_two() {
            return repair_img_two;
        }

        public void setRepair_img_two(String repair_img_two) {
            this.repair_img_two = repair_img_two;
        }

        public String getF_id() {
            return f_id;
        }

        public void setF_id(String f_id) {
            this.f_id = f_id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRepair_img_one() {
            return repair_img_one;
        }

        public void setRepair_img_one(String repair_img_one) {
            this.repair_img_one = repair_img_one;
        }

        public String getBase_img() {
            return base_img;
        }

        public void setBase_img(String base_img) {
            this.base_img = base_img;
        }

        public String getDone_time() {
            return done_time;
        }

        public void setDone_time(String done_time) {
            this.done_time = done_time;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }
}
