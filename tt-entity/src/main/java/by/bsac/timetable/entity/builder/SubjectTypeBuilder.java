package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.SubjectType;

public class SubjectTypeBuilder {

	private byte id;
	private String name;

	public SubjectTypeBuilder id(byte id) {
		this.id = id;
		return this;
	}

	public SubjectTypeBuilder name(String name) {
		this.name = name;
		return this;
	}

	public SubjectType build() {
		SubjectType subjectType = new SubjectType();
		subjectType.setId(id);
		subjectType.setName(name);
		return subjectType;
	}
}