package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.util.FrescoUtil;

public class StartPagerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button btnFirstStart;
    private SimpleDraweeView sdvStart;

    public StartPagerFragment() {
        // Required empty public constructor
    }

    public static StartPagerFragment newInstance(String param1, String param2) {
        StartPagerFragment fragment = new StartPagerFragment();
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
        View view = inflater.inflate(R.layout.fragment_start_pager, container, false);
        sdvStart = (SimpleDraweeView) view.findViewById(R.id.sdvStart);
//        btnFirstStart = (Button) view.findViewById(R.id.btnFirstStart);
//        if (mParam1.equals("2")) {
//            btnFirstStart.bringToFront();
//            btnFirstStart.setVisibility(View.VISIBLE);
//        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FrescoUtil.loadGifPicInApp(sdvStart, R.drawable.start);

    }
}
