package com.tianjin.beichentiyu.bean;

import java.util.List;

import cn.qqtheme.framework.entity.WheelItem;

public class DistrictListBean extends BaseRespBean {


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
        public ListBean(String districtName) {
            this.districtName = districtName;
        }

        /**
         * serialVersionUID : 1
         * districtId : 19
         * districtName : 和平区
         * cityId : 2
         */

        private String serialVersionUID;
        private String districtId;
        private String districtName;
        private String cityId;

        public String getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(String serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getDistrictId() {
            return districtId;
        }

        public void setDistrictId(String districtId) {
            this.districtId = districtId;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        @Override
        public String getName() {
            return districtName;
        }
    }
}
