package com.fanyafeng.recreation.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by zgw on 16/5/14 15:25.
 */
public class LunarItem {
    private int year;
    private int month;
    private int day;
    private boolean leap;
    final static String chineseNumber[] = { "一", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "十一", "十二", "", "" };
    final static String chineseMonth[] = { "正", "二", "三", "四", "五", "六", "七",
            "八", "九", "十", "冬", "腊", "", "" };
    SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    //16位为1月，15位为2月类推
//	public final static long[] lunarInfo = new long[] {
//			0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,   //1900
//			0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,   //1910
//			0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,   //1920
//			0x06566, 0x0d4a0, 0x0ea50, 0x16A95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,	0x1c8d7, 0x0c950,   //1930
//			0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,   //1940
//			0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0A5B0, 0x14573, 0x052b0, 0x0a9a8, 0x0e950, 0x06aa0,   //1950
//			0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,   //1960
//			0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b6a0, 0x195a6,   //1970
//			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,   //1980
//			0x04af5, 0x04970, 0x064b0, 0x074a3,	0x0ea50, 0x06b58, 0x05ac0, 0x0ab60, 0x096d5, 0x092e0,   //1990
//			0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,   //2000
//			0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,   //2010
//			0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,   //2020
//			0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,   //2030
//			0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 }; //2040

    //16位为1月，15位为2月类推
    public final static long[] lunarInfo = new long[] {
            0x00004bd8,0x00004ae0,0x0000a570,0x000054d5,0x0000d260,0x0000d950,0x00016554,0x000056a0,0x00009ad0,0x000055d2, //1900
            0x00004ae0,0x0000a5b6,0x0000a4d0,0x0000d250,0x0001d255,0x0000b540,0x0000d6a0,0x0000ada2,0x000095b0,0x00014977, //1910
            0x00004970,0x0000a4b0,0x0000b4b5,0x00006a50,0x00006d40,0x0001ab54,0x00002b60,0x00009570,0x000052f2,0x00004970, //1920
            0x00006566,0x0000d4a0,0x0000ea50,0x00016a95,0x00005ad0,0x00002b60,0x000186e3,0x000092e0,0x0001c8d7,0x0000c950, //1930
            0x0000d4a0,0x0001d8a6,0x0000b550,0x000056a0,0x0001a5b4,0x000025d0,0x000092d0,0x0000d2b2,0x0000a950,0x0000b557, //1940
            0x00006ca0,0x0000b550,0x00015355,0x00004da0,0x0000a5b0,0x00014573,0x000052b0,0x0000a9a8,0x0000e950,0x00006aa0, //1950
            0x0000aea6,0x0000ab50,0x00004b60,0x0000aae4,0x0000a570,0x00005260,0x0000f263,0x0000d950,0x00005b57,0x000056a0, //1960
            0x000096d0,0x00004dd5,0x00004ad0,0x0000a4d0,0x0000d4d4,0x0000d250,0x0000d558,0x0000b540,0x0000b6a0,0x000195a6, //1970
            0x000095b0,0x000049b0,0x0000a974,0x0000a4b0,0x0000b27a,0x00006a50,0x00006d40,0x0000af46,0x0000ab60,0x00009570, //1980
            0x00004af5,0x00004970,0x000064b0,0x000074a3,0x0000ea50,0x00006b58,0x00005ac0,0x0000ab60,0x000096d5,0x000092e0, //1990
            0x0000c960,0x0000d954,0x0000d4a0,0x0000da50,0x00007552,0x000056a0,0x0000abb7,0x000025d0,0x000092d0,0x0000cab5, //2000
            0x0000a950,0x0000b4a0,0x0000baa4,0x0000ad50,0x000055d9,0x00004ba0,0x0000a5b0,0x00015176,0x000052b0,0x0000a930, //2010
            0x00007954,0x00006aa0,0x0000ad50,0x00005b52,0x00004b60,0x0000a6e6,0x0000a4e0,0x0000d260,0x0000ea65,0x0000d530, //2020
            0x00005aa0,0x000076a3,0x000096d0,0x00004afb,0x00004ad0,0x0000a4d0,0x0001d0b6,0x0000d250,0x0000d520,0x0000dd45, //2030
            0x0000b5a0,0x000056d0,0x000055b2,0x000049b0,0x0000a577,0x0000a4b0,0x0000aa50,0x0001b255,0x00006d20,0x0000ada0, //2040
            0x00014b63,0x00009370,0x000049f8,0x00004970,0x000064b0,0x000168a6,0x0000ea50,0x00006aa0,0x0001a6c4,0x0000aae0, //2050
            0x000092e0,0x0000d2e3,0x0000c960,0x0000d557,0x0000d4a0,0x0000d850,0x00005d55,0x000056a0,0x0000a6d0,0x000055d4, //2060
            0x000052d0,0x0000a9b8,0x0000a950,0x0000b4a0,0x0000b6a6,0x0000ad50,0x000055a0,0x0000aba4,0x0000a5b0,0x000052b0, //2070
            0x0000b273,0x00006930,0x00007337,0x00006aa0,0x0000ad50,0x00014b55,0x00004b60,0x0000a570,0x000054e4,0x0000d160, //2080
            0x0000e968,0x0000d520,0x0000daa0,0x00016aa6,0x000056d0,0x00004ae0,0x0000a9d4,0x0000a2d0,0x0000d150,0x0000f252, //2090
            0x0000d520
    };

