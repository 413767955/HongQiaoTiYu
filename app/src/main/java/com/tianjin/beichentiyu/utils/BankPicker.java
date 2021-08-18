package com.tianjin.beichentiyu.utils;

import android.app.Activity;

import com.tianjin.beichentiyu.bean.MemberBankBean;

import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

/**
 * 提现选择银行
 *
 */
public class BankPicker extends SinglePicker<MemberBankBean.ListBean> {

    public BankPicker(Activity activity, MemberBankBean.ListBean[] items) {
        super(activity, items);
    }

    public BankPicker(Activity activity, List<MemberBankBean.ListBean> items) {
        super(activity, items);
    }

    public void setOnOptionPickListener(OnOptionPickListener listener) {
        super.setOnItemPickListener(listener);
    }

    public void setOnWheelListener(OnWheelListener onWheelListener) {
        super.setOnWheelListener(onWheelListener);
    }

    public interface OnWheelListener extends SinglePicker.OnWheelListener<MemberBankBean.ListBean> {

    }

    public static abstract class OnOptionPickListener implements OnItemPickListener<MemberBankBean.ListBean> {

        public abstract void onOptionPicked(int index, MemberBankBean.ListBean item);

        @Override
        public final void onItemPicked(int index, MemberBankBean.ListBean item) {
            onOptionPicked(index, item);
        }

    }

}
