package com.tianjin.beichentiyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import com.tianjin.beichentiyu.R;

/**
 * Created by 黑瞳 on 2019/9/21 17:00
 * E-Mail Address：673236072@qq.com
 */
public class CustomToolbar extends BaseToolbar {
    private View mChildView;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;
    private TextView mTvTitle;
    private TextView mTvRight;
    private ImageView mToolbarIvCollect;
    public CustomToolbar(Context context) {
        super(context);
        initView();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_custom, null);
            mLeftLayout = mChildView.findViewById(R.id.toolbar_left);
            mRightLayout = mChildView.findViewById(R.id.toolbar_right);
            mTvTitle = mChildView.findViewById(R.id.toolbar_title);
            mTvRight = mChildView.findViewById(R.id.toolbar_tv_right);
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
    }

    /**
     * 设置右边文字
     * @param name
     * @param ids
     */
    public void setRightTv(String name,@ColorRes int ids){
        mTvRight.setText(name);
        mTvRight.setTextColor(getContext().getResources().getColor(ids));
        mTvRight.setVisibility(View.VISIBLE);
    }

    @Override
    public ImageView getCollectionView() {
        return mToolbarIvCollect;
    }
}
