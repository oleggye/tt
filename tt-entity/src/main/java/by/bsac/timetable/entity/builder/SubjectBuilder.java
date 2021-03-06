package by.bsac.timetable.entity.builder;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;

public class SubjectBuilder {

	private short idSubject;
	private Chair chair;
	private String nameSubject;
	private byte eduLevel;
	private String abnameSubject;

	public SubjectBuilder id(short idSubject) {
		this.idSubject = idSubject;
		return this;
	}

	public SubjectBuilder chair(Chair chair) {
		this.chair = chair;
		return this;
	}

	public SubjectBuilder name(String nameSubject) {
		this.nameSubject = nameSubject;
		return this;
	}

	public SubjectBuilder eduLevel(byte eduLevel) {
		this.eduLevel = eduLevel;
		return this;
	}

	public SubjectBuilder abName(String abnameSubject) {
		this.abnameSubject = abnameSubject;
		return this;
	}

	public Subject build() {
		Subject subject = new Subject();
		subject.setIdSubject(idSubject);
		subject.setChair(chair);
		subject.setNameSubject(nameSubject);
		subject.setEduLevel((byte)eduLevel);
		subject.setAbnameSubject(abnameSubject);
		return subject;
	}
}