package com.tianjin.beichentiyu.bean;

/**
 * Created by 黑瞳 on 2019/10/17 21:34
 * E-Mail Address：673236072@qq.com
 */
public class BaseRespBean {
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 请求成功
     * @return
     */
    public boolean isSuccessful(){
        return "99999".equals(code);
    }

    /**
     * 登陆失效
     * @return
     */
    public boolean isLoginFailure(){
        return "00003".equals(code) || "00002".equals(code);
    }
}
