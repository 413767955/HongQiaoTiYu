package com.tianjin.beichentiyu.bean;

import java.io.Serializable;
import java.util.List;

public class ActivityPageBean extends BaseRespBean {


    /**
     * code : 99999
     * pageNo : 1
     * totalPage : 1
     * list : [{"msg":"系统测试活动","address":"福州市鼓楼区古乐路11号","total_mem":"200","end_time":"2019-06-30 00:29:25.0","gen_time":"2019-06-16 00:31:00.0","begin_time":"2019-06-16 00:29:22.0","title":"测试活动","type":"3","content":"这个是一个测试的项目","show_img":"http://47.111.177.130/img/six/123.jpg","a_id":"1","pro_id":"13","price":"20.00","see_num":"0","tel":"123456789","state":"1","now_mem":"0","m_id":"1","city_id":"115"},{"msg":"系统测试活动2","address":"福州市鼓楼区古乐路11号","total_mem":"200","end_time":"2019-06-30 00:29:25.0","gen_time":"2019-06-16 00:31:00.0","begin_time":"2019-06-16 00:29:22.0","title":"测试活动2","type":"3","content":"这个是一个测试的项目2","show_img":"http://47.111.177.130/img/six/123.jpg","a_id":"2","pro_id":"13","price":"21.00","see_num":"0","tel":"123456780","state":"1","now_mem":"0","m_id":"1","city_id":"115"}]
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
         * msg : 系统测试活动
         * address : 福州市鼓楼区古乐路11号
         * total_mem : 200
         * end_time : 2019-06-30 00:29:25.0
         * gen_time : 2019-06-16 00:31:00.0
         * begin_time : 2019-06-16 00:29:22.0
         * title : 测试活动
         * type : 3
         * content : 这个是一个测试的项目
         * show_img : http://47.111.177.130/img/six/123.jpg
         * a_id : 1
         * pro_id : 13
         * price : 20.00
         * see_num : 0
         * tel : 123456789
         * state : 1
         * now_mem : 0
         * m_id : 1
         * city_id : 115
         */

        private String msg;
        private String address;
        private String total_mem;
        private String end_time;
        private String gen_time;
        private String begin_time;
        private String title;
        private String type;
        private String content;
        private String show_img;
        private String a_id;
        private String pro_id;
        private String price;
        private String see_num;
        private String tel;
        private String state;
        private String now_mem;
        private String m_id;
        private String city_id;
        private String showActivityUrl;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getTotal_mem() {
            return total_mem;
        }

        public void setTotal_mem(String total_mem) {
            this.total_mem = total_mem;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getGen_time() {
            return gen_time;
        }

        public void setGen_time(String gen_time) {
            this.gen_time = gen_time;
        }

        public String getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(String begin_time) {
            this.begin_time = begin_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getShow_img() {
            return show_img;
        }

        public void setShow_img(String show_img) {
            this.show_img = show_img;
        }

        public String getA_id() {
            return a_id;
        }

        public void setA_id(String a_id) {
            this.a_id = a_id;
        }

        public String getPro_id() {
            return pro_id;
        }

        public void setPro_id(String pro_id) {
            this.pro_id = pro_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSee_num() {
            return see_num;
        }

        public void setSee_num(String see_num) {
            this.see_num = see_num;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getNow_mem() {
            return now_mem;
        }

        public void setNow_mem(String now_mem) {
            this.now_mem = now_mem;
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

        public String getShowActivityUrl() {
            return showActivityUrl;
        }

        public void setShowActivityUrl(String showActivityUrl) {
            this.showActivityUrl = showActivityUrl;
        }
    }
}
