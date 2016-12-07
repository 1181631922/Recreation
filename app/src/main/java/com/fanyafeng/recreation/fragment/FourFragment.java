package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.CreateCodeActivity;
import com.fanyafeng.recreation.activity.FileExplorerActivity;
import com.fanyafeng.recreation.activity.LoginActivity;
import com.fanyafeng.recreation.activity.NoteActivity;
import com.fanyafeng.recreation.activity.PlayVideoActivity;
import com.fanyafeng.recreation.activity.ScanCodeActivity;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;

public class FourFragment extends BaseFragment {

    private Toolbar toolbar;
    private SimpleDraweeView sdvPersonal;
    private RelativeLayout layoutVideo;
    private RelativeLayout layoutBarCode;
    private RelativeLayout layoutCreateBarCode;
    private RelativeLayout layoutOpenFile;
    private RelativeLayout layoutNote;

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

    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
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
        }
    }
}
