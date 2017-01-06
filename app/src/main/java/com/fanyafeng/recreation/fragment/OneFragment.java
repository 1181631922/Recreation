package com.fanyafeng.recreation.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.adapter.MainAdapter;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.refreshview.XRefreshView;
import com.fanyafeng.recreation.refreshview.XRefreshViewFooter;
import com.fanyafeng.recreation.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OneFragment extends BaseFragment {
    private Toolbar toolbar_one;

    private RecyclerView rvMain;
    private XRefreshView refreshMain;
    private List<MainItemBean> mainItemBeanList = new ArrayList<>();
    private MainAdapter mainAdapter;

    private FloatingActionButton fabMainToTop;

    private FragmentDialogInterface fragmentDialogInterface;

    public OneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        toolbar_one = (Toolbar) getActivity().findViewById(R.id.toolbar_one);
        toolbar_one.setTitle(getString(R.string.app_name));
        toolbar_one.setLogo(R.drawable.simle_logo_01);

        rvMain = (RecyclerView) getActivity().findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new GridLayoutManager(getActivity(), GridLayoutManager.VERTICAL, 1, false));

        refreshMain = (XRefreshView) getActivity().findViewById(R.id.refreshMain);
        refreshMain.setPullLoadEnable(true);
        refreshMain.setAutoLoadMore(true);

        fabMainToTop = (FloatingActionButton) getActivity().findViewById(R.id.fabMainToTop);

        Log.d("time", "当前时间戳" + System.currentTimeMillis() / 1000);
    }

    private void initData() {
        loadData();

        mainAdapter = new MainAdapter(getActivity(), mainItemBeanList);
        mainAdapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        rvMain.setAdapter(mainAdapter);

        refreshMain.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreData();
                    }
                }, 1000);
            }
        });

        fabMainToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mainItemBeanList.size() > 0)
                            rvMain.smoothScrollToPosition(0);
                    }
                }, 200);

            }
        });
    }

    private void refreshData() {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mainItemBeanList.clear();
                refreshMain.stopRefresh();
                try {
                    if (!StringUtil.isNullOrEmpty(s)) {
                        JSONObject jsonObject = new JSONObject(s);
                        if (jsonObject != null) {
                            String message = jsonObject.optString("message");//判断请求状态
                            if (message.equals("success")) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data != null) {
                                    JSONArray dataArray = data.optJSONArray("data");
                                    int dataLength = dataArray.length();
                                    for (int i = 0; i < dataLength; i++) {
                                        MainItemBean mainItemBean = new MainItemBean(dataArray.optJSONObject(i));
                                        mainItemBeanList.add(mainItemBean);
                                    }
                                    mainAdapter.notifyDataSetChanged();
                                    return;
                                }
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
                return NetUtil.httpGetUtil(getActivity(), Urls.GET_ARTICLE_LIST + System.currentTimeMillis() / 1000 + Urls.GET_ARTICLE_LIST_END);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void loadMoreData() {
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
                            String message = jsonObject.optString("message");//判断请求状态
                            if (message.equals("success")) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data != null) {
                                    JSONArray dataArray = data.optJSONArray("data");
                                    int dataLength = dataArray.length();
                                    for (int i = 0; i < dataLength; i++) {
                                        MainItemBean mainItemBean = new MainItemBean(dataArray.optJSONObject(i));
                                        mainItemBeanList.add(mainItemBean);
                                    }
                                    mainAdapter.notifyDataSetChanged();
                                    refreshMain.stopLoadMore();
                                    return;
                                }
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
                return NetUtil.httpGetUtil(getActivity(), Urls.GET_ARTICLE_LIST + System.currentTimeMillis() / 1000 + Urls.GET_ARTICLE_LIST_END);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void loadData() {
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
                            String message = jsonObject.optString("message");//判断请求状态
                            if (message.equals("success")) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                if (data != null) {
                                    JSONArray dataArray = data.optJSONArray("data");
                                    int dataLength = dataArray.length();
                                    for (int i = 0; i < dataLength; i++) {
                                        MainItemBean mainItemBean = new MainItemBean(dataArray.optJSONObject(i));
                                        mainItemBeanList.add(mainItemBean);
                                    }
                                    mainAdapter.notifyDataSetChanged();
                                    return;
                                }
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
                return NetUtil.httpGetUtil(getActivity(), Urls.GET_ARTICLE_LIST + System.currentTimeMillis() / 1000 + Urls.GET_ARTICLE_LIST_END);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentDialogInterface = (FragmentDialogInterface) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
