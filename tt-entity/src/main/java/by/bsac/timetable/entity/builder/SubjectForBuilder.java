package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.SubjectFor;

public class SubjectForBuilder {

	private byte id;
	private String name;

	public SubjectForBuilder id(byte id) {
		this.id = id;
		return this;
	}

	public SubjectForBuilder name(String name) {
		this.name = name;
		return this;
	}

	public SubjectFor build() {
		SubjectFor subjectFor = new SubjectFor();
		subjectFor.setId(id);
		subjectFor.setName(name);
		return subjectFor;
	}
}