    //======   传回农历   y年的总天数
    final public static int yearDays(int y) {
        if (y>2049 || y<1900)
            return 0;
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    //======   传回农历   y年闰月的天数
    final public static int leapDays(int y) {
        if (y>2049 || y<1900)
            return 0;
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    //======   传回农历   y年闰哪个月   1-12   ,   没闰传回   0
    final public static int leapMonth(int y) {
        if (y>2049 || y<1900)
            return 0;
        return (int) (lunarInfo[y - 1900] & 0xf);
    }

    /**
     * 传入农历年月(1-12)，返回该月的总天数
     * Author: linsiran
     * Date: 2012-8-19下午5:19:30
     */
    final public static int getMonthDays(int y, int m) {
        if (y>2049 || y<1900)
            return 30;
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    /**
     *
     *   传出y年m月d日对应的农历.
     *   yearCyl3:农历年与1864的相差数                             ?
     *   monCyl4:从1900年1月31日以来,闰月数
     *   dayCyl5:与1900年1月31日相差的天数,再加40             ?
     *   @param   cal
     *   @return
     */
    public LunarItem(Calendar cal) {
        setCalendar(cal);
    }

    public void setCalendar(Calendar c) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1900, 0, 31, 0, 0);

        Calendar cal = (Calendar) c.clone();
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR_OF_DAY, 12);

        //求出和1900年1月31日相差的天数
        int offset = (int) ((cal.getTimeInMillis() - calendar.getTimeInMillis()) / 86400000L);
        calculate(offset);
    }

    private void calculate(int offset) {

//		int key = offset;
//
//		if (hash[key] != null && hash[key].length() > 0) {
//			parse(hash[key]);
//			return;
//		}
//
//
//		if (map.containsKey(offset)) {
//			parse(map.get(offset));
//			return;
//		}
//

        int leapMonth;
        //用offset减去每农历年的天数
        //   计算当天是农历第几天
        //i最终结果是农历的年份
        //offset是当年的第几天
        int iYear, daysOfYear = 0;
        for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);
            offset -= daysOfYear;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
        }
        //农历年份
        year = iYear;

        leapMonth = leapMonth(iYear); //闰哪个月,1-12
        leap = false;

        //用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            //闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
                --iMonth;
                leap = true;
                daysOfMonth = leapDays(year);
            } else
                daysOfMonth = getMonthDays(year, iMonth);

            offset -= daysOfMonth;
            //解除闰月
            if (leap && iMonth == (leapMonth + 1))
                leap = false;
        }
        //offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (leap) {
                leap = false;
            } else {
                leap = true;
                --iMonth;
            }
        }
        //offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
        }
        month = iMonth;
        day = offset + 1;

