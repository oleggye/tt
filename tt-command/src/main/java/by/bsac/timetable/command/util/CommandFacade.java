package by.bsac.timetable.command.util;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.command.CommandProvider;
import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.util.ActionMode;

//FIXME: it's not a good practice!!!
@SuppressWarnings("unchecked")
public class CommandFacade {

	private static final CommandProvider PROVIDER = CommandProvider.getInstance();
	
	private CommandFacade() {
	}

	/*
	 * FACADE's methods
	 */
	private static void executeCommand(ActionMode action, Request request) throws CommandException {
		PROVIDER.getCommand(action).execute(request);
	}

	private static <E> void executeVoidCommandWithOneRequestVariable(ActionMode action, String variableName,
			E variableValue) throws CommandException {
		Request request = new Request().putParam(variableName, variableValue);
		executeCommand(action, request);
	}

	private static <E> List<E> getGenericList(ActionMode action, String resultVariableName) throws CommandException {
		Request request = new Request();
		executeCommand(action, request);
		return (List<E>) request.getValue(resultVariableName);
	}

	private static <E> List<E> getGenericList(ActionMode action, Request request, String resultVariableName)
			throws CommandException {
		executeCommand(action, request);
		return (List<E>) request.getValue(resultVariableName);
	}

	/*
	 * Constants for FACADE's interface methods
	 * 
	 */
	private static final String FACULTY_LIST_VARIABLE = "facultyList";
	private static final String CHAIR_LIST_VARIABLE = "chairList";
	private static final String LECTURER_LIST_VARIABLE = "lecturerList";
	private static final String CLASSROOM_LIST_VARIABLE = "classroomList";
	private static final String FLOW_LIST_VARIABLE = "flowList";
	private static final String SUBJECT_LIST_VARIABLE = "subjectList";
	private static final String GROUP_LIST_VARIABLE = "groupList";
	private static final String RECORD_LIST_VARIABLE = "recordList";

	private static final String CHAIR_VARIABLE = "chair";
	private static final String GROUP_VARIABLE = "group";
	private static final String SUBJECT_VARIABLE = "subject";
	private static final String FACULTY_VARIABLE = "faculty";
	private static final String LECTURER_VARIABLE = "lecturer";
	private static final String CLASSROOM_VARIABLE = "classroom";
	private static final String FLOW_VARIABLE = "flow";
	private static final String EDU_LEVEL_VARIABLE = "eduLevel";
	private static final String EDUCATION_LEVEL_VARIABLE = "educationLevel";
	private static final String REFERENCE_DATE_VARIABLE = "referenceDate";
	private static final String NAME_VARIABLE = "name";

	/*
	 * FACADE's interface methods
	 * 
	 */
	public static List<Faculty> getFacultyList() throws CommandException {
		return getGenericList(ActionMode.Get_Faculty_List, FACULTY_LIST_VARIABLE);
	}

	public static List<Chair> getChairList() throws CommandException {
		return getGenericList(ActionMode.Get_Chair_List, CHAIR_LIST_VARIABLE);
	}

	public static List<Lecturer> getLecturerList() throws CommandException {
		return getGenericList(ActionMode.Get_Lecturer_List, LECTURER_LIST_VARIABLE);
	}

	public static List<Classroom> getClassroomList() throws CommandException {
		return getGenericList(ActionMode.Get_Classroom_List, CLASSROOM_LIST_VARIABLE);
	}

	public static List<Flow> getFlowList() throws CommandException {
		return getGenericList(ActionMode.Get_Flow_List, FLOW_LIST_VARIABLE);
	}

	public static List<Flow> getFlowListByName(String name) throws CommandException {
		Request request = new Request().putParam(NAME_VARIABLE, name);
		return getGenericList(ActionMode.Get_Flow_List_By_Name, request, FLOW_LIST_VARIABLE);
	}

