package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.adapter.AddNoteAdapter;
import com.fanyafeng.recreation.datasupport.NoteData;
import com.fanyafeng.recreation.datasupport.NoteImgData;
import com.fanyafeng.recreation.util.ControllerListenerUtil;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.util.UriUtils;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class AddNoteActivity extends BaseActivity {
    private static final int RESULT_CAMERA = 1;
    private static final int RESULT_PICTURE = 2;

    private String EDIR = Environment.getExternalStorageDirectory().getPath();
    private Uri cameraUri;

    private TextView tvNoteTime;
    private FloatingActionButton fab;
    private LinearLayout layoutCamera;
    private LinearLayout layoutPicture;
    private RecyclerView rvAddNote;
    private View addNoteHeader;
    private EditText etAddNoteTitle;
    private EditText etAddNoteDesc;
    private AddNoteAdapter addNoteAdapter;
    private List<String> imgList = new ArrayList<>();

    private NoteData noteData;
    private List<NoteImgData> noteImgDataList = new ArrayList<>();

    private long noteDataId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_add_note);

        if (getIntent().getLongExtra("noteDataId", -1) != -1) {
            noteDataId = getIntent().getLongExtra("noteDataId", -1);
            noteData = DataSupport.find(NoteData.class, noteDataId);
        }

        initView();
        initData();
    }


    //初始化UI控件
    private void initView() {
        tvNoteTime = (TextView) findViewById(R.id.tvNoteTime);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        layoutCamera = (LinearLayout) findViewById(R.id.layoutCamera);
        layoutCamera.setOnClickListener(this);
        layoutPicture = (LinearLayout) findViewById(R.id.layoutPicture);
        layoutPicture.setOnClickListener(this);
        rvAddNote = (RecyclerView) findViewById(R.id.rvAddNote);
        rvAddNote.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));

        addNoteAdapter = new AddNoteAdapter(this, imgList);
        addNoteHeader = addNoteAdapter.setHeaderView(R.layout.top_add_note_layout, rvAddNote);
        etAddNoteTitle = (EditText) addNoteHeader.findViewById(R.id.etAddNoteTitle);
        etAddNoteDesc = (EditText) addNoteHeader.findViewById(R.id.etAddNoteDesc);
        rvAddNote.setAdapter(addNoteAdapter);
    }

    //初始化数据
    private void initData() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String nowTime = formatter.format(curDate);
        long myStr = System.currentTimeMillis() / 1000 / 60 / 60 / 24;//精确到天，用来排序

        if (noteDataId != -1) {
            title = "编辑备忘录";
            tvNoteTime.setText(formatter.format(noteData.getCreateData()));
            etAddNoteTitle.setText(noteData.getTitle());
            etAddNoteDesc.setText(noteData.getDesc());
        } else {
            tvNoteTime.setText(nowTime);
            noteData = new NoteData();
            noteData.setCreateData(System.currentTimeMillis());
            noteData.setTitleHeader(System.currentTimeMillis() / 1000 / 60 / 60 / 24);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "放弃编辑当前备忘录？", Snackbar.LENGTH_LONG).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }).show();
                break;
            case R.id.layoutCamera://调用系统摄像头拍照
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(EDIR + File.separator + "note_img");
                if (!file.exists()) {
                    file.mkdir();
                }
                String noteImg = EDIR + File.separator + "note_img" + File.separator + "temp_" + System.currentTimeMillis() / 1000 + ".jpg";
                cameraUri = Uri.fromFile(new File(noteImg));
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                startActivityForResult(intentCamera, RESULT_CAMERA);
                break;
            case R.id.layoutPicture://调用系统相册
                Intent intentPicture = new Intent();
                intentPicture.setAction(Intent.ACTION_PICK);
//                intentPicture.setAction(Intent.ACTION_GET_CONTENT);7.0有问题
                intentPicture.setType("image/*");
                startActivityForResult(intentPicture, RESULT_PICTURE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CAMERA:
                    imgList.add(cameraUri.toString());
                    addNoteAdapter.notifyDataSetChanged();
                    break;
                case RESULT_PICTURE:
                    imgList.add("file://" + UriUtils.getPath(this, data.getData()));
                    addNoteAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        fab.performClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.performClick();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        //        默认隐藏setting按钮
        if (toolbar != null) {
            MenuItem menuItem = toolbar.getMenu().getItem(0);
            if (menuItem != null) {
                menuItem.setTitle("完成添加");
                menuItem.setIcon(R.drawable.icon_check);
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
                noteData.setTitle(etAddNoteTitle.getText().toString());
                noteData.setDesc(etAddNoteDesc.getText().toString());
                for (int i = 0; i < imgList.size(); i++) {
                    NoteImgData noteImgData = new NoteImgData();
                    noteImgData.setImgUrl(imgList.get(i));
                    noteImgData.setNoteData(noteData);
                    noteImgData.save();
                    noteImgDataList.add(noteImgData);
                }
                noteData.setNoteImgDataList(noteImgDataList);
                noteData.setHasPic(!(noteImgDataList.size() == 0));
                noteData.save();
                Toast.makeText(this, "备忘录编辑成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
