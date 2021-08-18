package com.tianjin.beichentiyu.bean;

import java.util.List;

public class AppointmentDateBean extends BaseRespBean {


    /**
     * code : 99999
     * list : [{"gen_time":"2019-11-12 16:49:21.0","log_id":"157352589343886","appointment_time":"2019-11-12","tel":"15022010010","time_slot":"9:00-9:30"}]
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
         * gen_time : 2019-11-12 16:49:21.0
         * log_id : 157352589343886
         * appointment_time : 2019-11-12
         * tel : 15022010010
         * time_slot : 9:00-9:30
         */

        private String gen_time;
        private String log_id;
        private String appointment_time;
        private String tel;
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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTime_slot() {
            return time_slot;
        }

        public void setTime_slot(String time_slot) {
            this.time_slot = time_slot;
        }
    }
}
