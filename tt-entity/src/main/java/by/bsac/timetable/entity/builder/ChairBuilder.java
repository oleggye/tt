package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Chair;

public class ChairBuilder {

	private byte idChair;
	private String nameChair;

	public ChairBuilder id(byte idChair) {
		this.idChair = idChair;
		return this;
	}

	public ChairBuilder name(String nameChair) {
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