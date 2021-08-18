package com.tianjin.beichentiyu.bean;

/**
 * Created by 黑瞳 on 2019/9/22 13:16
 * E-Mail Address：673236072@qq.com
 */
public class InformationBean {
    private String title;
    private int readNum;
    private int commentNum;
    private String tag;
    private String imgUrl;

    public InformationBean() {}

    public InformationBean(String title, int readNum, int commentNum, String tag, String imgUrl) {
        this.title = title;
        this.readNum = readNum;
        this.commentNum = commentNum;
        this.tag = tag;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
