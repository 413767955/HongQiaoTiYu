package com.tianjin.beichentiyu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import com.heitong.frame.base.activity.BaseActivity;
import com.tianjin.beichentiyu.R;
import com.tianjin.beichentiyu.api.ApiManager;
import com.tianjin.beichentiyu.manager.AccountManager;
import com.tianjin.beichentiyu.widget.CustomToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 健身指导播放视频
 */
public class GuidanceVideoActivity extends BaseActivity {
    public static void toActivity(Context context, String aId) {
        Intent intent = new Intent(context, GuidanceVideoActivity.class);
        //intent.putExtra("url", videoURL);
        intent.putExtra("aId",aId);
        context.startActivity(intent);
    }

    @BindView(R.id.toolbar)
    CustomToolbar mToolbar;
//    @BindView(R.id.videoView)
//    VideoView mVideoView;
    @BindView(R.id.webView)
    WebView mWebView;

    private String videoURL;
    private String aId;
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_guidance_video;
    }


    @Override
    protected void bindView() {
        ButterKnife.bind(this);
        //videoURL = getIntent().getStringExtra("url");
        aId = getIntent().getStringExtra("aId");
        initToolbar();
        initVideo();
        //showSportsVideo(aId);
    }

    private void initToolbar() {
        mToolbar.setLeftOnClick(v -> finish());
    }

    private void initVideo() {

        mWebView.loadUrl(ApiManager.getURL()+"appSixEdge/showSportsVideo?id="+aId+"&tel="+AccountManager.getInstance().getAccount());
//        if (videoURL.isEmpty()) {
//            return;
//        }
        //base64编码
//        String strBase64 = Base64.encodeToString(videoURL.getBytes(), Base64.DEFAULT);
//        LogUtils.e("编码",strBase64);
        //base64解码
//        String url = new String(Base64.decode(videoURL.getBytes(), Base64.DEFAULT));
//        MediaController localMediaController = new MediaController(this);
//        mVideoView.setMediaController(localMediaController);
//        mVideoView.setVideoPath(url);
//        mVideoView.start();
        //mVideoView.setVideoURI(Uri.parse(videoURL));
    }
}
