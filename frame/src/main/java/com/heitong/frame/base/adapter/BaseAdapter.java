package com.heitong.frame.base.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黑瞳 on 2019/8/10 00:32
 * E-Mail Address：673236072@qq.com
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    protected List<T> mData;
    protected int mLayoutResId;
    protected Resources mResources;
    protected Context mContext;
    protected OnRecyclerViewItemClickListener mListener;
    protected OnRecyclerViewItemDataClickListener mDataListener;

    public BaseAdapter(int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseAdapter(int layoutResId) {
        this(layoutResId, new ArrayList<T>());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        mResources = mContext.getResources();
        View item = LayoutInflater.from(mContext).inflate(mLayoutResId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(item);

        if (mListener != null) {
            holder.itemView.setOnClickListener(view -> mListener.onItemClick(holder.getLayoutPosition()));
        }

        if (mDataListener != null) {
            holder.itemView.setOnClickListener(v -> mDataListener.onItemClick(getData(holder.getLayoutPosition()),holder.getLayoutPosition()));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        convert(holder, mData.get(position));
    }

    public void setData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void add(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void add(T data, int position) {
        this.mData.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        this.mData.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public T getData(int position) {
        return mData.get(position);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(int position);
    }
    public interface OnRecyclerViewItemDataClickListener<S> {
        void onItemClick(S data,int position);
    }
    public void setItemClickListner(OnRecyclerViewItemClickListener listner) {
        this.mListener = listner;
    }
    public void setItemDataClickListner(OnRecyclerViewItemDataClickListener listner) {
        this.mDataListener = listner;
    }

    protected abstract void convert(BaseViewHolder holder, T item);


}

