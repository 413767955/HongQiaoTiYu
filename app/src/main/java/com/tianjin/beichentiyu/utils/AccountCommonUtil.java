package com.tianjin.beichentiyu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountCommonUtil {
    /**
     * 打开外部浏览器
     */
    public static void openBrowser(Context context,String url){
        if(!TextUtils.isEmpty(url)){
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(url);
                intent.setData(content_url);
                context.startActivity(intent);
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ToastUtil.showToast("打开浏览器失败");
    }

    /**
     * 打开外部浏览器
     * url 为base64加密过的字符串
     * base64解密之后开启外部浏览器
     */
    public static void openBrowserBase64(Context context,String url){
        if(!TextUtils.isEmpty(url)){
            try {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                String str = new String(Base64.decode(url, Base64.DEFAULT));
                Uri content_url = Uri.parse(str);
                intent.setData(content_url);
                context.startActivity(intent);
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        ToastUtil.showToast("打开浏览器失败");
    }
    /**
     * 拨打电话
     * @param activity
     * @param phone
     */
    public static void actionCall(Activity activity, String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        //Intent intent = new Intent();
        //调用checkSelfPermission检查是否有权限
//        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
//            it.setAction(Intent.ACTION_CALL);//有权限则直接ACTION_CALL
//            it.setData(Uri.parse("tel:"+phone));
//            activity.startActivity(it);
//
//        }else{
//            ActivityCompat.requestPermissions(activity,new String []{Manifest.permission.CALL_PHONE},1);//无权限则询问开启权限
//            it.setAction(Intent.ACTION_CALL);
//            it.setData(Uri.parse("tel:"+phone));
//            activity.startActivity(it);
//        }
    }
    /**
     * 验证账号&验证码&新密码&重复密码
     * @param phone
     * @param code
     * @param newPwd
     * @param oldPwd
     * @return
     */
    public static boolean checkPhoneAndPwdAndCode(String phone,String code,String newPwd,String oldPwd){
        return checkPhone(phone) && checkSmsCode(code)  && checkPwd(newPwd) && checkRepeatPwd(newPwd,oldPwd);
    }
    /**
     * 验证账号&验证码&密码
     * @param phone
     * @param code
     * @param pwd
     * @return
     */
    public static boolean checkPhoneAndPwdAndCode(String phone,String code,String pwd){
        return checkPhone(phone) && checkSmsCode(code)  && checkPwd(pwd);
    }

    /**
     * 验证账号&新密码&旧密码
     * @param phone
     * @param newPwd
     * @param oldPwd
     * @return
     */
    public static boolean checkPhoneAndPwd(String phone,String newPwd,String oldPwd){
        return checkPhone(phone) && checkPwd(newPwd) && checkRepeatPwd(newPwd,oldPwd);
    }

    /**
     * 验证账号和密码
     * @param phone
     * @param pwd
     * @return
     */
    public static boolean checkPhoneAndPwd(String phone,String pwd){
        return checkPhone(phone) && checkPwd(pwd);
    }

    /**
     * 验证账号和验证码
     * @param phone
     * @param code
     * @return
     */
    public static boolean checkPhoneAndCode(String phone,String code){
        return checkPhone(phone) && checkSmsCode(code);
    }

    /**
     * 验证密码和验证码
     * @param pwd
     * @param code
     * @return
     */
    public static boolean checkPwdAndCode(String pwd,String code){
        return checkPwd(pwd) && checkSmsCode(code);
    }

    /**
     * 验证修改密码
     * @param oldPwd
     * @param newPwd1
     * @param newPwd2
     * @return
     */
    public static boolean checkUpdatePwd(String oldPwd,String newPwd1,String newPwd2){
        if(TextUtils.isEmpty(oldPwd)){
            ToastUtil.showToast("原密码不能为空!");
            return false;
        }else if(!checkPwd(oldPwd)){
            return false;
        }else if(TextUtils.isEmpty(newPwd1)){
            ToastUtil.showToast("新密码不能为空!");
            return false;
        }else if(!TextUtils.equals(newPwd1,newPwd2)){
            ToastUtil.showToast("两次密码不一致!");
            return false;
        }
        return true;
    }
    public static boolean checkRepeatPwd(String newPwd,String oldPwd){
        if(!TextUtils.equals(newPwd,oldPwd)){
            ToastUtil.showToast("两次密码不一致!");
            return false;
        }
        return true;
    }
    public static boolean checkSmsCode(String code){
        if(TextUtils.isEmpty(code)){
            ToastUtil.showToast("验证码不能为空!");
            return false;
        }
        return true;
    }
    public static boolean checkPwd(String pwd){
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast("密码不能为空!");
            return false;
        }else if (pwd.length() < 8) {
            ToastUtil.showToast("密码长度应为8-16位");
            return false;
        } else if (pwd.length() > 16) {
            ToastUtil.showToast("密码长度应为8-16位");
            return false;
        }
        return true;
    }
    public static boolean checkPhone(String phone){
        if(TextUtils.isEmpty(phone)){
            ToastUtil.showToast("手机号不能为空!");
            return false;
        }else if(!isNewCellPhone(phone)){
            ToastUtil.showToast("请填写正确手机号!");
            return false;
        }
        return true;
    }

    /**
     * 判断是否为手机号码
     * 中国电信号段 133、149、153、173、177、180、181、189、199
     * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
     * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     * 其他号段
     * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
     * 虚拟运营商
     * 电信：1700、1701、1702
     * 移动：1703、1705、1706
     * 联通：1704、1707、1708、1709、171
     * 卫星通信：1349
     * @param number 手机号码
     * @return
     */
    public static boolean isNewCellPhone(String number){
        Matcher match = Pattern.compile("^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$").matcher(number);
        return match.matches();
    }

    /**
     * 是否为手机号长度
     * @param number
     * @return
     */
    public static boolean isPhoneLength(String number) {
        return number.length() == 11;
    }
}
