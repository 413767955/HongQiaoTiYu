package com.tianjin.beichentiyu.bean;

public class DateListBean {

    private String date;
    private String week;

    public DateListBean(String date, String week) {
        this.date = date;
        this.week = week;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
