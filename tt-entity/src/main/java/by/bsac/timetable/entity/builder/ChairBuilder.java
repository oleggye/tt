package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Chair;

public class ChairBuilder {

	private short idChair;
	private String nameChair;

	public ChairBuilder buildId(short idChair) {
		this.idChair = idChair;
		return this;
	}

	public ChairBuilder buildNameChair(String nameChair) {
		this.nameChair = nameChair;
		return this;
	}

	public Chair build() {
		Chair chair = new Chair();
		chair.setIdChair((byte)idChair);
		chair.setNameChair(nameChair);
		return chair;
	}
}