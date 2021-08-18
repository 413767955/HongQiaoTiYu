package com.tianjin.beichentiyu.utils;

import android.widget.Toast;

import com.tianjin.beichentiyu.App;

public class ToastUtil {
    public static void showToast(String msg){
        Toast.makeText(App.get(), msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToast(int msg){
        Toast.makeText(App.get(), msg, Toast.LENGTH_SHORT).show();
    }
}
