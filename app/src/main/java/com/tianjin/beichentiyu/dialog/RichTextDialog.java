package com.tianjin.beichentiyu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianjin.beichentiyu.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RichTextDialog extends Dialog {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.message)
    TextView mMessage;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private View.OnClickListener confirmClickListener;
    private String title;
    private String content;
    private String btnConfirmStr;
    private SpannableString mSpannableString;

    public RichTextDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rich_text_dialog_layout);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initView();
    }
    private void initView(){
        mBtnCancel.setOnClickListener(v -> dismiss());
       mBtnConfirm.setOnClickListener(v -> {
            dismiss();
            confirmClickListener.onClick(v);
        });
       mTitle.setText(title);
       mMessage.setText(mSpannableString);
       mBtnConfirm.setText(btnConfirmStr);
    }
    public RichTextDialog setTitle(String title) {
        this.title = title;
        return this;
    }
    public RichTextDialog setContent(SpannableString mSpannableString) {
        this.mSpannableString = mSpannableString;
        return this;
    }

    public RichTextDialog setBtnConfirmStr(String btnConfirmStr) {
        this.btnConfirmStr = btnConfirmStr;
        return this;
    }

}
