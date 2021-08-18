package com.tianjin.beichentiyu.bean;

import androidx.annotation.IdRes;

/**
 * Created by 黑瞳 on 2019/9/21 17:13
 * E-Mail Address：673236072@qq.com
 */
public class HomeMeunBean {
    private String name;
    @IdRes
    private int icon;

    public HomeMeunBean(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
