package com.tianjin.beichentiyu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.tianjin.beichentiyu.R;

/**
 * Created by 黑瞳 on 2019/9/21 17:00
 * E-Mail Address：673236072@qq.com
 */
public class SiteFacilityToolbar extends Toolbar {
    private View mChildView;
    private LinearLayout mLeftLayout;
    private LinearLayout mRightLayout;
    private EditText mEtSearch;
    private RelativeLayout mRlSearch;
    private Callback callback;
    public SiteFacilityToolbar(Context context) {
        super(context);
        initView();
    }

    public SiteFacilityToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SiteFacilityToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    public void initView(){
        if (mChildView == null) {
            mChildView = View.inflate(getContext(), R.layout.toolbar_site_facility, null);
            mLeftLayout = mChildView.findViewById(R.id.toolbar_left);
            mRightLayout = mChildView.findViewById(R.id.toolbar_right);
            mEtSearch = mChildView.findViewById(R.id.toolbar_et_search);
            mRlSearch = mChildView.findViewById(R.id.toolbar_iv_search);
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
     * 设置右边按钮监听
     * @param onClick
     */
    public void setRightOnClick(OnClickListener onClick){
        mRightLayout.setOnClickListener(onClick);
        mRightLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 设置搜索按钮监听
     * @param callback
     */
    public void setSearchOnClick(Callback callback){
        this.callback = callback;
        mRlSearch.setOnClickListener(v -> {
            if(callback != null){
                this.callback.onSearch(mEtSearch.getText().toString());
            }
        });
    }

    public interface Callback{
        void onSearch(String text);
    }
}
