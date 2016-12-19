package com.fanyafeng.recreation.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.BaseActivity;
import com.fanyafeng.recreation.adapter.MenuDetailAdapter;
import com.fanyafeng.recreation.bean.FodderArtBean;
import com.fanyafeng.recreation.bean.FodderBean;
import com.fanyafeng.recreation.bean.MenuDetailBean;
import com.fanyafeng.recreation.fragment.ThreeFragment;
import com.fanyafeng.recreation.util.ControllerListenerUtil;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.MyUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

//需要搭配Baseactivity，这里默认为Baseactivity,并且默认BaseActivity为包名的根目录
public class MenuDetailActivity extends BaseActivity {
    private String url;
    private String myTitle;
    private RecyclerView rvMenuDetail;
    private List<MenuDetailBean> menuDetailBeanList = new ArrayList<>();

    private MenuDetailAdapter menuDetailAdapter;

    private View headerView;
    private SimpleDraweeView sdvMenuDetailHeader;
    private LinearLayout layoutFodder;
    private LinearLayout layoutFodderArt;
    private TextView tvRVTitle;

    private List<FodderBean> fodderBeanList = new ArrayList<>();
    private List<FodderArtBean> fodderArtBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_detail);
        //这里默认使用的是toolbar的左上角标题，如果需要使用的标题为中心的采用下方注释的代码，将此注释掉即可
        title = getString(R.string.title_activity_menu_detail);
        url = getIntent().getStringExtra("url");

        Log.d("jsoup", url);

        initView();
