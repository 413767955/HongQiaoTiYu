package com.tianjin.beichentiyu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianjin.beichentiyu.R;

public class TwoButtonDialog extends Dialog {
    public TwoButtonDialog(@NonNull Context context) {
        super(context,R.style.DialogTheme);
    }
    private TextView tv_message;
    private Button btn_confirm;
    private View.OnClickListener confirmClickListener;

    private String msg;
    private String btnStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_del_bank);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //一定要在setContentView之后调用，否则无效
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv_message = findViewById(R.id.tv_msg);
        btn_confirm = findViewById(R.id.btn_confirm);
        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            dismiss();
            confirmClickListener.onClick(v);
        });
        findViewById(R.id.btn_cancel).setOnClickListener(v -> {dismiss();});

        if (!TextUtils.isEmpty(msg)) {
            tv_message.setText(msg);
        }
        if (!TextUtils.isEmpty(btnStr)) {
            btn_confirm.setText(btnStr);
        }
    }

    public TwoButtonDialog setMsg(String msg){
        this.msg = msg;
        return this;
    }
    public TwoButtonDialog confirmClick(String btnStr, View.OnClickListener listener){
        this.btnStr = btnStr;
        confirmClickListener = listener;
        return this;
    }

}
