package com.fanyafeng.recreation.util;

import java.util.Calendar;

/**
 * Created by zgw on 16/5/14 15:28.
 */
public class LunarParser {
    // Array iSolarLunarTable stored the offset days
    // in New Year of solar calendar and lunar calendar from 1901 to 2050;
    public static final char[] mySolarLunarOffsetTable = {
            49, 38, 28, 46, 34, 24, 43, 32, 21, 40, // 1910
            29, 48, 36, 25, 44, 33, 22, 41, 31, 50, // 1920
            38, 27, 46, 35, 23, 43, 32, 22, 40, 29, // 1930
            47, 36, 25, 44, 34, 23, 41, 30, 49, 38, // 1940
            26, 45, 35, 24, 43, 32, 21, 40, 28, 47, // 1950
            36, 26, 44, 33, 23, 42, 30, 48, 38, 27, // 1960
            45, 35, 24, 43, 32, 20, 39, 29, 47, 36, // 1970
            26, 45, 33, 22, 41, 30, 48, 37, 27, 46, // 1980
            35, 24, 43, 32, 50, 39, 28, 47, 36, 26, // 1990
            45, 34, 22, 40, 30, 49, 37, 27, 46, 35, // 2000
            23, 42, 31, 21, 39, 28, 48, 37, 25, 44, // 2010
            33, 22, 40, 30, 49, 38, 27, 46, 35, 24, // 2020
            42, 31, 21, 40, 28, 47, 36, 25, 43, 33, // 2030
            22, 41, 30, 49, 38, 27, 45, 34, 23, 42, // 2040
            31, 21, 40, 29, 47, 36, 25, 44, 32, 22, // 2050

    };

    public static boolean isSolarLeapYear(int myYear) {
        return (((myYear % 4 == 0) && (myYear % 100 != 0)) || (myYear % 400 == 0));
    }

    static int myGetLMonthDays(int myYear, int myMonth) {
        int myLeapMonth = LunarItem.leapMonth(myYear);
        if ((myMonth > 12) && (myMonth - 12 != myLeapMonth) || (myMonth < 0)) {
            return -1;
        }
        if (myMonth - 12 == myLeapMonth) {
            return LunarItem.leapDays(myYear);
        }
        if ((LunarItem.lunarInfo[myYear - 1900] & (0x10000 >> myMonth)) == 0)
            return 29;
        else
            return 30;
    }

    static int myGetLYearDays(int myYear) {
        int myYearDays = 0;
        int myLeapMonth = LunarItem.leapMonth(myYear);

        for (int i = 1; i < 13; i++)
            myYearDays += myGetLMonthDays(myYear, i);
        if (myLeapMonth > 0)
            myYearDays += myGetLMonthDays(myYear, myLeapMonth + 12);
        return myYearDays;
    }

    public static int myGetLNewYearOffsetDays(int myYear, int myMonth, int myDay) {
        int myOffsetDays = 0;
        int myLeapMonth = LunarItem.leapMonth(myYear);

        if ((myLeapMonth > 0) && (myLeapMonth == myMonth - 12)) {
            myMonth = myLeapMonth;
            myOffsetDays += myGetLMonthDays(myYear, myMonth);
        }

        for (int i = 1; i < myMonth; i++) {
            myOffsetDays += myGetLMonthDays(myYear, i);
            if (i == myLeapMonth)
                myOffsetDays += myGetLMonthDays(myYear, myLeapMonth + 12);
        }
        myOffsetDays += myDay - 1;

        return myOffsetDays;
    }

    public static String sCalendarLunarToSolar(int myYear, int myMonth, int myDay) {
        int[] result = parseToSolar(myYear, myMonth, myDay);

        return "" + result[0] + "-" + (result[1] > 9 ? result[1] + "" : "0" + result[1]) + "-" + (result[2] > 9 ? result[2] + "" : "0" + result[2]);
    }

    /**
     *
     * @param myYear
     * @param myMonth
     *            between 1-12
     * @param myDay
     * @return int[year, month, day] the month between 1-12
     */
    public static int[] parseToSolar(int myYear, int myMonth, int myDay) {
        int mySYear, mySMonth, mySDay;
        int myOffsetDays = myGetLNewYearOffsetDays(myYear, myMonth, myDay) + mySolarLunarOffsetTable[myYear - 1901];
        int myYearDays = isSolarLeapYear(myYear) ? 366 : 365;
//		int myYearDays = LunarItem.yearDays(myYear);

        // //System.out.println("!!!!!!!!!!!!!!!"+myYear);
        // //System.out.println("!!!!!!!!!!!!!!!"+myMonth);
        // //System.out.println("!!!!!!!!!!!!!!!"+myDay);
//		 System.out.println("The solar !!!!!!!!!!!!!!!"+myOffsetDays);
//		 System.out.println("The solar !!!!!!!!!!!!!!!"+myYearDays);

        if (myOffsetDays >= myYearDays) {
            mySYear = myYear + 1;
            myOffsetDays -= myYearDays;
        } else {
            mySYear = myYear;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mySYear);
        calendar.set(Calendar.DATE, 1);

        mySDay = myOffsetDays + 1;
        for (mySMonth = 1; myOffsetDays >= 0; mySMonth++) {
            mySDay = myOffsetDays + 1;
            calendar.set(Calendar.MONTH, mySMonth-1);
            myOffsetDays -= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        mySMonth--;

        int[] list = { mySYear, mySMonth, mySDay };

        return list;
    }

    public static int[] parseToSolar(LunarItem lunarItem) {
        int myYear = lunarItem.getYear();
        int myMonth = lunarItem.getMonth() + 1;
        int myDay = lunarItem.getDay();
        if (lunarItem.isLeep()) {
            myMonth += 12;
        }

        return parseToSolar(myYear, myMonth, myDay);
    }
}

