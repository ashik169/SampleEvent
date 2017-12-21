package com.qinnovation.sample.utils;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtil {


    public static final int SAME_DATE = 0;

    // used for auto gen workId for CCE, WSA and MTA
    public static final String DMYHMS_PATTERN = "ddMMyyHHmmss";

    public static final String dd_MMM_yyyy_PATTERN = "dd-MMM-yyyy";
    public static final String ddMMyyyy_SLACE_PATTERN = "dd/MM/yyyy";
    public static final String dd_MMM_yyyy_HM_PATTERN = "dd/MM/yyyy hh:mm";
    public static final String dd_MMM_yyyy_HMA_PATTERN = "dd/MMM/yyyy hh:mm a";
    public static final String dd_MM_yyyy_HMA_PATTERN = "dd/MM/yyyy hh:mm a";
    public static final String dd_MMM_yyyy_HMS_PATTERN = "dd-MMM-yyyy HH:mm:ss";
    public static final String DATE_TIME_TAGGING_PATTERN = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_TIME_POLICY_TERM_PATTERN = "dd/MM/yyyy";
    public static final String YYYY = "yyyy";
    public static final String YY = "yy";

    private static DateTimeUtil ourInstance = new DateTimeUtil();

    public static DateTimeUtil getInstance() {
        return ourInstance;
    }

    private DateTimeUtil() {
    }

    public int checkDate(String sStartDate, String sEndDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date startDate = null, endDate = null;
        try {
            startDate = simpleDateFormat.parse(sStartDate);
            endDate = simpleDateFormat.parse(sEndDate);
            if (startDate.compareTo(endDate) == SAME_DATE)
                return SAME_DATE;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getTodayDate(@NonNull String pattern) {
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
            return dateFormat.format(calendar.getTime());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public String getDate(String pattern, long milliSecond) {
        if (TextUtils.isEmpty(pattern))
            return null;
        Date date = new Date(milliSecond);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return dateFormat.format(date.getTime());
    }

    public static boolean isDateSame(long millisecond) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dd_MMM_yyyy_PATTERN, Locale.getDefault());
        Date createdDate = new Date(millisecond);
        Date todayDate = new Date();
        if (createdDate.compareTo(todayDate) == SAME_DATE)
            return true;
        return false;
    }

    public long getMaxDate() {
        long maxDate = 0;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -18);
            maxDate = calendar.getTime().getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return maxDate;
    }

    public boolean isSameTime(String startDateTime, String endDateTime) {
//        if (startDateTime == null || endDateTime == null) {
//            return false;
//        }
        return false;
    }
}
