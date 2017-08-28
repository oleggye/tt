package by.bsac.timetable.util;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

	private Date date;

	@Before
	public void setUp() throws Exception {
		date = new Date("2017/01/15");
	}

	@After
	public void tearDown() throws Exception {
		date = null;
	}

	@Test
	public void testGetWeekNumber() {
		byte weekNumber = DateUtil.getWeekNumber(date);
		System.out.println("weekNumber:" + weekNumber);
	}

	@Test
	public void testGetWeekDay() {
		byte weekDay = DateUtil.getWeekDay(date);
		System.out.println("weekDay:" + weekDay);
	}
}