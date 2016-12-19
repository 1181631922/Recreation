package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.adapter.YinYueAdapter;
import com.fanyafeng.recreation.bean.YinYueBean;
import com.fanyafeng.recreation.refreshview.XRefreshView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ThreeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar_three;
    private XRefreshView refreshYinYue;
    private RecyclerView rvYinYue;

    private List<YinYueBean> yinYueBeanList = new ArrayList<>();
    private YinYueAdapter yinYueAdapter;

    public ThreeFragment() {
    }

    public static ThreeFragment newInstance(String param1, String param2) {
        ThreeFragment fragment = new ThreeFragment();
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
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
//        initData();
        Thread thread = new Thread(new LoadThread());
        thread.start();
    }

    private void initView() {
        toolbar_three = (Toolbar) getActivity().findViewById(R.id.toolbar_three);
        toolbar_three.setLogo(R.drawable.simle_logo_03);
        toolbar_three.setTitle("音乐视频");

        refreshYinYue = (XRefreshView) getActivity().findViewById(R.id.refreshYinYue);
        refreshYinYue.setPullLoadEnable(true);
        refreshYinYue.setAutoLoadMore(true);
        rvYinYue = (RecyclerView) getActivity().findViewById(R.id.rvYinYue);
        rvYinYue.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        yinYueAdapter = new YinYueAdapter(getActivity(), yinYueBeanList);
        rvYinYue.setAdapter(yinYueAdapter);
    }

    private void initData() {
        try {
            Document document = Jsoup.connect("http://pl.yinyuetai.com/playlist_promo/1").get();

            //列表的单个项的网页链接
            Elements itemElement = document.getElementsByClass("pl_img");
//            String itemUrl = itemElement.select("a").attr("href");
//            Log.d("jsoup", itemUrl);

            //列表单个项的title
//            String itemTitle = itemElement.select("a").attr("title");
//            Log.d("jsoup", itemTitle);

            //列表单个项的icon大图
//            String itemIcon = itemElement.select("a").select("img").attr("src");
//            Log.d("jsoup", itemIcon);

            //人物头像
            Elements iconBoxElement = document.getElementsByClass("icon_box");
//            String itemImg = iconBoxElement.select("img").attr("src");
//            Log.d("jsoup", itemImg);

            int itemSize = itemElement.size();
            for (int i = 0; i < itemSize; i++) {
                YinYueBean yinYueBean = new YinYueBean();
                String itemUrl = itemElement.get(i).select("a").attr("href");
                yinYueBean.setHref(itemUrl);
                String itemTitle = itemElement.get(i).select("a").attr("title");
                yinYueBean.setTitle(itemTitle);
                String itemImg = itemElement.get(i).select("a").select("img").attr("src");
                yinYueBean.setImg(itemImg);
                String itemIcon = iconBoxElement.get(i).select("img").attr("src");
                yinYueBean.setIcon(itemIcon);
                yinYueBeanList.add(yinYueBean);
            }
            Message message0 = Message.obtain();
            message0.what = 0;
            handler.sendMessage(message0);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LoadThread implements Runnable {

        @Override
        public void run() {
            initData();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    yinYueAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
