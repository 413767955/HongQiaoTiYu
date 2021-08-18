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
public class LoginCustomToolbar extends Toolbar {
    private View mChildView;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;
    private TextView mTvTitle;
    private TextView mTvRight;
    public LoginCustomToolbar(Context context) {
        super(context);
        initView();
    }

    public LoginCustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoginCustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_login, null);
            mLeftLayout = mChildView.findViewById(R.id.toolbar_left);
            mRightLayout = mChildView.findViewById(R.id.toolbar_right);
            mTvTitle = mChildView.findViewById(R.id.toolbar_title);
            mTvRight = mChildView.findViewById(R.id.toolbar_tv_right);
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
        mTvTitle.setText(title);
    }

    /**
     * 设置左边按钮监听
     * @param onClick
     */
    public void setLeftOnClick(OnClickListener onClick){
        mLeftLayout.setOnClickListener(onClick);
        mLeftLayout.setVisibility(View.VISIBLE);
    }
    /**
     * 设置右边按钮监听
     * @param onClick
     */
    public void setRightOnClick(OnClickListener onClick){
        mRightLayout.setOnClickListener(onClick);
        mRightLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右边文字
     * @param name
     */
    public void setRightTv(String name){
        mTvRight.setText(name);
    }
}
