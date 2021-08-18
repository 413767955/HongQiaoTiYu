package com.tianjin.beichentiyu.bean;

import java.util.List;

public class AppIndexBean extends BaseRespBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * serialVersionUID : 1
         * id : 1
         * imgUrl : http://47.111.177.130/img/six/p_15727970388088.jpg
         * state : 0
         * url : null
         * typeId : 1
         * gradeNum : 1
         */

        private int serialVersionUID;
        private int id;
        private String imgUrl;
        private String state;
        private String url;
        private int typeId;
        private int gradeNum;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getGradeNum() {
            return gradeNum;
        }

        public void setGradeNum(int gradeNum) {
            this.gradeNum = gradeNum;
        }
    }
}
