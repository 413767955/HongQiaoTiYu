package com.tianjin.beichentiyu.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.heitong.frame.GlideApp;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.heitong.frame.base.fragment.BaseFragment;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.OrganizationBean;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.ui.activity.EntryActivity;
import com.tianjin.beichentiyu.ui.activity.OrganizationalInfoActivity;
import com.tianjin.beichentiyu.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 体育组织  协会 俱乐部
 */
public class AssociationFragment extends BaseFragment {

    public static final String ASSOCIATION_TYPE = "1";//协会
    public static final String CLUB_TYPE = "2";//俱乐部
    private static final String TYPE_KEY = "TYPE_KEY";

    @BindView(R.id.tv_nodata_view)
    TextView mNodataView;

    public static AssociationFragment newInstance(String type){
        AssociationFragment fragment = new AssociationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TYPE_KEY,type);
        fragment.setArguments(bundle);
        return fragment;
    }
    private String type;
    @BindView(R.id.recyclerView)
    RecyclerView mRvContent;
    @BindView(R.id.btn_confirm)
    Button btn_confirm;

    private BaseAdapter adapter;
    private List<OrganizationBean.ListBean> mList;


    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_association, container, false);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            type = bundle.getString(TYPE_KEY,ASSOCIATION_TYPE);
        }
    }

    @Override
    protected void firstRequest() {
        String tel = AccountManager.getInstance().getMemBean().getTel();
        String nonstr = AccountManager.getInstance().getMemBean().getNonstr();
        ApiManager.getBusinessService().getOrganizationList(tel, nonstr,type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<OrganizationBean>() {
                    @Override
                    protected void onSuccees(OrganizationBean bean) throws Exception {
                        if(bean.isSuccessful()) {
                            if (bean.getList().size() > 0) {
                                mList = bean.getList();
                                mNodataView.setVisibility(View.GONE);
                            }else {
                                mNodataView.setVisibility(View.VISIBLE);
                            }
                        }else{
                            mList = null;
                        }
                        adapter.setData(mList);
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this,mView);
        initRecyclerView();
    }

    private void initRecyclerView(){
        adapter = new BaseAdapter<OrganizationBean.ListBean>(R.layout.item_association,mList){
            @Override
            protected void convert(BaseViewHolder holder, OrganizationBean.ListBean item) {
                holder.setTvText(R.id.tv_name, item.getOrgName());
                holder.setTvText(R.id.tv_address, item.getBigOrg());
                ImageView ivAss = holder.getView(R.id.iv_association);
                GlideApp.with(getContext()).load(item.getOrgLogo()).error(R.drawable.icon_null_img).transition(new DrawableTransitionOptions().crossFade()).into(ivAss);
            }
        };
        adapter.setItemClickListner(position -> {
            OrganizationBean.ListBean bean = mList.get(position);
            OrganizationalInfoActivity.toActivity(getContext(),bean.getOrgId());
        });
        mRvContent.setLayoutManager(new GridLayoutManager(getContext(),3));
        //解决数据加载完成后, 没有停留在顶部的问题
        mRvContent.setFocusable(false);
        mRvContent.setAdapter(adapter);

    }

    @Override
    protected void bindEvent() {

        btn_confirm.setOnClickListener(v -> {
            EntryActivity.toActivity(getContext());
        });
    }
}
