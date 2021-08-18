package com.tianjin.beichentiyu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    /**
     * 获取年月日
     * @return
     */
    public static String getDateString(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获取日期时间
     * @return
     */
    public static String getDateTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


}
