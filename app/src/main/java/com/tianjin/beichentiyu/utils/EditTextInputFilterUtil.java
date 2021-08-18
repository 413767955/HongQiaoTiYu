package com.tianjin.beichentiyu.utils;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wjy
 * @time 2019/3/6
 **/
public class EditTextInputFilterUtil {
    /**
     * 限制密码输入长度
     * @return
     */
    public static InputFilter getLengthFilter(){
        return new InputFilter.LengthFilter(16) {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                if (source.length() > 0 && dest.length() == 16) {
                    ToastUtil.showToast("密码长度应为8-16位");
                }
                return super.filter(source, start, end, dest, dstart, dend);
            }
        };
    }

    /**
     * 限制输入字符为字母和数字
     * @return
     */
    public static InputFilter getTextTypeFilter(){

        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(source);
                String str = m.replaceAll("").trim();    //删掉不是字母或数字的字符
                if(!str.equals(source.toString())){
                    ToastUtil.showToast("密码仅支持数字和字母");
                    return "";
                }
                return null;
            }
        };
    }
}
