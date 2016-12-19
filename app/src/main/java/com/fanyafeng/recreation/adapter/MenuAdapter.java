package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.MenuDetailActivity;
import com.fanyafeng.recreation.bean.MenuBean;
import com.fanyafeng.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.fanyafeng.recreation.util.FrescoUtil;

import java.util.List;

/**
 * Created by fanyafeng on 16/12/16.
 */

public class MenuAdapter extends BaseRecyclerAdapter<MenuAdapter.MenuViewHolder> {
    private Context context;
    private List<MenuBean> menuBeanList;

    public MenuAdapter(Context context, List<MenuBean> menuBeanList) {
        this.context = context;
        this.menuBeanList = menuBeanList;
    }


    @Override
    public MenuViewHolder getViewHolder(View view) {
        return new MenuViewHolder(view);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position, boolean isItem) {
        final MenuBean menuBean = menuBeanList.get(position);
        FrescoUtil.loadPicOnNet(holder.sdvMenuImg, menuBean.getPic(), 1.0f);
        holder.tvMenuTitle.setText(menuBean.getTitle());
        holder.tvBurden.setText(menuBean.getBurden());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MenuDetailActivity.class);
                intent.putExtra("url", menuBean.getUrl());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getAdapterItemCount() {
        return menuBeanList.size();
    }

    class MenuViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvMenuImg;
        TextView tvMenuTitle;
        TextView tvBurden;

        public MenuViewHolder(View itemView) {
            super(itemView);
            sdvMenuImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvMenuImg);
            tvMenuTitle = (TextView) itemView.findViewById(R.id.tvMenuTitle);
            tvBurden = (TextView) itemView.findViewById(R.id.tvBurden);
        }
    }

}
