package com.tianjin.beichentiyu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskBean extends BaseRespBean{

    /**
     * pageNo : 1
     * totalPage : 1
     * list : [{"msg":"巡检","repair_id":"null","field_id":"1","log_id":"4","field_address":"荣溪园","gen_time":"2019-12-08 23:37:32.0","build_time":"2019-12-08 23:37:40.0","end_time":"null","type":"2","field_name":"荣溪园","b_id":"1","show_img_two":"null","state":"0","task_type":"3","show_img_one":"null","base_img":"http://47.111.154.197/img/six/p_15744757279408.jpg","m_id":"1"},{"msg":"巡检","repair_id":"null","field_id":"1","log_id":"3","field_address":"荣溪园","gen_time":"2019-12-08 23:37:29.0","build_time":"2019-12-08 23:37:34.0","end_time":"null","type":"2","field_name":"荣溪园","b_id":"1","show_img_two":"null","state":"0","task_type":"3","show_img_one":"null","base_img":"http://47.111.154.197/img/six/p_15744757279408.jpg","m_id":"1"}]
     */

    private String pageNo;
    private String totalPage;
    private List<ListBean> list;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
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
         * msg : 巡检
         * repair_id : null
         * field_id : 1
         * log_id : 4
         * field_address : 荣溪园
         * gen_time : 2019-12-08 23:37:32.0
         * build_time : 2019-12-08 23:37:40.0
         * end_time : null
         * type : 2
         * field_name : 荣溪园
         * b_id : 1
         * show_img_two : null
         * state : 0
         * task_type : 3
         * show_img_one : null
         * base_img : http://47.111.154.197/img/six/p_15744757279408.jpg
         * m_id : 1
         */

        @SerializedName("msg")
        private String msgX;
        private String repair_id;
        private String field_id;
        private String log_id;
        private String field_address;
        private String gen_time;
        private String build_time;
        private String end_time;
        private String type;
        private String field_name;
        private String b_id;
        private String show_img_two;
        private String state;
        private String task_type;
        private String show_img_one;
        private String base_img;
        private String m_id;

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }

        public String getRepair_id() {
            return repair_id;
        }

        public void setRepair_id(String repair_id) {
            this.repair_id = repair_id;
        }

        public String getField_id() {
            return field_id;
        }

        public void setField_id(String field_id) {
            this.field_id = field_id;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
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

        public String getBuild_time() {
            return build_time;
        }

        public void setBuild_time(String build_time) {
            this.build_time = build_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public String getB_id() {
            return b_id;
        }

        public void setB_id(String b_id) {
            this.b_id = b_id;
        }

        public String getShow_img_two() {
            return show_img_two;
        }

        public void setShow_img_two(String show_img_two) {
            this.show_img_two = show_img_two;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getTask_type() {
            return task_type;
        }

        public void setTask_type(String task_type) {
            this.task_type = task_type;
        }

        public String getShow_img_one() {
            return show_img_one;
        }

        public void setShow_img_one(String show_img_one) {
            this.show_img_one = show_img_one;
        }

        public String getBase_img() {
            return base_img;
        }

        public void setBase_img(String base_img) {
            this.base_img = base_img;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }
    }
}
