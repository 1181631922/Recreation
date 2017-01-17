package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.bean.MainDetailBean;
import com.fanyafeng.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.fanyafeng.recreation.util.FrescoAttributeUtil;
import com.fanyafeng.recreation.util.FrescoUtil;

import java.util.List;

/**
 * Author： fanyafeng
 * Data： 17/1/17 上午11:52
 * Email: fanyafeng@live.cn
 */
public class MainDetailAdapter extends BaseRecyclerAdapter<MainDetailAdapter.MainDetailViewHolder> {

    private Context context;
    private List<MainDetailBean> mainDetailBeanList;

    public MainDetailAdapter(Context context, List<MainDetailBean> mainDetailBeanList) {
        this.context = context;
        this.mainDetailBeanList = mainDetailBeanList;
    }

    @Override
    public MainDetailViewHolder getViewHolder(View view) {
        return new MainDetailViewHolder(view);
    }

    @Override
    public MainDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_detail_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainDetailViewHolder holder, int position, boolean isItem) {
        MainDetailBean mainDetailBean = mainDetailBeanList.get(position);
        holder.tvDetailUserName.setText(mainDetailBean.getUserName());
        holder.tvMainDetailItem.setText(mainDetailBean.getText());
        FrescoUtil.loadPicOnNet(holder.sdvDetailUserImg, mainDetailBean.getUserImg());
        holder.sdvDetailUserImg.setHierarchy(FrescoAttributeUtil.setCircleRingHierarchy(context, Color.BLUE, 2f));

    }

    @Override
    public int getAdapterItemCount() {
        return mainDetailBeanList.size();
    }

    class MainDetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvDetailUserName;
        TextView tvMainDetailItem;
        SimpleDraweeView sdvDetailUserImg;

        public MainDetailViewHolder(View itemView) {
            super(itemView);
            tvDetailUserName = (TextView) itemView.findViewById(R.id.tvDetailUserName);
            tvMainDetailItem = (TextView) itemView.findViewById(R.id.tvMainDetailItem);
            sdvDetailUserImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvDetailUserImg);
        }
    }
}
