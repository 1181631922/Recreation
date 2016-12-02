package com.fanyafeng.recreation.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.WindowManager;


import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyUtils {
    private static String channel = null;

    public static String getChannel(Context context) {

        if (channel == null) {
            ApplicationInfo ai;
            try {
                ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

                if (ai != null) {
                    channel = String.valueOf(ai.metaData.get("UMENG_CHANNEL"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return channel;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            @SuppressWarnings("rawtypes")
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");

            int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Get the screen height.
     *
     * @param context
     * @return the screen height
     */
    public static int getScreenHeight(Context context) {
        if (context != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            return displayMetrics.heightPixels;
        } else {
            return 1920;
        }
    }

    /**
     * Get the screen width.
     *
     * @param context
     * @return the screen width
     */
    public static int getScreenWidth(Context context) {
        if (context != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);

            return displayMetrics.widthPixels;
        } else {
            return 1080;
        }
    }

    public static float getDensity(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public static String getMetaValue(Context context, String metaKey) {

        if (context == null || metaKey == null) {
            return null;
        }

        try {
            ApplicationInfo aiApplicationInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);

            if (null != aiApplicationInfo) {
                if (null != aiApplicationInfo.metaData) {
                    return aiApplicationInfo.metaData.getString(metaKey);
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /***
     * 格式化日期
     *
     * @param time
     * @return
     */
    public static String formatDynamicDate(long time) {
        String formattedDate = "";

        long currentTime = System.currentTimeMillis() / 1000;

        long diff = currentTime - time;

//		LogUtils.d(Utils.class, "formatDynamicDate", "current:" + currentTime + " time:" + time);

        if (diff < 60) {
            formattedDate = diff + "秒前";
        } else if (diff < 60 * 60) {
            formattedDate = Math.round(diff / 60) + "分钟前";
        } else if (diff < 60 * 60 * 24) {
            formattedDate = Math.round(diff / (60 * 60)) + "小时前";
        } else if (diff < 60 * 60 * 24 * 7) {
            formattedDate = Math.round(diff / (60 * 60 * 24)) + "天前";
        } else if (diff < 60 * 60 * 24 * 30) {
            formattedDate = Math.round(diff / (60 * 60 * 24 * 7)) + "周前";
        } else if (diff < 60 * 60 * 24 * 365) {
            formattedDate = Math.round(diff / (60 * 60 * 24 * 30)) + "个月前";
        } else {
            formattedDate = DateFormat.format("yyyy年mm月dd日", time * 1000).toString();
        }

        return formattedDate;
    }

    /**
     * 格式化生日日期
     *
     * @param timeInMilliseconds
     * @return
     */
    public static String formatBirthdayDate(long timeInMilliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(timeInMilliseconds);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化日期时间
     *
     * @param timeInMilliseconds
     * @return
     */
    public static String formatSystemDatetime(long timeInMilliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timeInMilliseconds);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化日期时间
     * 毫秒*1000
     *
     * @param timeInMilliseconds
     * @return
     */
    public static String formatSystemDatetimeCN(long timeInMilliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(timeInMilliseconds*1000);
        return simpleDateFormat.format(date);
    }

    /**
     * 格式化日期时间
     * yyyy-MM-dd HH:mm:ss 转为 yyyy年MM月dd日 HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatSystemDatetimeCN(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String standarTime;

            Date date = simpleDateFormat.parse(time);
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            standarTime = simpleDateFormat.format(date);

            return standarTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 格式化日期
     * yyyy-MM-dd 转为 yyyy年MM月dd日
     *
     * @param dateStr
     * @return
     */
    public static String formatSystemDateCN(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String standarTime;

            Date date = simpleDateFormat.parse(dateStr);
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            standarTime = simpleDateFormat.format(date);

            return standarTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 格式化日期
     * 时间戳 转为 yyyy年MM月dd日
     *
     * @param timeInMilliseconds
     * @return
     */
    public static String formatSystemDateCN(long timeInMilliseconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(timeInMilliseconds);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取今日格式化日期
     * yyyy-MM-dd
     *
     * @return
     */
    public static String getTodayDateEN() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date(System.currentTimeMillis()));
    }


    /**
     * 格式化生日日期格式
     * yyyy-MM-dd HH:mm:ss 转为 yyyy年MM月dd日 HH时
     *
     * @param birthday
     * @return
     */
    public static String formatBirthday(String birthday, boolean isLunar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (birthday.equals("0000-00-00 00:00:00")) {
                return "无";
            }

            String standarBirthday = (isLunar ? "阴历" : "阳历");

            Date date = simpleDateFormat.parse(birthday);
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            standarBirthday += " " + simpleDateFormat.format(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);

            switch (hour) {
                case 23:
                case 0:
                    standarBirthday += " 子时";
                    break;

                case 1:
                case 2:
                    standarBirthday += " 丑时";
                    break;

                case 3:
                case 4:
                    standarBirthday += " 寅时";
                    break;

                case 5:
                case 6:
                    standarBirthday += " 卯时";
                    break;

                case 7:
                case 8:
                    standarBirthday += " 辰时";
                    break;

                case 9:
                case 10:
                    standarBirthday += " 巳时";
                    break;

                case 11:
                case 12:
                    standarBirthday += " 午时";
                    break;

                case 13:
                case 14:
                    standarBirthday += " 未时";
                    break;

                case 15:
                case 16:
                    standarBirthday += " 申时";
                    break;

                case 17:
                case 18:
                    standarBirthday += " 酉时";
                    break;

                case 19:
                case 20:
                    standarBirthday += " 戌时";
                    break;

                case 21:
                case 22:
                    standarBirthday += " 亥时";
                    break;

                default:
                    break;
            }


            return standarBirthday;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthday;
    }

    /**
     * 格式化生辰
     *
     * @param birthday
     * @param birthtime
     * @param isLunar
     * @return
     */
    public static String formatBirthday(String birthday, String birthtime, boolean isLunar) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if (StringUtil.isNullOrEmpty(birthday) || birthday.equals("0000-00-00 00:00:00")) {
                return "无";
            }

            String standarBirthday = (isLunar ? "阴历" : "阳历");

            Date date = simpleDateFormat.parse(birthday);
            simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            standarBirthday += " " + simpleDateFormat.format(date);

            if (birthtime != null && !birthtime.equals("")) {
                standarBirthday += " " + birthtime;
            }

            return standarBirthday;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthday;
    }

    /**
     * 获取日期的时间戳
     *
     * @param dateEn 格式yyyy-MM-dd
     * @return
     */
    public static long timeOfDateEN(String dateEn) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String standarTime;

            Date date = simpleDateFormat.parse(dateEn);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
