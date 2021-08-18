package com.tianjin.beichentiyu.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tianjin.beichentiyu.bean.Province;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liu on 2019/9/27.
 */

public class CityUtil {
    private static List<Province> list;
    public static List<Province> getProvince(Context context){
        try {
            if(list !=null){
                return list;
            }
            String json = getAssetsData(context.getAssets().open("cities.json"),"utf-8");
            Type typeOfT = new TypeToken<List<Province>>(){}.getType();
            list = new Gson().fromJson(json,typeOfT);
            return list;
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }
    public static String getAssetsData(InputStream is, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    sb.append(line).append("\n");
                }
            }
            reader.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
