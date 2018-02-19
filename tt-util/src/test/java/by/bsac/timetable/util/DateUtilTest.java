package by.bsac.timetable.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

  private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");
  private static final String dateString = "2017-09-12";

  private Date date;

  @Before
  public void setUp() throws Exception {
    date = FORMAT.parse(dateString);
  }

  @After
  public void tearDown() throws Exception {
    date = null;
  }

  @Test
  public void testGetWeekNumber() {
    final byte expectedWeekNumber = 3;

    byte takenWeekNumber = DateUtil.getWeekNumber(date);

    assertThat(takenWeekNumber).isEqualTo(expectedWeekNumber);
  }

  @Test
  public void testGetWeekDay() {
    final byte expectedWeekDay = 2;

    byte takenWeekDay = DateUtil.getWeekDay(date);

    assertThat(takenWeekDay).isEqualTo(expectedWeekDay);
  }

  @Test
  public void testGetDateFromAndDateToByReferenceDate() throws ParseException {
    final Date expectedDateFrom = FORMAT.parse("2017-08-28");
    final Date expectedDateTo = FORMAT.parse("2017-09-24");
    final int expectedArraySize = 2;

    Date[] dates = DateUtil.getDateFromAndDateToByReferenceDate(date);

    assertThat(dates).isNotNull();
    assertThat(dates).isNotEmpty();
    assertThat(dates).isNotEmpty();
    assertThat(dates.length).isEqualTo(expectedArraySize);
    assertThat(expectedDateFrom).isEqualTo(dates[0]);
    assertThat(expectedDateTo).isEqualTo(dates[1]);
  }
}
