package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.PreviewActivity;
import com.fanyafeng.recreation.refreshview.recyclerview.BaseRecyclerAdapter;
import com.fanyafeng.recreation.util.ControllerListenerUtil;
import com.fanyafeng.recreation.util.DpPxConvert;
import com.fanyafeng.recreation.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/8 11:22
 * Email: fanyafeng@live.cn
 */
public class AddNoteAdapter extends BaseRecyclerAdapter<AddNoteAdapter.AddNoteViewHolder> {
    private Context context;
    private List<String> imgList;
    private PopupWindow popupView;

    public AddNoteAdapter(Context context, List<String> imgList) {
        this.context = context;
        this.imgList = imgList;
        View popView = LayoutInflater.from(context).inflate(R.layout.layout_popwindow_recyclerview, null);
        popupView = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupView.setTouchable(true);
        popupView.setOutsideTouchable(true);
        popupView.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }

    @Override
    public AddNoteViewHolder getViewHolder(View view) {

        return new AddNoteViewHolder(view);
    }

    @Override
    public AddNoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_note_layout, parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddNoteViewHolder holder, final int position, boolean isItem) {
        final String img = imgList.get(position);
        ControllerListenerUtil.setControllerListener(holder.sdvAddItem, img,
                (int) (MyUtils.getScreenWidth(context) - DpPxConvert.dip2px(context, 20)));
        holder.sdvAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);
                if (MyUtils.getScreenHeight(context) - MyUtils.getNavigationBarHeight(context) - location[1] < MyUtils.dip2px(context, 40)) {
                    popupView.showAsDropDown(v, MyUtils.getScreenWidth(context) / 2 - (int) MyUtils.dip2px(context, 80), -v.getHeight() - (int) MyUtils.dip2px(context, 40));
                } else {
                    popupView.showAsDropDown(v, MyUtils.getScreenWidth(context) / 2 - (int) MyUtils.dip2px(context, 80), 0);
                }
                popupView.getContentView().findViewById(R.id.tvPreview).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String> list = new ArrayList<String>();
                        list.add(img);
                        Intent intent = new Intent(context, PreviewActivity.class);
                        intent.putStringArrayListExtra("list", list);
                        context.startActivity(intent);
                        popupView.dismiss();
                    }
                });
                popupView.getContentView().findViewById(R.id.tvDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imgList.remove(position);
                        popupView.dismiss();
                        notifyDataSetChanged();
                    }
                });


            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return imgList.size();
    }

    class AddNoteViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView sdvAddItem;

        public AddNoteViewHolder(View itemView) {
            super(itemView);
            sdvAddItem = (SimpleDraweeView) itemView.findViewById(R.id.sdvAddItem);
        }
    }

}
