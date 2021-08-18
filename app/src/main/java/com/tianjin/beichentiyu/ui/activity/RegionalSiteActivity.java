package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.FieldListByDisIdBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/3 15:05
 * E-Mail Address：673236072@qq.com
 * 区域场地列表
 */
public class RegionalSiteActivity extends BaseMvpActivity<BaseContract.Presenter> implements BaseContract.View {
    public static void toActivity(Context context,String disId){
        Intent intent = new Intent(context, RegionalSiteActivity.class);
        intent.putExtra("disId",disId);
        context.startActivity(intent);
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.tv_nodata_view)
    TextView mTvNodataView;

    private String disId = "";
    private BaseAdapter mAdapter;
    private List<FieldListByDisIdBean.ListBean> mList = new ArrayList<>();

    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_regional_site;
    }

    @Override
    protected void initData() {
       disId = getIntent().getStringExtra("disId");
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRvContent();
        getFieldListByDisId("31");
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());

    }

    private void initRvContent(){

        mAdapter = new BaseAdapter<FieldListByDisIdBean.ListBean>(R.layout.item_regional_site_list,mList){

            @Override
            protected void convert(BaseViewHolder holder, FieldListByDisIdBean.ListBean item) {
                holder.setTvText(R.id.tv_title,item.getFieldName());
                holder.setTvText(R.id.tv_address,item.getAddress());

                GlideApp.with(mContext).load(item.getBaseImg()).placeholder(R.drawable.icon_field_err).error(R.drawable.icon_field_err).transition(new DrawableTransitionOptions().crossFade()).into((ImageView) holder.getView(R.id.iv_img));
            }
        };
        mAdapter.setItemClickListner(position -> {
            //UploadFacilityInfoActivity.toActivity(this);
            UploadFacilityInfoActivity.toActivity(this,mList.get(position).getId());
        });
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mRvContent.setAdapter(mAdapter);
    }

    private void getFieldListByDisId(String disId){
        if (TextUtils.isEmpty(disId)){
            ToastUtil.showToast("获取失败");
            return;
        }
        LodingDialog lodingDialog = new LodingDialog(this,"加载中...");
        lodingDialog.show();
        ApiManager.getBusinessService().getFieldListByDisId(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),disId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldListByDisIdBean>() {
                    @Override
                    protected void onSuccees(FieldListByDisIdBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()){
                            if (bean.getList().size() > 0){
                                mList.addAll(bean.getList());
                                mAdapter.setData(mList);
                                mRvContent.setVisibility(View.VISIBLE);
                                mTvNodataView.setVisibility(View.GONE);
                            }else {
                                mRvContent.setVisibility(View.GONE);
                                mTvNodataView.setVisibility(View.VISIBLE);
                            }

                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

}
