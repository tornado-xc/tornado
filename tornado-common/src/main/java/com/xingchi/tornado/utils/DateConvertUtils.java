package com.xingchi.tornado.utils;

import org.springframework.util.Assert;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 日期转换工具类，进行Date、LocalDate、LocalDateTime、LocalTime等日期之间的转换
 *
 * @author xiaoya
 * @date 2022/12/20 13:29
 * @modified xiaoya
 */
public class DateConvertUtils {

    /**
     * date转LocalDateTime
     *
     * @param date      java.util.Date日期类
     * @return          java.time.LocalDateTime java8提供的日期类
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * date转LocalDate
     *
     * @param date      java.util.Date日期类
     * @return          java.time.LocalDate java8提供的日期类
     */
    public static LocalDate dateToLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * localDate转Date
     *
     * @param localDate java8 新日期时间类
     * @return          java.util.Date类
     */
    public static Date localDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return DateConvertUtils.localDateToDate(localDate, LocalTime.MIN);
    }

    /**
     * localDate转Date
     *
     * @param localDate     localDate日期
     * @param localTime     日期使用的时间
     * @return              date
     */
    public static Date localDateToDate(LocalDate localDate, LocalTime localTime) {
        if (localDate == null) {
            return null;
        }
        LocalDateTime localDateTime = localDate.atTime(localTime);
        return DateConvertUtils.localDateTimeToDate(localDateTime);
    }

    /**
     * localDateTime转Date
     *
     * @param localDateTime     localDateTime
     * @return                  date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 根据localTime重置date的时间
     *
     * <p>
     *     当localTime等于 {@link LocalTime#MIN}
     *     2022-12-20 14:30:00  ->  2022-12-20 00:00:00
     * </p>
     * <p>
     *     当localTime等于 {@link LocalTime#MAX}
     *     2022-12-20 14:30:00  ->  2022-12-20 23:23:59
     * </p>
     * <p>
     *     或者自定义localTime如通过 {@link LocalTime#of(int hour, int minute, int second)} 进行指定
     *     2022-12-20 14:30:00  ->  2022-12-20 23:23:59
     * </p>
     *
     * @param date              待重置的日期
     * @param localTime         localTime
     * @return                  重置后的日期
     */
    public static Date resetDate(Date date, LocalTime localTime) {
        if (date == null) {
            return null;
        }

        if (localTime == null) {
            return date;
        }
        LocalDate localDate = DateConvertUtils.dateToLocalDate(date);
        return localDateToDate(localDate, localTime);
    }

    /**
     * 将date的时间重置为00:00
     *
     * <p>
     *     2022-12-20 14:30:00  ->  2022-12-20 00:00:00
     *     2022-12-20 23:30:00  ->  2022-12-20 00:00:00
     * </p>
     *
     * @param date              待重置的日期
     * @return                  重置后的日期
     */
    public static Date resetDateMin(Date date) {
        return resetDate(date, LocalTime.MIN);
    }

    /**
     * 获取一天中最小的时间
     *
     * @param localDateTime     localDateTime
     * @return                  一天中最大的时间如：2022-12-30 00:00:00
     */
    public static LocalDateTime getLocalDateTimeDayMin(LocalDateTime localDateTime) {
        return DateConvertUtils.resetLocalDateTime(localDateTime, LocalTime.MIN);
    }

    /**
     * 获取一天中最大的时间
     *
     * @param localDateTime     localDateTime
     * @return                  一天中最大的时间如：2022-12-30 23:59:59
     */
    public static LocalDateTime getLocalDateTimeDayMax(LocalDateTime localDateTime) {
        return DateConvertUtils.resetLocalDateTime(localDateTime, LocalTime.MAX);
    }

    /**
     * 重置localDateTime的时间
     *
     * @param localDateTime java8日期
     * @param localTime     指定时间
     * @return              指定时间的日期
     */
    public static LocalDateTime resetLocalDateTime(LocalDateTime localDateTime, LocalTime localTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalDate().atTime(localTime);
    }

    /**
     * 获取两个日期之间的差值
     *
     * @param start     开始日期
     * @param end       结束日期
     * @param timeUnit  时间单位
     * @return          相差时间值
     */
    public static long getBetweenByUnit(Date start, Date end, TimeUnit timeUnit) {
        LocalDateTime startTime = timestampToObj(start.getTime(), LocalDateTime.class);
        LocalDateTime endTime = timestampToObj(end.getTime(), LocalDateTime.class);
        return getBetweenByUnit(startTime, endTime, timeUnit);
    }

    /**
     * 根据指定时间单位获取两个日期之间相差的时间
     *
     * @param start         开始时间
     * @param end           结束时间
     * @param timeUnit      时间单位
     * @return              结果
     */
    public static <T extends Temporal> long getBetweenByUnit(T start, T end, TimeUnit timeUnit) {

        if (start == null || end == null) {
            return 0;
        }

        long result = 0;
        if (timeUnit == null) {
            timeUnit = TimeUnit.DAYS;
        }
        Duration duration = null;
        if (start instanceof LocalDate) {
            duration = Duration.between(((LocalDate) start).atTime(LocalTime.MIN), ((LocalDate) end).atTime(LocalTime.MAX));
        } else {
            duration = Duration.between(start, end);
        }

        switch (timeUnit) {
            case DAYS:
                result = duration.toDays();
                break;
            case HOURS:
                result = duration.toHours();
                break;
            case MINUTES:
                result = duration.toMinutes();
                break;
            case SECONDS:
                result = duration.toSeconds();
                break;
            case MILLISECONDS:
                result = duration.toMillis();
                break;
            case MICROSECONDS:
                result = duration.toMillis() * 1000;
                break;
            case NANOSECONDS:
                result = duration.toNanos();
                break;
            default:
                throw new IllegalArgumentException("不支持的时间转换单位");
        }
        return result;
    }

    /**
     * 获取指定日期的时间戳
     *
     * @param date          指定日期
     * @return              指定日期的时间戳
     */
    public static long getTimestamp(Date date) {
        Assert.notNull(date, "Date is not null");
        return date.getTime();
    }

    /**
     * 获取指定日期的时间戳截止00:00:00
     *
     * @param localDate     指定日期
     * @return              指定日期的时间戳
     */
    public static long getTimestamp(LocalDate localDate) {
        Assert.notNull(localDate, "localDate is not null");
        return localDate.atTime(LocalTime.MIN).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的时间戳
     *
     * @param localDate     指定日期
     * @param localTime     指定时间
     * @return              指定日期的时间戳
     */
    public static long getTimestamp(LocalDate localDate, LocalTime localTime) {
        Assert.notNull(localDate, "localDate is not null");
        return localDate.atTime(localTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取指定日期的时间戳
     *
     * @param localDateTime 指定日期
     * @return              指定日期的时间戳
     */
    public static long getTimestamp(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime, "localDateTime is not null");
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 根据根据时间戳将其转为指定的对象
     *
     * @param timestamp     时间戳
     * @param type          类型字节码，支持 {@link Date}、{@link Instant}、{@link LocalDate}、{@link LocalTime}、{@link LocalDateTime}
     * @param <T>           实际类型
     * @return              指定类型的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T timestampToObj(long timestamp, Class<T> type) {

        ZonedDateTime zonedDateTime = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault());
        if (Date.class == type) {
            return (T) Date.from(zonedDateTime.toInstant());
        }

        if (Instant.class == type) {
            return (T) zonedDateTime.toInstant();
        }

        if (LocalDateTime.class == type) {
            return (T) zonedDateTime.toLocalDateTime();
        }

        if (LocalDate.class == type) {
            return (T) zonedDateTime.toLocalDate();
        }

        if (LocalTime.class == type) {
            return (T) zonedDateTime.toLocalTime();
        }

        throw new IllegalArgumentException("无法将时间戳转为 [" + type.getName() + "] 类型");
    }

    /**
     * 获取指定年份，某个月的天数
     *
     * @param year      年份
     * @param month     月份
     * @return          year年month月的实际天数
     */
    public static int getDaysOfMonthCount(int year, int month) {
        LocalDate localDate = LocalDate.of(year, month, 1);
        return localDate.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
    }

    /**
     * 获取指定年份的总天数
     *
     * @param year      年份
     * @return          year年的实际天数
     */
    public static int getDaysOfYearCount(int year) {
        LocalDate localDate = LocalDate.of(year, 1, 1);
        return localDate.with(TemporalAdjusters.lastDayOfYear()).getDayOfYear();
    }

}
