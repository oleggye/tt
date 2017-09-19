package by.bsac.timetable.util;

import java.util.List;
import java.util.Vector;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.IName;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Subject;

public class GetNamesClass {

	public final static <E> Vector<E> listToVector(List<E> list) {
		Vector<E> vec = new Vector<>();
		for (E f : list) {
			vec.add(f);
		}
		return vec;
	}

	public final static Vector<Vector<IName>> listToNameVector(List<? extends IName> collection) {

		Vector<Vector<IName>> vec = new Vector<>();

		for (IName e : collection) {
			Vector<IName> element = new Vector<>();
			element.add(e);
			vec.add(element);
		}
		return vec;
	}

	public final static Vector<String> getFacultiesNames(List<Faculty> facultyList) {
		Vector<String> vec = new Vector<>();
		for (Faculty f : facultyList) {
			vec.add(f.getNameFaculty());
		}
		return vec;
	}

	public final static Vector<String> getGroupsNames(List<Group> groupList) {
		Vector<String> vec = new Vector<>();
		for (Group f : groupList) {
			vec.add(f.getNameGroup());
		}
		return vec;
	}

	public final static Vector<Chair> getChairsNames(List<Chair> chairList) {
		Vector<Chair> vec = new Vector<>();
		for (Chair f : chairList) {
			vec.add(f);
		}
		return vec;
	}

	public final static Vector<String> getClassroomsNames(List<Classroom> classroomList) {
		Vector<String> vec = new Vector<>();

		String str = null;
		final String format = "%s (%s)";
		for (Classroom f : classroomList) {
			str = String.format(format, f.getName(), f.getBuilding());
			vec.add(str);
		}
		return vec;
	}

	public final static Vector<String> getSubjectsNames(List<Subject> subjectList) {
		Vector<String> vec = new Vector<>();
		for (Subject f : subjectList) {
			vec.add(f.getNameSubject());
		}
		return vec;
	}

	public final static Vector<String> getLecturersNames(List<Lecturer> lecturerList) {
		Vector<String> vec = new Vector<>();
		for (Lecturer f : lecturerList) {
			vec.add(f.getNameLecturer());
		}
		return vec;
	}
}