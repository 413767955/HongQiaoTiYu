package com.tianjin.beichentiyu.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.tianjin.beichentiyu.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class BottomDialogFr extends DialogFragment {

    private View frView;
    private Window window;
    private View.OnClickListener aliPayClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        frView = inflater.inflate(R.layout.bottom_dialog, null);
        return frView;
    }

    @Override
    public void onStart() {
        super.onStart();
        window = getDialog().getWindow();
        // 如果不设置这句代码, 那么弹框就会与四边都有一定的距离
        window.setBackgroundDrawableResource(android.R.color.transparent);
        // 设置动画
        window.setWindowAnimations(R.style.bottomDialog);

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 如果不设置宽度,那么即使你在布局中设置宽度为 match_parent 也不会起作用
        params.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
        frView.findViewById(R.id.alipay).setOnClickListener(view -> {
            aliPayClickListener.onClick(view);
            dismiss();
        });
        frView.findViewById(R.id.cancel).setOnClickListener(view -> {
            dismiss();
        });


    }
    public BottomDialogFr aliPayClick( View.OnClickListener listener){
        aliPayClickListener = listener;
        return this;
    }
}
