package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.eventbus.FileExploreEvents;
import com.fanyafeng.recreation.fragment.FileListFragment;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.io.IOException;

//需要搭配baseactivity，这里默认为baseactivity,并且默认Baseactivity为包名的根目录
public class FileExplorerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_explorer);
//这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_file_explorer);
        doOpenDirectory("/", false);

        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FileExploreEvents.getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        FileExploreEvents.getBus().unregister(this);
    }

    //初始化UI空间
    private void initView() {

    }

    //初始化数据
    private void initData() {

    }

    private void doOpenDirectory(String path, boolean addToBackStack) {
        Fragment newFragment = FileListFragment.newInstance(path);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Subscribe
    public void onClickFile(FileExploreEvents.OnClickFile event) {
        File f = event.mFile;
        try {
            f = f.getAbsoluteFile();
            f = f.getCanonicalFile();
            if (TextUtils.isEmpty(f.toString()))
                f = new File("/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (f.isDirectory()) {
            String path = f.toString();
//            mSettings.setLastDirectory(path);
            doOpenDirectory(path, true);
        } else if (f.exists()) {
//            VideoActivity.intentTo(this, f.getPath(), f.getName());
            Toast.makeText(this, "点击的为文件", Toast.LENGTH_SHORT).show();
        }
    }

}
