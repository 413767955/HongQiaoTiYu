package com.tianjin.beichentiyu.bean;

import java.io.Serializable;
import java.util.List;

public class ExpenditureDetailsBean extends BaseRespBean {

    /**
     * code : 99999
     * pageNo : 1
     * list : [{"log_id":"3","gen_time":"2019-11-12 16:04:22.0","type":"1","bank_no":"9558821402001759861","money":"23.00","bank_user":"刘义力","bank_id":"1","bank_name":"建设银行","tel":"123456","state":"1","pay_id":"null","m_id":"1","done_time":"2019-11-12 16:04:26.0"},{"log_id":"2","gen_time":"2019-11-12 15:53:54.0","type":"2","bank_no":"null","money":"50.00","bank_user":"null","bank_id":"null","bank_name":"null","tel":"123456","state":"1","pay_id":"f8dfg54sgr8fg54ty","m_id":"1","done_time":"2019-11-12 15:53:57.0"},{"log_id":"1","gen_time":"2019-11-12 15:53:25.0","type":"1","bank_no":"9558821402001759861","money":"30.00","bank_user":"刘义力","bank_id":"1","bank_name":"建设银行","tel":"123456","state":"0","pay_id":"null","m_id":"1","done_time":"null"}]
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

    public static class ListBean implements Serializable {
        /**
         * log_id : 3
         * gen_time : 2019-11-12 16:04:22.0
         * type : 1
         * bank_no : 9558821402001759861
         * money : 23.00
         * bank_user : 刘义力
         * bank_id : 1
         * bank_name : 建设银行
         * tel : 123456
         * state : 1
         * pay_id : null
         * m_id : 1
         * done_time : 2019-11-12 16:04:26.0
         */

        private String log_id;
        private String gen_time;
        private String type;
        private String bank_no;
        private String money;
        private String bank_user;
        private String bank_id;
        private String bank_name;
        private String tel;
        private String state;
        private String pay_id;
        private String m_id;
        private String done_time;

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getGen_time() {
            return gen_time;
        }

        public void setGen_time(String gen_time) {
            this.gen_time = gen_time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBank_no() {
            return bank_no;
        }

        public void setBank_no(String bank_no) {
            this.bank_no = bank_no;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBank_user() {
            return bank_user;
        }

        public void setBank_user(String bank_user) {
            this.bank_user = bank_user;
        }

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
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

        public String getPay_id() {
            return pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public String getM_id() {
            return m_id;
        }

        public void setM_id(String m_id) {
            this.m_id = m_id;
        }

        public String getDone_time() {
            return done_time;
        }

        public void setDone_time(String done_time) {
            this.done_time = done_time;
        }
    }
}
