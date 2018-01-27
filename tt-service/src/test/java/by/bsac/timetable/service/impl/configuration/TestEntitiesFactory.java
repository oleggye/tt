package by.bsac.timetable.service.impl.configuration;

import static by.bsac.timetable.service.impl.configuration.TestEntitiesFactory.PropertyHelper.getId;
import static by.bsac.timetable.service.impl.configuration.TestEntitiesFactory.PropertyHelper.getName;
import static by.bsac.timetable.service.impl.configuration.TestEntitiesFactory.PropertyHelper.getParam;

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
import by.bsac.timetable.entity.builder.ChairBuilder;
import by.bsac.timetable.entity.builder.ClassroomBuilder;
import by.bsac.timetable.entity.builder.FacultyBuilder;
import by.bsac.timetable.entity.builder.FlowBuilder;
import by.bsac.timetable.entity.builder.GroupBuilder;
import by.bsac.timetable.entity.builder.LecturerBuilder;
import by.bsac.timetable.entity.builder.RecordBuilder;
import by.bsac.timetable.entity.builder.SubjectBuilder;
import by.bsac.timetable.entity.builder.SubjectForBuilder;
import by.bsac.timetable.entity.builder.SubjectTypeBuilder;
import by.bsac.timetable.util.LessonFor;
import java.util.Date;
import java.util.ResourceBundle;

public class TestEntitiesFactory {

  private TestEntitiesFactory() {
  }

  public static TestEntitiesFactory getInstance() {
    return SingletonHolder.instance;
  }

  public Record newRecord() {
    final Class clazz = Record.class;
    final Short id = Short.valueOf(getId(clazz));
    final Date date = new Date();
    final Byte weekNumber = Byte.valueOf(getParam(clazz, "weekNumber"));
    final Byte weekDay = Byte.valueOf(getParam(clazz, "weekDay"));
    final Byte subjOrdinalNumber = Byte.valueOf(getParam(clazz, "subjOrdinalNumber"));

    return new RecordBuilder().id(id)
        .lecturer(newLecturer())
        .subject(newSubject())
        .group(newGroup())
        .dateFrom(date)
        .dateTo(date)
        .weekNumber(weekNumber)
        .weekDay(weekDay)
        .subjOrdinalNumber(subjOrdinalNumber)
        .subjectFor(newSubjectFor(LessonFor.FULL_GROUP))
        .subjectType(newSubjectType())
        .classroom(newClassroom())
        .build();
  }

  public Faculty newFaculty() {
    final Class clazz = Faculty.class;
    final Short id = Short.valueOf(getId(clazz));
    final String name = getName(clazz);

    return new FacultyBuilder().id(id)
        .name(name)
        .build();
  }

  public Group newGroup() {
    final Class clazz = Group.class;
    final Short id = Short.valueOf(getId(clazz));
    final String name = getName(clazz);
    final byte eduLevel = 1;

    return new GroupBuilder().id(id)
        .name(name)
        .flow(newFlow())
        .faculty(newFaculty())
        .eduLevel(eduLevel)
        .build();
  }

  public Flow newFlow() {
    final Class clazz = Flow.class;
    final Short id = Short.valueOf(getId(clazz));
    final String name = getName(clazz);

    return new FlowBuilder().id(id).name(name).build();
  }

  public Chair newChair() {
    final Class clazz = Chair.class;
    final Byte id = Byte.valueOf(getId(clazz));
    final String name = getName(clazz);

    return new ChairBuilder().id(id)
        .name(name)
        .build();
  }

  public Lecturer newLecturer() {
    final Class clazz = Lecturer.class;
    final Byte id = Byte.valueOf(getId(clazz));
    final String name = getName(clazz);

    return new LecturerBuilder().id(id)
        .name(name)
        .chair(newChair())
        .build();
  }

  public Subject newSubject() {
    final Class clazz = Subject.class;
    final Byte id = Byte.valueOf(getId(clazz));
    final String name = getName(clazz);
    final byte eduLevel = Byte.valueOf(getParam(clazz, "eduLevel"));
    final String abName = getParam(clazz, "abName");

    return new SubjectBuilder().id(id)
        .name(name)
        .chair(newChair())
        .abName(abName)
        .eduLevel(eduLevel)
        .build();
  }

  public SubjectType newSubjectType() {
    final Class clazz = SubjectType.class;
    final byte id = Byte.parseByte(getId(clazz));
    final String name = getName(clazz);

    return new SubjectTypeBuilder().id(id)
        .name(name)
        .build();
  }

  public SubjectFor newSubjectFor(LessonFor lessonFor) {
    final byte id = (byte) (lessonFor.ordinal() + 1);
    final String name = lessonFor.name();

    return new SubjectForBuilder().id(id).name(name).build();
  }

  public Classroom newClassroom() {
    final Class clazz = Classroom.class;
    final Short id = Short.valueOf(getId(clazz));
    final String name = getName(clazz);
    final Byte building = Byte.valueOf(getParam(clazz, "building"));

    return new ClassroomBuilder().id(id)
        .name(name).building(building)
        .build();
  }

  static class PropertyHelper {

    private static final String DEFAULT_PROPERTY_FILE_PATH = "test";
    private static final String ID = "id";
    private static final String NAME = "name";

    private static final ResourceBundle bundle = ResourceBundle
        .getBundle(DEFAULT_PROPERTY_FILE_PATH);

    private PropertyHelper() {
    }

    static String getName(Class clazz) {
      return getParam(clazz, NAME);
    }

    static String getId(Class<?> clazz) {
      return getParam(clazz, ID);
    }

    static String getParam(Class clazz, String paramName) {
      final String rootName = getRootNameByClass(clazz);
      return bundle.getString(String.format("%s.%s", rootName, paramName));
    }

    static String getRootNameByClass(Class<?> clazz) {
      return clazz.getSimpleName().toLowerCase();
    }
  }

  private static class SingletonHolder {

    private static final TestEntitiesFactory instance =
        new TestEntitiesFactory();
  }
}
