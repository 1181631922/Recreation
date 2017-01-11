package com.fanyafeng.recreation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;
import android.widget.VideoView;

/**
 * Author： fanyafeng
 * Data： 17/1/11 下午6:06
 * Email: fanyafeng@live.cn
 */
public class FullScreenVideoView extends VideoView{
    public FullScreenVideoView(Context context) {
        super(context);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreenVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
        MediaController mediaController = new MediaController(getContext());
        mediaController.setVisibility(GONE);
        this.setMediaController(mediaController);
    }
}
