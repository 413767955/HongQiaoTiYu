package com.tianjin.beichentiyu.bean;

public class TaskTotalMsgBean  extends BaseRespBean{
    private String finishNum;
    private String totalNum;
    private String notNum;

    public String getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(String finishNum) {
        this.finishNum = finishNum;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getNotNum() {
        return notNum;
    }

    public void setNotNum(String notNum) {
        this.notNum = notNum;
    }
}
