package com.fanyafeng.recreation.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zgw on 16/5/14 15:28.
 */

public class DateFormatManager {

    /**
     * 传入格式为hh:m 或h:mm 或h:m 的时间，返回hh:mm格式的时间
     *
     * @param time
     * @return hh:mm格式的时间
     */
    public static String Pad(String time) {
        if (time.length() > 5) {
            time = time.substring(time.indexOf(" ", 0) + 1, time
                    .lastIndexOf(":"));
        }
        if (time.indexOf(":", 0) == 1)
            time = "0" + time;
        if (time.length() - time.indexOf(":", 0) == 2)
            time = time.substring(0, time.indexOf(":", 0) + 1) + "0"
                    + time.substring(time.indexOf(":", 0) + 1);
        return time;
    }

    /**
     * 将数字变为两位数以上，若小于10则前面补0
     *
     * @param num
     * @return 两位或多位的数字
     */
    public static String Pad(int num) {
        if (num < 10)
            return "0" + num;
        else
            return "" + num;
    }

    /**
     * 取得阳历某一天的开始时间，将Calendar格式时间转为"yyyy:MM:dd 00:00:00"格式
     *
     * @param now Calendar格式的时间
     * @return "yyyy:MM:dd 00:00:00"格式的时间
     */
    public static String GetDateAtBeginString(Calendar now) {
        return now.get(Calendar.YEAR) + "-" + Pad(now.get(Calendar.MONTH) + 1) + "-" + Pad(now.get(Calendar.DAY_OF_MONTH)) + " " + "00:00:00";
    }

    /**
     * 取得农历某一天的开始时间，将LunarItem格式时间转为"yyyy:MM:dd 00:00:00"格式
     *
     * @param now Calendar格式的时间
     * @return "yyyy:MM:dd 00:00:00"格式的时间
     */
    public static String GetDateAtBeginString(LunarItem now) {
        return now.getYear() + "-" + Pad(now.getMonth() + 1) + "-" + Pad(now.getDay()) + " " + "00:00:00";
    }

//	public final static String GetDateString(Calendar now) {
//    	return now.get(Calendar.YEAR)+"-"+Pad(now.get(Calendar.MONTH)+1)+"-"+Pad(now.get(Calendar.DAY_OF_MONTH))+" "+Pad(now.get(Calendar.HOUR))+":"+Pad(now.get(Calendar.MINUTE))+":00";
//	}
//
//	public final static String GetDateString(LunarItem now) {
//    	return now.getYear()+"-"+Pad(now.getMonth()+1)+"-"+Pad(now.getDay())+" "+"00:00:00";
//	}
//

