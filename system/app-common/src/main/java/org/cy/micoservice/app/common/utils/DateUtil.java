package org.cy.micoservice.app.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

  public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
  public static final String YYYY_MM_DD = "yyyy-MM-dd";
  public static final String YYYYMMDD = "YYYYMMDDD";
  public static final String yyyyMMdd_HH = "yyyyMMdd_HH";

  private static DateTimeFormatter formatterForTime = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);

  private static DateTimeFormatter formatterForDate = DateTimeFormatter.ofPattern(YYYY_MM_DD);

  private static DateTimeFormatter formatterForDateYYYYMMDD = DateTimeFormatter.ofPattern(YYYYMMDD);

  private static DateTimeFormatter formatterForDateyyyyMMdd_HH = DateTimeFormatter.ofPattern(yyyyMMdd_HH);

  private DateUtil() {}

  /**
   * 格式化时间转换为时间戳
   * 格式: Long -> yyyy-MM-dd HH:mm:ss
   * @param millis
   * @return 时间戳
   */
  public static String getDateTimeFormat(long millis) {
    return Instant.ofEpochMilli(millis)
      .atZone(ZoneId.of("Asia/Shanghai"))
      .format(formatterForTime);
  }

  /**
   * 获取当前时间戳的毫秒数
   * @return
   */
  public static Long getCurrentDateTimeMilli() {
    return Instant.now().toEpochMilli();
  }

  /**
   * 获得当前时间
   * 格式: yyyy-MM-dd
   * @return 当天时间
   */
  public static String getNowDateTimeForYMD() {
    String format = DateTimeFormatter.ofPattern(YYYY_MM_DD).format(LocalDateTime.now());
    return format.replaceAll("-", "");
  }

  /**
   * str -> LocalDate
   * @param date
   * @return
   */
  public static LocalDate localDateToStr(String date) {
    return LocalDate.parse(date, formatterForDate);
  }

  /**
   * str -> LocalDateTime
   * @param date
   * @return
   */
  public static LocalDateTime localDateTimeToStr(String date){
    return LocalDateTime.parse(date, formatterForTime);
  }

  /**
   * Date -> LocalDateTime
   * @param date
   * @return
   */
  public static LocalDateTime dateToLocalDate(Date date) {
    Instant instant = date.toInstant();
    return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  /**
   * get localDateTime for now
   * @return
   */
  public static LocalDateTime localDateTimeNow() {
    return LocalDateTime.now(ZoneId.systemDefault());
  }

  /**
   * LocalDateTime -> Date
   * @return
   */
  public static Date dateTimeNow() {
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = LocalDateTime.now().atZone(zoneId);
    return Date.from(zdt.toInstant());
  }

  /**
   * LocalDateTime -> Date
   * @param localDateTime
   * @return
   */
  public static Date localDateTimeToDate(LocalDateTime localDateTime) {
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = localDateTime.atZone(zoneId);
    return Date.from(zdt.toInstant());
  }

}
