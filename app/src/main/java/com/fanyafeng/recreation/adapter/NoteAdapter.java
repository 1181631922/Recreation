package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.bean.NoteBean;

import java.util.List;

/**
 * Author： fanyafeng
 * Data： 16/12/7 16:59
 * Email: fanyafeng@live.cn
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
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
        NoteBean noteBean = noteBeanList.get(position);
        holder.tvNoteTitle.setText(noteBean.getTitle());
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
}