    /**
     * 取得中文星期几的字符串
     *
     * @param date 按周的第一天为星期天的的格式
     * @return 中文"周几"
     */
    public static String DayOfWeekDisplay(int date) {
        String[] chineseNum = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};
        return "周" + chineseNum[date];
    }

    /**
     * 取得中文星期几的字符串
     *
     * @param date 按周的第一天为星期天的的格式
     * @return 中文"星期几"
     */
    public static String DayOfXingqiDisplay(int date) {
        String[] chineseNum = new String[]{"", "日", "一", "二", "三", "四", "五", "六"};
        return "星期" + chineseNum[date];
    }

    /**
     * 将多个周几的数字字符串转换为中文字符串
     *
     * @param str 数字周几的字符串，按周的第一天为星期天的的格式，逗号分隔
     * @return 中文字符串，逗号分隔
     */
    public static String WeekOfDayDisplay(String str) {
        if (str == null)
            return "";
        String[] chineseNum = new String[]{"", "日", "一", "二", "三", "四", "五",
                "六"};
        String value = "";

        for (int i = 0; i < str.length(); i += 2) {
            if (i > 0)
                value += ",";
            value += chineseNum[str.charAt(i) - '0'];
        }
        return value;
    }

    /**
     * 将以周日为第一天的字符串变为周一为第一天的字符串  1,2,3 -> 7,1,2
     *
     * @param str
     * @return
     */
    public static String padDayOfWeekCtoS(String str) {
        if (str == null)
            return "";
        String value = "";
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ',')
                value += ",";
            else if (str.charAt(i) == '1')
                value += "7";
            else
                value += str.charAt(i) - '1';
        }
        return value;
    }

    /**
     * 获得"yyyy:MM:dd DD:mm:ss"格式的时间串中的年份值
     *
     * @param time
     * @return
     */
    public static int getYear(String time) {
        return Integer.parseInt(time.substring(0, 4));
    }

    /**
     * 获得"yyyy:MM:dd DD:mm:ss"格式的时间串中的月份值
     *
     * @param time
     * @return 1-12的月份值
     */
    public static int getMonth(String time) {
        return Integer.parseInt(time.substring(5, 7));
    }

    /**
     * 获得"yyyy:MM:dd DD:mm:ss"格式的时间串中的日期值
     *
     * @param time
     * @return
     */
    public static int getDay(String time) {
        return Integer.parseInt(time.substring(8, 10));
    }

    /**
     * 获得"yyyy:MM:dd DD:mm:ss"格式的时间串中的小时值
     *
     * @param time
     * @return 24小时值
     */
    public static int getHour(String time) {
        return Integer.parseInt(time.substring(11, 13));
    }

    /**
     * 获得"yyyy:MM:dd DD:mm:ss"格式的时间串中的分钟值
     *
     * @param time
     * @return
     */
    public static int getMinute(String time) {
        return Integer.parseInt(time.substring(14, 16));
    }

    /**
     * 传入时间time，判断其是否属于传入的年月，若早于传入的年月，返回0，若晚于传入的年月，返回32，若属于传入的年月，返回日期
     *
     * @param time
     * @param month
     * @param year
     * @return
     */
    public static int getDayInMonth(String time, int month, int year) {
        if (getYear(time) * 100 + getMonth(time) < year * 100 + month)
            return 0;
        else if (getYear(time) * 100 + getMonth(time) > year * 100 + month)
            return 32;
        else
            return Integer.parseInt(time.substring(8, 10));
    }

    /**
     * 传入农历时间time，判断其是否属于传入的公历年月
     *
     * @param time
     * @return 若早于传入的年月，返回0，若晚于传入的年月，返回32，若属于传入的年月，返回日期
     */
    public static int getLunarBeginDay(String time, LunarItem begin, LunarItem end, int max) {
        long timeInt = getYear(time) * 10000 + getMonth(time) * 100 + getDay(time);
        //System.out.println("The time is: " + timeInt + " " + begin.getYear()*10000+(begin.getMonth()+1)*100+begin.getDay());
        if (timeInt < begin.getYear() * 10000 + (begin.getMonth() + 1) * 100 + begin.getDay())
            return 0;
        else if (timeInt > end.getYear() * 10000 + (end.getMonth() + 1) * 100 + end.getDay())
            return 32;
        else if (time.contains(begin.getYear() + "-" + Pad(begin.getMonth() + 1) + "-")) {
            return getDay(time) - begin.getDay() + 1;
        } else if (time.contains(end.getYear() + "-" + Pad(end.getMonth() + 1) + "-")) {
            return max - (end.getDay() - getDay(time));
        } else
            return -1;
    }

    public static int getLunarBeginDayForYearRepeat(String time, LunarItem begin, LunarItem end) {
        long timeInt = ((begin.getMonth() == 11 && getMonth(time) != 12) ? 10000 : 0) + getMonth(time) * 100 + getDay(time);
        if (timeInt < (begin.getMonth() + 1) * 100 + begin.getDay())
            return 0;
        else if (timeInt > (begin.getMonth() == 11 ? 10000 : 0) + (end.getMonth() + 1) * 100 + end.getDay())
            return 32;
        else if (time.contains("-" + Pad(begin.getMonth() + 1) + "-")) {
            return getDay(time) - begin.getDay() + 1;
        } else
            return -1;
    }

    public static int getLunarEndDay(String time, LunarItem begin, LunarItem end, int max) {
        long timeInt = getYear(time) * 10000 + getMonth(time) * 100 + getDay(time);
        if (timeInt > end.getYear() * 10000 + (end.getMonth() + 1) * 100 + end.getDay())
            return 32;
        else if (timeInt < begin.getYear() * 10000 + (begin.getMonth() + 1) * 100 + begin.getDay())
            return 0;
        else if (time.contains(begin.getYear() + "-" + Pad(begin.getMonth() + 1) + "-")) {
            return getDay(time) - begin.getDay() + 1;
        } else if (time.contains(end.getYear() + "-" + Pad(end.getMonth() + 1) + "-")) {
            return max - (end.getDay() - getDay(time));
        } else
            return -1;
    }

    public static int getLunarEndDayForYearRepeat(String time, LunarItem begin, LunarItem end, int max) {
        long timeInt = ((begin.getMonth() == 11 && getMonth(time) != 12) ? 10000 : 0) + getMonth(time) * 100 + getDay(time);
        if (timeInt > (begin.getMonth() == 11 ? 10000 : 0) + (end.getMonth() + 1) * 100 + end.getDay())
            return 32;
        else if (timeInt < (begin.getMonth() + 1) * 100 + begin.getDay())
            return 0;
        else if (time.contains("-" + Pad(end.getMonth() + 1) + "-")) {
            return max - (end.getDay() - getDay(time));
        } else
            return -1;
    }

    public static SimpleDateFormat getFormatter() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(Calendar.getInstance().getTimeZone());

        return formatter;
    }

    public static String getDate(Long time) {
        if (time != null && time != -1) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(time);
            return formatter.format(c.getTime());
        } else return "";
    }
}

