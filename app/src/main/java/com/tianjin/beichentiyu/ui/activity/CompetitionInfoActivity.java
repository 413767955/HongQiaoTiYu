package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heitong.frame.base.activity.BaseMvpActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.bean.ArtileMsgBean;
import com.tianjin.beichentiyu.presenter.CompetitionInfoPresenter;
import com.tianjin.beichentiyu.presenter.contract.CompetitionInfoContract;
import com.tianjin.beichentiyu.utils.Constant;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 赛事详情
 * 资讯详情
 */
public class CompetitionInfoActivity extends BaseMvpActivity<CompetitionInfoContract.Presenter> implements CompetitionInfoContract.View {
    private static final String TYPE_KEY = "TYPE_KEY";
    private static final String ID_KEY = "ID_KEY";

    public static void toActivity(Context context,int type,String aId){
        Intent intent = new Intent(context,CompetitionInfoActivity.class);
        intent.putExtra(TYPE_KEY,type);
        intent.putExtra(ID_KEY,aId);
        context.startActivity(intent);
    }
    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
    @BindView(R.id.lin_content)
    LinearLayout mLinContent;
    @BindView(R.id.tv_title)
    TextView mTeTitle;
    @BindView(R.id.tv_see)
    TextView mSee;
    @BindView(R.id.iv_cover)
    ImageView mIvCover;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.wv)
    WebView mWebView;

    private String aId;
    private int type;
    private ArtileMsgBean bean = new ArtileMsgBean();

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_competition_info;
    }

    @Override
    protected CompetitionInfoContract.Presenter initPresenter() {
        return new CompetitionInfoPresenter();
    }

    @Override
    protected void initData() {
        type = getIntent().getIntExtra(TYPE_KEY,Constant.Information.SYXW);
        aId = getIntent().getStringExtra(ID_KEY);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        WebSettings webSettings = mWebView.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        initToolbar();
    }

    private void initToolbar(){
        if (type == Constant.Information.SSXQ){
            mToolbar.setMyTitle("赛事详情");
        }else if (type == Constant.Information.PTGG){
            mToolbar.setMyTitle("公告详情");
        }else if (type == Constant.Information.XWZX || type == Constant.Information.SYXW){
            mToolbar.setMyTitle("资讯");
        }
    }

    @Override
    protected void bindEvent() {
        mToolbar.setLeftOnClick(v -> finish());
    }


    @Override
    protected void firstRequest() {
        mPresenter.loadArtileMsg(aId);
    }

    @Override
    public void addData(ArtileMsgBean bean) {
        this.bean = bean;

        //mWebView.loadData(bean.getArt().getContent(),"text/html; charset=UTF-8", null);//这种写法可以正确
        mWebView.loadUrl(bean.getShowArtUrl());
        boolean isCollection = TextUtils.equals("0",bean.getCollectState());
        mToolbar.initCollectionView(isCollection,aId,type);
        /*mTeTitle.setText(bean.getArt().getTitle());
        mSee.setText(bean.getArt().getSeeNum()+"次阅读");
        mTvContent.setText(bean.getArt().getContent());

        GlideApp.with(CompetitionInfoActivity.this).load(bean.getArt().getImgUrl()).transition(new DrawableTransitionOptions().crossFade()).into(mIvCover);
       *//* GlideApp.with(CompetitionInfoActivity.this)
                .asBitmap()
                .load(bean.getArt().getImgUrl())
                .dontAnimate().diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mIvCover.getLayoutParams();
                        layoutParams.height = (int) (layoutParams.width /((float)resource.getWidth())*((float)resource.getHeight()));
                        mIvCover.setLayoutParams(layoutParams);
                        mIvCover.setImageBitmap(resource);
                        return true;
                    }
                })
                .into(mIvCover);//加载原图大*/
    }
}
