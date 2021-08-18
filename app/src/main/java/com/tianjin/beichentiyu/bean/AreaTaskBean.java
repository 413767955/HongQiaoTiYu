package com.tianjin.beichentiyu.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wjy on 2020/5/24
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class AreaTaskBean extends BaseRespBean {


    /**
     * pageNo : 1
     * totalPage : 1
     * list : [{"msg":"","repair_id":"157701943602956","field_id":"1","log_id":"157723594218529","field_address":"天津市天津市北辰区荣溪园","gen_time":"2019-12-2509:05:42.0","build_time":"2019-12-2509:05:42.0","end_time":"null","type":"1","dis_id":"31","field_name":"荣溪园","pro_id":"2","b_id":"1","show_img_two":"","task_mounth":"null","state":"0","task_type":"1","show_img_one":"http://123.57.138.191/img/six/p_15854770515415.jpg","base_img":"http://123.57.138.191/img/six/p_15744757279408.jpg","m_id":"157665259798472","city_id":"2"},{"msg":"把手损坏","repair_id":"157701950645185","field_id":"1","log_id":"157719776010456","field_address":"天津市天津市北辰区荣溪园","gen_time":"2019-12-2422:29:20.0","build_time":"2019-12-2422:29:20.0","end_time":"null","type":"1","dis_id":"31","field_name":"荣溪园","pro_id":"2","b_id":"1","show_img_two":"null","task_mounth":"null","state":"0","task_type":"1","show_img_one":"null","base_img":"http://123.57.138.191/img/six/p_15744757279408.jpg","m_id":"157665057850633","city_id":"2"}]
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
         * msg :
         * repair_id : 157701943602956
         * field_id : 1
         * log_id : 157723594218529
         * field_address : 天津市天津市北辰区荣溪园
         * gen_time : 2019-12-2509:05:42.0
         * build_time : 2019-12-2509:05:42.0
         * end_time : null
         * type : 1
         * dis_id : 31
         * field_name : 荣溪园
         * pro_id : 2
         * b_id : 1
         * show_img_two :
         * task_mounth : null
         * state : 0
         * task_type : 1
         * show_img_one : http://123.57.138.191/img/six/p_15854770515415.jpg
         * base_img : http://123.57.138.191/img/six/p_15744757279408.jpg
         * m_id : 157665259798472
         * city_id : 2
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
        private String dis_id;
        private String field_name;
        private String pro_id;
        private String b_id;
        private String show_img_two;
        private String task_mounth;
        private String state;
        private String task_type;
        private String show_img_one;
        private String base_img;
        private String m_id;
        private String city_id;

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

        public String getDis_id() {
            return dis_id;
        }

        public void setDis_id(String dis_id) {
            this.dis_id = dis_id;
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

        public String getTask_mounth() {
            return task_mounth;
        }

        public void setTask_mounth(String task_mounth) {
            this.task_mounth = task_mounth;
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

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }
    }
}
