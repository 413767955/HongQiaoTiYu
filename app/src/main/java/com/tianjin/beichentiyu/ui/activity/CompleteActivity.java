package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;

import com.blankj.utilcode.util.LogUtils;
import com.heitong.frame.base.activity.BaseMvpActivity;
import com.heitong.frame.base.presenter.impl.IPresenter;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.adapter.GridViewAddImgesAdpter;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.dialog.OneButtonDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.UploadPictures;
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
 * 任务结果反馈
 * 完工
 */
public class CompleteActivity extends BaseMvpActivity {
    public static void toActivity(Context context, String logId) {
        Intent intent = new Intent(context, CompleteActivity.class);
        intent.putExtra("logId", logId);
        ((Activity)context).startActivityForResult(intent,InspectionMaintenanceActivity.REQUEST_CODE);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.rb_state0)
    RadioButton mRbState0;
    @BindView(R.id.rb_state1)
    RadioButton mRbState1;
    @BindView(R.id.rb_state2)
    RadioButton mRbState2;
    @BindView(R.id.myGridView)
    MyGridView myGridView;
    @BindView(R.id.btn_confirm)
    Button mButConfirm;

    private String logId;
    private int isState = -1;
    private GridViewAddImgesAdpter gridViewAddImgesAdpter;
    private int MAX_COUNT = 2;//最多可选2张图
    private int REQUEST_CODE = 0;
    private List<String> imgDatas;
    private List<String> upImgList = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_complete;
    }

    @Override
    protected IPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        logId = getIntent().getStringExtra("logId");
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        inGridView();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
        mRbState0.setOnCheckedChangeListener((buttonView, isChecked) -> onRadioButtonClicked(buttonView));
        mRbState1.setOnCheckedChangeListener((buttonView, isChecked) -> onRadioButtonClicked(buttonView));
        mRbState2.setOnCheckedChangeListener((buttonView, isChecked) -> onRadioButtonClicked(buttonView));

        mButConfirm.setOnClickListener(v -> confirm());
    }

    LodingDialog lodingDialog;

    private void confirm() {
        if (isState == -1) {
            ToastUtil.showToast("请选择维修状态");
            return;
        }
        if (imgDatas.size() == 0) {
            ToastUtil.showToast("请上传设备图片");
            return;
        }
        lodingDialog = new LodingDialog(this, "提交中...");
        lodingDialog.show();
        uploadHeadPortrait(0);

    }

    /**
     * 上传图片
     *
     * @param index
     */
    private void uploadHeadPortrait(int index) {
        if (index >= imgDatas.size()) {
            toDoTheTaskResult();
            return;
        }
        String img = imgDatas.get(index);
        Uri uri = Uri.fromFile(new File(img));
        new UploadPictures(this, uri)
                .setCallback(new UploadPictures.Callback() {
                    @Override
                    public void uploadedSuccess(String url, String path) {
                        upImgList.add(url);
                        uploadHeadPortrait(index + 1);
                    }

                    @Override
                    public void uploadedError(String msg) {
                        ToastUtil.showToast("图片上传失败!");
                    }
                }).upload();
    }

    /**
     * 任务结果反馈接口
     */
    private void toDoTheTaskResult() {
        String img1 = "";
        String img2 = "";
        for (int i = 0; i < upImgList.size(); i++) {
            if (i == 0) {
                img1 = upImgList.get(i);
            } else if (i == 1) {
                img2 = upImgList.get(i);
            }
        }
        ApiManager.getBusinessService().toDoTheTaskResult(AccountManager.getInstance().getAccount(), AccountManager.getInstance().getNonstr(),
                logId, isState, img1, img2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean bean) throws Exception {
                        if (bean.isSuccessful()) {
                            lodingDialog.dismiss();

                            new OneButtonDialog(CompleteActivity.this)
                                    .setMsg("提交成功")
                                    .confirmClick("确认", v -> {
                                                setResult(RESULT_OK);
                                                finish();
                                            }
                                    )
                                    .show();
                        } else {
                            ToastUtil.showToast(bean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        ToastUtil.showToast(R.string.request_failure);
                    }
                });
    }

    private void inGridView() {
        imgDatas = new ArrayList<>();
        gridViewAddImgesAdpter = new GridViewAddImgesAdpter(imgDatas, this, 2);
        gridViewAddImgesAdpter.setMaxImages(MAX_COUNT);
        myGridView.setAdapter(gridViewAddImgesAdpter);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == imgDatas.size()) {
                    if (imgDatas.size() < MAX_COUNT) {
                        ImageUtils.PhotoAlbum(CompleteActivity.this, true, REQUEST_CODE, MAX_COUNT, (ArrayList<String>) imgDatas);
                    } else {
                        //Base.showToast(UploadFacilityInfoActivity.this,"最多选择"+MAX_COUNT+"张图片");
                        ToastUtil.showToast("最多选择" + MAX_COUNT + "张图片");
                    }

                } else {
                    ImagePreview.getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                            .setContext(CompleteActivity.this)
                            // 从第几张图片开始，索引从0开始哦~
                            .setIndex(0)
                            .setImage(imgDatas.get(position))
                            .start();
                }
            }
        });
    }


    private void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        switch (view.getId()) {
            case R.id.rb_state0:
                if (isChecked) {
                    //Toast.makeText(MainActivity.this, button.getText(), 1).show();
                    LogUtils.e(mRbState0.getText().toString());
                    isState = 0;
                    mRbState0.setButtonDrawable(R.mipmap.radio_button_selected);
                } else {
                    mRbState0.setButtonDrawable(R.mipmap.radio_button);
                }
                break;
            case R.id.rb_state1:
                if (isChecked) {
                    //Toast.makeText(MainActivity.this, button.getText(), 1).show();
                    LogUtils.e(mRbState1.getText().toString());
                    isState = 1;
                    mRbState1.setButtonDrawable(R.mipmap.radio_button_selected);
                } else {
                    mRbState1.setButtonDrawable(R.mipmap.radio_button);
                }
                break;
            case R.id.rb_state2:
                if (isChecked) {
                    //Toast.makeText(MainActivity.this, button.getText(), 1).show();
                    LogUtils.e(mRbState2.getText().toString());
                    isState = 2;
                    mRbState2.setButtonDrawable(R.mipmap.radio_button_selected);
                } else {
                    mRbState2.setButtonDrawable(R.mipmap.radio_button);
                }
                break;
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
//            ArrayList<String> images = data.getStringArrayListExtra(
//                    ImageSelector.SELECT_RESULT);
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            imgDatas.clear();
            for (int i = 0; i < resultPhotos.size(); i++) {
                imgDatas.add(resultPhotos.get(i).path);
            }
            gridViewAddImgesAdpter.notifyDataSetChanged(imgDatas);
        }
    }
}
