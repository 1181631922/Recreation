package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class MenuDetailActivity extends BaseActivity {
    private RecyclerView rvMenuDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_menu_detail);

        initView();
//        initData();
    }


    //初始化UI控件
    private void initView() {
        rvMenuDetail = (RecyclerView) findViewById(R.id.rvMenuDetail);
        rvMenuDetail.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
    }

    //初始化数据
    private void initData() {

    }

}
