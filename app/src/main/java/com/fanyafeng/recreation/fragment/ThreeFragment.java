package com.fanyafeng.recreation.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fanyafeng.recreation.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ThreeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar_three;
    private LinearLayout layoutFodder;


    public ThreeFragment() {
    }

    public static ThreeFragment newInstance(String param1, String param2) {
        ThreeFragment fragment = new ThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_three, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
//        initData();
        Thread thread = new Thread(new LoadThread());
        thread.start();
    }

    private void initView() {
        toolbar_three = (Toolbar) getActivity().findViewById(R.id.toolbar_three);
        toolbar_three.setLogo(R.drawable.simle_logo_03);
        toolbar_three.setTitle("笑话");
        layoutFodder = (LinearLayout) getActivity().findViewById(R.id.layoutFodder);

        for (int i = 0; i < 3; i++) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(3);
            for (int j = 0; j < 3; j++) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_fodder_layout, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 1f;
                linearLayout.addView(view, layoutParams);
            }
            layoutFodder.addView(linearLayout);
        }


    }

    private void initData() {
        try {
            Document document = Jsoup.connect("http://home.meishichina.com/recipe-304301.html").get();

            //菜名
            Elements recipeDeImgBox = document.getElementsByClass("recipe_De_imgBox");
            String title = recipeDeImgBox.select("a").attr("title");
//            Log.d("jsoup", title);

            //顶部大图
            String imgUrl = recipeDeImgBox.select("a").select("img").attr("src");
//            Log.d("jsoup", imgUrl);

            //食材明细
            Elements fodderElement = document.getElementsByClass("mo mt20");
            String fodderTitle = fodderElement.get(0).select("h3").text();
//            Log.d("jsoup", fodderTitle);

            //具体食材
            Elements fodderItemElement = document.getElementsByClass("recipeCategory_sub_R clear");
            int fodderSize = fodderItemElement.select(".category_s1").size();
            for (int i = 0; i < fodderSize; i++) {
                String fodderName = fodderItemElement.select(".category_s1").get(i).select("b").text();
//                Log.d("jsoup", fodderName);//食材名称
                String fodderMany = fodderItemElement.select(".category_s2").get(i).text();
//                Log.d("jsoup", fodderMany);//食材计量
            }

            //食材工艺
            Elements fodderArtElement = document.getElementsByClass("recipeCategory_sub_R mt30 clear");
            int fodderArtSize = fodderArtElement.select(".category_s1").size();
            for (int i = 0; i < fodderArtSize; i++) {
                String fodderArtTitle = fodderItemElement.select(".category_s1").get(i).select("a").attr("title");
//                Log.d("jsoup", fodderArtTitle);
                String fodderArtDetail = fodderItemElement.select(".category_s2").get(i).text();
//                Log.d("jsoup", fodderArtDetail);
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
}
