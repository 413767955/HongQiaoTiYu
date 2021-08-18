package com.tianjin.beichentiyu.bean;

import java.util.List;

/**
 * Created by wjy on 2020/1/14
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class IntellectImgListBean extends BaseRespBean{

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
         * logId : 1
         * fId : 1
         * imgUrl : http://lin1.csttyg.com:1805/img/field/f_15317505738585.jpg
         */

        private int serialVersionUID;
        private String logId;
        private int fId;
        private String imgUrl;

        public int getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(int serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public int getFId() {
            return fId;
        }

        public void setFId(int fId) {
            this.fId = fId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
