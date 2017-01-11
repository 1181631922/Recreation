package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.MyUtils;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class StartActivity extends BaseActivity {
    private SimpleDraweeView sdvStartImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_start);
        isShowToolbar = false;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//

        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //toolbar_center_title.setText(getString(R.string.title_activity_start));
    }

    //初始化UI控件
    private void initView() {
        int width = MyUtils.getScreenWidth(this);
        int height = MyUtils.getScreenHeight(this);
        float aspectRatio = width / height;

        sdvStartImg = (SimpleDraweeView) findViewById(R.id.sdvStartImg);
        if (FrescoDealPicUtil.isExist("http://upload-images.jianshu.io/upload_images/2155432-43f1b0fe0916918d.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240")) {
            FrescoUtil.loadPicOnNet(sdvStartImg, "http://upload-images.jianshu.io/upload_images/2155432-43f1b0fe0916918d.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
        } else {
            FrescoUtil.loadPicInApp(sdvStartImg, R.drawable.start, aspectRatio);
        }
    }

    //初始化数据
    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

}
