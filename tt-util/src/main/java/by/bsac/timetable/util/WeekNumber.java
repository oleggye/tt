package by.bsac.timetable.util;

public enum WeekNumber {
	FIRST(1), SECOND(2), THIRD(3), FOURTH(4);

	private byte weekNumber;

	private WeekNumber(int weekNumber) {
		this.weekNumber = (byte) weekNumber;
	}

	public static WeekNumber getWeekNumberInstance(byte weekNumber) {

		switch (weekNumber) {
		case 1:
			return FIRST;
		case 2:
			return SECOND;
		case 3:
			return THIRD;
		case 4:
			return FOURTH;
		default:
			throw new IllegalArgumentException();
		}
	}

	public byte getWeekNumber() {
		return weekNumber;
	}
}
