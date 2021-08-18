package com.tianjin.beichentiyu.bean;

import java.util.List;

public class FieldEqueListBean extends BaseRespBean {


    /**
     * code : 99999
     * list : [{"e_name":"跑步机","sfe_id":"1","equi_name":"null","b_id":"1","e_img":"http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg","build_time":"null","brand_name":"测试","id":"1","state":"0","type":"2","content":"运动良品"},{"e_name":"动感单车","sfe_id":"2","equi_name":"null","b_id":"1","e_img":"http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg","build_time":"null","brand_name":"测试","id":"2","state":"0","type":"1","content":"运动良品"}]
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * e_name : 跑步机
         * sfe_id : 1
         * equi_name : null
         * b_id : 1
         * e_img : http://lin1.csttyg.com:1805/img/equipment/e_15308798949065.jpg
         * build_time : null
         * brand_name : 测试
         * id : 1
         * state : 0
         * type : 2
         * content : 运动良品
         * e_type
         */

        private String e_name;
        private String sfe_id;
        private String equi_name;
        private String b_id;
        private String e_img;
        private String build_time;
        private String brand_name;
        private String id;
        private String state;
        private String type;
        private String content;
        private String e_type;

        private boolean isShow;
        private int size;

        public String getE_name() {
            return e_name;
        }

        public void setE_name(String e_name) {
            this.e_name = e_name;
        }

        public String getSfe_id() {
            return sfe_id;
        }

        public void setSfe_id(String sfe_id) {
            this.sfe_id = sfe_id;
        }

        public String getEqui_name() {
            return equi_name;
        }

        public void setEqui_name(String equi_name) {
            this.equi_name = equi_name;
        }

        public String getB_id() {
            return b_id;
        }

        public void setB_id(String b_id) {
            this.b_id = b_id;
        }

        public String getE_img() {
            return e_img;
        }

        public void setE_img(String e_img) {
            this.e_img = e_img;
        }

        public String getBuild_time() {
            return build_time;
        }

        public void setBuild_time(String build_time) {
            this.build_time = build_time;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
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

        public String getE_type() {
            return e_type;
        }

        public void setE_type(String e_type) {
            this.e_type = e_type;
        }

        public boolean isShow() {
            return isShow;
        }

        public void setShow(boolean show) {
            isShow = show;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
