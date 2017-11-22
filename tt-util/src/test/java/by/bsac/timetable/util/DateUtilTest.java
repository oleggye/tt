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
  private static final String dateString = "2017-09-12";

  private Date date;

  @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("STCAL_INVOKE_ON_STATIC_DATE_FORMAT_INSTANCE")
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

    Assertions.assertThat(takenWeekNumber).isEqualTo(expectedWeekNumber);
  }

  @Test
  public void testGetWeekDay() {
    final byte expectedWeekDay = 2;

    byte takenWeekDay = DateUtil.getWeekDay(date);

    Assertions.assertThat(takenWeekDay).isEqualTo(expectedWeekDay);
  }
}
