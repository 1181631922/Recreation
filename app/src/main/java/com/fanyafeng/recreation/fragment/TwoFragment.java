package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.network.NetUtil;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.util.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class TwoFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar_two;


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
        initData();
    }

    private void initView() {
        toolbar_two = (Toolbar) getActivity().findViewById(R.id.toolbar_two);
        toolbar_two.setLogo(R.drawable.simle_logo_02);
        toolbar_two.setTitle("视频");
    }

    private void initData() {
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
                            Log.d("音悦台数据：", jsonObject.toString());
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), "数据加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {
                return NetUtil.httpGetUtil(getActivity(), Urls.GET_TOP_VIDEO_LIST);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
