package com.tianjin.beichentiyu.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.tianjin.beichentiyu.App;

import java.util.Map;

public class SpUtil {

    public static String CONFIG = "cachePath";


    /**
     * 默认文件 cachePath
     *
     * @return sp
     */
    private static SharedPreferences getSp() {
        return App.get().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    }


    /**
     * 指定文件
     *
     * @param fileName 指定文件名
     * @return sp
     */
    private static SharedPreferences getSp(String fileName) {
        return App.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * SP中是否包含key
     *
     * @param key The name of the preference to check.
     * @return
     */
    public static boolean isContain(String key) {
        return getSp().contains(key);
    }

    /**
     * 删除SP中某个key
     *
     * @param key 要删除的key
     */
    public static void remove(String key) {

        getSp().edit().remove(key).commit();

    }

    /**
     * 删除SP中某个key
     *
     * @param keys 要删除的key
     */
    public static void removeKeys(String... keys) {

        SharedPreferences.Editor editor = getSp().edit();
        for (String key : keys) {

            editor.remove(key);
        }
        editor.commit();

    }

    /**
     * 保存String
     *
     * @param key   键名
     * @param value 键值
     */
    public static void putString(String key, String value) {
        getSp().edit().putString(key, value).commit();
    }

    /**
     * 获得SP中的String
     *
     * @param key      键名
     * @param defValue 默认值
     * @return 返回key对应的值，如果不存在则返回默认值
     */
    public static String getString(String key, String defValue) {
        return getSp().getString(key, defValue);
    }

    /**
     * 保存int
     *
     * @param key   键名
     * @param value 键值
     */
    public static void putInt(String key, int value) {
        getSp().edit().putInt(key, value).commit();
    }

    /**
     * 获得SP中的int
     *
     * @param key      键名
     * @param defValue 默认值
     * @return 返回key对应的值，如果不存在则返回默认值
     */
    public static int getInt(String key, int defValue) {
        return getSp().getInt(key, defValue);
    }

    /**
     * 保存Long
     *
     * @param key   键名
     * @param value 键值
     */
    public static void putLong(String key, long value) {
        getSp().edit().putLong(key, value).commit();
    }

    /**
     * 获得SP中的Long
     *
     * @param key      键名
     * @param defValue 默认值
     * @return 返回key对应的值，如果不存在则返回默认值
     */
    public static long getLong(String key, long defValue) {
        return getSp().getLong(key, defValue);
    }

    /**
     * 保存Boolean
     *
     * @param key   键名
     * @param value 键值
     */
    public static void putBoolean(String key, Boolean value) {
        getSp().edit().putBoolean(key, value).commit();
    }

    /**
     * 获得SP中的Boolean
     *
     * @param key      键名
     * @param defValue 默认值
     * @return 返回key对应的值，如果不存在则返回默认值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return getSp().getBoolean(key, defValue);
    }

    /**
     * 保存Float
     *
     * @param key   键名
     * @param value 键值
     */
    public static void putFloat(String key, float value) {
        getSp().edit().putFloat(key, value).commit();
    }


    /**
     * 获得SP中的Float
     *
     * @param key      键名
     * @param defValue 默认值
     * @return 返回key对应的值，如果不存在则返回默认值
     */
    public static float getFloat(String key, float defValue) {
        return getSp().getFloat(key, defValue);
    }


    /**
     * 获取指定SP文件中的String值
     *
     * @param fileName 文件名
     * @param key      键名
     * @param defValue 默认值
     * @return 返回key对应的值，如果不存在则返回默认值
     */
    public static String getString(String fileName, String key, String defValue) {

        return getSp(fileName).getString(key, defValue);
    }


    /**
     * 保存String类型值到指定SP文件中
     *
     * @param fileName 文件名
     * @param key      键名
     * @param value    值
     */
    public static void putString(String fileName, String key, String value) {

        getSp(fileName).edit().putString(key, value).commit();
    }

    /**
     * 保存StringMap
     *
     * @param map 保存数据集合
     */
    public static void putStringMap(Map<String, String> map) {

        if (map != null && !map.isEmpty()) {
            SharedPreferences.Editor editor = getSp().edit();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                editor.putString(entry.getKey(), entry.getValue());
            }
            editor.commit();
        }


    }
}
