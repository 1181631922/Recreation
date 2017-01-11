package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.fragment.StartPagerFragment;

import java.util.ArrayList;
import java.util.List;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class FirstStartActivity extends BaseActivity {
    private ViewPager startViewPager;
    private StartPagerAdapter startPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_first_start);
        isShowToolbar = false;
        initView();
        initData();
    }

    //初始化UI控件
    private void initView() {

        startViewPager = (ViewPager) findViewById(R.id.startViewPager);
        for (int i = 0; i < 3; i++) {
            fragmentList.add(new StartPagerFragment());
        }

    }

    //初始化数据
    private void initData() {
        startPagerAdapter = new StartPagerAdapter(getSupportFragmentManager(), fragmentList);
        startViewPager.setAdapter(startPagerAdapter);
    }

    class StartPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragmentList;

        public StartPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}
