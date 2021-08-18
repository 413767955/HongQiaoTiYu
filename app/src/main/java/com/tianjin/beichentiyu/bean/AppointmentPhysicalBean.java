package com.tianjin.beichentiyu.bean;

import java.util.List;

public class AppointmentPhysicalBean extends BaseRespBean{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * gen_time : 2019-11-12 17:48:27.0
         * log_id : 157352590833018
         * appointment_time : 2019-11-12
         * phy_name : 人民医院
         * base_img : http://lin1.csttyg.com:1805/img/field/f_15317505738585.jpg
         * time_slot : 8:00-8:30
         */

        private String gen_time;
        private String log_id;
        private String appointment_time;
        private String phy_name;
        private String base_img;
        private String time_slot;

        public String getGen_time() {
            return gen_time;
        }

        public void setGen_time(String gen_time) {
            this.gen_time = gen_time;
        }

        public String getLog_id() {
            return log_id;
        }

        public void setLog_id(String log_id) {
            this.log_id = log_id;
        }

        public String getAppointment_time() {
            return appointment_time;
        }

        public void setAppointment_time(String appointment_time) {
            this.appointment_time = appointment_time;
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

        public String getTime_slot() {
            return time_slot;
        }

        public void setTime_slot(String time_slot) {
            this.time_slot = time_slot;
        }
    }
}
