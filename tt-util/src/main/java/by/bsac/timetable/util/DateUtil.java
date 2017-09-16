package by.bsac.timetable.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Date;

public final class DateUtil {

  private static final byte WEEK_NUMBER_LOW_BOUND = 1;
  private static final byte WEEK_NUMBER_TOP_BOUND = 4;

  private static final byte WEEK_DAY_LOW_BOUND = 1;
  private static final byte WEEK_DAY_TOP_BOUND = 7;

  public static int whatWeekNumberIs(LocalDate date) {

    if (date != null) {

      int date_dayOfYear = 0;
      int date_dayOfWeek = date.get(ChronoField.DAY_OF_WEEK);

      /**
       * если полученная дата не является ПН, то нужно сместиться к понедельнику этой недели, т.е.
       * date - будет всегда ПН
       */
      if (date.get(ChronoField.DAY_OF_WEEK) != 1) {
        date = date.minusDays(date_dayOfWeek - 1);
        // System.out.println(date);

        date_dayOfYear = date.get(ChronoField.DAY_OF_YEAR);
      } else {
        date_dayOfYear = date.get(ChronoField.DAY_OF_YEAR);
      }

      /**
       * работаем с первым сентября
       * 
       */

      /**
       * определяем дату для него исходя из полученной т.е. если порядковое значения месяца
       * полученной даты меньше, то значит, что это новый год, и мы задаем для сентября предыдущий
       */
      int yearOfSeptember = 0;

      if (date.get(ChronoField.MONTH_OF_YEAR) < Month.SEPTEMBER.getValue()) {
        yearOfSeptember = date.get(ChronoField.YEAR) - 1;

      } else {
        yearOfSeptember = date.get(ChronoField.YEAR);
      }

      /** задаем дату для 1 сентября */

      LocalDate firstSeptDate = LocalDate.of(yearOfSeptember, Month.SEPTEMBER, 1);
      int firstSeptDate_dayOfYear = firstSeptDate.get(ChronoField.DAY_OF_YEAR);
      int firstSeptDate_dayOfWeek = firstSeptDate.get(ChronoField.DAY_OF_WEEK);

      /**
       * начинаем подсчитывать номер недели
       * 
       */

      int weekNumb = 0; // номер недели;
      boolean needWeekOffset = false; /**
                                       * смещение, если 1-ое сентября не пн, то начинаем с // пн
                                       * 2-ой недели и weekOffset=true;
                                       */
      int daysDiff = 0; // разница в количестве дней между двумя датами;

      /**
       * если 1-ое сентября не понедельник //изменить!!! т.е мы переходим к ПН следующей недели
       * сентября чтобы вести отсчет от него и задаем это в переменной needWeekOffset
       */
      if (firstSeptDate.get(ChronoField.DAY_OF_WEEK) != 1) {
        LocalDate secWeekOfSeptMondayDate = firstSeptDate.plusDays(7 - firstSeptDate_dayOfWeek + 1);
        int secWeekOfSeptMondayDate_dayOfYear =
            secWeekOfSeptMondayDate.get(ChronoField.DAY_OF_YEAR);

        if (yearOfSeptember != date.get(ChronoField.YEAR)) {
          /**
           * т.к. две даты в разных года, то нужно пересчитать количество дней
           */
          LocalDate temp = LocalDate.of(yearOfSeptember, Month.DECEMBER, 31);
          date_dayOfYear = date_dayOfYear + temp.get(ChronoField.DAY_OF_YEAR);
        }

        daysDiff = date_dayOfYear - secWeekOfSeptMondayDate_dayOfYear;
        needWeekOffset = true;
      } else {
        daysDiff = date_dayOfYear - firstSeptDate_dayOfYear;

      }

      int weeksCount = daysDiff / 7;
      weekNumb = weeksCount % 4;
      if (needWeekOffset) {
        if (weekNumb != 3) {
          weekNumb = weekNumb + 2;
        } else {
          weekNumb = 1;
        }
      }

      return weekNumb;
    } else {
      throw new IllegalArgumentException("date is null");
    }
  }

  public static String formatDate(LocalDate date) {
    if (date != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
      return date.format(formatter);
    } else {
      throw new IllegalArgumentException("date is null");
    }
  }

