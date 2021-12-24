package com.lanyun.iot.gateway.utils;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * @ Author     ：fengzhaofeng.
 * @ Date       ：Created in 23:18 2018/6/26
 * @ Description：${description}
 * @ Modified By：
 */
@Slf4j
public class DateUtil {

    public static final String FMT_DATE_SEQUENCE = "yyyyMMddHH";
    public static final String FMT_DATE_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String FMT_DATE_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String FMT_DATE_YYYYMMDD = "yyyyMMdd";
    public static final String FMT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FMT_DATE_YYYYMM = "yyyyMM";

    //匹配20180719这样的精确到日日期格式
    private static Pattern pattern_ymd = Pattern.compile("^20\\d{2}((0[1-9]{1})|(1[0-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$");
    //匹配201807 这样精确到月的格式
    private static Pattern pattern_ym = Pattern.compile("^20\\d{2}((0[1-9]{1})|(1[0-2]{1}))$");

    /**
     * 是否符合形如20180720这样的年月日格式
     *
     * @param date
     * @return
     */
    public static boolean checkStatisticDateYMD(String date) {
        return pattern_ymd.matcher(date).matches();
    }

    /**
     * 是否符合形如201807这样的年月格式
     *
     * @param date
     * @return
     */
    public static boolean checkStatisticDateYM(String date) {
        return pattern_ym.matcher(date).matches();
    }

    public static final String format() {
        return format(new Date());
    }

    public static final String format(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }


    /**
     * 日期转字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date, String format) {
        if (date == null)
            return null;
        DateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }

    /**
     * 日期转字符串
     *
     * @param date
     * @return
     */
    public static String formatDefault(Date date) {
        return format(date, FMT_DATE_YYYY_MM_DD_HHMMSS);
    }

    /**
     * 字符串转日期
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parse(String date, String format) {
        DateFormat df = new SimpleDateFormat(format);
        try {
            return df.parse(date);
        } catch (ParseException e) {
            log.error("解析日期出错：" + date, e);
        }
        return null;
    }

    /**
     * 加若干分钟
     *
     * @param source
     * @param minute
     * @return
     */
    public static Date addMinute(Date source, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(source.getTime());
        calendar.add(Calendar.MINUTE, minute);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 是否是同一年
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isTheSameYear(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
    }


    /**
     * 获取一天的起始时间，如 2018-10-31 11:23:45 处理后为 2018-10-31 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getTheBeginOfDay(Date date) {
        if (date == null)
            return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 是否同一天
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isTheSameDay(Date d1, Date d2) {
        if (d1 == null)
            throw new IllegalArgumentException("date1 is null");
        if (d2 == null)
            throw new IllegalArgumentException("date2 is null");

        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(d1.getTime());
        //
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(d2.getTime());
        //
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DATE) == c2.get(Calendar.DATE);
    }

    /**
     * 一个时间在另一个时间之前或者是同一天
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isTheSameDayOrBefore(Date d1, Date d2) {
        boolean isTheSameDay = isTheSameDay(d1, d2);
        if (isTheSameDay)
            return true;
        return d1.before(d2);
    }

    /**
     * 是否是月份第一天
     *
     * @param calendar
     * @return
     */
    public static boolean isTheFirstDayOfMonth(Calendar calendar) {
        int actually = calendar.get(Calendar.DATE);
        int min = calendar.getActualMinimum(Calendar.DATE);
        return actually == min;
    }

    /**
     * 是否是某个月的最后一天
     *
     * @param calendar
     * @return
     */
    public static boolean isTheLastDayOfMonth(Calendar calendar) {
        int actually = calendar.get(Calendar.DATE);
        int max = calendar.getActualMaximum(Calendar.DATE);
        return actually == max;
    }

    /**
     * 只比较年月，前一个日期是否小于后一个日期
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean beforeOrSameOnYearAndMonth(Calendar date1, Calendar date2) {
        //同一年则比较月
        if (isTheSameYear(date1, date2))
            return date1.get(Calendar.MONTH) <= date2.get(Calendar.MONTH);
        //不是同一年则直接比较年份
        return date1.get(Calendar.YEAR) < date2.get(Calendar.YEAR);
    }

    /**
     * 获得该月第一天
     * @param year
     * @param month
     * @return
     */
    public static Date getFirstDayOfMonth(int year,int month){
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);

        return cal.getTime();
    }

    /**
     * 获得该月最后一天
     * @param year
     * @param month
     * @return
     */
    public static Date getLastDayOfMonth(int year,int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);

