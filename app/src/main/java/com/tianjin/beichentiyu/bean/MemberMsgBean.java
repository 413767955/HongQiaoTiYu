package com.tianjin.beichentiyu.bean;

public class MemberMsgBean extends BaseRespBean{
    private MemBean mem;
    private String homeCity;
    private String livingCity;
    public MemBean getMem() {
        return mem;
    }

    public void setMem(MemBean mem) {
        this.mem = mem;
    }
}
