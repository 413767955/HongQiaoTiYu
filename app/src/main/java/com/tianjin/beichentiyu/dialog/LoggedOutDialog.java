package com.tianjin.beichentiyu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.manager.AccountManager;

public class LoggedOutDialog extends Dialog {
    public LoggedOutDialog(@NonNull Context context) {
        super(context,R.style.DialogTheme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_logged_out);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

        findViewById(R.id.mb_quit).setOnClickListener(v -> {
            dismiss();
            AccountManager.getInstance().loggedOut();
        });
        findViewById(R.id.mb_cancel).setOnClickListener(v -> {dismiss();});
       /* if(!TextUtils.isEmpty(msg)){
            TextView tv = findViewById(R.id.tv_load_dialog);
            tv.setText(msg);
        }*/

    }

}
