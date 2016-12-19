package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.PlayVideoActivity;
import com.fanyafeng.recreation.bean.YinYueBean;
import com.fanyafeng.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.fanyafeng.recreation.util.FrescoAttributeUtil;
import com.fanyafeng.recreation.util.FrescoUtil;

import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/19 下午5:51
 * Email: fanyafeng@live.cn
 */
public class YinYueAdapter extends BaseRecyclerAdapter<YinYueAdapter.YinYueViewHolder> {
    private Context context;
    private List<YinYueBean> yinYueBeanList;

    public YinYueAdapter(Context context, List<YinYueBean> yinYueBeanList) {
        this.context = context;
        this.yinYueBeanList = yinYueBeanList;
    }

    @Override
    public YinYueViewHolder getViewHolder(View view) {
        return new YinYueViewHolder(view);
    }

    @Override
    public YinYueViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_yinyue_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YinYueViewHolder holder, int position, boolean isItem) {
        final YinYueBean yinYueBean = yinYueBeanList.get(position);
        FrescoUtil.loadPicOnNet(holder.sdvImg, yinYueBean.getImg());
        holder.tvTitle.setText(yinYueBean.getTitle());
        FrescoUtil.loadPicOnNet(holder.sdvIcon, yinYueBean.getIcon());
        holder.sdvIcon.setHierarchy(FrescoAttributeUtil.setCircleRingHierarchy(context, Color.BLUE, 5f));
        holder.sdvIcon.bringToFront();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PlayVideoActivity.class);
                intent.putExtra("href", yinYueBean.getHref());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return yinYueBeanList.size();
    }

    class YinYueViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvImg;
        TextView tvTitle;
        SimpleDraweeView sdvIcon;

        public YinYueViewHolder(View itemView) {
            super(itemView);
            sdvImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvImg);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            sdvIcon = (SimpleDraweeView) itemView.findViewById(R.id.sdvIcon);
        }
    }
}
