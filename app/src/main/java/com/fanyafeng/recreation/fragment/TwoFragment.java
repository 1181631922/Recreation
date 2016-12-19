package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.adapter.MenuAdapter;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.bean.MenuBean;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.refreshview.XRefreshView;
import com.fanyafeng.recreation.refreshview.XRefreshViewFooter;
import com.fanyafeng.recreation.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TwoFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int XREFRESH_GET_DATA = 0;
    private static final int XREFRESH_FRESH = 1;
    private static final int XREFRESH_LOAD_MORE = 2;

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar_two;

    private XRefreshView refreshTwo;
    private RecyclerView rvTwo;

    private List<MenuBean> menuBeanList = new ArrayList<>();

    private MenuAdapter menuAdapter;

    private int page = 1;


    public TwoFragment() {
        // Required empty public constructor
    }

    public static TwoFragment newInstance(String param1, String param2) {
        TwoFragment fragment = new TwoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        Thread thread = new Thread(new LoadThread(XREFRESH_GET_DATA));
        thread.start();
    }

    private void initView() {
        toolbar_two = (Toolbar) getActivity().findViewById(R.id.toolbar_two);
        toolbar_two.setLogo(R.drawable.simle_logo_02);
        toolbar_two.setTitle("美食");

        refreshTwo = (XRefreshView) getActivity().findViewById(R.id.refreshTwo);
        refreshTwo.setAutoLoadMore(true);
        refreshTwo.setPullLoadEnable(true);

        rvTwo = (RecyclerView) getActivity().findViewById(R.id.rvTwo);
        rvTwo.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        menuAdapter = new MenuAdapter(getActivity(), menuBeanList);
        menuAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        rvTwo.setAdapter(menuAdapter);

        refreshTwo.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                super.onRefresh();
//                getData(1, XREFRESH_FRESH);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Thread thread = new Thread(new LoadThread(XREFRESH_FRESH));
                        thread.start();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Thread thread = new Thread(new LoadThread(XREFRESH_LOAD_MORE));
                        thread.start();
                    }
                }, 1000);
            }
        });
    }

    private void getData(int page, int refreshState) {

        try {
//            Document document = Jsoup.connect("http://home.meishichina.com/show-top-type-recipe.html").get();
//            http://home.meishichina.com/show-top-type-recipe-page-2.html


            Document document = Jsoup.connect("http://home.meishichina.com/show-top-type-recipe-page-" + page + ".html").get();

            Log.d("jsoup:", "http://home.meishichina.com/show-top-type-recipe-page-" + page + ".html");

            Elements elements = document.select("div.top-bar");
//            Log.d("jsoup:", elements.select("a").attr("title"));

            Elements titleAndPic = document.select("div.pic");
//            Log.d("jsoup", "数量：" + titleAndPic.size());
//            Log.d("jsoup", "title:" + titleAndPic.get(1).select("a").attr("title") + "pic:" + titleAndPic.get(1).select("a").select("img").attr("data-src"));
            Elements url = document.select("div.detail").select("h2").select("a");
//            Log.d("jsoup", "url:" + url.get(1).attr("href"));
            Elements burden = document.select("p.subcontent");
//            Log.d("jsoup", "burden:" + burden.get(1).text());


            for (int i = 0; i < titleAndPic.size(); i++) {
//                Log.d("jsoup", "title:" + titleAndPic.get(i).select("a").attr("title") + "pic:" + titleAndPic.get(i).select("a").select("img").attr("data-src"));
//                Log.d("jsoup", "url:" + url.get(i).attr("href"));
//                Log.d("jsoup", "burden:" + burden.get(i).text());
                int imgLength = titleAndPic.get(i).select("a").select("img").attr("data-src").length();
                String img = titleAndPic.get(i).select("a").select("img").attr("data-src");
//                Log.d("jsoup", img.substring(0, imgLength - 3) + "640");
                String title = titleAndPic.get(i).select("a").attr("title");
                String pic = img.substring(0, imgLength - 3) + "640";
                String itemUrl = url.get(i).attr("href");
                String itemBurden = burden.get(i).text();
                MenuBean menuBean = new MenuBean();
                menuBean.setTitle(title);
                menuBean.setPic(pic);
                menuBean.setUrl(itemUrl);
                menuBean.setBurden(itemBurden);
                menuBeanList.add(menuBean);
                Message message = Message.obtain();
                message.what = refreshState;
                handler.sendMessage(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LoadThread implements Runnable {

        int refreshState;

        public LoadThread(int refreshState) {
            this.refreshState = refreshState;
        }

        @Override
        public void run() {
            if (refreshState == XREFRESH_GET_DATA) {
                menuBeanList.clear();
                page = 1;
            } else if (refreshState == XREFRESH_FRESH) {
                menuBeanList.clear();
                page = 1;
            } else if (refreshState == XREFRESH_LOAD_MORE) {
                page++;
            }
            getData(page, refreshState);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case XREFRESH_FRESH:
                    refreshTwo.stopRefresh();
                    break;
                case XREFRESH_GET_DATA:
                    break;
                case XREFRESH_LOAD_MORE:
                    refreshTwo.stopLoadMore();
                    break;
            }
            menuAdapter.notifyDataSetChanged();
        }
    };
}
