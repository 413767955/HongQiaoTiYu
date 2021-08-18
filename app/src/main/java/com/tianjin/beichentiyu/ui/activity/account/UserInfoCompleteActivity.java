package com.tianjin.beichentiyu.ui.activity.account;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.heitong.frame.GlideApp;
import com.heitong.frame.base.activity.BaseActivity;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.MemBean;
import com.tianjin.beichentiyu.dialog.LodingDialog;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.manager.UploadPictures;
import com.tianjin.beichentiyu.ui.MainActivity;
import com.tianjin.beichentiyu.utils.ImageUtils;
import com.tianjin.beichentiyu.utils.ToastUtil;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserInfoCompleteActivity extends BaseActivity {
    private static int COMPLETE_INFORMATION_TYPE = 0;//完善信息
    private static int CHANGE_INFORMATION_TYPE = 1;//修改信息

    public static void toCompleteInfoActivity(Context context) {
        toActivity(context, COMPLETE_INFORMATION_TYPE);
    }

    public static void toChangeInfoActivity(Context context) {
        toActivity(context, CHANGE_INFORMATION_TYPE);
    }

    public static void toActivity(Context context, int type) {
        Intent intent = new Intent(context, UserInfoCompleteActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }


    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    //头像
    @BindView(R.id.rel_head_portrait)
    RelativeLayout mRelHeadPortrait;
    @BindView(R.id.civ_head)
    CircleImageView mCivHead;
    //昵称
    @BindView(R.id.rel_nick_name)
    RelativeLayout mRelNickName;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    //性别
    @BindView(R.id.rel_sex)
    RelativeLayout mRelSex;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    //生日
    @BindView(R.id.rel_birthday)
    RelativeLayout mRelBirthday;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    //区域
    @BindView(R.id.rel_address)
    RelativeLayout mRelAddress;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    //家乡
    @BindView(R.id.rel_hometown)
    RelativeLayout mRelHometown;
    @BindView(R.id.tv_hometown)
    TextView mTvHometown;

    private static int REQUEST_CODE = 101;
    private static int MAX_COUNT = 1;

    private LodingDialog lodingDialog;

    private int type = COMPLETE_INFORMATION_TYPE;

    @Override
    protected void initData() {
        type = getIntent().getExtras().getInt("type", COMPLETE_INFORMATION_TYPE);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_userinfo_complete;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        if (type == COMPLETE_INFORMATION_TYPE) {
            AccountManager.getInstance().loggedOut();
        }
        super.onBackPressed();
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> {
            if (type == COMPLETE_INFORMATION_TYPE) {
                AccountManager.getInstance().loggedOut();
            }
            finish();
        });
        //完善信息才有完成选项
        if (type == COMPLETE_INFORMATION_TYPE) {
            mToolbar.setMyTitle("完善信息");
            mToolbar.setRightTv("完成", R.color.color_161616);
            mToolbar.setRightOnClick(v -> {
                if (AccountManager.getInstance().isCompleteInformation()) {
                    MainActivity.toActivity(this);
                }
            });
        } else {
            mToolbar.setMyTitle("个人信息");
        }
        mRelHeadPortrait.setOnClickListener(v -> {
            //ImageUtils.PhotoAlbum(this, true, true, true, REQUEST_CODE, MAX_COUNT, null);
            ImageUtils.PhotoAlbum(this, true, REQUEST_CODE, MAX_COUNT, null);

        });
        mRelNickName.setOnClickListener(v -> {
            NickNameActivity.toActivity(this);
        });
        mRelSex.setOnClickListener(v -> {
            SexActivity.toActivity(this);
        });
        mRelBirthday.setOnClickListener(v -> {
            BirthdayActivity.toActivity(this);
        });
        mRelAddress.setOnClickListener(v -> {
            SelectAddressActivity.toActicity(this);
        });
        mRelHometown.setOnClickListener(v -> HometownActivity.toActicity(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        MemBean memBean = AccountManager.getInstance().getMemBean();
        if (!TextUtils.isEmpty(memBean.getHeadImg())) {
            GlideApp.with(UserInfoCompleteActivity.this)
                    .load(memBean.getHeadImg())
                    .placeholder(R.drawable.icon_head_pic)
                    .error(R.drawable.icon_head_pic)
                    //.transition(new DrawableTransitionOptions().crossFade())
                    .into(mCivHead);
        }
        mTvNickName.setText(memBean.getNickName());
        if ("1".equals(memBean.getSex())) {
            mTvSex.setText("男");
        } else if ("2".equals(memBean.getSex())) {
            mTvSex.setText("女");
        } else {
            mTvSex.setText("未设置");
        }
        mTvBirthday.setText(memBean.getBirthday());
        mTvHometown.setText(memBean.getHometownName());
        mTvAddress.setText(memBean.getLivingPlaceName());
    }

    /**
     * 上传头像
     *
     * @param uri
     */
    private void uploadHeadPortrait(Uri uri) {
        lodingDialog = new LodingDialog(this, "头像上传中...");
        lodingDialog.show();
        new UploadPictures(this, uri)
                .setCallback(new UploadPictures.Callback() {
                    @Override
                    public void uploadedSuccess(String url, String path) {
                        Log.e("test", "图片上传成功 网络地址:" + url + " 本地路径:" + path);
                        Uri uri = Uri.fromFile(new File(path));
                        GlideApp.with(UserInfoCompleteActivity.this).load(uri).into(mCivHead);
                        lodingDialog.dismiss();
                        saveHead(url);
                    }

                    @Override
                    public void uploadedError(String msg) {
                        Log.e("test", "图片上传失败 " + msg);
                        ToastUtil.showToast("头像上传失败!");
                        lodingDialog.dismiss();
                    }
                }).upload();
    }

    /**
     * 保存昵称
     */
    private void saveHead(String url) {
        if (TextUtils.isEmpty(url)) {
            ToastUtil.showToast("头像上传失败!");
            return;
        }
        lodingDialog = new LodingDialog(this, "设置头像中...");
        lodingDialog.show();
        MemBean memBean = AccountManager.getInstance().getMemBean();
        String tel = memBean.getTel();
        String nonstr = memBean.getNonstr();
        String headImg = url;
        ApiManager.getAccountService().finishMemHeadImg(tel, nonstr, headImg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<BaseRespBean>() {
                    @Override
                    protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                        lodingDialog.dismiss();
                        if (baseRespBean.isSuccessful()) {
                            AccountManager.getInstance().getMemBean().setHeadImg(headImg);
                            AccountManager.getInstance().saveMenBean();
                        } else {
                            ToastUtil.showToast(baseRespBean.getMsg());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        lodingDialog.dismiss();
                        ToastUtil.showToast("头像设置失败，请检查网络!");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //获取选择器返回的数据
            try {
//                ArrayList<String> images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
//                boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
//                Uri uri = Uri.fromFile(new File(images.get(0)));

                ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
                uploadHeadPortrait(resultPhotos.get(0).uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