        return cal.getTime();

    }

    /**
     * 获取某年的第一天
     * @param year
     * @return
     */
    public static Date getFirstDayOfYear(int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 获取某年的最后一天
     * @param year
     * @return
     */
    public static Date getLastDayOfYear(int year)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 11); //12月
        calendar.set(Calendar.DATE, 31); //31日
        calendar.set(Calendar.AM_PM, Calendar.PM); //下午
        calendar.set(Calendar.HOUR, 11); //11点
        calendar.set(Calendar.MINUTE, 59); //59分
        calendar.set(Calendar.SECOND, 59); //59秒
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 日期中加上或减去多少天
     * @param source
     * @param day
     * @return
     */
    public static Date addDayOf(Date source, int day)
    {
        if (source == null)
            return null;
        if (day == 0)
            return source;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(source.getTime());
        //
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 给出的日期加任意时间单位
     *
     * @param source
     * @param scale  Calendar.Second/Calendar.Hour
     * @param step
     * @return
     */
    public static Date timeAdd(Date source, int scale, int step) {
        if (source == null)
            return null;
        if (step == 0)
            return source;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(source.getTime());
        calendar.add(scale, step);
        return calendar.getTime();
    }

    public static Date minusDayOf(Date source, int day) {
        return addDayOf(source, -day);
    }

    public static long daysDiff(Date d1, Date d2)
    {
        LocalDateTime ld1 = convertDateToLDT(d1);
        LocalDateTime ld2 = convertDateToLDT(d2);
        long diff = ld1.until(ld2, ChronoUnit.DAYS);
        return Math.abs(diff);
    }

    //Date转换为LocalDateTime
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    //LocalDateTime转换为Date
    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将起止时间划分，其中的整月从月报中读取数据，非整月从日报中读取数据
     *
     * @param dateBegin
     * @param dateEnd
     */
    public static DateDuration parseDateDuration(Date dateBegin, Date dateEnd) {
        //校验
        notNull(dateBegin, "起始时间不能为空");
        notNull(dateEnd, "结束时间不能为空");
        String beginStr = format(dateBegin, FMT_DATE_YYYYMMDD);
        String endStr = format(dateEnd, FMT_DATE_YYYYMMDD);
        isTrue(endStr.compareTo(beginStr) >= 0, "起始日期必须在结束日期之前或者是同一天[" + beginStr + "--" + endStr + "]");
        //转换类型为Calendar
        Calendar begin = Calendar.getInstance();
        begin.setTime(dateBegin);
        Calendar end = Calendar.getInstance();
        end.setTime(dateEnd);
        //起始年月
        Calendar beginOfMonth = Calendar.getInstance();
        beginOfMonth.set(Calendar.YEAR, begin.get(Calendar.YEAR));
        beginOfMonth.set(Calendar.MONTH, begin.get(Calendar.MONTH));
        //结束年月
        Calendar endOfMonth = Calendar.getInstance();
        endOfMonth.set(Calendar.YEAR, end.get(Calendar.YEAR));
        endOfMonth.set(Calendar.MONTH, end.get(Calendar.MONTH));
        //如果起始日期不是某月的第一天，则起始月份+1
        boolean hasFirstDuration = false;
        if (!isTheFirstDayOfMonth(begin)) {
            beginOfMonth.add(Calendar.MONTH, 1);
            hasFirstDuration = true;
        }
        //如果结束日期不是某月份的最后一天，则结束月份-1
        boolean hasSecondDuration = false;
        if (!isTheLastDayOfMonth(end)) {
            endOfMonth.add(Calendar.MONTH, -1);
            hasSecondDuration = true;
        }
        //准备分隔
        DateDuration dateDuration = new DateDuration();
        DateFormat dfYMD = new SimpleDateFormat(FMT_DATE_YYYYMMDD);
        DateFormat dfYM = new SimpleDateFormat(FMT_DATE_YYYYMM);
        //只比较年月，前一个日期小于后一个日期，这种情况说明中间存在完整月份
        if (DateUtil.beforeOrSameOnYearAndMonth(beginOfMonth, endOfMonth)) {
            //完整月份
            dateDuration.setBeginMonth(dfYM.format(beginOfMonth.getTime()));
            dateDuration.setEndMonth(dfYM.format(endOfMonth.getTime()));
            //开头日期
            if (hasFirstDuration) {
                //第一段的结束时间为当月最后一天
                Calendar firstEnd = Calendar.getInstance();
                firstEnd.setTimeInMillis(begin.getTimeInMillis());
                firstEnd.set(Calendar.DATE, firstEnd.getActualMaximum(Calendar.DATE));
                //
                dateDuration.setFirstBeginDay(dfYMD.format(begin.getTime()));
                dateDuration.setFirstEndDay(dfYMD.format(firstEnd.getTime()));
            }
            //结尾日期
            if (hasSecondDuration) {
                //第二段的起始时间为当月第一天
                Calendar secondBegin = Calendar.getInstance();
                secondBegin.setTimeInMillis(end.getTimeInMillis());
                secondBegin.set(Calendar.DATE, secondBegin.getActualMinimum(Calendar.DATE));
                //
                dateDuration.setSecondBeginDay(dfYMD.format(secondBegin.getTime()));
                dateDuration.setSecondEndDay(dfYMD.format(end.getTime()));
            }
        }
        // 不存在完整月份的情况
        else {
            dateDuration.setFirstBeginDay(dfYMD.format(begin.getTime()));
            dateDuration.setFirstEndDay(dfYMD.format(end.getTime()));
        }

        return dateDuration;
    }

    /**
     * 将时间分解，其中的整月可以直接从月报读取数据，开头和结尾的不满月的天数分别计算
     */
    @Data
    @ToString
    public static class DateDuration {
        //第一时间端（日报）
        private String firstBeginDay;
        private String firstEndDay;
        //第二时间段（月报）
        private String beginMonth;
        private String endMonth;
        //第三时间段（日报）
        private String secondBeginDay;
        private String secondEndDay;

        public boolean hasFirstDayDuration() {
            return StringUtils.isNotBlank(firstBeginDay) && StringUtils.isNotBlank(firstEndDay);
        }

        public boolean hasMonthDuration() {
            return StringUtils.isNotBlank(beginMonth) && StringUtils.isNotBlank(endMonth);
        }

        public boolean hasSecondDayDuration() {
            return StringUtils.isNotBlank(secondBeginDay) && StringUtils.isNotBlank(secondEndDay);
        }
    }


}
