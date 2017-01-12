package com.fanyafeng.recreation.activity;

import android.content.Intent;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.fragment.StartPagerFragment;
import com.fanyafeng.recreation.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class FirstStartActivity extends BaseActivity {
    private ViewPager startViewPager;
    private StartPagerAdapter startPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Button btnFirstStart;
    private int maxX = 0;
    private ImageView ivMovementCircle;
    private static int[] startPage = new
            int[]{R.drawable.start_one, R.drawable.start_two, R.drawable.start_three, R.drawable.start_four, R.drawable.start_five};
    private int totalSize = 5;
    private RelativeLayout layoutStartRoot;
    private float moveCircleY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_first_start);
        isShowToolbar = false;
        initView();
        initData();
        initCursorPosition();
    }

    //初始化UI控件
    private void initView() {
        layoutStartRoot = (RelativeLayout) findViewById(R.id.layoutStartRoot);
        ivMovementCircle = (ImageView) findViewById(R.id.ivMovementCircle);
        btnFirstStart = (Button) findViewById(R.id.btnFirstStart);
        startViewPager = (ViewPager) findViewById(R.id.startViewPager);
        for (int i = 0; i < totalSize; i++) {
            fragmentList.add(new StartPagerFragment());
        }

    }

    private void initCursorPosition() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width = displayMetrics.widthPixels;
        maxX = width;
        Matrix matrix = new Matrix();
        matrix.postTranslate(width / (totalSize + 1) - MyUtils.dip2px(this, 4), 0);
        ivMovementCircle.setImageMatrix(matrix);
        ivMovementCircle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (ivMovementCircle.getY() != 0) {
                    moveCircleY = ivMovementCircle.getY();
                    for (int i = 0; i < totalSize; i++) {
                        ImageView imageView = new ImageView(FirstStartActivity.this);
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.white_shape_circle));
                        imageView.setX(width / (totalSize + 1) * (i + 1) - MyUtils.dip2px(FirstStartActivity.this, 3));
                        imageView.setY(moveCircleY);
                        layoutStartRoot.addView(imageView);
                        ivMovementCircle.bringToFront();
                    }
                } else {
                    return;
                }
                ivMovementCircle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

    }

    //初始化数据
    private void initData() {
        startPagerAdapter = new StartPagerAdapter(getSupportFragmentManager(), fragmentList);
        startViewPager.setAdapter(startPagerAdapter);
        startViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (maxX != 0 && positionOffsetPixels != 0) {
                    float alph = 255 * positionOffsetPixels / maxX;
                    if (position == totalSize - 2) {
                        btnFirstStart.setVisibility(View.VISIBLE);
                        btnFirstStart.getBackground().setAlpha((int) alph);
                        btnFirstStart.setAlpha(alph);
                    } else {
                        btnFirstStart.setVisibility(View.GONE);
                    }
                }
                if (positionOffsetPixels != 0) {
                    ViewCompat.setTranslationX(ivMovementCircle, maxX / (totalSize + 1) * position + positionOffsetPixels / (totalSize + 1));
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class StartPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList;

        public StartPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("param1", String.valueOf(position));
            bundle.putInt("param2", startPage[position]);
            Fragment fragment = fragmentList.get(position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btnFirstStart:
                finish();
                break;
            case R.id.btnFirstFinish:
                finish();
                break;
        }
        startActivity(new Intent(this, MainActivity.class));
    }
}
