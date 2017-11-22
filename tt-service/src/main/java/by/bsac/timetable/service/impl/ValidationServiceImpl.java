package by.bsac.timetable.service.impl;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.entity.SubjectType;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class ValidationServiceImpl implements IValidationService {
  private static final String WRONG_IDENTIFIER_MESSAGE = "Неверный идентификатор: ";

  @Override
  public void validateRecord(Record record, boolean withId) throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(record)) {
      throw new ServiceValidationException("Record is null");
    }

    if (withId && Checker.isIdInvalid(record.getIdRecord())) {
      builder.append("Неверный идентификатор:");
      builder.append(record.getIdRecord());
      throw new ServiceValidationException(builder.toString());
    }
    Date dateFrom = record.getDateFrom();
    Date dateTo = record.getDateTo();
    validateDates(dateFrom,dateTo);
    
    Classroom classroom = record.getClassroom();
    validateClassroom(classroom, true);

    Group group = record.getGroup();
    validateGroup(group, true);

    Lecturer lecturer = record.getLecturer();
    validateLecturer(lecturer, true);

    Subject subject = record.getSubject();
    validateSubject(subject, true);

    SubjectFor subjectFor = record.getSubjectFor();
    if (Checker.isNull(subjectFor) || Checker.isIdInvalid(subjectFor.getId())) {
      throw new ServiceValidationException("Не задано, для кого назначается запись!");
    }
    SubjectType subjectType = record.getSubjectType();
    if (Checker.isNull(subjectType) || Checker.isIdInvalid(subjectType.getId())) {
      throw new ServiceValidationException("Не задан тип записи!");
    }
    byte weekNumber = record.getWeekNumber();
    if (Checker.isWeekNumberInvalid(weekNumber)) {
      throw new ServiceValidationException("Неверный номер недели: !" + weekNumber);
    }
    byte weekDay = record.getWeekDay();
    if (Checker.isWeekDayInvalid(weekDay)) {
      throw new ServiceValidationException("Неверный номер дня недели: !" + weekDay);
    }
    byte subjectOrdinalNumber = record.getSubjOrdinalNumber();
    if (Checker.isSubjectOrdinalNumberInvalid(subjectOrdinalNumber)) {
      throw new ServiceValidationException(
          "Неверный порядковый номер занятия: !" + subjectOrdinalNumber);
    }

  }

  @Override
  public void validateFaculty(Faculty faculty, boolean withId) throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(faculty)) {
      throw new ServiceValidationException("Faculty is null");
    }

    int idFaculty = faculty.getIdFaculty();
    if (withId && Checker.isIdInvalid(idFaculty)) {
      builder.append(WRONG_IDENTIFIER_MESSAGE);
      builder.append(idFaculty);
      throw new ServiceValidationException(builder.toString());
    }
    String name = faculty.getNameFaculty();
    if (Checker.isNameInvalid(name)) {
      builder.append("Неверно заданно название факультета: ");
      builder.append(name);
      throw new ServiceValidationException(builder.toString());
    }
  }

  @Override
  public void validateChair(Chair chair, boolean withId) throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(chair)) {
      throw new ServiceValidationException("Chair is null");
    }

    int idChair = chair.getIdChair();
    if (withId && Checker.isIdInvalid(idChair)) {
      builder.append(WRONG_IDENTIFIER_MESSAGE);
      builder.append(idChair);
    }
    String name = chair.getNameChair();
    if (Checker.isNameInvalid(name)) {
      builder.append("Неверно заданно название кафедры: ");
      builder.append(name);
      throw new ServiceValidationException(builder.toString());
    }
  }

  @Override
  public void validateClassroom(Classroom classroom, boolean withId)
      throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(classroom)) {
      throw new ServiceValidationException("Classroom is null");
    }

    int idClassroom = classroom.getIdClassroom();
    if (withId && Checker.isIdInvalid(idClassroom)) {
      builder.append(WRONG_IDENTIFIER_MESSAGE);
      builder.append(idClassroom);
      throw new ServiceValidationException(builder.toString());
    }
    String name = classroom.getName();
    if (Checker.isNameInvalid(name)) {
      builder.append("Неверно задано название аудитории: ");
      builder.append(name);
      throw new ServiceValidationException(builder.toString());
    }
    byte building = classroom.getBuilding();
    if (Checker.isBuildingInvalid(building)) {
      builder.append("Неверно задан номер корпуса: ");
      builder.append(building);
      throw new ServiceValidationException(builder.toString());
    }
  }

  @Override
  public void validateSubject(Subject subject, boolean withId) throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(subject)) {
      throw new ServiceValidationException("Subject is null");
    }

    int idSubject = subject.getIdSubject();
    if (withId && Checker.isIdInvalid(idSubject)) {
      builder.append(WRONG_IDENTIFIER_MESSAGE);
      builder.append(idSubject);
      throw new ServiceValidationException(builder.toString());
    }
    String name = subject.getNameSubject();
    if (Checker.isNameInvalid(name)) {
      builder.append("Неверное название предмета: ");
      builder.append(name);
      throw new ServiceValidationException(builder.toString());
    }
    String abreviationName = subject.getAbnameSubject();
    if (Checker.isNameInvalid(abreviationName)) {
      builder.append("Неверная аббревиатура: ");
      builder.append(abreviationName);
      throw new ServiceValidationException(builder.toString());
    }
    byte educationLevel = subject.getEduLevel();
    if (Checker.isEducationLevelInvalid(educationLevel)) {
      builder.append("Задан неверный уровень образования: ");
      builder.append(educationLevel);
      throw new ServiceValidationException(builder.toString());
    }
  }

  @Override
  public void validateLecturer(Lecturer lecturer, boolean withId)
      throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(lecturer)) {
      throw new ServiceValidationException("Lecturer is null");
    }

    int idLecturer = lecturer.getIdLecturer();
    if (withId && Checker.isIdInvalid(idLecturer)) {
      builder.append(WRONG_IDENTIFIER_MESSAGE);
      builder.append(idLecturer);
      throw new ServiceValidationException(builder.toString());
    }
    String name = lecturer.getNameLecturer();
    if (Checker.isNameInvalid(name)) {
      builder.append("Неверное имя преподавателя:");
      builder.append(name);
      throw new ServiceValidationException(builder.toString());
    }
    Chair chair = lecturer.getChair();
    validateChair(chair, true);
  }

  @Override
  public void validateGroup(Group group, boolean withId) throws ServiceValidationException {
    StringBuilder builder = new StringBuilder();

    if (Checker.isNull(group)) {
      throw new ServiceValidationException("Group is null");
    }

    int idGroup = group.getIdGroup();
    if (withId && Checker.isIdInvalid(idGroup)) {
      builder.append(WRONG_IDENTIFIER_MESSAGE);
      builder.append(idGroup);
      throw new ServiceValidationException(builder.toString());
    }
    String name = group.getNameGroup();
    if (Checker.isNameInvalid(name)) {
      builder.append("Неверное название группы: ");
      builder.append(name);
      throw new ServiceValidationException(builder.toString());
    }
    byte educationLevel = group.getEduLevel();
    if (Checker.isEducationLevelInvalid(educationLevel)) {
      builder.append("Задан неверный уровень образования: ");
      builder.append(educationLevel);
      throw new ServiceValidationException(builder.toString());
    }
    Faculty faculty = group.getFaculty();
    validateFaculty(faculty, true);
    Flow flow = group.getFlow();
    validateFlow(flow, true);
  }

  @Override
  public void validateFlow(Flow flow, boolean withId) throws ServiceValidationException {
    if (!Checker.isNull(flow)) {
      StringBuilder builder = new StringBuilder();

      int idFlow = flow.getIdFlow();
      if (withId && Checker.isIdInvalid(idFlow)) {
        builder.append(WRONG_IDENTIFIER_MESSAGE);
        builder.append(idFlow);
        throw new ServiceValidationException(builder.toString());
      }
      String name = flow.getName();
      if (Checker.isNameInvalid(name)) {
        builder.append("Неверное название потока: ");
        builder.append(name);
        throw new ServiceValidationException(builder.toString());
      }
    }
  }

  @Override
  public void validateEducationLevel(byte educationLevel) throws ServiceValidationException {
    if (Checker.isEducationLevelInvalid(educationLevel))
      throw new ServiceValidationException("wrong educationLevel value: " + educationLevel);
  }

  @Override
  public void validateDates(Date dateFrom, Date dateTo) throws ServiceValidationException {
    if (Checker.isDatesInvalid(dateFrom, dateTo)) {
      StringBuilder builder = new StringBuilder();
      builder.append("Параметры даты введены не верно!\n");
      builder.append("начальная дата:");
      builder.append(dateFrom);
      builder.append("конечная дата:");
      builder.append(dateTo);
      throw new ServiceValidationException(builder.toString());
    }
  }

  private static class Checker {
    private Checker() {}

    private static final short LOW_ID_BOUND = 1;

    private static final short LOW_BUILDIN_NUMBER_BOUND = 1;
    private static final short TOP_BUILDIN_NUMBER_BOUND = 2;

    private static final short LOW_WEEK_NUMBER_BOUD = 1;
    private static final short TOP_WEEK_NUMBER_BOUD = 4;

    private static final short LOW_WEEK_DAY_BOUD = 1;
    private static final short TOP_WEEK_DAY_BOUD = 7;

    private static final short LOW_SUBJECT_ORDINAl_NUMBER_BOUD = 1;
    private static final short TOP_SUBJECT_ORDINAl_NUMBER_BOUD = 7;

    private static final short LOW_CLASSROOM_NUMBER_BOUND = 1;
    private static final short TOP_CLASSROOM_NUMBER_BOUND = 500;

    private static final short LOW_EDUCATION_LEVEL_BOUND = 1;
    private static final short TOP_EDUCATION_LEVEL_BOUND = 4;

    static boolean isIdInvalid(int id) {
      return id < LOW_ID_BOUND ? true : false;
    }

    static boolean isDatesInvalid(Date dateFrom, Date dateTo) {
      return (isNull(dateFrom) || isNull(dateTo) || dateFrom.compareTo(dateTo) > 0);
    }

    static boolean isWeekNumberInvalid(byte weekNumber) {
      return (weekNumber < LOW_WEEK_NUMBER_BOUD || weekNumber > TOP_WEEK_NUMBER_BOUD);
    }

    static boolean isWeekDayInvalid(byte weekDay) {
      return (weekDay < LOW_WEEK_DAY_BOUD || weekDay > TOP_WEEK_DAY_BOUD);
    }

    static boolean isSubjectOrdinalNumberInvalid(byte subjOrdinalNumber) {
      return (subjOrdinalNumber < LOW_SUBJECT_ORDINAl_NUMBER_BOUD
          || subjOrdinalNumber > TOP_SUBJECT_ORDINAl_NUMBER_BOUD);
    }

    static boolean isNull(Object value) {
      return (value == null);
    }

    static boolean isNameInvalid(String name) {
      return (isNull(name) /*|| name.isEmpty()*/);
    }

    static boolean isBuildingInvalid(byte building) {
      return (building < LOW_BUILDIN_NUMBER_BOUND || building > TOP_BUILDIN_NUMBER_BOUND);
    }

    static boolean isNumberInvalid(short number) {
      return (number < LOW_CLASSROOM_NUMBER_BOUND || number > TOP_CLASSROOM_NUMBER_BOUND);
    }

    static boolean isEducationLevelInvalid(byte educationLevel) {
      return (educationLevel < LOW_EDUCATION_LEVEL_BOUND
          || educationLevel > TOP_EDUCATION_LEVEL_BOUND);
    }
  }
}
