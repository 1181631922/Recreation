package com.fanyafeng.recreation.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.util.StringUtil;
import com.fanyafeng.recreation.widget.media.AndroidMediaController;
import com.fanyafeng.recreation.widget.media.IjkVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

//需要搭配baseactivity，这里默认为baseactivity,并且默认Baseactivity为包名的根目录
public class PlayVideoActivity extends BaseActivity {
    private boolean backPressed;
    private IjkVideoView videoPlayView;
    private String videoUrl;
    private AndroidMediaController androidMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
//这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_play_video);
        isShowToolbar = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        androidMediaController = new AndroidMediaController(this, false);
//        androidMediaController.setSupportActionBar(actionBar);

        videoUrl = getIntent().getStringExtra("videoUrl");

        initView();
        initData();

    }

    //初始化UI空间
    private void initView() {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");


        videoPlayView = (IjkVideoView) findViewById(R.id.videoPlayView);
        videoPlayView.setMediaController(androidMediaController);
//http://58.254.132.66/hc.yinyuetai.com/uploads/videos/common/4630015876835EBDA2F3F4C4D857873A.mp4?sc=510e0f8052f7ed36&br=780&rd=Android&uniqueId=35314df2291a7ba05851ec60beef5a35
        String video = "http://58.254.132.66/hc.yinyuetai.com/uploads/videos/common/4630015876835EBDA2F3F4C4D857873A.mp4?sc=510e0f8052f7ed36&br=780&rd=Android&uniqueId=35314df2291a7ba05851ec60beef5a35";


        videoPlayView.setVideoPath(videoUrl);
        videoPlayView.start();
    }

    //初始化数据
    private void initData() {

    }


    @Override
    public void onBackPressed() {
        backPressed = true;
        super.onBackPressed();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoPlayView.getCurrentPosition() > 0) {
            videoPlayView.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backPressed || videoPlayView.isBackgroundPlayEnabled()) {
            videoPlayView.stopPlayback();
            videoPlayView.release(true);
            videoPlayView.stopBackgroundPlay();
        } else {
            videoPlayView.stopBackgroundPlay();
        }
        videoPlayView.pause();
        IjkMediaPlayer.native_profileEnd();
    }
}
