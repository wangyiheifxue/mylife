package com.mylife.util;

import com.mylife.constant.DateTimeConst;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @descirption : 日期、时间处理工具
 * @author : wyh
 * @date : 2020/9/4 09:27
 */
public class DateTimeUtil {

    /**
     * 私有化构造器，使得不能产生该类对象，类中所有的方法均为静态方法
     */
    private DateTimeUtil(){}

    /**
     * 根据 yyyy-MM-dd HH:mm:ss 正则格式化LocalDateTime
     * @param ldt
     * @return
     */
    public static String toString(LocalDateTime ldt){
        return toString(ldt, DateTimeConst.PATTERN_YMDHMS);
    }

    /**
     * 根据 yyyy-MM-dd 正则格式化LocalDateTime
     * @param ldt
     * @return
     */
    public static String toShortString(LocalDateTime ldt){
        return toString(ldt, DateTimeConst.PATTERN_YMD);
    }

    /**
     * 根据参数正则格式化LocalDateTime
     * @param ldt
     * @param pattern
     * @return
     */
    public static String toString(LocalDateTime ldt,String pattern){
        if(ldt != null && StringUtils.isNotBlank(pattern)){
            return ldt.format(DateTimeFormatter.ofPattern(pattern));
        }
        return "";
    }

    /**
     *  将日期时间str转化为LocalDateTime
     * @param dateTimeStr
     * @return
     */
    public static LocalDateTime toLocalDateTime(String dateTimeStr){
        if(StringUtils.isNotBlank(dateTimeStr)){
            return LocalDateTime.parse(dateTimeStr,DateTimeFormatter.ofPattern(DateTimeConst.PATTERN_YMDHMS));
        }
        return null;
    }

    /**
     * 获取日期时间的格式
     * @param dateTimeStr
     * @return
     */
    public static String getDateTimeFormat(String dateTimeStr) {
        if(StringUtils.isNotBlank(dateTimeStr)){
            if (dateTimeStr.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                return DateTimeConst.PATTERN_YMDHMS;
            } else if (dateTimeStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return DateTimeConst.PATTERN_YMD;
            }
        }
        return "";
    }

    /**
     * 获取参数日期时间是一周的第几天（1至7）
     * @param ldt
     * @return
     */
    public static Integer getDayOfWeek(LocalDateTime ldt) {
        if(ldt != null){
            return ldt.getDayOfWeek().getValue();
        }
        return null;
    }

    /**
     * 获取参数日期时间的星期中文名称
     * @param ldt
     * @return
     */
    public static String getWeekName(LocalDateTime ldt) {
        if(ldt != null){
            String[] weekDays = {"一", "二", "三", "四", "五", "六","日"};
            return  weekDays[getDayOfWeek(ldt) - 1];
        }
        return "";
    }

    /**
     * 获取两个参数日期时间的相差天数
     * @param startLdt
     * @param endLdt
     * @return
     */
    public static Long getDiffDays(LocalDateTime startLdt,LocalDateTime endLdt){
        if(startLdt != null && endLdt != null){
            Duration duration = Duration.between(startLdt,endLdt);
            return duration.toDays();
        }
        return null;
    }
}