	public static List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel) throws CommandException {
		Request request = new Request().putParam(FACULTY_VARIABLE, faculty).putParam(EDUCATION_LEVEL_VARIABLE,
				eduLevel);
		return getGenericList(ActionMode.Get_Group_List_By_Faculty_And_EduLevel, request, GROUP_LIST_VARIABLE);
	}

	public static List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel) throws CommandException {
		Request request = new Request().putParam(CHAIR_VARIABLE, chair).putParam(EDU_LEVEL_VARIABLE, eduLevel);
		return getGenericList(ActionMode.Get_Subject_List_By_Chair_And_EduLevel, request, SUBJECT_LIST_VARIABLE);
	}

	public static List<Subject> getSubjectListByName(String name) throws CommandException {
		Request request = new Request().putParam(NAME_VARIABLE, name);
		return getGenericList(ActionMode.Get_Subject_List_By_Name, request, SUBJECT_LIST_VARIABLE);
	}

	public static List<Lecturer> getLecturerListByChair(Chair chair) throws CommandException {
		Request request = new Request().putParam(CHAIR_VARIABLE, chair);
		return getGenericList(ActionMode.Get_Lecturer_List_By_Chair, request, LECTURER_LIST_VARIABLE);
	}

	public static List<Lecturer> getLecturerListByName(String name) throws CommandException {
		Request request = new Request().putParam(NAME_VARIABLE, name);
		return getGenericList(ActionMode.Get_Lecturer_List_By_Name, request, LECTURER_LIST_VARIABLE);
	}

	public static List<Record> getTimetableForGroup(Group group, Date referenceDate) throws CommandException {
		Request request = new Request().putParam(GROUP_VARIABLE, group).putParam(REFERENCE_DATE_VARIABLE,
				referenceDate);
		return getGenericList(ActionMode.Get_Timetable_For_Group, request, RECORD_LIST_VARIABLE);
	}

	public static List<Group> getGroupListByFlow(Flow flow) throws CommandException {
		Request request = new Request().putParam(FLOW_VARIABLE, flow);
		return getGenericList(ActionMode.Get_Group_List_By_Flow, request, GROUP_LIST_VARIABLE);
	}

	public static List<Group> getGroupListByName(String name) throws CommandException {
		Request request = new Request().putParam(NAME_VARIABLE, name);
		return getGenericList(ActionMode.Get_Group_List_By_Name, request, GROUP_LIST_VARIABLE);
	}

	/*
	 * ADD METHODS
	 */

	public static void addFaculty(Faculty faculty) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Faculty, FACULTY_VARIABLE, faculty);
	}

	public static void addSubject(Subject subject) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Subject, SUBJECT_VARIABLE, subject);
	}

	public static void addGroup(Group group) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Group, GROUP_VARIABLE, group);
	}

	public static void addLecturer(Lecturer lecturer) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Lecturer, LECTURER_VARIABLE, lecturer);
	}

	public static void addClassroom(Classroom classroom) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Classroom, CLASSROOM_VARIABLE, classroom);
	}

	public static void addChair(Chair chair) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Chair, CHAIR_VARIABLE, chair);
	}

	public static void addFlow(Flow flow) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Add_Flow, FLOW_VARIABLE, flow);
	}

	/*
	 * UPDATE METHODS
	 */

	public static void updateFaculty(Faculty faculty) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Faculty, FACULTY_VARIABLE, faculty);
	}

	public static void updateSubject(Subject subject) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Subject, SUBJECT_VARIABLE, subject);
	}

	public static void updateGroup(Group group) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Group, GROUP_VARIABLE, group);
	}

	public static void updateLecturer(Lecturer lecturer) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Lecturer, LECTURER_VARIABLE, lecturer);
	}

	public static void updateClassroom(Classroom classroom) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Classroom, CLASSROOM_VARIABLE, classroom);
	}

	public static void updateChair(Chair chair) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Chair, CHAIR_VARIABLE, chair);
	}

	public static void updateFlow(Flow flow) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Update_Flow, FLOW_VARIABLE, flow);
	}

	/*
	 * DELETE METHODS
	 */

	public static void deleteFaculty(Faculty faculty) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Faculty, FACULTY_VARIABLE, faculty);
	}

	public static void deleteSubject(Subject subject) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Subject, SUBJECT_VARIABLE, subject);
	}

	public static void deleteGroup(Group group) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Group, GROUP_VARIABLE, group);
	}

	public static void deleteLecturer(Lecturer lecturer) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Lecturer, LECTURER_VARIABLE, lecturer);
	}

	public static void deleteClassroom(Classroom classroom) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Classroom, CLASSROOM_VARIABLE, classroom);
	}

	public static void deleteChair(Chair chair) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Chair, CHAIR_VARIABLE, chair);
	}

	public static void deleteFlow(Flow flow) throws CommandException {
		executeVoidCommandWithOneRequestVariable(ActionMode.Delete_Flow, FLOW_VARIABLE, flow);
	}
}