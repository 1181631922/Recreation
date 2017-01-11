package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.view.FullScreenVideoView;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class StartActivity extends BaseActivity {
    private SimpleDraweeView sdvStartImg;
    private FullScreenVideoView fullVideoView;
    private MediaController mediaController;
    private int width = 1080;
    private int height = 1920;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_start);
        isShowToolbar = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        width = MyUtils.getScreenWidth(this);
        height = MyUtils.getScreenHeight(this);

        initView();
        initData();
    }

    //初始化UI控件
    private void initView() {
        sdvStartImg = (SimpleDraweeView) findViewById(R.id.sdvStartImg);
        fullVideoView = (FullScreenVideoView) findViewById(R.id.fullVideoView);
    }

    //初始化数据
    private void initData() {
        float aspectRatio = width / height;
        if (FrescoDealPicUtil.isExist("http://upload-images.jianshu.io/upload_images/2155432-43f1b0fe0916918d.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240")) {
            FrescoUtil.loadGifPicOnNet(sdvStartImg, "http://upload-images.jianshu.io/upload_images/2155432-43f1b0fe0916918d.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
        } else {
            FrescoUtil.loadGifPicInApp(sdvStartImg, R.drawable.start, aspectRatio);
        }

        mediaController = new MediaController(this);
        fullVideoView.setMediaController(mediaController);
        if (!true) {
            sdvStartImg.setVisibility(View.GONE);
            fullVideoView.setVideoURI(Uri.parse("android.resource://com.fanyafeng.recreation/" + R.raw.login_video));
            fullVideoView.start();
            fullVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                }
            });
        }else {

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, FirstStartActivity.class));
                finish();
            }
        }, 1000);
    }

}
