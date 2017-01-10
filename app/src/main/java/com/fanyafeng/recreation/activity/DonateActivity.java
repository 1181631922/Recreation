package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.util.FrescoUtil;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class DonateActivity extends BaseActivity {
    private SimpleDraweeView sdvWeChatDonate;
    private SimpleDraweeView sdvAlipayDonate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_donate);

        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //toolbar_center_title.setText(getString(R.string.title_activity_donate));
    }

    //初始化UI控件
    private void initView() {
        sdvWeChatDonate = (SimpleDraweeView) findViewById(R.id.sdvWeChatDonate);
        sdvAlipayDonate = (SimpleDraweeView) findViewById(R.id.sdvAlipayDonate);
    }

    //初始化数据
    private void initData() {
        FrescoUtil.loadPicInApp(sdvWeChatDonate,R.drawable.wechat_donate,1);
        FrescoUtil.loadPicInApp(sdvAlipayDonate,R.drawable.alipay_donate,1);
    }

}
