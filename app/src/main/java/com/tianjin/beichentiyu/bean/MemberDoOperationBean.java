package com.tianjin.beichentiyu.bean;

public class MemberDoOperationBean extends BaseRespBean{
    private String collectNum;
    private String fieldAppointmentNum;
    private String phyAppointmentNum;

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
    }

    public String getFieldAppointmentNum() {
        return fieldAppointmentNum;
    }

    public void setFieldAppointmentNum(String fieldAppointmentNum) {
        this.fieldAppointmentNum = fieldAppointmentNum;
    }

    public String getPhyAppointmentNum() {
        return phyAppointmentNum;
    }

    public void setPhyAppointmentNum(String phyAppointmentNum) {
        this.phyAppointmentNum = phyAppointmentNum;
    }
}
