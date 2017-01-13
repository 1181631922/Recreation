package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.bean.StartBean;
import com.fanyafeng.recreation.fragment.FourFragment;
import com.fanyafeng.recreation.fragment.OneFragment;
import com.fanyafeng.recreation.fragment.ThreeFragment;
import com.fanyafeng.recreation.fragment.TwoFragment;
import com.fanyafeng.recreation.util.FrescoDealPicUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.StringUtil;

import java.util.ArrayList;

//需要搭配baseactivity，这里默认为baseactivity,并且默认Baseactivity为包名的根目录
public class MainActivity extends BaseActivity {
    private static String[] FRAGMENT_TAGS = new String[]{"1", "2", "3", "4"};
    private View[] tabs = new View[4];

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;

    private FragmentManager fragmentManager;

    private ImageView tab_img1;
    private TextView tab_text1;

    private ImageView tab_img2;
    private TextView tab_text2;

    private ImageView tab_img3;
    private TextView tab_text3;

    private ImageView tab_img4;
    private TextView tab_text4;

    private int current = 0;
    private int currentTab = -1;

    private ArrayList<StartBean> startBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            current = savedInstanceState.getInt("tab");
            Log.d("main", "打印的current：" + current);
        } else {
            current = 0;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUrl();
        if (getIntent().getParcelableArrayListExtra("startBeanList") != null) {
            startBeanList = getIntent().getParcelableArrayListExtra("startBeanList");
            preLoadPic(startBeanList);
        }

        title = getString(R.string.app_name);
        isSetLogo = true;
        isShowToolbar = false;
        isSetNavigationIcon = false;


        fragmentManager = getSupportFragmentManager();

        initView();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getUrl();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (current != -1) {
            outState.putInt("tab", current);
        }
        super.onSaveInstanceState(outState);
    }

    private void getUrl() {
        if (!StringUtil.isNullOrEmpty(getIntent().getStringExtra("url"))) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("url", getIntent().getStringExtra("url"));
            startActivity(intent);
        }
    }

    private void preLoadPic(ArrayList<StartBean> startBeanList) {
        for (int i = 0; i < startBeanList.size(); i++) {
            FrescoDealPicUtil.download(this, startBeanList.get(i).getImgUrl());
        }
    }


    //初始化UI空间
    private void initView() {

        tabs[0] = findViewById(R.id.tab_one);
        tab_img1 = (ImageView) findViewById(R.id.tab_img1);
        tab_text1 = (TextView) findViewById(R.id.tab_text1);

        tabs[1] = findViewById(R.id.tab_two);
        tab_img2 = (ImageView) findViewById(R.id.tab_img2);
        tab_text2 = (TextView) findViewById(R.id.tab_text2);

        tabs[2] = findViewById(R.id.tab_three);
        tab_img3 = (ImageView) findViewById(R.id.tab_img3);
        tab_text3 = (TextView) findViewById(R.id.tab_text3);

        tabs[3] = findViewById(R.id.tab_four);
        tab_img4 = (ImageView) findViewById(R.id.tab_img4);
        tab_text4 = (TextView) findViewById(R.id.tab_text4);

        for (int i = 0; i < tabs.length; i++) {
            final int j = i;
            tabs[i].setTag(i);
            tabs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectTab(j);
                }
            });
        }
        selectTab(current);
    }

    private void selectTab(int tab) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (tab) {
            case 0:
                if (currentTab != 0) {
                    clearTabSelection();
                    hideFragement(fragmentTransaction);
                    tab_text1.setTextColor(Color.parseColor("#78e0ff"));
                    tab_img1.setImageResource(R.drawable.simle_logo_00);
                    tab_img2.setImageResource(R.drawable.simle_logo_02);
                    tab_img3.setImageResource(R.drawable.simle_logo_03);
                    tab_img4.setImageResource(R.drawable.simle_logo_04);
                    if (oneFragment == null) {
                        oneFragment = new OneFragment();
                        fragmentTransaction.add(R.id.container, oneFragment, FRAGMENT_TAGS[0]);
                    } else {
                        fragmentTransaction.show(oneFragment);
                    }
                    current = 0;
                    currentTab = 0;
                }
                break;
            case 1:
                if (currentTab != 1) {
                    clearTabSelection();
                    hideFragement(fragmentTransaction);
                    tab_text2.setTextColor(Color.parseColor("#78e0ff"));
                    tab_img1.setImageResource(R.drawable.simle_logo_01);
                    tab_img2.setImageResource(R.drawable.simle_logo_00);
                    tab_img3.setImageResource(R.drawable.simle_logo_03);
                    tab_img4.setImageResource(R.drawable.simle_logo_04);
                    if (twoFragment == null) {
                        twoFragment = new TwoFragment();
                        fragmentTransaction.add(R.id.container, twoFragment, FRAGMENT_TAGS[1]);
                    } else {
                        fragmentTransaction.show(twoFragment);
                    }
                    current = 1;
                    currentTab = 1;
                }
                break;
            case 2:
                if (currentTab != 2) {
                    clearTabSelection();
                    hideFragement(fragmentTransaction);
                    tab_text3.setTextColor(Color.parseColor("#78e0ff"));
                    tab_img1.setImageResource(R.drawable.simle_logo_01);
                    tab_img2.setImageResource(R.drawable.simle_logo_02);
                    tab_img3.setImageResource(R.drawable.simle_logo_00);
                    tab_img4.setImageResource(R.drawable.simle_logo_04);
                    if (threeFragment == null) {
                        threeFragment = new ThreeFragment();
                        fragmentTransaction.add(R.id.container, threeFragment, FRAGMENT_TAGS[2]);
                    } else {
                        fragmentTransaction.show(threeFragment);
                    }
                    current = 2;
                    currentTab = 2;
                }
                break;
            case 3:
                if (currentTab != 3) {
                    clearTabSelection();
                    hideFragement(fragmentTransaction);
                    tab_text4.setTextColor(Color.parseColor("#78e0ff"));
                    tab_img1.setImageResource(R.drawable.simle_logo_01);
                    tab_img2.setImageResource(R.drawable.simle_logo_02);
                    tab_img3.setImageResource(R.drawable.simle_logo_03);
                    tab_img4.setImageResource(R.drawable.simle_logo_00);
                    if (fourFragment == null) {
                        fourFragment = new FourFragment();
                        fragmentTransaction.add(R.id.container, fourFragment, FRAGMENT_TAGS[3]);
                    } else {
                        fragmentTransaction.show(fourFragment);
                    }
                    current = 3;
                    currentTab = 3;
                }
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 设置为默认颜色
     */
    private void clearTabSelection() {
        tab_text1.setTextColor(Color.RED);
        tab_text2.setTextColor(Color.RED);
        tab_text3.setTextColor(Color.RED);
        tab_text4.setTextColor(Color.RED);
    }

    /**
     * 默认隐藏所有的fragment
     *
     * @param fragmentTransaction
     */

    private void hideFragement(FragmentTransaction fragmentTransaction) {
        if (oneFragment != null) {
            fragmentTransaction.hide(oneFragment);
        }
        if (twoFragment != null) {
            fragmentTransaction.hide(twoFragment);
        }
        if (threeFragment != null) {
            fragmentTransaction.hide(threeFragment);
        }
        if (fourFragment != null) {
            fragmentTransaction.hide(fourFragment);
        }
    }

    //初始化数据
    private void initData() {
        //获取开屏图
        FrescoDealPicUtil.download(this, "http://upload-images.jianshu.io/upload_images/2155432-43f1b0fe0916918d.JPG?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240");
    }

    private boolean firstBack = false;

    @Override
    public void onBackPressed() {

        if (!firstBack) {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            firstBack = true;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        firstBack = false;
        return super.dispatchTouchEvent(ev);
    }

}
