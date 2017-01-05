package com.fanyafeng.recreation.fragment;

import android.os.AsyncTask;
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

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.adapter.VideoAdapter;
import com.fanyafeng.recreation.bean.VideoBean;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.refreshview.XRefreshView;
import com.fanyafeng.recreation.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.fanyafeng.recreation.network.NetUtil.JSON;

public class ThreeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar_three;
    private XRefreshView refreshVideo;
    private RecyclerView rvVideo;

    private List<VideoBean> videoBeanList = new ArrayList<>();
    private VideoAdapter videoAdapter;

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
//        Thread thread = new Thread(new LoadThread());
//        thread.start();

        new RecommandWineTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void initView() {
        toolbar_three = (Toolbar) getActivity().findViewById(R.id.toolbar_three);
        toolbar_three.setLogo(R.drawable.simle_logo_03);
        toolbar_three.setTitle("音乐视频");

        refreshVideo = (XRefreshView) getActivity().findViewById(R.id.refreshVideo);
        refreshVideo.setPullLoadEnable(true);
        refreshVideo.setAutoLoadMore(true);
        rvVideo = (RecyclerView) getActivity().findViewById(R.id.rvVideo);
        rvVideo.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        videoAdapter = new VideoAdapter(getActivity(), videoBeanList);
        rvVideo.setAdapter(videoAdapter);
    }

    private void initData() {

    }

    class RecommandWineTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!StringUtil.isNullOrEmpty(s)) {
                Log.d("video", s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject != null) {
                        String state = jsonObject.optString("state");
                        if (state.equals(NetUtil.STATE_OK)) {
                            JSONArray jsonArray = jsonObject.optJSONArray("videoList");
                            int videoSize = jsonArray.length();
                            for (int i = 0; i < videoSize; i++) {
                                VideoBean videoBean = new VideoBean(jsonArray.optJSONObject(i));
                                videoBeanList.add(videoBean);
                                videoAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected String doInBackground(String... param) {
            return NetUtil.httpGetUtil(getActivity(), "http://localhost:8080/recreation-1.0/videos/findVideoByPage?page=0");
        }
    }

    private void postJson() {
        //申明给服务端传递一个json串
        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        JSONObject jsonObject = new JSONObject();
        JSONObject common = null;
        JSONObject params = null;
        try {
            common = new JSONObject();
            common.put("identifier", "mo098fd4ac5ed161ad921d6048636625");
            common.put("app_version", "1006010802");
            common.put("os_version", "23");
            common.put("device", "Google Nexus 6P - 6.0.0 - API 23 - 1440x2560");
            common.put("platform", "Android");
            common.put("pid", "5080");
            common.put("language", "CN");
            common.put("uid", "366617270");
            common.put("width", "1440");
            common.put("height", "2392");

            params = new JSONObject();
            params.put("category_id", "113");
            params.put("city_id", "33");
            params.put("page_past", "0");
            params.put("page_length", "10");
            params.put("is_webp", "1");

            jsonObject.put("common", common);
            jsonObject.put("params", params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        //创建一个请求对象
        Request request = new Request.Builder()
                .url("http://fds.api.moji.com/card/recommendV2")
                .post(requestBody)
                .build();
        //发送请求获取响应
        try {
            Response response = okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if (response.isSuccessful()) {
                //打印服务端返回结果
                Log.i("three", response.body().string());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
