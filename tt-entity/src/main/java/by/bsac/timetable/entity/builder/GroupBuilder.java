package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;

public class GroupBuilder {

	private short idGroup;
	private Faculty faculty;
	private Flow flow;
	private String nameGroup;
	private byte eduLevel;

	public GroupBuilder id(short idGroup) {
		this.idGroup = idGroup;
		return this;
	}

	public GroupBuilder faculty(Faculty faculty) {
		this.faculty = faculty;
		return this;
	}

	public GroupBuilder flow(Flow flow) {
		this.flow = flow;
		return this;
	}

	public GroupBuilder name(String nameGroup) {
		this.nameGroup = nameGroup;
		return this;
	}

	public GroupBuilder eduLevel(byte eduLevel) {
		this.eduLevel = eduLevel;
		return this;
	}

	public Group build() {
		Group group = new Group();
		group.setIdGroup(idGroup);
		group.setFaculty(faculty);
		group.setFlow(flow);
		group.setNameGroup(nameGroup);
		group.setEduLevel(eduLevel);
		return group;
	}
}