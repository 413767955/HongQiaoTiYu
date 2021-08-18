package com.tianjin.beichentiyu.bean;

/**
 * Created by 黑瞳 on 2019/9/23 22:27
 * E-Mail Address：673236072@qq.com
 */
public class NoopsycheFiancoCentreBean {
    private String cover;
    private String name;
    private String site;
    private String time;
    private String peopleNum;
    private String phone;

    public NoopsycheFiancoCentreBean(String cover, String name, String site, String time, String peopleNum, String phone) {
        this.cover = cover;
        this.name = name;
        this.site = site;
        this.time = time;
        this.peopleNum = peopleNum;
        this.phone = phone;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(String peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
