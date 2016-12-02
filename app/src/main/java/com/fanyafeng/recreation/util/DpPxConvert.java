package com.fanyafeng.recreation.util;

import android.content.Context;

/**
 * Created by  on 2015/09/28/0028.
 */
public class DpPxConvert {
    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) (dipValue * scale + 0.5f);
    }

    public static float px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) (pxValue / scale + 0.5f);
    }
}