  public static LocalDate[] get4Mondays(LocalDate referenceDate) {

    if (referenceDate != null) {

      LocalDate[] mondays = new LocalDate[4];
      LocalDate bearingMonday;

      int bearingDate_dayOfWeek = referenceDate.get(ChronoField.DAY_OF_WEEK);

      if (referenceDate.get(ChronoField.DAY_OF_WEEK) != 1) {
        bearingMonday = referenceDate.minusDays(bearingDate_dayOfWeek - 1);
      } else {
        bearingMonday = referenceDate;
      }

      int weekNumb = whatWeekNumberIs(bearingMonday);
      mondays[weekNumb - 1] = bearingMonday;

      switch (weekNumb) {
        case 1: // 0-ой индекс в массиве
          mondays[1] = DateUtil.getNextMonday(bearingMonday);
          mondays[2] = DateUtil.getNextMonday(mondays[1]);
          mondays[3] = DateUtil.getNextMonday(mondays[2]);
          break;
        case 2:// 1-ый индекс в массиве
          mondays[0] = DateUtil.getPrivMonday(bearingMonday);
          mondays[2] = DateUtil.getNextMonday(bearingMonday);
          mondays[3] = DateUtil.getNextMonday(mondays[2]);
          break;
        case 3:
          mondays[1] = DateUtil.getPrivMonday(bearingMonday);
          mondays[0] = DateUtil.getPrivMonday(mondays[1]);
          mondays[3] = DateUtil.getNextMonday(bearingMonday);
          break;
        case 4:
          mondays[2] = DateUtil.getPrivMonday(bearingMonday);
          mondays[1] = DateUtil.getPrivMonday(mondays[2]);
          mondays[0] = DateUtil.getPrivMonday(mondays[1]);
          break;
      }
      return mondays;
    } else
      throw new IllegalArgumentException("Wrong referenceDate=" + referenceDate);
  }

  public static LocalDate getNextMonday(LocalDate referenceMonday) {
    if (referenceMonday != null && isMonday(referenceMonday))
      return referenceMonday.plusDays(7);
    else {
      throw new IllegalArgumentException("Wrong referenceMonday=" + referenceMonday);
    }
  }

  public static LocalDate getPrivMonday(LocalDate referenceMonday) {
    if (referenceMonday != null && isMonday(referenceMonday))
      return referenceMonday.minusDays(7);
    else {
      throw new IllegalArgumentException("Wrong referenceMonday=" + referenceMonday);
    }
  }

  /**
   * Method returns instance {@link LocalDate} of a next Sunday by Monday's date param
   *
   * @param mondayDate
   * @return
   * @throws Exception
   */
  public static LocalDate getSundayByMonday(LocalDate mondayDate) {
    // нужно проверять дату на null и этоПонедельник?
    if (mondayDate != null && isMonday(mondayDate))
      return mondayDate.plusDays(6);
    else {
      throw new IllegalArgumentException("Wrong param: mondayDate=" + mondayDate);
    }
  }

  /**
   * Checks if param date is a Monday
   * 
   * @param date
   * @return
   */
  private static boolean isMonday(LocalDate date) {
    if (date == null) {
      return false;
    }

    int dayOfWeek = date.get(ChronoField.DAY_OF_WEEK);
    if (dayOfWeek == 1) {// это ПН
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method returns array of two instances of {@link Date}. The first represents a Monday of first
   * study week and the second a Sunday of fourth study week.
   * 
   * @param referenceDate date, which represents a point of date on four weeks study window
   * @return
   */
  public static Date[] getDateFromAndDateToByReferenceDate(Date referenceDate) {
    LocalDate localDate = referenceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    Date[] result = new Date[2];

    LocalDate[] fourMondays = get4Mondays(localDate);
    LocalDate firstWeekMonday = fourMondays[0];
    LocalDate fourWeekMonday = fourMondays[3];
    LocalDate lastSunday = getSundayByMonday(fourWeekMonday);
    result[0] = Date.from(firstWeekMonday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    result[1] = Date.from(lastSunday.atStartOfDay(ZoneId.systemDefault()).toInstant());
    return result;
  }

  public static Date getDateByRefDateAndWeekNumberAndDay(Date referenceDate, byte weekNumber,
      byte weekDay) {

    if (referenceDate == null || weekNumber < WEEK_NUMBER_LOW_BOUND
        || weekNumber > WEEK_NUMBER_TOP_BOUND || weekDay < WEEK_DAY_LOW_BOUND
        || weekDay > WEEK_DAY_TOP_BOUND) {
      throw new IllegalArgumentException("wrong params: referenceDate=" + referenceDate
          + ",weekNumber=" + weekNumber + ", weekDay=" + weekDay);
    }

    Date result = null;
    LocalDate localDate = referenceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate[] fourMondays = get4Mondays(localDate);
    LocalDate weekMonday = fourMondays[weekNumber - 1];
    LocalDate resultLocalDate = getDateByMondayDate(weekMonday, weekDay);
    result = Date.from(resultLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

    return result;
  }

  private static LocalDate getDateByMondayDate(LocalDate mondayDate, byte weekDay) {
    if (mondayDate == null || !isMonday(mondayDate)) {
      throw new IllegalArgumentException(mondayDate + " isn't Monday");
    }
    if (weekDay == 1) {
      return mondayDate;
    }
    return mondayDate.plusDays(weekDay - 1);
  }

  public static byte getWeekNumber(java.util.Date date) {
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return (byte) whatWeekNumberIs(localDate);
  }

  public static byte getWeekDay(java.util.Date date) {
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return (byte) localDate.getDayOfWeek().getValue();
  }
}
