package com.tianjin.beichentiyu.bean;

import java.util.List;

public class AppointmentBean extends BaseRespBean{

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * gen_time : 2019-11-28 01:29:21.0
         * log_id : 157352589343886
         * appointment_time : 2019-11-29
         * base_img : http://47.111.154.197/img/six/p_15744757279408.jpg
         * field_name : 荣溪园
         * time_slot : 9:00-9:30
         */

        private String gen_time;
        private String log_id;
        private String appointment_time;
        private String base_img;
        private String field_name;
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

        public String getBase_img() {
            return base_img;
        }

        public void setBase_img(String base_img) {
            this.base_img = base_img;
        }

        public String getField_name() {
            return field_name;
        }

        public void setField_name(String field_name) {
            this.field_name = field_name;
        }

        public String getTime_slot() {
            return time_slot;
        }

        public void setTime_slot(String time_slot) {
            this.time_slot = time_slot;
        }
    }
}
