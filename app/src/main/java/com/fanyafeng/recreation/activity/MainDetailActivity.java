package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.adapter.MainDetailAdapter;
import com.fanyafeng.recreation.bean.MainDetailBean;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.bean.VideoBean;
import com.fanyafeng.recreation.fragment.ThreeFragment;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.refreshview.XRefreshView;
import com.fanyafeng.recreation.refreshview.XRefreshViewFooter;
import com.fanyafeng.recreation.util.ControllerListenerUtil;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.FitScreenUtil;
import com.fanyafeng.recreation.util.FrescoAttributeUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.util.StringUtil;
import com.fanyafeng.recreation.widget.media.AndroidMediaController;
import com.fanyafeng.recreation.widget.media.IjkVideoView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class MainDetailActivity extends BaseActivity {
    private XRefreshView refreshMainDetail;
    private RecyclerView rvMainDetail;
    private List<MainDetailBean> mainDetailBeanList = new ArrayList<>();
    private MainDetailAdapter mainDetailAdapter;
    private MainItemBean mainItemBean;
    private long id;
    private int page = 0;

    private View headerView;
    private LinearLayout layoutItemMain;
    private LinearLayout layoutUser;
    private SimpleDraweeView sdvUserImg;
    private TextView tvUserName;
    private TextView tvMainItem;
    private SimpleDraweeView sdvMainItem;
    private IjkVideoView videoMain;

    private AndroidMediaController androidMediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_detail);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_main_detail);
        if (getIntent().getParcelableExtra("mainItemBean") != null) {
            mainItemBean = getIntent().getParcelableExtra("mainItemBean");
            id = mainItemBean.getId();
        }
        androidMediaController = new AndroidMediaController(this, false);
        initView();
        initData();
    }


    //初始化UI控件
    private void initView() {
        refreshMainDetail = (XRefreshView) findViewById(R.id.refreshMainDetail);
        rvMainDetail = (RecyclerView) findViewById(R.id.rvMainDetail);
        refreshMainDetail.setPullRefreshEnable(false);
        refreshMainDetail.setAutoLoadMore(true);
        refreshMainDetail.setMoveForHorizontal(true);
        rvMainDetail.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
    }

    //初始化数据
    private void initData() {
        mainDetailAdapter = new MainDetailAdapter(this, mainDetailBeanList);
        rvMainDetail.setAdapter(mainDetailAdapter);
        mainDetailAdapter.setCustomLoadMoreView(new XRefreshViewFooter(this));
        headerView = mainDetailAdapter.setHeaderView(R.layout.item_main_layout, rvMainDetail);

        videoMain = (IjkVideoView) headerView.findViewById(R.id.videoMain);
        videoMain.setMediaController(androidMediaController);
        if (!StringUtil.isNullOrEmpty(mainItemBean.getMp4Url())) {
            FitScreenUtil.FixScreenXY(videoMain, MyUtils.getScreenWidth(this), MyUtils.getScreenWidth(this) * 3 / 4);
            videoMain.setVideoPath(mainItemBean.getMp4Url());
            videoMain.start();
            videoMain.setVisibility(View.VISIBLE);
        }

        layoutItemMain = (LinearLayout) headerView.findViewById(R.id.layoutItemMain);
        layoutUser = (LinearLayout) headerView.findViewById(R.id.layoutUser);

        sdvUserImg = (SimpleDraweeView) headerView.findViewById(R.id.sdvUserImg);
        FrescoUtil.loadPicOnNet(sdvUserImg, mainItemBean.getUserImg());
        sdvUserImg.setHierarchy(FrescoAttributeUtil.setCircleRingHierarchy(MainDetailActivity.this, Color.BLUE, 2f));

        tvUserName = (TextView) headerView.findViewById(R.id.tvUserName);
        tvUserName.setText(mainItemBean.getUserName());

        tvMainItem = (TextView) headerView.findViewById(R.id.tvMainItem);
        if (!StringUtil.isNullOrEmpty(mainItemBean.getContent())) {
            tvMainItem.setText(mainItemBean.getContent());
        } else {
            tvMainItem.setVisibility(View.GONE);
        }

        if (!StringUtil.isNullOrEmpty(mainItemBean.getContent())) {
            tvMainItem.setText(mainItemBean.getContent());
        } else {
            tvMainItem.setVisibility(View.GONE);
        }

        sdvMainItem = (SimpleDraweeView) headerView.findViewById(R.id.sdvMainItem);
        if (!StringUtil.isNullOrEmpty(mainItemBean.getImage())) {
            final String img = mainItemBean.getImage();
            ControllerListenerUtil.setControllerListener(sdvMainItem, img, MyUtils.getScreenWidth(MainDetailActivity.this));
            sdvMainItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mainItemBean.getIsGif() != 1) {
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(img);
                        Intent intent = new Intent(MainDetailActivity.this, PreviewActivity.class);
                        intent.putStringArrayListExtra("list", list);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainDetailActivity.this, PreGifviewActivity.class);
                        intent.putExtra("url", img);
                        startActivity(intent);
                    }
                }
            });
        } else {
            sdvMainItem.setVisibility(View.GONE);
        }

        refreshMainDetail.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page += 15;
                        new GetMainDetailTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    }
                }, 1000);
            }
        });

        new GetMainDetailTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class GetMainDetailTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            refreshMainDetail.stopLoadMore();
            if (!StringUtil.isNullOrEmpty(s)) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject != null) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            JSONArray recentComments = data.optJSONArray("recent_comments");
                            if (recentComments != null) {
                                int recentSize = recentComments.length();
                                for (int i = 0; i < recentSize; i++) {
                                    MainDetailBean mainDetailBean = new MainDetailBean(recentComments.optJSONObject(i));
                                    mainDetailBeanList.add(mainDetailBean);
                                }
                                mainDetailAdapter.notifyDataSetChanged();
                            }
                            JSONArray topComments = data.optJSONArray("top_comments");
                            if (topComments != null) {
                                int topSize = topComments.length();
                                for (int i = 0; i < topSize; i++) {
                                    MainDetailBean mainDetailBean = new MainDetailBean(topComments.optJSONObject(i));
                                    View view = LayoutInflater.from(MainDetailActivity.this).inflate(R.layout.item_main_detail_layout, null);
                                    SimpleDraweeView sdvDetailUserImg = (SimpleDraweeView) view.findViewById(R.id.sdvDetailUserImg);
                                    FrescoUtil.loadPicOnNet(sdvDetailUserImg, mainDetailBean.getUserImg());
                                    sdvDetailUserImg.setHierarchy(FrescoAttributeUtil.setCircleRingHierarchy(MainDetailActivity.this, Color.BLUE, 2f));
                                    TextView tvDetailUserName = (TextView) view.findViewById(R.id.tvDetailUserName);
                                    tvDetailUserName.setText(mainDetailBean.getUserName());
                                    TextView tvMainDetailItem = (TextView) view.findViewById(R.id.tvMainDetailItem);
                                    tvMainDetailItem.setText(mainDetailBean.getText());
                                    layoutItemMain.addView(view);
                                }
                            }
                            return;
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... param) {
            return NetUtil.httpGetUtil(MainDetailActivity.this, Urls.GET_USER_DETAIL_START + "group_id=" + id + "&item_id=" + id + "&count=20&offset=" + page + Urls.GET_USER_DETAIL_END);
        }
    }

}
