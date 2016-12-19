package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.bean.MenuDetailBean;
import com.fanyafeng.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.fanyafeng.recreation.util.FrescoUtil;

import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/16 下午6:15
 * Email: fanyafeng@live.cn
 */
public class MenuDetailAdapter extends BaseRecyclerAdapter<MenuDetailAdapter.MenuDetailViewHolder> {
    private Context context;
    private List<MenuDetailBean> menuDetailBeanList;

    public MenuDetailAdapter(Context context, List<MenuDetailBean> menuDetailBeanList) {
        this.context = context;
        this.menuDetailBeanList = menuDetailBeanList;
    }

    @Override
    public MenuDetailViewHolder getViewHolder(View view) {
        return new MenuDetailViewHolder(view);
    }

    @Override
    public MenuDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_detail_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuDetailViewHolder holder, int position, boolean isItem) {
        MenuDetailBean menuDetailBean = menuDetailBeanList.get(position);
        FrescoUtil.loadPicOnNet(holder.sdvMenuDetailImg, menuDetailBean.getMenuDetailImg());
        holder.tvMenuDetai.setText(menuDetailBean.getMenuDetail());
    }

    @Override
    public int getAdapterItemCount() {
        return menuDetailBeanList.size();
    }

    class MenuDetailViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvMenuDetailImg;
        TextView tvMenuDetai;

        public MenuDetailViewHolder(View itemView) {
            super(itemView);
            sdvMenuDetailImg = (SimpleDraweeView) itemView.findViewById(R.id.sdvMenuDetailImg);
            tvMenuDetai = (TextView) itemView.findViewById(R.id.tvMenuDetai);
        }
    }
}
