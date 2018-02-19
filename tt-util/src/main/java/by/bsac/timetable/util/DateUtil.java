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

  private static final byte MONDAY_ORDINAL_NUMBER = 1;

  private DateUtil() {
  }

  public static int whatWeekNumberIs(LocalDate date) {

    if (date != null) {

      int dateDayOfYear;
      int dateDayOfWeek = date.get(ChronoField.DAY_OF_WEEK);

      /**
       * если полученная дата не является ПН, то нужно сместиться к понедельнику этой недели, т.е.
       * date - будет всегда ПН
       */
      if (date.get(ChronoField.DAY_OF_WEEK) != 1) {
        date = date.minusDays((long) (dateDayOfWeek - 1));

        dateDayOfYear = date.get(ChronoField.DAY_OF_YEAR);
      } else {
        dateDayOfYear = date.get(ChronoField.DAY_OF_YEAR);
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
      int firstSeptemberDateDayOfYear = firstSeptDate.get(ChronoField.DAY_OF_YEAR);
      int firstSeptemberDateDayOfWeek = firstSeptDate.get(ChronoField.DAY_OF_WEEK);

      /**
       * начинаем подсчитывать номер недели
       *
       */

      int weekNumber;
      boolean needWeekOffset = false; /**
       * смещение, если 1-ое сентября не пн, то начинаем с // пн
       * 2-ой недели и weekOffset=true;
       */

      int daysDifferenceBetweenTwoDates;

      /**
       * если 1-ое сентября не понедельник //изменить!!! т.е мы переходим к ПН следующей недели
       * сентября чтобы вести отсчет от него и задаем это в переменной needWeekOffset
       */
      if (firstSeptDate.get(ChronoField.DAY_OF_WEEK) != 1) {
        LocalDate secondWeekMondayOfSeptemberDate = firstSeptDate
            .plusDays((long) (7 - firstSeptemberDateDayOfWeek + 1));
        int secondWeekMondayOfSeptemberDateDayOfYear =
            secondWeekMondayOfSeptemberDate.get(ChronoField.DAY_OF_YEAR);

        if (yearOfSeptember != date.get(ChronoField.YEAR)) {
          /**
           * т.к. две даты в разных года, то нужно пересчитать количество дней
           */
          LocalDate temp = LocalDate.of(yearOfSeptember, Month.DECEMBER, 31);
          dateDayOfYear = dateDayOfYear + temp.get(ChronoField.DAY_OF_YEAR);
        }

        daysDifferenceBetweenTwoDates = dateDayOfYear - secondWeekMondayOfSeptemberDateDayOfYear;
        needWeekOffset = true;
      } else {
        daysDifferenceBetweenTwoDates = dateDayOfYear - firstSeptemberDateDayOfYear;

      }

      int weeksCount = daysDifferenceBetweenTwoDates / 7;
      weekNumber = weeksCount % 4;
      if (needWeekOffset) {
        if (weekNumber != 3) {
          weekNumber = weekNumber + 2;
        } else {
          weekNumber = 1;
        }
      }

      return weekNumber;
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

      long bearingDateDayOfWeek = referenceDate.get(ChronoField.DAY_OF_WEEK);

      if (referenceDate.get(ChronoField.DAY_OF_WEEK) != 1) {
        bearingMonday = referenceDate.minusDays(bearingDateDayOfWeek - 1);
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
          mondays[0] = DateUtil.getPreviousMonday(bearingMonday);
          mondays[2] = DateUtil.getNextMonday(bearingMonday);
          mondays[3] = DateUtil.getNextMonday(mondays[2]);
          break;
        case 3:
          mondays[1] = DateUtil.getPreviousMonday(bearingMonday);
          mondays[0] = DateUtil.getPreviousMonday(mondays[1]);
          mondays[3] = DateUtil.getNextMonday(bearingMonday);
          break;
        case 4:
          mondays[2] = DateUtil.getPreviousMonday(bearingMonday);
          mondays[1] = DateUtil.getPreviousMonday(mondays[2]);
          mondays[0] = DateUtil.getPreviousMonday(mondays[1]);
          break;
        default:
          throw new IllegalArgumentException(
              String.format("Wrong weeknumber value = %s", weekNumb));
      }
      return mondays;
    } else {
      throw new IllegalArgumentException("Wrong referenceDate is null");
    }
  }

  public static LocalDate getNextMonday(LocalDate referenceMonday) {
    if (referenceMonday != null && isMonday(referenceMonday)) {
      return referenceMonday.plusDays(7);
    } else {
      throw new IllegalArgumentException("Wrong referenceMonday=" + referenceMonday);
    }
  }

  public static LocalDate getPreviousMonday(LocalDate referenceMonday) {
    if (referenceMonday != null && isMonday(referenceMonday)) {
      return referenceMonday.minusDays(7);
    } else {
      throw new IllegalArgumentException("Wrong referenceMonday=" + referenceMonday);
    }
  }

  /**
   * Method returns instance {@link LocalDate} of a next Sunday by Monday's date param
   */
  public static LocalDate getSundayByMonday(LocalDate mondayDate) {
    // нужно проверять дату на null и этоПонедельник?
    if (mondayDate != null && isMonday(mondayDate)) {
      return mondayDate.plusDays(6);
    } else {
      throw new IllegalArgumentException("Wrong param: mondayDate=" + mondayDate);
    }
  }

  /**
   * Checks if param date is a Monday
   */
  private static boolean isMonday(LocalDate date) {
    if (date == null) {
      return false;
    }

    int dayOfWeek = date.get(ChronoField.DAY_OF_WEEK);
    return dayOfWeek == MONDAY_ORDINAL_NUMBER;
  }

  /**
   * Method returns array of two instances of {@link Date}. The first represents a Monday of first
   * study week and the second a Sunday of fourth study week.
   *
   * @param referenceDate date, which represents a point of date on four weeks study window
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

    LocalDate localDate = referenceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate[] fourMondays = get4Mondays(localDate);
    LocalDate weekMonday = fourMondays[weekNumber - 1];
    LocalDate resultLocalDate = getDateByMondayDate(weekMonday, weekDay);

    return Date.from(resultLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  private static LocalDate getDateByMondayDate(LocalDate mondayDate, byte weekDay) {
    if (mondayDate == null || !isMonday(mondayDate)) {
      throw new IllegalArgumentException(mondayDate + " isn't Monday");
    }
    if (weekDay == 1) {
      return mondayDate;
    }
    return mondayDate.plusDays((long) (weekDay - 1));
  }

  public static byte getWeekNumber(Date date) {
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return (byte) whatWeekNumberIs(localDate);
  }

  public static byte getWeekDay(Date date) {
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    return (byte) localDate.getDayOfWeek().getValue();
  }
}
