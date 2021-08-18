package com.tianjin.beichentiyu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.heitong.frame.base.adapter.BaseAdapter;
import com.heitong.frame.base.adapter.BaseViewHolder;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.api.BaseObserver;
import com.tianjin.beichentiyu.bean.BaseRespBean;
import com.tianjin.beichentiyu.bean.LoginBean;
import com.tianjin.beichentiyu.manager.UploadPictures;
import com.tianjin.beichentiyu.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {
    public static void toActivity(Context context){
        context.startActivity(new Intent(context, TestActivity.class));
    }
    private TextView mTv;
    private RecyclerView mRv;
    private List<Pair<String,View.OnClickListener>> mList;

    private String nonstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mTv = findViewById(R.id.tv);
        mRv = findViewById(R.id.rv);
        initButtons();
        initRv();
    }
    private void initButtons(){
        BaseObserver baseObserver =new BaseObserver<Object>() {
            @Override
            protected void onSuccees(Object baseRespBean) throws Exception {
                mTv.setText(new Gson().toJson(baseRespBean));
            }

            @Override
            protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                mTv.setText(new Gson().toJson(e.toString()+" "+isNetWorkError));
            }
        };
        mList = new ArrayList<>();
        mList.add(new Pair<>("发送注册验证码", v -> {
            ApiManager.getAccountService().sendPhone("13559172333", Constant.Sms.REGISTER)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<BaseRespBean>() {
                        @Override
                        protected void onSuccees(BaseRespBean baseRespBean) throws Exception {
                            mTv.setText(new Gson().toJson(baseRespBean));
                        }

                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            mTv.setText(new Gson().toJson(e.toString()+" "+isNetWorkError));
                        }
                    });
        }));
        mList.add(new Pair<>("注册", v -> {
            ApiManager.getAccountService().register("13559172333", "123456","123")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("登陆", v -> {
            ApiManager.getAccountService().login("13559172333", "123456",123d,123d)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<LoginBean>() {
                        @Override
                        protected void onSuccees(LoginBean loginBean) throws Exception {
                            nonstr = loginBean.getMem().getNonstr();
                            mTv.setText(loginBean.toString());
                        }
                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        }
                    });
        }));
        mList.add(new Pair<>("文章列表",v -> {
            ApiManager.getBusinessService().getArticleList("13559172333", nonstr,"1","1","",1,"")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("文章详情",v -> {
            ApiManager.getBusinessService().getArtileMsg("13559172333", nonstr,"4")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
   /*     mList.add(new Pair<>("活动列表接口",v -> {
            ApiManager.getBusinessService().getActivityPage("13559172333", nonstr,1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));*/
        mList.add(new Pair<>("活动详情",v -> {
            ApiManager.getBusinessService().getActivityMsg("13559172333", nonstr,"1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("活动报名",v -> {
            ApiManager.getBusinessService().userEnrollActivity("13559172333", nonstr,"1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));


        mList.add(new Pair<>("智能健身或场馆页面推荐",v -> {
            ApiManager.getBusinessService().getNearFieldPageHot("13559172333", nonstr,5,"1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("场地详情",v -> {
            ApiManager.getBusinessService().getFieldMsg("13559172333", nonstr,"1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("场地图片",v -> {
            ApiManager.getBusinessService().getFieldImgList("13559172333", nonstr,"1")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("场地器械",v -> {
            ApiManager.getBusinessService().getFieldEqueList("13559172333", nonstr,"1","0")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("报修记录",v -> {
            ApiManager.getBusinessService().getMemSetRepairPage("13559172333", nonstr,1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(baseObserver);
        }));
        mList.add(new Pair<>("上传图片",v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        }));
    }
    private void initRv(){
        mRv.setLayoutManager(new GridLayoutManager(this,2));
        mRv.setAdapter(new BaseAdapter<Pair<String,View.OnClickListener>>(R.layout.item_test,mList) {
            @Override
            protected void convert(BaseViewHolder holder, Pair<String,View.OnClickListener> item) {
                MaterialButton mb = holder.getView(R.id.mb);
                mb.setText(item.first);
                mb.setOnClickListener(item.second);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null){
            Uri uri = data.getData();
            new UploadPictures(this,uri)
                    .setCallback(new UploadPictures.Callback() {
                        @Override
                        public void uploadedSuccess(String url, String path) {
                            Log.e("test","图片上传成功 网络地址:"+url + " 本地路径:" + path);
                        }

                        @Override
                        public void uploadedError(String msg) {
                            Log.e("test","图片上传失败 " + msg);
                        }
                    }).upload();
        }
    }
}
