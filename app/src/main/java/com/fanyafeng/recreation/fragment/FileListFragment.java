package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.adapter.FileListAdapter;
import com.fanyafeng.recreation.util.PathCursorLoader;

import java.io.File;

public class FileListFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PATH = "path";

    private String myPath;
    private RecyclerView rvFileList;

    private FileListAdapter fileListAdapter;

    public FileListFragment() {
        // Required empty public constructor
    }

    public static FileListFragment newInstance(String path) {
        FileListFragment fragment = new FileListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PATH, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_file_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            myPath = bundle.getString(ARG_PATH);
            myPath = new File(myPath).getAbsolutePath();
        }

        initView();
        initData();

        getLoaderManager().initLoader(1, null, this);
    }

    private void initView() {
        rvFileList = (RecyclerView) getActivity().findViewById(R.id.rvFileList);
        rvFileList.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));

    }

    private void initData() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (TextUtils.isEmpty(myPath))
            return null;
        return new PathCursorLoader(getActivity(), myPath);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("filelist", "路径：" + data.toString());
        fileListAdapter = new FileListAdapter(getActivity(), data);
        rvFileList.setAdapter(fileListAdapter);
        fileListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
