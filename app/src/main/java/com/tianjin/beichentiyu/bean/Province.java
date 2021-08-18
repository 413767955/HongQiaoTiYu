package com.tianjin.beichentiyu.bean;

import java.util.List;

import cn.qqtheme.framework.entity.WheelItem;

/**
 * Created by 刘一手 on 2019/9/27
 * E-Mail Address：413767955@qq.com
 */
public class Province implements WheelItem {
    private String areaId;
    private String areaName;
    private List<City> cities;

    public Province() {}
    public Province(String areaName) {
        this.areaName = areaName;
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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    @Override
    public String getName() {
        return areaName;
    }
}
