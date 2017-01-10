package com.fanyafeng.recreation.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.CreateCodeActivity;
import com.fanyafeng.recreation.activity.FileExplorerActivity;
import com.fanyafeng.recreation.activity.LoginActivity;
import com.fanyafeng.recreation.activity.NoteActivity;
import com.fanyafeng.recreation.activity.PlayVideoActivity;
import com.fanyafeng.recreation.activity.ScanCodeActivity;
import com.fanyafeng.recreation.activity.WebViewActivity;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FourFragment extends BaseFragment {

    private Toolbar toolbar;
    private SimpleDraweeView sdvPersonal;
    private RelativeLayout layoutVideo;
    private RelativeLayout layoutBarCode;
    private RelativeLayout layoutCreateBarCode;
    private RelativeLayout layoutOpenFile;
    private RelativeLayout layoutNote;
    private RelativeLayout layoutUpdate;
    private RelativeLayout layoutWeather;

    private int fileLength;
    private int DownedFileLength = 0;
    private InputStream inputStream;
    private URLConnection connection;
    private OutputStream outputStream;

    private NotificationManager manager;
    private RemoteViews contentView;
    private String packageName;
    private PendingIntent contentIntent;
    private FloatingActionButton fab;

    private int icon_download = android.R.drawable.stat_sys_download;
    Notification mNotification = new Notification(icon_download, "开始下载更新", System.currentTimeMillis());

    public FourFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_four, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_four);
        toolbar.setTitle("个人");
        toolbar.setLogo(R.drawable.simle_logo_04);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(this);

        sdvPersonal = (SimpleDraweeView) getActivity().findViewById(R.id.sdvPersonal);
        FrescoUtil.loadPicOnNet(sdvPersonal, "http://news.mydrivers.com/img/20141111/aced68582b904db8a876ea21ffe15ca6.jpg");

        layoutVideo = (RelativeLayout) getActivity().findViewById(R.id.layoutVideo);
        layoutVideo.setOnClickListener(this);

        layoutBarCode = (RelativeLayout) getActivity().findViewById(R.id.layoutBarCode);
        layoutBarCode.setOnClickListener(this);

        layoutCreateBarCode = (RelativeLayout) getActivity().findViewById(R.id.layoutCreateBarCode);
        layoutCreateBarCode.setOnClickListener(this);

        layoutOpenFile = (RelativeLayout) getActivity().findViewById(R.id.layoutOpenFile);
        layoutOpenFile.setOnClickListener(this);

        layoutNote = (RelativeLayout) getActivity().findViewById(R.id.layoutNote);
        layoutNote.setOnClickListener(this);

        layoutUpdate = (RelativeLayout) getActivity().findViewById(R.id.layoutUpdate);
        layoutUpdate.setOnClickListener(this);

        layoutWeather = (RelativeLayout) getActivity().findViewById(R.id.layoutWeather);
        layoutWeather.setOnClickListener(this);
    }

    private void initData() {
        manager = (NotificationManager) getContext().getSystemService(getActivity().NOTIFICATION_SERVICE);
        packageName = getActivity().getPackageName();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.fab://分享
                shareAPK(view, Urls.GET_NEW_VERSION, null);
                break;
            case R.id.layoutVideo:
                startActivity(new Intent(getActivity(), FileExplorerActivity.class));
                break;
            case R.id.layoutBarCode://扫码
                startActivity(new Intent(getActivity(), ScanCodeActivity.class));
                break;
            case R.id.layoutCreateBarCode://生成二维码
                startActivity(new Intent(getActivity(), CreateCodeActivity.class));
                break;
            case R.id.layoutOpenFile:
                startActivity(new Intent(getActivity(), FileExplorerActivity.class));
                break;
            case R.id.layoutNote:
                startActivity(new Intent(getActivity(), NoteActivity.class));
                break;
            case R.id.layoutWeather:
                startActivity(new Intent(getActivity(), WebViewActivity.class));
                break;
            case R.id.layoutUpdate:
                //添加在线更新
                getNewVersion();
//                updateAPK();
                break;
        }
    }

    private void shareAPK(View view, String content, Uri uri) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (uri != null) {
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/*");
            shareIntent.putExtra("sms_body", content);
        } else {
            shareIntent.setType("text/plain");
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(Intent.createChooser(shareIntent, "分享给好友"));
//        startActivity(shareIntent);
    }

    public static String getVersion(Context context)//获取版本号
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return context.getString(R.string.version_unknown);
        }
    }

    private void getNewVersion() {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if (!StringUtil.isNullOrEmpty(s)) {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject != null) {
                            String state = jsonObject.optString("state");//判断请求状态
                            if (state.equals(NetUtil.STATE_OK)) {
                                if (jsonObject.optInt("version") > Integer.parseInt(getVersion(getActivity()).replace(".", ""))) {
                                    Toast.makeText(getActivity(), "正在为您下载更新", Toast.LENGTH_SHORT).show();
                                    updateAPK();
                                } else {
                                    Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                return NetUtil.httpGetUtil(getActivity(), Urls.GET_NEW_VERSION);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void updateAPK() {
        DownLoadThread downLoadThread = new DownLoadThread();
        downLoadThread.start();
    }

    private class DownLoadThread extends Thread {

        @Override
        public void run() {
            DownFile("http://60.205.223.7:8080/Recreation/recreation_001.apk");
        }

    }

    private void DownFile(String durl) {
        try {
            URL url = new URL(durl);
            connection = url.openConnection();
            inputStream = connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (inputStream == null) {
            Message message3 = Message.obtain();
            message3.what = 3;
            progressHandler.sendMessage(message3);
            return;
        }
        String downloadDir = Environment.getExternalStorageDirectory().getPath() + "/download";
        File file1 = new File(downloadDir);
        if (!file1.exists()) {
            file1.mkdir();
        }
        String filePath = downloadDir + "/recreation.apk";
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Message message = Message.obtain();
        try {
            outputStream = new FileOutputStream(file);
            fileLength = connection.getContentLength();
            int bufferLen = 1024;
            byte[] buffer = new byte[bufferLen];
            message.what = 0;
            progressHandler.sendMessage(message);
            int count = 0;
            while ((count = inputStream.read(buffer)) != -1) {
                DownedFileLength += count;
//                temp = (float) this.notificationProgress.getProgress() / (float) this.notificationProgress.getMax();
                outputStream.write(buffer, 0, count);
                Message message1 = Message.obtain();
                message1.what = 1;
                progressHandler.sendMessage(message1);
            }
            Message message2 = Message.obtain();
            message2.what = 2;
            progressHandler.sendMessage(message2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler progressHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (!Thread.currentThread().isInterrupted()) {
                switch (msg.what) {
                    case 0:
                        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                        contentView = new RemoteViews(packageName, R.layout.notification_view_download);
                        contentView.setTextViewText(R.id.notificationTitle, "开心一刻:开始下载...");
                        contentView.setTextViewText(R.id.notificationPercent, "下载开始");
                        contentView.setProgressBar(R.id.notificationProgress, fileLength, DownedFileLength, false);
                        mNotification.contentView = contentView;
                        mNotification.contentIntent = contentIntent;
                        manager.notify(1, mNotification);
                        break;
                    case 1:
                        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                        contentView = new RemoteViews(packageName, R.layout.notification_view_download);
                        contentView.setTextViewText(R.id.notificationTitle, "开心一刻:正在下载中...");
                        // notification显示
                        contentView.setTextViewText(R.id.notificationPercent, "下载中");

                        contentView.setProgressBar(R.id.notificationProgress, fileLength, DownedFileLength, false);
                        mNotification.contentView = contentView;
                        mNotification.contentIntent = contentIntent;
                        manager.notify(1, mNotification);
                        break;
                    case 2:
                        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
                        contentView = new RemoteViews(packageName, R.layout.notification_view_download);
                        contentView.setTextViewText(R.id.notificationTitle, "Download:开心一刻安装包下载完成");
                        // notification显示
                        contentView.setTextViewText(R.id.notificationPercent, "下载完成");
                        contentView.setImageViewResource(R.id.notificationImage, R.mipmap.ic_launcher);
                        mNotification.icon = R.mipmap.ic_launcher;
                        contentView.setProgressBar(R.id.notificationProgress, fileLength, DownedFileLength, false);
                        mNotification.contentView = contentView;
                        mNotification.contentIntent = contentIntent;
                        manager.notify(1, mNotification);

                        installApk(Environment.getExternalStorageDirectory().getPath() + "/download" + "/recreation.apk");
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "暂无版本更新", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    };

    // 安装apk文件
    private void installApk(String filename) {
        File file = new File(filename);
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW); // 浏览网页的Action(动作)
        String type = "application/vnd.android.package-archive";
        intent.setDataAndType(Uri.fromFile(file), type); // 设置数据类型
        getContext().startActivity(intent);
    }
}
