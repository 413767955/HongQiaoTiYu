package com.tianjin.beichentiyu.bean;

import java.util.List;

public class OrganizationDutiesBean extends BaseRespBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * nick_name : 刘义力
         * head_img : https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1344096634,1355205704&fm=26&gp=0.jpg
         * duties_name : 董事长
         */

        private String nick_name;
        private String head_img;
        private String duties_name;

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getDuties_name() {
            return duties_name;
        }

        public void setDuties_name(String duties_name) {
            this.duties_name = duties_name;
        }
    }
}
