package cn.qqtheme.framework.entity;

import java.util.List;

/**
 * 用于联动选择器展示的第三级条目
 * <br />
 * Author:李玉江[QQ:1032694760]
 * DateTime:2017/04/17 00:35
 * Builder:Android Studio
 *
 * @see cn.qqtheme.framework.picker.LinkagePicker
 */
public interface LinkageThirdX<Fou> extends LinkageItem {
    List<Fou> getThirds();
}
