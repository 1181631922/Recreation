package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.bean.StartBean;
import com.fanyafeng.recreation.bean.VideoBean;
import com.fanyafeng.recreation.cachemanager.Start;
import com.fanyafeng.recreation.cachemanager.StartManager;
import com.fanyafeng.recreation.fragment.ThreeFragment;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.util.StringUtil;
import com.fanyafeng.recreation.view.FullScreenVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class StartActivity extends BaseActivity {
    private SimpleDraweeView sdvStartImg;
    private FullScreenVideoView fullVideoView;
    private MediaController mediaController;
    private int width = 1080;
    private int height = 1920;
    private int seconds = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_start);
        isShowToolbar = false;
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);视频的话不展示，图片还是展示效果比较好

        width = MyUtils.getScreenWidth(this);
        height = MyUtils.getScreenHeight(this);

        initView();
        initData();
        if (StartManager.getFirstState(this, Start.START_FIRST)) {
            startActivity(new Intent(this, FirstStartActivity.class));
            finish();
            StartManager.setFirstState(this, Start.START_FIRST, false);
        } else {
            new GetStartInfoTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    //初始化UI控件
    private void initView() {
        sdvStartImg = (SimpleDraweeView) findViewById(R.id.sdvStartImg);
        sdvStartImg.setOnClickListener(this);
        fullVideoView = (FullScreenVideoView) findViewById(R.id.fullVideoView);
        FrescoUtil.loadGifPicInApp(sdvStartImg, R.drawable.start, width / height);
    }

    //初始化数据
    private void initData() {

        mediaController = new MediaController(this);
        fullVideoView.setMediaController(mediaController);

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

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
        }
    }

    class GetStartInfoTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!StringUtil.isNullOrEmpty(s)) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject != null) {
                        String state = jsonObject.optString("state");
                        if (state.equals(NetUtil.STATE_OK)) {
                            StartManager.setStartCache(StartActivity.this, Start.START_IMAGE_LIST, jsonObject.optJSONArray("imageList").toString());
                            handleStartInfo(jsonObject.optJSONArray("imageList"));
                            return;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray(StartManager.getStartCache(StartActivity.this, Start.START_IMAGE_LIST).replaceAll("&quot", "\""));
                    handleStartInfo(jsonArray);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    showLocalImg();
                }
            }
            showLocalImg();
        }


        @Override
        protected String doInBackground(String... param) {
            return NetUtil.httpGetUtil(StartActivity.this, Urls.GET_START_INFO);
        }
    }

    private void handleStartInfo(JSONArray jsonArray) {
        final ArrayList<StartBean> startBeanList = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() > 0) {
            int imageArrayLength = jsonArray.length();
            for (int i = 0; i < imageArrayLength; i++) {
                StartBean startBean = new StartBean(jsonArray.optJSONObject(i));
                startBeanList.add(startBean);
            }
            float aspectRatio = width / height;
            if (imageArrayLength == 1) {//开屏图
                final StartBean startBean = startBeanList.get(0);
                if (FrescoDealPicUtil.isExist(startBean.getImgUrl())) {
                    seconds = startBean.getSeconds();
                    FrescoUtil.loadGifPicOnNet(sdvStartImg, startBean.getImgUrl(), aspectRatio);
                    sdvStartImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startBean.getLinkUrl();
                        }
                    });
                } else {
                    FrescoUtil.loadGifPicInApp(sdvStartImg, R.drawable.start, aspectRatio);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putParcelableArrayListExtra("startBeanList", startBeanList);
                        startActivity(intent);
                        finish();
                    }
                }, seconds);
            } else {//轮播页
                boolean isLoad = true;
                for (int i = 0; i < imageArrayLength; i++) {
                    if (!FrescoDealPicUtil.isExist(startBeanList.get(i).getImgUrl())) {
                        isLoad = false;
                        break;
                    }
                }
                if (isLoad) {
                    Intent intent = new Intent(StartActivity.this, FirstStartActivity.class);
                    intent.putParcelableArrayListExtra("startBeanList", startBeanList);
                    startActivity(intent);
                    finish();
                } else {
                    FrescoUtil.loadGifPicInApp(sdvStartImg, R.drawable.start, aspectRatio);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(StartActivity.this, MainActivity.class);
                            intent.putParcelableArrayListExtra("startBeanList", startBeanList);
                            startActivity(intent);
                            finish();
                        }
                    }, seconds);
                }

            }
        } else {
            showLocalImg();
        }
    }

    private void showLocalImg() {
        float aspectRatio = width / height;
        FrescoUtil.loadGifPicInApp(sdvStartImg, R.drawable.start, aspectRatio);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }, seconds);
    }
}
