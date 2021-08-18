package com.tianjin.beichentiyu.bean;

import androidx.annotation.IdRes;

/**
 * Created by liu on 2019/9/22.
 */

public class MyMenuTitleBean {
    @IdRes
    private int icon;
    private String title;
    private String num;

    public MyMenuTitleBean(int icon, String title, String num) {
        this.icon = icon;
        this.title = title;
        this.num = num;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
