package com.tianjin.beichentiyu.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by wjy on 2020/5/24
 * E-Mail Address: 673236072@qq.com
 * des:
 **/
public class YearPopupWindow extends PopupWindow {
    private String selectTime;
    private BaseAdapter.OnRecyclerViewItemDataClickListener<String> listener;

    public YearPopupWindow(Context context, String selectTime) {
        super(context);
        this.selectTime = selectTime;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.pop_year, null);
        RecyclerView rv_list = contentView.findViewById(R.id.rv_list);
        Calendar cd = Calendar.getInstance();
        List<String> timeList = new ArrayList<>();
        for (int i = 2010; i < cd.get(Calendar.YEAR)+1  ; i++) {
            timeList.add(i + "");

        }
        Collections.reverse(timeList);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        BaseAdapter adapter = new BaseAdapter<String>(R.layout.item_pop_year, timeList) {
            @Override
            protected void convert(BaseViewHolder holder, String item) {
                TextView tvTime = holder.getView(R.id.tv_time);
                View popTopLin = holder.getView(R.id.pop_top_lin);
                View popBottomLin = holder.getView(R.id.pop_bottom_lin);
                if (TextUtils.equals(selectTime, item)) {
                    tvTime.setTextColor(0xff5ac2f2);
                    popTopLin.setVisibility(View.VISIBLE);
                    popBottomLin.setVisibility(View.VISIBLE);
                } else {
                    tvTime.setTextColor(0xff979696);
                    popTopLin.setVisibility(View.GONE);
                    popBottomLin.setVisibility(View.GONE);
                }
                tvTime.setText(item);
            }
        };
        adapter.setItemDataClickListner((BaseAdapter.OnRecyclerViewItemDataClickListener<String>) (data, position) -> {
                    if (listener != null) {
                        listener.onItemClick(data, position);
                    }
                    dismiss();
                }
        );
        rv_list.setAdapter(adapter);

        if (timeList != null && !TextUtils.isEmpty(selectTime)) {
            for (int i = 0; i < timeList.size(); i++) {
                if (TextUtils.equals(selectTime, timeList.get(i))) {
                    rv_list.scrollToPosition(i);
                    break;
                }
            }
        }
        setContentView(contentView);
        setWidth(dip2px(context, 200));
        setHeight(dip2px(context, 300));
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent)));
    }

    public void setListener(BaseAdapter.OnRecyclerViewItemDataClickListener<String> listener) {
        this.listener = listener;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);

    }
}
