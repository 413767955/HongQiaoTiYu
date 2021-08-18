package com.tianjin.beichentiyu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tianjin.beichentiyu.R;

public class LodingDialog extends Dialog {
    private String msg;
    public LodingDialog(@NonNull Context context) {
        super(context,R.style.DialogTheme);
    }
    public LodingDialog(@NonNull Context context,String msg) {
        super(context,R.style.DialogTheme);
        this.msg = msg;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        setCancelable(true);
        setCanceledOnTouchOutside(false);

        if(!TextUtils.isEmpty(msg)){
            TextView tv = findViewById(R.id.tv_load_dialog);
            tv.setText(msg);
        }

    }

   /* @Override
    public void show() {
        super.show();
        showTime = System.currentTimeMillis();
    }
    private static final long SHOW_LONGTIME = 500;
    @Override
    public void dismiss() {
        //至少显示500毫秒，防止一闪而过的现象
        handler.removeCallbacks(runnable);
        long time = System.currentTimeMillis() - showTime;
        if(time > SHOW_LONGTIME){
            super.dismiss();
        }else{
            handler.postDelayed(runnable,SHOW_LONGTIME - time);
        }
    }

    private long showTime;
    private Handler handler = new Handler();
    private Runnable runnable = () -> superDismiss();

    private void superDismiss(){
        try{
            super.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/
}
