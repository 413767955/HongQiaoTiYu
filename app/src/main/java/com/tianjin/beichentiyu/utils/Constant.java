package com.tianjin.beichentiyu.utils;

/**
 * Created by 黑瞳 on 2019/10/17 21:42
 * E-Mail Address：673236072@qq.com
 */
public interface Constant {
    interface Sms{
        String REGISTER = "1";          //注册
        String LOGIN = "2";             //登陆
        String CHANGE_PASSWORD  = "3";  //修改密码
    }
    interface Cache{
        String MEMDATA = "MEMDATA";
        String HOMECITY = "HOMECITY";
        String LIVINGCITY = "LIVINGCITY";
        String CITY = "CITY";
        String MEMBER_DOOPERATION = "MEMBER_DOOPERATION";
    }
    interface Information{
        int SYXW = 1000;        //首页新闻
        int PTGG = 1001;        //平台公告
        int XWZX = 1002;        //新闻资讯
        int SSXQ = 1003;        //赛事详情
        int CDXQ = 1004;        //场地详情
        int TCXQ = 1005;        //体测详情
        int ZNJS = 1006;        //智能健身
    }
    //智能健身
    interface SmartFitness{
        String ZNBD = "1";//智能步道
        String ZNQC = "2";//智能球场
        String ZNLJ = "3";//智能路径
        String ZNJSF = "4";//智能健身房
    }
}
