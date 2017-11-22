package by.bsac.timetable.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {
  private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  private Date date;

  @Before
  public void setUp() throws Exception {
    date = FORMAT.parse("2017-09-12");
  }

  @After
  public void tearDown() throws Exception {
    date = null;
  }

  @Test
  public void testGetWeekNumber() {
    final byte expectedWeekNumber = 3;

    byte takenWeekNumber = DateUtil.getWeekNumber(date);

    Assertions.assertThat(takenWeekNumber).isEqualTo(expectedWeekNumber);
  }

  @Test
  public void testGetWeekDay() {
    final byte expectedWeekDay = 2;

    byte takenWeekDay = DateUtil.getWeekDay(date);

    Assertions.assertThat(takenWeekDay).isEqualTo(expectedWeekDay);
  }
}
