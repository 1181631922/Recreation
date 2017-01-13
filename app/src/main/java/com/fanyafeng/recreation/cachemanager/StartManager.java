package com.fanyafeng.recreation.cachemanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.fanyafeng.recreation.util.StringUtil;

/**
 * Author： fanyafeng
 * Data： 17/1/13 上午10:56
 * Email: fanyafeng@live.cn
 */
public class StartManager {

    public static void setStartCache(Context context, String keyName, String value) {
        if (!StringUtil.isNullOrEmpty(value)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(Start.START_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(keyName, value);
            editor.commit();
        }
    }

    public static String getStartCache(Context context, String keyName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Start.START_INFO, Context.MODE_PRIVATE);
        return sharedPreferences.getString(keyName, "");
    }

    public static void setFirstState(Context context, String keyName, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Start.START_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(keyName, !value);
        editor.commit();
    }

    public static boolean getFirstState(Context context, String keyName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Start.START_INFO, Context.MODE_PRIVATE);
        return !sharedPreferences.getBoolean(keyName, false);
    }


}
