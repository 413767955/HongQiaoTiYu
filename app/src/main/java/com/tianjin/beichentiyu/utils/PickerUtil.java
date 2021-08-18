package com.tianjin.beichentiyu.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.MemberBankBean;

import java.util.ArrayList;
import java.util.Calendar;

import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;

import static cn.qqtheme.framework.picker.DateTimePicker.YEAR_MONTH;

/**
 * 地址选择器
 */

public class PickerUtil {
    /**
     * setOnAddressPickListener(new AddressPicker.OnAddressPickListener()
     * 地区选择器  省市
     */

    public static AddressPicker region(Context context){
        AddressPicker picker = null;
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(context.getAssets().open("cities.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            picker= new AddressPicker((Activity) context, data);
            picker.setWidth((int) (picker.getScreenWidthPixels()*0.9));
            picker.setTopBackgroundColor(Color.parseColor("#00000000"));
            picker.setBackgroundColor(Color.parseColor("#00000000"));
            picker.setCancelTextColor(context.getResources().getColor(R.color.color_FF6464));
            picker.setCancelTextSize(16);
            picker.setSubmitTextColor(context.getResources().getColor(R.color.color_6977FF));
            picker.setSubmitTextSize(16);
            picker.setTopPadding(16);
            picker.setTextSize(20);
            picker.setTextPadding(15);
            picker.setDividerVisible(false);
            picker.setShadowColor(context.getResources().getColor(R.color.color_f2f2f2));
            picker.setTextColor(context.getResources().getColor(R.color.color_6977FF));
            picker.setContentPadding(15,15);
            picker.getRootView().setBackground(context.getResources().getDrawable(R.drawable.bg_rectangle_top_ffffff_15));
            picker.setTopHeight(55);
            picker.setHideCounty(true);

            picker.show();
        } catch (Exception e) {
//                    showToast(LogUtils.toStackTraceString(e));
        }
        return picker;
    }
    /**
     * setOnAddressPickListener(new AddressPicker.OnAddressPickListener()
     * 地区选择器  省市区
     */

    public static AddressPicker regionall(Context context){
        AddressPicker picker = null;
        try {
            ArrayList<Province> data = new ArrayList<>();
            String json = ConvertUtils.toString(context.getAssets().open("cities.json"));
            data.addAll(JSON.parseArray(json, Province.class));
            picker= new AddressPicker((Activity) context, data);
            picker.setWidth((int) (picker.getScreenWidthPixels()*0.9));
            picker.setTopBackgroundColor(Color.parseColor("#00000000"));
            picker.setBackgroundColor(Color.parseColor("#00000000"));
            picker.setCancelTextColor(context.getResources().getColor(R.color.color_FF6464));
            picker.setCancelTextSize(16);
            picker.setSubmitTextColor(context.getResources().getColor(R.color.color_6977FF));
            picker.setSubmitTextSize(16);
            picker.setTopPadding(16);
            picker.setTextSize(20);
            picker.setTextPadding(15);
            picker.setDividerVisible(false);
            picker.setShadowColor(context.getResources().getColor(R.color.color_f2f2f2));
            picker.setTextColor(context.getResources().getColor(R.color.color_6977FF));
            picker.setContentPadding(15,15);
            picker.getRootView().setBackground(context.getResources().getDrawable(R.drawable.bg_rectangle_top_ffffff_15));
            picker.setTopHeight(55);
//            picker.setHideCounty(true);

            picker.show();
        } catch (Exception e) {
//                    showToast(LogUtils.toStackTraceString(e));
        }
        return picker;
    }

    /**   picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener()
     *      年月日
     * @param context
     * @param time   时间格式 2019-02-25
     * @return
     */
    public static DatePicker datePickerYearMonthDay (Context context, String time){
        final DatePicker picker = new DatePicker((Activity) context);
        picker.setWidth((int) (picker.getScreenWidthPixels()*0.9));
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        picker.setRangeEnd(Calendar.getInstance().get(Calendar.YEAR),  Calendar.getInstance().get(Calendar.MONTH)+1, Calendar.getInstance().get(Calendar.DATE));
        picker.setRangeStart(1900, 1, 1);
        picker.setTopBackgroundColor(Color.parseColor("#00000000"));
        picker.setBackgroundColor(Color.parseColor("#00000000"));
        picker.setCancelTextColor(context.getResources().getColor(R.color.color_FF6464));
        picker.setCancelTextSize(16);
        picker.setSubmitTextColor(context.getResources().getColor(R.color.color_6977FF));
        picker.setSubmitTextSize(16);
        picker.setTopPadding(16);
        picker.setTextSize(20);
        picker.setTextPadding(15);
        picker.setDividerVisible(false);
        picker.setShadowColor(context.getResources().getColor(R.color.color_f2f2f2));
        picker.setTextColor(context.getResources().getColor(R.color.color_6977FF));
        picker.setContentPadding(15,15);
        picker.getRootView().setBackground(context.getResources().getDrawable(R.drawable.bg_rectangle_top_ffffff_15));
        picker.setTopHeight(55);
        if(TextUtils.isEmpty(time)||time.equals("日期")){
            picker.setSelectedItem(Calendar.getInstance().get(Calendar.YEAR),  Calendar.getInstance().get(Calendar.MONTH)+1, Calendar.getInstance().get(Calendar.DATE));
        }else{
            String[] date=time.split("-");
            picker.setSelectedItem(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        }

        picker.setResetWhileWheel(false);
//        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
//            @Override
//            public void onDatePicked(String year, String month, String day) {
//                tv_time.setText(year + "-" + month + "-" + day);
//                time=year + "-" + month + "-" + day;
//                mRefreshLayout.setRefreshEnable(true);
//                mRefreshLayout.autoRefresh();
//            }
//        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.setLabel("","","");
        picker.show();
        return picker;
    }
    /**  picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener()
     *      年月
     * @param context
     * @param time   时间格式 2019-02 不能为null
     * @return
     */
    public static DatePicker datePickerYearMonth (Context context, String time){
        final DatePicker picker = new DatePicker((Activity)context,YEAR_MONTH);
        picker.setWidth((int) (picker.getScreenWidthPixels()*0.9));
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        String[] date = time.split("-");
        picker.setRangeEnd(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1);
        picker.setRangeStart(1900, 1, 1);
        picker.setSelectedItem(Integer.parseInt(date[0]), Integer.parseInt(date[1]));
        picker.setResetWhileWheel(false);
        picker.setTopBackgroundColor(Color.parseColor("#00000000"));
        picker.setBackgroundColor(Color.parseColor("#00000000"));
        picker.setCancelTextColor(context.getResources().getColor(R.color.color_FF6464));
        picker.setCancelTextSize(16);
        picker.setSubmitTextColor(context.getResources().getColor(R.color.color_6977FF));
        picker.setSubmitTextSize(16);
        picker.setTopPadding(16);
        picker.setTextSize(20);
        picker.setTextPadding(15);
        picker.setDividerVisible(false);
        picker.setShadowColor(context.getResources().getColor(R.color.color_f2f2f2));
        picker.setTextColor(context.getResources().getColor(R.color.color_6977FF));
        picker.setContentPadding(15,15);
        picker.getRootView().setBackground(context.getResources().getDrawable(R.drawable.bg_rectangle_top_ffffff_15));
        picker.setTopHeight(55);

//        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener() {
//
//            @Override
//            public void onDatePicked(String year, String month) {
//                tv_time.setText(year + "-" + month );
//                mRefreshLayout.setRefreshEnable(true);
//                mRefreshLayout.autoRefresh();
//            }
//        });
        picker.setLabel("", "", "");
        picker.show();
        return picker;
    }
    public static OptionPicker sort (Context context){
        OptionPicker picker = new OptionPicker((Activity)context, new String[]{
                "排行","升序", "降序"
        });
        picker.setWidth((int) (picker.getScreenWidthPixels()*0.9));
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(context, 10));
        picker.setTopBackgroundColor(Color.parseColor("#00000000"));
        picker.setBackgroundColor(Color.parseColor("#00000000"));
        picker.setCancelTextColor(context.getResources().getColor(R.color.color_FF6464));
        picker.setCancelTextSize(16);
        picker.setSubmitTextColor(context.getResources().getColor(R.color.color_6977FF));
        picker.setSubmitTextSize(16);
        picker.setTopPadding(16);
        picker.setTextSize(20);
        picker.setTextPadding(15);
        picker.setDividerVisible(false);
        picker.setShadowColor(context.getResources().getColor(R.color.color_f2f2f2));
        picker.setTextColor(context.getResources().getColor(R.color.color_6977FF));
        picker.setContentPadding(15,15);
        picker.getRootView().setBackground(context.getResources().getDrawable(R.drawable.bg_rectangle_top_ffffff_15));
        picker.setTopHeight(55);
        picker.show();
        return picker;
    }

    public static BankPicker choiceBank (Context context, MemberBankBean bean){
        BankPicker picker = null;
        try {
            picker = new BankPicker((Activity)context, bean.getList());
            picker.setWidth((int) (picker.getScreenWidthPixels()*0.9));
            picker.setCanceledOnTouchOutside(true);
            picker.setUseWeight(true);
            picker.setTopPadding(ConvertUtils.toPx(context, 10));
            picker.setTopBackgroundColor(Color.parseColor("#00000000"));
            picker.setBackgroundColor(Color.parseColor("#00000000"));
            picker.setCancelTextColor(context.getResources().getColor(R.color.color_FF6464));
            picker.setCancelTextSize(16);
            picker.setSubmitTextColor(context.getResources().getColor(R.color.color_6977FF));
            picker.setSubmitTextSize(16);
            picker.setTopPadding(16);
            picker.setTextSize(20);
            picker.setTextPadding(15);
            picker.setDividerVisible(false);
            picker.setShadowColor(context.getResources().getColor(R.color.color_f2f2f2));
            picker.setTextColor(context.getResources().getColor(R.color.color_6977FF));
            picker.setContentPadding(15,15);
            picker.getRootView().setBackground(context.getResources().getDrawable(R.drawable.bg_rectangle_top_ffffff_15));
            picker.setTopHeight(55);
            picker.show();
        }catch (Exception e) {
//                    showToast(LogUtils.toStackTraceString(e));
        }

        return picker;
    }
}
