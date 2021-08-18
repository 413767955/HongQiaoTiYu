package com.tianjin.beichentiyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.tianjin.beichentiyu.R;

/**
 * Created by 黑瞳 on 2019/9/21 17:00
 * E-Mail Address：673236072@qq.com
 */
public class TransparentToolbar extends BaseToolbar {
    private View mChildView;
    private LinearLayout mRelLeftLayout;
    private ImageView mToolBarLeftButton;
    private LinearLayout mRelRightLayout;
    private TextView mTvTitle;
    private TextView mTvRight;
    private String myTitle;
    private ImageView mToolbarIvCollect;
    public TransparentToolbar(Context context) {
        super(context);
        initView();
    }

    public TransparentToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TransparentToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_transparent, null);
            mRelLeftLayout = mChildView.findViewById(R.id.toolbar_left);
            mToolBarLeftButton = mChildView.findViewById(R.id.toolbar_left_button);
            mRelRightLayout = mChildView.findViewById(R.id.toolbar_right);
            mTvRight = mChildView.findViewById(R.id.toolbar_tv_right);
            mTvTitle = mChildView.findViewById(R.id.toolbar_title);
            mToolbarIvCollect = mChildView.findViewById(R.id.toolbar_iv_collect);
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
     * 设置标题颜色
     * @param color
     */
    public void setMyTitleColor(@ColorInt int color){
        if(mTvTitle != null) {
            mTvTitle.setTextColor(color);
        }
    }

    public void setBackWhite(){
        mToolBarLeftButton.setBackgroundResource(R.mipmap.back_white);
    }

    public void setTitleAlpha(float alpha){
        mTvTitle.setAlpha(alpha);
    }
    /**
     * 设置左边按钮监听
     * @param onClick
     */
    public void setLeftOnClick(OnClickListener onClick){
        if(mRelLeftLayout!=null){
            mRelLeftLayout.setOnClickListener(onClick);
            mRelLeftLayout.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 设置右边按钮监听
     * @param onClick
     */
    public void setRightOnClick(OnClickListener onClick,String name){
        if(mRelRightLayout!=null){
            mRelRightLayout.setOnClickListener(onClick);
            mTvRight.setText(name);
            mTvRight.setVisibility(VISIBLE);
        }
    }
    @Override
    public ImageView getCollectionView() {
        return mToolbarIvCollect;
    }
}
