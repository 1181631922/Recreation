package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.MainDetailActivity;
import com.fanyafeng.recreation.activity.PreGifviewActivity;
import com.fanyafeng.recreation.activity.PreviewActivity;
import com.fanyafeng.recreation.bean.MainItemBean;
import com.fanyafeng.recreation.network.Urls;
import com.fanyafeng.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.fanyafeng.recreation.util.ControllerListenerUtil;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.FrescoAttributeUtil;
import com.fanyafeng.recreation.util.FrescoUtil;
import com.fanyafeng.recreation.util.MyUtils;
import com.fanyafeng.recreation.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/11/9 14:52
 * Email: fanyafeng@live.cn
 */
public class MainAdapter extends BaseRecyclerAdapter<MainAdapter.MainViewHolder> {

    private Context context;
    private List<MainItemBean> mainItemBeanList;

    public MainAdapter(Context context, List<MainItemBean> mainItemBeanList) {
        this.context = context;
        this.mainItemBeanList = mainItemBeanList;
    }

    @Override
    public MainViewHolder getViewHolder(View view) {
        return new MainViewHolder(view);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_layout, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position, boolean isItem) {
        MainViewHolder mainViewHolder = holder;
        final MainItemBean mainItemBean = mainItemBeanList.get(position);
        mainViewHolder.tvMainItem.setText(mainItemBean.getContent());
        mainViewHolder.sdvMainItem.setVisibility(View.GONE);

        //user
        mainViewHolder.layoutUser.setVisibility(View.GONE);
        if (!StringUtil.isNullOrEmpty(mainItemBean.getUserName())) {
            mainViewHolder.tvUserName.setText(mainItemBean.getUserName());
            mainViewHolder.layoutUser.setVisibility(View.VISIBLE);
        } else {
            mainViewHolder.layoutUser.setVisibility(View.GONE);
        }
        if (!StringUtil.isNullOrEmpty(mainItemBean.getUserImg())) {
            FrescoUtil.loadPicOnNet(mainViewHolder.sdvUserImg, mainItemBean.getUserImg());
            mainViewHolder.sdvUserImg.setHierarchy(FrescoAttributeUtil.setCircleRingHierarchy(context, Color.BLUE, 2f));
        }

        if (!StringUtil.isNullOrEmpty(mainItemBean.getImage())) {
            mainViewHolder.sdvMainItem.setVisibility(View.VISIBLE);
            final String img = mainItemBean.getImage();
            ControllerListenerUtil.setControllerListener(mainViewHolder.sdvMainItem, img,MyUtils.getScreenWidth(context));
            mainViewHolder.sdvMainItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mainItemBean.getIsGif() != 1) {
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(img);
                        Intent intent = new Intent(context, PreviewActivity.class);
                        intent.putStringArrayListExtra("list", list);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, PreGifviewActivity.class);
                        intent.putExtra("url", img);
                        context.startActivity(intent);
                    }
                }
            });
        } else {
            mainViewHolder.sdvMainItem.setVisibility(View.GONE);
        }
        mainViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainDetailActivity.class);
                intent.putExtra("mainItemBean", mainItemBean);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mainItemBeanList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        TextView tvMainItem;
        SimpleDraweeView sdvMainItem;
        SimpleDraweeView sdvUserImg;
        TextView tvUserName;
        LinearLayout layoutUser;

        public MainViewHolder(View itemView) {
            super(itemView);
            tvMainItem = (TextView) itemView.findViewById(R.id.tvMainItem);
            sdvMainItem = (SimpleDraweeView) itemView.findViewById(R.id.sdvMainItem);
            sdvUserImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvUserImg);
            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            layoutUser = (LinearLayout) itemView.findViewById(R.id.layoutUser);
        }
    }
}
