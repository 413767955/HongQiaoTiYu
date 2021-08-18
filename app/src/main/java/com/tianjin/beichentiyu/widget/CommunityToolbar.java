package com.tianjin.beichentiyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tianjin.beichentiyu.R;

/**
 * Created by 黑瞳 on 2019/9/21 17:00
 * E-Mail Address：673236072@qq.com
 * 小区
 */
public class CommunityToolbar extends Toolbar {
    private View mChildView;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;
    private RelativeLayout mSearchLayout;
    public CommunityToolbar(Context context) {
        super(context);
        initView();
    }

    public CommunityToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CommunityToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_community, null);
            mLeftLayout = mChildView.findViewById(R.id.toolbar_left);
            mRightLayout = mChildView.findViewById(R.id.toolbar_right);
            mSearchLayout = mChildView.findViewById(R.id.toolbar_rel_search);
            addView(mChildView);
        }
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
     * 设置搜索监听
     * @param onClick
     */
    public void setSearchOnClick(OnClickListener onClick){
        mSearchLayout.setOnClickListener(onClick);
    }
}
