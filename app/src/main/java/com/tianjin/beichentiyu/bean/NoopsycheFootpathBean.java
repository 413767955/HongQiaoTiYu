package com.tianjin.beichentiyu.bean;

/**
 * Created by 黑瞳 on 2019/9/23 21:12
 * E-Mail Address：673236072@qq.com
 * 智能步道
 */
public class NoopsycheFootpathBean {
    private String name;
    private String site;
    private String price;
    private String journey;
    public NoopsycheFootpathBean(){}
    public NoopsycheFootpathBean(String name, String site, String price, String journey) {
        this.name = name;
        this.site = site;
        this.price = price;
        this.journey = journey;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getJourney() {
        return journey;
    }

    public void setJourney(String journey) {
        this.journey = journey;
    }
}
