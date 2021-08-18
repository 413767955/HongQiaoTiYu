package com.tianjin.beichentiyu.bean;

/**
 * Created by 黑瞳 on 2019/10/19 21:03
 * E-Mail Address：673236072@qq.com
 */
public class CompetitionBean {
    private String title;
    private String content;
    private String mun;
    private int img;
    private float width;
    private float height;
    public CompetitionBean() {}
    public CompetitionBean(String title, String content, String mun, int img) {
        this.title = title;
        this.content = content;
        this.mun = mun;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMun() {
        return mun;
    }

    public void setMun(String mun) {
        this.mun = mun;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}
