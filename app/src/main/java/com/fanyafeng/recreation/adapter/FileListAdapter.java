package com.fanyafeng.recreation.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanyafeng.recreation.R;
import com.fanyafeng.recreation.eventbus.FileExploreEvents;
import com.fanyafeng.recreation.util.PathCursor;

/**
 * Author： fanyafeng
 * Data： 16/10/27 15:49
 * Email: fanyafeng@live.cn
 */
public class FileListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Cursor cursor;

    private OnItemClickListener onItemClickListener;

    public FileListAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, Cursor c);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_file_list, parent, false);
        return new FileListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FileListViewHolder fileListViewHolder = (FileListViewHolder) holder;
        fileListViewHolder.name.setText(getFileName(position));
        if (isDirectory(position)) {
            fileListViewHolder.icon.setImageResource(R.drawable.ic_theme_folder);
        } else if (isVideo(position)) {
            fileListViewHolder.icon.setImageResource(R.drawable.ic_theme_play_arrow);
        } else {
            fileListViewHolder.icon.setImageResource(R.drawable.ic_theme_description);
        }
        fileListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = FileListAdapter.this.getFilePath(position);
                if (TextUtils.isEmpty(path))
                    return;
                FileExploreEvents.getBus().post(new FileExploreEvents.OnClickFile(path));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    class FileListViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        public FileListViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    Cursor moveToPosition(int position) {
        final Cursor cursor = this.cursor;
        if (cursor.getCount() == 0 || position >= cursor.getCount()) {
            return null;
        }
        cursor.moveToPosition(position);
        return cursor;
    }

    public boolean isDirectory(int position) {
        final Cursor cursor = moveToPosition(position);
        if (cursor == null)
            return true;
        return cursor.getInt(PathCursor.CI_IS_DIRECTORY) != 0;
    }

    public boolean isVideo(int position) {
        final Cursor cursor = moveToPosition(position);
        if (cursor == null)
            return true;
        return cursor.getInt(PathCursor.CI_IS_VIDEO) != 0;
    }

    public String getFileName(int position) {
        final Cursor cursor = moveToPosition(position);
        if (cursor == null)
            return "";
        return cursor.getString(PathCursor.CI_FILE_NAME);
    }

    public String getFilePath(int position) {
        final Cursor cursor = moveToPosition(position);
        if (cursor == null)
            return "";
        return cursor.getString(PathCursor.CI_FILE_PATH);
    }


}
