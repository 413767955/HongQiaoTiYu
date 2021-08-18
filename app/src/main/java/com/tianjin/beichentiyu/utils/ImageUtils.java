package com.tianjin.beichentiyu.utils;

import android.app.Activity;
import android.content.Context;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.setting.Setting;
import com.tianjin.beichentiyu.utils.easyPhotosUtil.GlideEngine;

import java.util.ArrayList;

public class ImageUtils {
    /**
     *
     * @param context
     * @param isphoto 是否使用拍照
     * @param single 单选
     * @param enlarge 查看大图
     * @param request_code
     */
    public static void  PhotoAlbum(Context context, boolean isphoto, boolean single, boolean enlarge, int request_code, int maxcount, ArrayList<String> selected){
        ImageSelector.builder()
                .useCamera(isphoto) // 设置是否使用拍照
                .setSingle(single)  //设置是否单选
                .setViewImage(enlarge) //是否点击放大图片查看,，默认为true
                .setMaxSelectCount(maxcount)
                .setSelected(selected) // 把已选的图片传入默认选中。
                .start((Activity) context, request_code); // 打开相册

    }

    public static void  PhotoAlbum(Context context, boolean isphoto, int request_code, int maxcount, ArrayList<String> selectedPhotos){
        if (selectedPhotos != null) {
            EasyPhotos.createAlbum((Activity) context, isphoto, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.tianjin.beichentiyu.fileprovider")
                    .setCount(maxcount)
                    .setSelectedPhotoPaths(selectedPhotos)
                    .setCameraLocation(Setting.LIST_FIRST)
                    .setPuzzleMenu(false)
                    .setCleanMenu(false)
                    .start(request_code);
        }else {
            EasyPhotos.createAlbum((Activity) context, isphoto, GlideEngine.getInstance())
                    .setFileProviderAuthority("com.tianjin.beichentiyu.fileprovider")
                    .setCount(maxcount)
                    .setCameraLocation(Setting.LIST_FIRST)
                    .setPuzzleMenu(false)
                    .setCleanMenu(false)
                    .start(request_code);
        }

    }
}
