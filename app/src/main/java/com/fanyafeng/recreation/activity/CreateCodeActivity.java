package com.fanyafeng.recreation.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.util.QRCode;
import com.fanyafeng.recreation.util.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

//需要搭配baseactivity，这里默认为baseactivity,并且默认Baseactivity为包名的根目录
public class CreateCodeActivity extends BaseActivity {
    private ImageView ivBarCode;
    private FloatingActionButton fab;
    private AppCompatEditText etCodeMessageInput;
    private String getCodeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_code);
//这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_create_code);

        initView();

        if (!StringUtil.isNullOrEmpty(getIntent().getStringExtra("codeMessage"))) {
            getCodeMessage = getIntent().getStringExtra("codeMessage");
            Bitmap bitmap = QRCode.createQRCode(getCodeMessage, (int) DpPxConvert.dip2px(CreateCodeActivity.this, 290));
            ivBarCode.setImageBitmap(bitmap);
            etCodeMessageInput.setText(getCodeMessage);
            etCodeMessageInput.selectAll();
        } else {
            etCodeMessageInput.requestFocus();
            etCodeMessageInput.setFocusableInTouchMode(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) etCodeMessageInput.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInput(etCodeMessageInput, 0);
                }
            }, 200);
        }

        initData();
    }


    //初始化UI空间
    private void initView() {
        ivBarCode = (ImageView) findViewById(R.id.ivBarCode);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        etCodeMessageInput = (AppCompatEditText) findViewById(R.id.etCodeMessageInput);
    }

    //初始化数据
    private void initData() {


    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fab:
                String input = etCodeMessageInput.getText().toString().trim();
                if (!StringUtil.isNullOrEmpty(input)) {
                    Bitmap bitmap = QRCode.createQRCode(input, (int) DpPxConvert.dip2px(CreateCodeActivity.this, 290));
                    ivBarCode.setImageBitmap(bitmap);
                    Snackbar.make(v, "二维码已生成", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                } else {
                    Snackbar.make(v, "请输入您要生成二维码的信息", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }

                break;
        }
    }
}
