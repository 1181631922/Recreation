package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
        doOpenDirectory(String.valueOf(Environment.getExternalStorageDirectory()), false);

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

            String path = f.getPath();
            String name = f.getName();
            File fileName = new File(path + name);

            openFile(path, name, fileName);

        }
    }

    private void openFile(String path, String fileName, File currentPath) {
        Intent intent;
        if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingImage))) {
            intent = getImageFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingWebText))) {
            intent = getHtmlFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingPackage))) {
            intent = getApkFileIntent(currentPath);
            startActivity(intent);

        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingAudio))) {
            intent = getAudioFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingVideo))) {
//            可以采用自己的播放器，也可以采用系统的
            intent = getVideoFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingText))) {
            intent = getTextFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingPdf))) {
            intent = getPdfFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingWord))) {
            intent = getWordFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingExcel))) {
            intent = getExcelFileIntent(currentPath);
            startActivity(intent);
        } else if (checkEndsWithInStringArray(fileName, getResources().
                getStringArray(R.array.fileEndingPPT))) {
            intent = getPPTFileIntent(currentPath);
            startActivity(intent);
        } else {
            Toast.makeText(this, "无法打开，请安装相应的软件！", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkEndsWithInStringArray(String checkItsEnd, String[] fileEndings) {
        for (String aEnd : fileEndings) {
            if (checkItsEnd.endsWith(aEnd))
                return true;
        }
        return false;
    }

    //android获取一个用于打开HTML文件的intent
    public static Intent getHtmlFileIntent(File file) {
        Uri uri = Uri.parse(file.toString()).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(file.toString()).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    //android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //android获取一个用于打开音频文件的intent
    public static Intent getAudioFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //android获取一个用于打开视频文件的intent
    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }


    //android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }


    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //android获取一个用于打开PPT文件的intent
    public static Intent getPPTFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //android获取一个用于打开apk文件的intent
    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }


}
