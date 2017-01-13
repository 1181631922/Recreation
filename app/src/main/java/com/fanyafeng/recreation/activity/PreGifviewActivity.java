package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.util.ControllerListenerUtil;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.util.StringUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class PreGifviewActivity extends BaseActivity {
    private SimpleDraweeView sdvGifView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_gifview);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_pre_gifview);
        url = getIntent().getStringExtra("url");
        initView();
        initData();
    }


    //初始化UI控件
    private void initView() {
        sdvGifView = (SimpleDraweeView) findViewById(R.id.sdvGifView);

    }

    //初始化数据
    private void initData() {
        if (!StringUtil.isNullOrEmpty(url)) {
            ControllerListenerUtil.setControllerListener(sdvGifView, url, MyUtils.getScreenWidth(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        //        默认隐藏setting按钮
        if (toolbar != null) {
            MenuItem menuItem = toolbar.getMenu().getItem(0);
            if (menuItem != null) {
                menuItem.setTitle("下载图片");
                menuItem.setIcon(R.drawable.image_download);
                menuItem.setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
                String nowTime = simpleDateFormat.format(new Date().getTime());
                if (FrescoDealPicUtil.copyPicFile(url, Environment.getExternalStorageDirectory() + File.separator + "recreation", "recreation_" + nowTime + ".gif")) {
                    Toast.makeText(PreGifviewActivity.this, "图片下载成功\n目录为：" + Environment.getExternalStorageDirectory() + File.separator + "recreation", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PreGifviewActivity.this, "图片下载失败，请尝试截屏", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
