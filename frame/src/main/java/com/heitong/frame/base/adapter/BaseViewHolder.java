package com.heitong.frame.base.adapter;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 黑瞳 on 2019/8/10 00:33
 * E-Mail Address：673236072@qq.com
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> mViews;
    private View mConvertView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        mConvertView = itemView;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    /**
     * 设置 TextView 的值
     * @param viewId
     * @param text
     * @return
     */
    public BaseViewHolder setTvText(int viewId, String text){
        TextView tv  = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置 imageVeiw 图片
     * @param viewId
     * @param resId
     * @return
     */
    public BaseViewHolder setIvResource(int viewId, int resId){
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置 imageView 图片
     * @param viewId
     * @param bitmap
     * @return
     */
    public BaseViewHolder setIvBitmap(int viewId, Bitmap bitmap){
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置 TextView 的字体颜色
     * @param viewId
     * @param color
     * @return
     */
    public BaseViewHolder setTvTextColor(int viewId, @ColorRes int color){
        TextView tv = getView(viewId);
        if(tv != null) {
            tv.setTextColor(tv.getContext().getResources().getColor(color));
        }
        return this;
    }

    public BaseViewHolder setVisibility(int viewId, int visibility){
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setBackgroundResource(int viewId, int resid){
        View view = getView(viewId);
        view.setBackgroundResource(resid);
        return this;
    }
}
