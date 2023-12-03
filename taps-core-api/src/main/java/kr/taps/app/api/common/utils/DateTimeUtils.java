package kr.taps.app.api.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

  /**
   * localDateTime to YYYY.MM.DD 변환
   * 2023-05-18 13:39:37.570 -> 2023.05.18
   * @param dateTime
   * @return
   */
  public static String toyyyymmdd(LocalDateTime dateTime){
    if (dateTime == null) {
      return "";
    }
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
  }

  /**
   * TimeStamp to YYYY.MM.DD 변환
   * 2023-05-18 13:39:37.570 -> 2023.05.18
   * @return
   */
  public static String toyyyymmdd(Timestamp timestamp){
    LocalDateTime dateTime = timestamp.toLocalDateTime();
    return DateTimeUtils.toyyyymmdd(dateTime);
  }

  /**
   * LocalDateTime to yyyyMMddHHmmss 변환
   * 2023-05-18 13:39:37 -> 20230518133937
   * @return
   */
  public static String toyyyyMMddHHmmss(LocalDateTime dateTime){
    if (dateTime == null) {
      return "";
    }
    return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  /**
   * Timestamp to yyyyMMddHHmmss 변환
   * 2023-05-18 13:39:37 -> 20230518133937
   * @return
   */
  public static String toyyyyMMddHHmmss(Timestamp timestamp){
    LocalDateTime dateTime = timestamp.toLocalDateTime();
    return DateTimeUtils.toyyyymmdd(dateTime);
  }

  public static long diffDay(String date1, String date2) throws ParseException {
    Date format1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
    Date format2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);

    long diffSec = (format2.getTime() - format1.getTime()) / 1000; //초 차이
    //long diffMin = (format1.getTime() - format2.getTime()) / 60000; //분 차이
    //long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //시 차이
    return diffSec / (24*60*60); //일자수 차이
  }

}
