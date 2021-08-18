package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.adapter.GridViewAddImgesAdpter;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.FieldEqueListBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.UploadPictures;
import com.tianjin.beichentiyu.presenter.contract.BaseContract;
import com.tianjin.beichentiyu.utils.ImageUtils;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;
import com.tianjin.beichentiyu.widget.MyGridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.shinichi.library.ImagePreview;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 黑瞳 on 2019/10/3 15:22
 * E-Mail Address：673236072@qq.com
 * 设备报修 - 上传信息
 */
public class UploadFacilityInfoActivity extends BaseMvpActivity<BaseContract.Presenter> {
    public static void toActivity(Context context, String fId) {
        Intent intent = new Intent(context, UploadFacilityInfoActivity.class);
        intent.putExtra("fId", fId);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rv_facility)
    RecyclerView mRvFacility;
    @BindView(R.id.tv_submit)
    TextView mTvSubmit;
    @BindView(R.id.myGridView)
    MyGridView myGridView;
    @BindView(R.id.et_content)
    EditText mEtContent;

    private BaseAdapter mAdapter;
    private String fId;//场地主键id
    private String type = "";//设备类型 0 可用， 1 维修中
    private List<FieldEqueListBean.ListBean> equeList = new ArrayList<>();//设备列表
    private int isEqueSelectPosition = -1; //选中的设备
    private int MAX_COUNT = 2;//最多可选2张图
    private int REQUEST_CODE = 0;
    private List<String> imgDatas;
    private List<String> upImgList = new ArrayList<>();
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private boolean isUpImg = false;
    private String sfeId;//记录点击的器械id


    @Override
    protected BaseContract.Presenter initPresenter() {
        return null;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_update_facility_info;
    }

    @Override
    protected void initData() {
        fId = getIntent().getStringExtra("fId");
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        initRvContent();
        inGridView();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        //mToolbar.setRightTv("XXX小区",R.color.color_398DEE);
        mTvSubmit.setOnClickListener(v ->
                //SubmitSucceedActivity.toActivity(this)
                upImg()
        );
    }

    @Override
    protected void firstRequest() {
        getFieldEqueList(fId, type);
    }

    private void initRvContent() {

        mAdapter = new BaseAdapter<FieldEqueListBean.ListBean>(R.layout.item_facility, equeList) {

            @Override
            protected void convert(BaseViewHolder holder, FieldEqueListBean.ListBean item) {
                holder.setTvText(R.id.tv_name, item.getE_name());
                if (holder.getLayoutPosition() == isEqueSelectPosition) {
                    holder.setBackgroundResource(R.id.iv_select, R.mipmap.icon_select_blue);
                } else {
                    holder.setBackgroundResource(R.id.iv_select, R.mipmap.radio_button);
                }
            }
        };
        mRvFacility.setLayoutManager(new LinearLayoutManager(this));
        mRvFacility.setAdapter(mAdapter);
        mAdapter.setItemClickListner(position -> {
            isEqueSelectPosition = position;
            sfeId = equeList.get(position).getSfe_id();
            mAdapter.notifyDataSetChanged();
        });
    }

    private void inGridView() {
        imgDatas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(imgDatas, this,2);
        gridViewAddImgesAdpter.setMaxImages(MAX_COUNT);
        myGridView.setAdapter(gridViewAddImgesAdpter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == imgDatas.size()) {
                    if (imgDatas.size() < MAX_COUNT) {
                        //ImageUtils.PhotoAlbum(UploadFacilityInfoActivity.this, true, false, true, REQUEST_CODE, MAX_COUNT, (ArrayList<String>) imgDatas);
                        ImageUtils.PhotoAlbum(UploadFacilityInfoActivity.this, true, REQUEST_CODE, MAX_COUNT, (ArrayList<String>) imgDatas);
                    } else {
                        //Base.showToast(UploadFacilityInfoActivity.this,"最多选择"+MAX_COUNT+"张图片");
                        ToastUtil.showToast("最多选择"+MAX_COUNT+"张图片");
                    }

                } else {
                    ImagePreview.getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                            .setContext(UploadFacilityInfoActivity.this)
                            // 从第几张图片开始，索引从0开始哦~
                            .setIndex(0)
                            .setImage(imgDatas.get(position))
                            .start();
                }
            }
        });
    }

    /**
     * 场地器械
     * @param fId
     * @param type
     */
    private void getFieldEqueList(String fId, String type) {
        LodingDialog lodingDialog = new LodingDialog(this, "获取设备...");
        lodingDialog.show();
        ApiManager.getBusinessService().getFieldEqueList(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(), fId, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<FieldEqueListBean>() {
                    @Override
                    protected void onSuccees(FieldEqueListBean bean) throws Exception {
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            equeList.addAll(bean.getList());
                            mAdapter.setData(equeList);
                        } else {
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
    private LodingDialog lodingDialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE && data != null) {

            //获取选择器返回的数据
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
//            ArrayList<String> images = data.getParcelableArrayListExtra(
//                    EasyPhotos.RESULT_PHOTOS);
            imgDatas.clear();
            for (int i = 0; i < resultPhotos.size(); i++) {
                imgDatas.add(resultPhotos.get(i).path);
            }
            gridViewAddImgesAdpter.notifyDataSetChanged(imgDatas);
        }
    }

    private void upImg(){
        if (isEqueSelectPosition == -1){
            ToastUtil.showToast("请选择设备");
            return;
        }
        if (imgDatas.size() == 0){
            ToastUtil.showToast("请上传损坏设备图片");
            return;
        }
        lodingDialog = new LodingDialog(this,"提交中...");
        lodingDialog.show();
        uploadHeadPortrait(0);

    }

    private void uploadHeadPortrait(int index){
        if(index >= imgDatas.size()){
            setLogRepair();
            return;
        }

        String img = imgDatas.get(index);
        Uri uri = Uri.fromFile(new File(img));
        new UploadPictures(this,uri)
                .setCallback(new UploadPictures.Callback() {
                    @Override
                    public void uploadedSuccess(String url, String path) {
                        Log.e("test","图片上传成功 网络地址:"+url + " 本地路径:" + path);
//                        imgDatas.add(img);
//                        gridViewAddImgesAdpter.notifyDataSetChanged();
                        upImgList.add(url);
                        uploadHeadPortrait(index + 1);
                    }

                    @Override
                    public void uploadedError(String msg) {
                        Log.e("test","图片上传失败 " + msg);
                        ToastUtil.showToast("图片上传失败!");
                        lodingDialog.dismiss();
                        //uploadHeadPortrait(index + 1);
                    }
                }).upload();
    }
    private void setLogRepair(){
        String img1 = "";
        String img2 = "";
        for (int i = 0; i < upImgList.size(); i++) {
            if ( i == 0){
                img1 = upImgList.get(i);
            }else if (i == 1){
                img2 = upImgList.get(i);
            }
        }
        ApiManager.getBusinessService().setLogRepair(AccountManager.getInstance().getAccount(),AccountManager.getInstance().getNonstr(),
                sfeId,img1,img2,mEtContent.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        //ToastUtil.showToast(bean.getMsg());
                        lodingDialog.dismiss();
                        if (bean.isSuccessful()) {
                            SubmitSucceedActivity.toActivity(UploadFacilityInfoActivity.this);
                            finish();
                        }else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                        lodingDialog.dismiss();
                    }
                });
    }

}
