package com.tianjin.beichentiyu.bean;

import java.util.List;

import cn.qqtheme.framework.entity.WheelItem;

/**
 * Created by 刘一手 on 2019/9/27
 * E-Mail Address：413767955@qq.com
 */
public class City implements WheelItem {
    private String provinceId;
    private String areaId;
    private String areaName;
    private List<County> counties;
    public City() {}
    public City(String areaName) {
        this.areaName = areaName;
    }
    public City(String areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }

    @Override
    public String getName() {
        return areaName;
    }
}
