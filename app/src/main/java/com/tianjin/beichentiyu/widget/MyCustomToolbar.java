package com.tianjin.beichentiyu.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tianjin.beichentiyu.R;

/**
 * Created by 黑瞳 on 2019/9/21 17:00
 * E-Mail Address：673236072@qq.com
 */
public class MyCustomToolbar extends Toolbar {
    private View mChildView;
    private RelativeLayout mRelLeftButton;
    private LinearLayout mLinRightButton;
    private TextView mTvTitle;

    private String myTitle;
    public MyCustomToolbar(Context context) {
        super(context);
        initView();
    }

    public MyCustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyCustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_my, null);
            mRelLeftButton = mChildView.findViewById(R.id.toolbar_left);
            mLinRightButton = mChildView.findViewById(R.id.toolbar_right);
            mTvTitle = mChildView.findViewById(R.id.toolbar_title);
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
        if(mRelLeftButton!=null){
            mRelLeftButton.setOnClickListener(onClick);
        }
    }
    /**
     * 设置左边按钮监听
     * @param onClick
     */
    public void setRightOnClick(OnClickListener onClick){
        if(mLinRightButton!=null){
            mLinRightButton.setOnClickListener(onClick);
        }
    }
}
