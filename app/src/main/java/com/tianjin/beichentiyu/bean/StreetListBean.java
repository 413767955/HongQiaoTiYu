package com.tianjin.beichentiyu.bean;

import java.util.List;

import cn.qqtheme.framework.entity.WheelItem;

public class StreetListBean extends BaseRespBean {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements WheelItem {
        public ListBean() {
        }
        public ListBean(String streetName) {
            this.streetName = streetName;
        }
        private String serialVersionUID;
        private String streetId;
        private String streetName;
        private String disId;
        private String state;

        public String getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(String serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getStreetId() {
            return streetId;
        }

        public void setStreetId(String streetId) {
            this.streetId = streetId;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getDisId() {
            return disId;
        }

        public void setDisId(String disId) {
            this.disId = disId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String getName() {
            return streetName;
        }
    }
}
