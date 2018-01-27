package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Faculty;

public class FacultyBuilder {

	private short idFaculty;
	private String nameFaculty;

	public FacultyBuilder id(short idFaculty) {
		this.idFaculty = idFaculty;
		return this;
	}

	public FacultyBuilder name(String nameFaculty) {
		this.nameFaculty = nameFaculty;
		return this;
	}

	public Faculty build() {
		Faculty faculty = new Faculty();
		faculty.setIdFaculty((byte)idFaculty);
		faculty.setNameFaculty(nameFaculty);
		return faculty;
	}
}