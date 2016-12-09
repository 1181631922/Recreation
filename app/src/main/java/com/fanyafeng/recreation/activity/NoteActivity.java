package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.adapter.NoteAdapter;
import com.fanyafeng.recreation.bean.NoteBean;
import com.fanyafeng.recreation.datasupport.NoteData;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class NoteActivity extends BaseActivity {
    private FloatingActionButton fab;
    private RecyclerView rvNote;
    private List<NoteBean> noteBeanList = new ArrayList<>();
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_note);

        initView();
        initData();
    }


    //初始化UI控件
    private void initView() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        rvNote = (RecyclerView) findViewById(R.id.rvNote);
        rvNote.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
    }

    //初始化数据
    private void initData() {

        List<NoteData> noteDataList = DataSupport.findAll(NoteData.class);
        for (int i = 0; i < noteDataList.size(); i++) {
            NoteData noteData = noteDataList.get(i);
            NoteBean noteBean = new NoteBean();
            noteBean.setTitle(noteData.getTitle());
            noteBean.setHasPic(noteData.isHasPic());
            noteBean.setTitleHeader(noteData.getTitleHeader());
            noteBean.setId(noteData.getId());
            noteBeanList.add(noteBean);
        }

        noteAdapter = new NoteAdapter(this, noteBeanList);
        rvNote.setAdapter(noteAdapter);
        rvNote.addItemDecoration(new StickyRecyclerHeadersDecoration(noteAdapter));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fab:
                startActivity(new Intent(this, AddNoteActivity.class));
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        //        默认隐藏setting按钮
        if (toolbar != null) {
            MenuItem menuItem = toolbar.getMenu().getItem(0);
            if (menuItem != null) {
                menuItem.setTitle("查询备忘录");
                menuItem.setIcon(R.drawable.note_find);
                menuItem.setVisible(true);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
