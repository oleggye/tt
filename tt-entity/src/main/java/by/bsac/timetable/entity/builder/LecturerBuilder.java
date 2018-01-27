package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;

public class LecturerBuilder {

	private short idLecturer;
	private Chair chair;
	private String nameLecturer;

	public LecturerBuilder id(short idLecturer) {
		this.idLecturer = idLecturer;
		return this;
	}

	public LecturerBuilder chair(Chair chair) {
		this.chair = chair;
		return this;
	}

	public LecturerBuilder name(String nameLecturer) {
		this.nameLecturer = nameLecturer;
		return this;
	}

	public Lecturer build() {
		Lecturer lecturer = new Lecturer();
		lecturer.setIdLecturer(idLecturer);
		lecturer.setChair(chair);
		lecturer.setNameLecturer(nameLecturer);
		return lecturer;
	}
}