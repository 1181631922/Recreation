package com.fanyafeng.recreation.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    private String href;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
//这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_play_video);
        isShowToolbar = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        href = getIntent().getStringExtra("href");

        initView();
//        initData();

        Thread thread = new Thread(new GetRealVideoThread());
        thread.start();
    }

    //初始化UI空间
    private void initView() {
//        IjkMediaPlayer.loadLibrariesOnce(null);
//        IjkMediaPlayer.native_profileBegin("libijkplayer.so");


        videoPlayView = (IjkVideoView) findViewById(R.id.videoPlayView);
//http://58.254.132.66/hc.yinyuetai.com/uploads/videos/common/4630015876835EBDA2F3F4C4D857873A.mp4?sc=510e0f8052f7ed36&br=780&rd=Android&uniqueId=35314df2291a7ba05851ec60beef5a35
        String video = "http://58.254.132.66/hc.yinyuetai.com/uploads/videos/common/4630015876835EBDA2F3F4C4D857873A.mp4?sc=510e0f8052f7ed36&br=780&rd=Android&uniqueId=35314df2291a7ba05851ec60beef5a35";
        playVideo(video);
        getData();

    }

    //初始化数据
    private void initData() {
        try {
            Document document = Jsoup.connect("http://pl.yinyuetai.com/playlist_promo/1").get();
            //列表的单个项的网页链接
            Elements itemElement = document.getElementsByClass("pl_img");

//            http://www.yinyuetai.com/api/info/get-video-urls?callback=callback&videoId=".$song_id."&_=".$time
//            http://www.yinyuetai.com/api/info/get-video-urls?callback=callback&videoId=4584236&_=


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void getData() {
        final String getVideoUrl = "http://www.yinyuetai.com/api/info/get-video-urls?callback=callback&videoId=4584236&_=" + System.currentTimeMillis();
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                try {
                if (!StringUtil.isNullOrEmpty(s)) {
//                        JSONObject jsonObject = new JSONObject(s);
                    Log.d("video", s);
                }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(PlayVideoActivity.this, "数据加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                return NetUtil.httpGetUtil(PlayVideoActivity.this, getVideoUrl);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class GetRealVideoThread implements Runnable {

        @Override
        public void run() {
            initData();
        }
    }

    private void playVideo(String videoUrl) {
        videoPlayView.setVideoPath(videoUrl);
        videoPlayView.start();


        String se = "4584627";
        int[] e = new int[8];
        for (int i = 0; i < se.length(); i++) {
            e[i] = se.charAt(i);
        }
        String t = "0123456789abcdef";
        String n = "";

        for (int r = 0; r < e.length * 4; r++) {
            n += t.charAt(e[r >> 2] >> r % 4 * 8 + 4 & 15) + t.charAt(e[r >> 2] >> r % 4 * 8 & 15);
        }

        Log.d("video", n);
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