//        initData();
        Thread thread = new Thread(new LoadThread());
        thread.start();
    }


    //初始化UI控件
    private void initView() {
        rvMenuDetail = (RecyclerView) findViewById(R.id.rvMenuDetail);
        rvMenuDetail.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        menuDetailAdapter = new MenuDetailAdapter(this, menuDetailBeanList);
        headerView = menuDetailAdapter.setHeaderView(R.layout.header_menu_detail_layout, rvMenuDetail);
        layoutFodder = (LinearLayout) headerView.findViewById(R.id.layoutFodder);
        layoutFodderArt = (LinearLayout) headerView.findViewById(R.id.layoutFodderArt);
        tvRVTitle = (TextView) headerView.findViewById(R.id.tvRVTitle);
        sdvMenuDetailHeader = (SimpleDraweeView) headerView.findViewById(R.id.sdvMenuDetailHeader);
        rvMenuDetail.setAdapter(menuDetailAdapter);
    }

    private void initData() {
        try {
            Document document = Jsoup.connect(url).get();

            //菜名
            Elements recipeDeImgBox = document.getElementsByClass("recipe_De_imgBox");
            myTitle = recipeDeImgBox.select("a").attr("title");
            Message titleMessage = Message.obtain();
            titleMessage.what = 1;
            handler.sendMessage(titleMessage);

//            Log.d("jsoup", title);

            //顶部大图
            String imgUrl = recipeDeImgBox.select("a").select("img").attr("src");
//            Log.d("jsoup", imgUrl);
            Message headerImgUrlMessage = Message.obtain();
            headerImgUrlMessage.what = 2;
            headerImgUrlMessage.obj = imgUrl;
            handler.sendMessage(headerImgUrlMessage);

            //食材明细
            Elements fodderElement = document.getElementsByClass("mo mt20");
//            String fodderTitle = fodderElement.get(0).select("h3").text();
//            Log.d("jsoup", fodderTitle);

            //具体食材
            Elements fodderItemElement = document.getElementsByClass("recipeCategory_sub_R clear");
            int fodderSize = fodderItemElement.select(".category_s1").size();
            for (int i = 0; i < fodderSize; i++) {
                String fodderName = fodderItemElement.select(".category_s1").get(i).select("b").text();
//                Log.d("jsoup", fodderName);//食材名称
                String fodderMany = fodderItemElement.select(".category_s2").get(i).text();
//                Log.d("jsoup", fodderMany);//食材计量
                FodderBean fodderBean = new FodderBean();
                fodderBean.setFooderName(fodderName);
                fodderBean.setFooderMany(fodderMany);
                fodderBeanList.add(fodderBean);
            }
//            Log.d("jsoup", fodderBeanList.toString());

            int weight = fodderSize % 3 == 0 ? fodderSize / 3 : fodderSize / 3 + 1;

            for (int i = 0; i < weight; i++) {
                LinearLayout linearLayout = new LinearLayout(MenuDetailActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(3);
                for (int j = 0; j < 3; j++) {
                    View view = LayoutInflater.from(MenuDetailActivity.this).inflate(R.layout.item_fodder_layout, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 1f;
                    if ((3 * i + j + 1) <= fodderSize) {
                        FodderBean fodderBean = fodderBeanList.get(3 * i + j);
                        TextView tvFodderTitle = (TextView) view.findViewById(R.id.tvFodderTitle);
                        tvFodderTitle.setText(fodderBean.getFooderName());
                        TextView tvFodderNum = (TextView) view.findViewById(R.id.tvFodderNum);
                        tvFodderNum.setText(fodderBean.getFooderMany());
                    }
                    linearLayout.addView(view, layoutParams);
                }
                Message message3 = Message.obtain();
                message3.obj = linearLayout;
                message3.what = 3;
                handler.sendMessage(message3);
//                layoutFodder.addView(linearLayout);子线程加载太多控件更新ui
            }

            //食材工艺
            Elements fodderArtElement = document.getElementsByClass("recipeCategory_sub_R mt30 clear");
            int fodderArtSize = fodderArtElement.select(".category_s1").size();
            for (int i = 0; i < fodderArtSize; i++) {
                String fodderArtTitle = fodderArtElement.select(".category_s1").get(i).select("a").attr("title");
                Log.d("jsoup", fodderArtTitle);
                String fodderArtDetail = fodderArtElement.select(".category_s2").get(i).text();
                Log.d("jsoup", fodderArtDetail);
                FodderArtBean fodderArtBean = new FodderArtBean();
                fodderArtBean.setFodderArtTitle(fodderArtTitle);
                fodderArtBean.setFodderArtDetail(fodderArtDetail);
                fodderArtBeanList.add(fodderArtBean);
            }

            Log.d("jsoup", fodderArtBeanList.toString());

            int Aweight = fodderArtSize % 3 == 0 ? fodderArtSize / 3 : fodderArtSize / 3 + 1;

            for (int i = 0; i < Aweight; i++) {
                LinearLayout linearLayout = new LinearLayout(MenuDetailActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(3);
                for (int j = 0; j < 3; j++) {
                    View view = LayoutInflater.from(MenuDetailActivity.this).inflate(R.layout.item_fodder_layout, null);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.weight = 1f;
                    if ((3 * i + j + 1) <= fodderArtSize) {
                        FodderArtBean fodderArtBean = fodderArtBeanList.get(3 * i + j);
                        TextView tvFodderTitle = (TextView) view.findViewById(R.id.tvFodderTitle);
                        tvFodderTitle.setText(fodderArtBean.getFodderArtTitle());
                        TextView tvFodderNum = (TextView) view.findViewById(R.id.tvFodderNum);
                        tvFodderNum.setText(fodderArtBean.getFodderArtDetail());
                    }
                    linearLayout.addView(view, layoutParams);
                }
                Message message4 = Message.obtain();
                message4.obj = linearLayout;
                message4.what = 4;
                handler.sendMessage(message4);
//                layoutFodder.addView(linearLayout);子线程加载太多控件更新ui
            }


            //步骤讲解
            Elements recipeStepElement = document.getElementsByClass("recipeStep_word");
            Elements recipeStepImgElement = document.getElementsByClass("recipeStep_img");

            int recipeStepSize = recipeStepElement.size();
            for (int i = 0; i < recipeStepSize; i++) {
                String recipeImg = recipeStepImgElement.get(i).select("img").attr("src");
//                Log.d("jsoup", recipeImg);//步骤图片
                String recipeStep = recipeStepElement.get(i).text();
//                Log.d("jsoup", recipeStep);//步骤说明
                MenuDetailBean menuDetailBean = new MenuDetailBean();
                menuDetailBean.setMenuDetailImg(recipeImg);
                menuDetailBean.setMenuDetail(recipeStep);
                menuDetailBeanList.add(menuDetailBean);
                Message message = Message.obtain();
                message.what = 0;
                handler.sendMessage(message);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LoadThread implements Runnable {

        @Override
        public void run() {
            initData();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    menuDetailAdapter.notifyDataSetChanged();
                    break;
                case 1:
                    toolbar.setTitle(myTitle);
                    tvRVTitle.setText(myTitle);
                    break;
                case 2:
                    ControllerListenerUtil.setControllerListener(sdvMenuDetailHeader, (String) msg.obj,
                            MyUtils.getScreenWidth(MenuDetailActivity.this));
                    break;
                case 3:
                    LinearLayout linearLayout = (LinearLayout) msg.obj;
                    layoutFodder.addView(linearLayout);
                    break;
                case 4:
                    LinearLayout linearLayout1 = (LinearLayout) msg.obj;
                    layoutFodderArt.addView(linearLayout1);
                    break;
            }
        }
    };

}
