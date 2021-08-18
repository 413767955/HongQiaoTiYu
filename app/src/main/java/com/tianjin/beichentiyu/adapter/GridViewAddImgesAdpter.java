package com.tianjin.beichentiyu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.donkingliang.imageselector.utils.UriUtils;
import com.heitong.frame.GlideApp;
import com.tianjin.beichentiyu.R;

import java.util.List;

/**
 * com.bm.falvzixun.adapter.GridViewAddImgAdpter
 *
 * @author yuandl on 2015/12/24.
 *         添加上传图片适配器
 */
public class GridViewAddImgesAdpter extends BaseAdapter {
    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;
    /**
     * 可以动态设置最多上传几张，之后就不显示+号了，用户也无法上传了
     * 默认9张
     */
    private int maxImages = 2;

    public GridViewAddImgesAdpter(List<String> datas, Context context,int maxImages) {
        this.datas = datas;
        this.context = context;
        this.maxImages = maxImages;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 获取最大上传张数
     *
     * @return
     */
    public int getMaxImages() {
        return maxImages;
    }

    /**
     * 设置最大上传张数
     *
     * @param maxImages
     */
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    /**
     * 让GridView中的数据数目加1最后一个显示+号
     *
     * @return 返回GridView中的数量
     */
    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
//        if (count >=maxImages) {
//            return datas.size();
//        } else {
            return count;
//        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void notifyDataSetChanged(List<String> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (datas != null && position < datas.size()) {
            GlideApp.with(context)
                    .load(UriUtils.getImageContentUri(context, datas.get(position)))
                    .into(viewHolder.ivimage);
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datas.remove(position);
                    notifyDataSetChanged();
                    //((UploadFacilityInfoActivity)context).refreshImgNum(datas.size());
                }
            });
        } else {
            RequestOptions options = new RequestOptions()
                    .placeholder(R.mipmap.icon_add_picture)//图片加载出来前，显示的图片
                    .fallback( R.mipmap.icon_add_picture) //url为空的时候,显示的图片
                    .error(R.mipmap.icon_add_picture)//图片加载失败后，显示的图片
                    .diskCacheStrategy(DiskCacheStrategy.NONE);
            GlideApp.with(context)
                    .load(R.mipmap.icon_add_picture)
                    .apply(options)
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.btdel.setVisibility(View.GONE);
        }

        return convertView;

    }

    public class ViewHolder {
        public final ImageView ivimage;
        public final TextView btdel;
        public final View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.iv_image);
            btdel = (TextView) root.findViewById(R.id.bt_del);
            this.root = root;
        }
    }
}
