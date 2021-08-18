package com.tianjin.beichentiyu.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tianjin.beichentiyu.R;

/**
 * Created by 黑瞳 on 2019/9/21 17:00
 * E-Mail Address：673236072@qq.com
 */
public class HomeCustomToolbar extends Toolbar {
    private View mChildView;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;
    private TextView mTvTitle;
    private TextView mTvLocation;

    private String myTitle;
    public HomeCustomToolbar(Context context) {
        super(context);
        initView();
    }

    public HomeCustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HomeCustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_home, null);
            mLeftLayout = mChildView.findViewById(R.id.toolbar_left);
            mRightLayout = mChildView.findViewById(R.id.toolbar_right);
            mTvTitle = mChildView.findViewById(R.id.toolbar_title);
            mTvLocation = mChildView.findViewById(R.id.tv_location);

            addView(mChildView);

            if(mTvTitle != null) {
                mTvTitle.setText(getTitle());
            }
        }
    }

    /**
     * 设置标题
     * @param title
     */
    public void setMyTitle(String title){
        this.myTitle = title;
        if(mTvTitle != null) {
            mTvTitle.setText(myTitle);
        }
    }

    /**
     * 设置左边按钮监听
     * @param onClick
     */
    public void setLeftOnClick(OnClickListener onClick){
        if(mLeftLayout!=null){
            mLeftLayout.setOnClickListener(onClick);
        }
    }
    /**
     * 设置左边按钮监听
     * @param onClick
     */
    public void setRightOnClick(OnClickListener onClick){
        if(mRightLayout!=null){
            mRightLayout.setOnClickListener(onClick);
        }
    }

    /**
     * 设置定位名称
     * @param name
     */
    public void setLocation(String name){
        if(mTvLocation!=null){
            mTvLocation.setText(name);
        }
    }
}
