package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.view.photoview.PhotoView;

import java.util.ArrayList;

//需要搭配baseactivity，这里默认为baseactivity,并且默认Baseactivity为包名的根目录
public class PreviewActivity extends BaseActivity {
    private ViewPager pager;
    private int currPos = -1;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
//这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_preview);
        list = getIntent().getStringArrayListExtra("list");

        initView();
        initData();
    }


    //初始化UI空间
    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    //初始化数据
    private void initData() {
        if (list == null || list.size() == 0) {
            toolbar.setTitle("0 / 0");
            return;
        }

        pager = (ViewPager) findViewById(R.id.pager);

        int select = getIntent().getIntExtra("select", 0);
        currPos = select;
        toolbar.setTitle((select + 1) + " / " + list.size());

        pager.setAdapter(new Adapter());
        pager.setCurrentItem(select, false);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                toolbar.setTitle((i + 1) + " / " + list.size());
                currPos = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    class Adapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PhotoView view = new PhotoView(PreviewActivity.this);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            view.setImageUri(list.get(position));
//            view.setImageURI(Uri.parse(list.get(position)));
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

}