//		sb.append(year + " ");
//		sb.append(month + " ");
//		sb.append(day + " ");
//		sb.append(dyear + " ");
//		sb.append(myear + " ");
//		sb.append(mmonth + " ");
//		sb.append(mday + " ");
//		sb.append(dmonth + " ");
//		sb.append(dday + " ");
//		sb.append(leap ? "1" : "0");
//
//		hash[key] = sb.toString();
//		map.put(key, sb.toString());
    }

    public String getChinaCurrentMonthAndDayString() {
        return getChinaMonth(month)+getChinaDayString(day);
    }
    /**
     * 返回农历的月份跟日（修改农历的11，12,1 月份分别为冬 腊 正）
     */
    public String getCurrentLunarMonthAndDay() {
        return getChinaMonth(month)+getChinaDayString(day);
    }

    public String getChinaCurrentYearAndMonthAndDayString() {
        return "农历" + year + "年" + getChinaMonth(month)+ getChinaDayString(day);
    }

    public String getChinaYearAndMonthAndDayString() {
        return "农历 " + getChinaYearString(year) + " " + getChinaMonth(month)+ getChinaDayString(day);
    }

    public static String getChinaDayString(int day) {
        String chineseTen[] = { "初", "十", "廿", "卅" };
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 20)
            return "二十";
        if (day == 10)
            return "初十";
        if (day == 30)
            return "三十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }

    public static String getChinaYearString(int year) {
        String[] numbers = new String[]{"零","一","二","三","四","五","六","七","八","九"};
        return numbers[year/1000] + numbers[year/100%10]
                + numbers[year/10%10] + numbers[year%10] + "年";
    }

    /**
     * 返回农历的11，12,1 月份分别为冬 腊 正
     */
    public static String getChinaMonthString(int month, boolean isLeap) {
        if (month <= 12) {
            if (isLeap)
                return "闰" + chineseMonth[month - 1] + "月";
            else
                return chineseMonth[month - 1] + "月";
        }
        else {
            return "";
        }
    }

    public String getChinaMonth(int month) {
        if (month <= 12) {
            if (leap)
                return "闰" + chineseNumber[month - 1] + "月";
            else
                return chineseNumber[month - 1] + "月";
        }
        else {
            return "";
        }
    }

    public static String getChineseNumber(int num) {
        return chineseNumber[num-1];
    }

    @Override
    public String toString() {
        if (day == 1) {
            switch (month) {
                case 1:
                    return "正月";
                case 11:
                    return "冬月";
                case 12:
                    return "腊月";
                default:
                    return chineseNumber[month - 1] + "月";
            }
        }
        else
            return getChinaDayString(day);
    }

    public String getFormatDate() {
        return year + "-" + pad(month) + "-" + pad(day) + " 00:00:00";
    }

    public int getYear() {
        return year;
    }

    /**
     *
     * @return month between 0-11
     */
    public int getMonth() {
        return month-1;
    }

    public int getDay() {
        return day;
    }

    public boolean isLeep() {
        return leap;
    }
    /*public static void main(String[] args) throws ParseException {
        Calendar today = Calendar.getInstance();
        today.setTime(chineseDateFormat.parse("2004年1月22日"));
        LunarItem lunar = new LunarItem(today);

        //System.out.println("北京时间：" + chineseDateFormat.format(today.getTime())
                + "　农历" + lunar);
    }
    */
    private String pad(int num) {
        if (num < 10){
            return "0" + num;
        }
        else{
            return "" + num;
        }
    }
}

