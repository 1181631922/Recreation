package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.activity.AddNoteActivity;
import com.fanyafeng.recreation.bean.NoteBean;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/7 16:59
 * Email: fanyafeng@live.cn
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements StickyRecyclerHeadersAdapter<NoteAdapter.NoteHeaderViewHolder> {
    private Context context;
    private List<NoteBean> noteBeanList;

    public NoteAdapter(Context context, List<NoteBean> noteBeanList) {
        this.context = context;
        this.noteBeanList = noteBeanList;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note_layout, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        final NoteBean noteBean = noteBeanList.get(position);
        holder.tvNoteTitle.setText(noteBean.getTitle());
        if (noteBean.isHasPic()) {
            holder.ivNoteIcon.setVisibility(View.VISIBLE);
        } else {
            holder.ivNoteIcon.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddNoteActivity.class);
                intent.putExtra("noteDataId", noteBean.getId());
                context.startActivity(intent);
            }
        });
        if (position == noteBeanList.size() - 1) {
            holder.tvItemLine.setVisibility(View.GONE);
        } else {
            holder.tvItemLine.setVisibility(View.VISIBLE);
        }
        if (position + 1 < noteBeanList.size()) {
            if (noteBeanList.get(position + 1) != null) {
                if (noteBeanList.get(position).getTitleHeader() != noteBeanList.get(position + 1).getTitleHeader()) {
                    holder.tvItemLine.setVisibility(View.GONE);
                } else {
                    holder.tvItemLine.setVisibility(View.VISIBLE);
                }
            } else {
                holder.tvItemLine.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public long getHeaderId(int position) {
        return noteBeanList.get(position).getTitleHeader();
    }

    @Override
    public NoteHeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.header_note_layout, parent, false);
        return new NoteHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(NoteHeaderViewHolder holder, int position) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        long nowTime = noteBeanList.get(position).getTitleHeader() * 1000 * 60 * 60 * 24;
        Date curDate = new Date(nowTime);
        holder.tvNoteTime.setText("时间：" + formatter.format(curDate));
        Log.d("时间：", "得到的长整形时间：" + noteBeanList.get(position).getTitleHeader());
    }

    @Override
    public int getItemCount() {
        return noteBeanList.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteLine;
        TextView tvNoteTitle;
        ImageView ivNoteIcon;
        TextView tvItemLine;

        public NoteViewHolder(View itemView) {
            super(itemView);
            tvNoteLine = (TextView) itemView.findViewById(R.id.tvNoteLine);
            tvNoteTitle = (TextView) itemView.findViewById(R.id.tvNoteTitle);
            ivNoteIcon = (ImageView) itemView.findViewById(R.id.ivNoteIcon);
            tvItemLine = (TextView) itemView.findViewById(R.id.tvItemLine);
        }
    }

    class NoteHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTime;

        public NoteHeaderViewHolder(View itemView) {
            super(itemView);
            tvNoteTime = (TextView) itemView.findViewById(R.id.tvNoteTime);
        }
    }
}
