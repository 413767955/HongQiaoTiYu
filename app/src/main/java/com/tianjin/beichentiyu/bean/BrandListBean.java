package com.tianjin.beichentiyu.bean;

import java.util.List;

/**
 * Created by wjy on 2020/4/19
 * E-Mail Address: 673236072@qq.com
 * des:品牌列表
 **/
public class BrandListBean extends BaseRespBean {
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String serialVersionUID;
        private String id;
        private String brandName;
        private String brandImg;
        private String state;
        private String companyName;

        public String getSerialVersionUID() {
            return serialVersionUID;
        }

        public void setSerialVersionUID(String serialVersionUID) {
            this.serialVersionUID = serialVersionUID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getBrandImg() {
            return brandImg;
        }

        public void setBrandImg(String brandImg) {
            this.brandImg = brandImg;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }
    }
}
