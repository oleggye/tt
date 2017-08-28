package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Classroom;

public class ClassroomBuilder {

	private short idClassroom;
	private short number;
	private byte building;

	public ClassroomBuilder buildId(short idClassroom) {
		this.idClassroom = idClassroom;
		return this;
	}

	public ClassroomBuilder buildNumber(short number) {
		this.number = number;
		return this;
	}

	public ClassroomBuilder buildBuilding(byte building) {
		this.building = building;
		return this;
	}

	public Classroom build() {
		Classroom classroom = new Classroom();
		classroom.setIdClassroom(idClassroom);
		classroom.setNumber(number);
		classroom.setBuilding(building);
		return classroom;
	}
}