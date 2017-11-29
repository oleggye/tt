package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.entity.builder.RecordBuilder;
import by.bsac.timetable.entity.builder.SubjectForBuilder;
import by.bsac.timetable.entity.builder.SubjectTypeBuilder;
import by.bsac.timetable.util.LessonFor;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class RecordDAOImplTest {

  private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  private IRecordDAO dao;

  @Autowired
  private IClassroomDAO classroomDao;

  @Autowired
  private IGroupDAO groupDao;

  @Autowired
  private ILecturerDAO lecturerDao;

  @Autowired
  private ISubjectDAO subjectDao;

  @Autowired
  private IFlowDAO flowDao;

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordList() throws DAOException {
    final int expectedSize = 11;

    List<Record> recordList = dao.getAll();

    assertThat(recordList).isNotNull();
    assertThat(recordList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/record/replaceClassroom.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testReplaceClassroomForAllRecordsWithIdOneToIdTwo() throws DAOException {
    final Short oldClassroomId = 1;
    final Short newClassroomId = 2;

    Classroom oldClassroom = classroomDao.getById(oldClassroomId);
    Classroom newClassroom = classroomDao.getById(newClassroomId);

    dao.replaceClassroomForAllRecords(oldClassroom, newClassroom);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/record/replaceLecturer.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testReplaceLecturerForAllRecordsWithIdOneToIdTwo() throws DAOException {
    final Short oldLecturerId = 1;
    final Short newLecturerId = 3;

    Lecturer oldLecturer = lecturerDao.getById(oldLecturerId);
    Lecturer newLecturer = lecturerDao.getById(newLecturerId);

    dao.replaceLecturerForAllRecords(oldLecturer, newLecturer);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordForGroupLikeThisWithRecordIdOne() throws DAOException {
    final Integer usingRecordId = 1;
    final Integer expectedLikeUsingRecordId = 2;
    final Short idGroup = 2;

    Record usingRecord = dao.getById(usingRecordId);
    Group expectedGroup = groupDao.getById(idGroup);

    Record expectedLikeUsingRecord = dao.getById(expectedLikeUsingRecordId);

    Record takenRecord = dao.getRecordForGroupLikeThis(expectedGroup, usingRecord);

    assertThat(takenRecord).isNotNull();
    assertThat(takenRecord).isEqualTo(expectedLikeUsingRecord);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordWithGroupIdOneAndSubjectForFullFlow() throws DAOException {
    final short idGroup = 1;
    final int expectedSize = 1;
    final int expectedRecordId = 11;

    Record expectedRecord = dao.getById(expectedRecordId);
    Group group = groupDao.getById(idGroup);
    SubjectFor subjectFor = LessonFor.FULL_GROUP.lessonForToSubjectFor();

    List<Record> recordList = dao.getRecordListByGroupAndSubjectFor(group, subjectFor);

    assertThat(recordList).isNotNull();
    assertThat(recordList.size()).isEqualTo(expectedSize);

    Record takenRecord = recordList.get(0);

    assertThat(takenRecord).isNotNull();
    assertThat(takenRecord).isEqualTo(expectedRecord);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/record/addRecord.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddRecordWithIdTwelve() throws DAOException, ParseException {
    final Integer idRecord = 12;
    final byte weekNumber = 1;
    final byte weekDay = 1;
    final byte subjectOrdinalNumber = 1;
    final Short idGroup = 2;
    final Short idSubject = 1;
    final Short idLecturer = 1;
    final Byte idSubjectType = 4;
    final byte idSubjectFor = 3;
    final Date dateFrom = FORMAT.parse("2017-08-28");
    final Date dateTo = FORMAT.parse("2017-09-24");
    final Short idClassroom = 1;

    Record record = new RecordBuilder()
        .buildId(idRecord)
        .buildWeekDay(weekDay)
        .buildWeekNumber(weekNumber)
        .buildSubjOrdinalNumber(subjectOrdinalNumber)
        .buildGroup(groupDao.getById(idGroup))
        .buildSubject(subjectDao.getById(idSubject))
        .buildLecturer(lecturerDao.getById(idLecturer))
        .buildSubjectType(new SubjectTypeBuilder().buildId(idSubjectType).build())
        .buildSubjectFor(new SubjectForBuilder().buildId(idSubjectFor).build())
        .buildDateFrom(dateFrom)
        .buildDateTo(dateTo)
        .buildClassroom(classroomDao.getById(idClassroom))
        .build();

    dao.add(record);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/record/updateRecord.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testUpdateRecordWithIdSix() throws DAOException, ParseException {
    final Integer idRecord = 6;
    final byte weekNumber = 3;
    final byte weekDay = 3;
    final byte subjectOrdinalNumber = 2;
    final Short idGroup = 1;
    final Short idSubject = 3;
    final Short idLecturer = 1;
    final Byte idSubjectType = 2;
    final byte idSubjectFor = 1;
    final Date dateFrom = FORMAT.parse("2018-08-28");
    final Date dateTo = FORMAT.parse("2018-09-24");
    final Short idClassroom = 2;

    Record record = new RecordBuilder()
        .buildId(idRecord)
        .buildWeekDay(weekDay)
        .buildWeekNumber(weekNumber)
        .buildSubjOrdinalNumber(subjectOrdinalNumber)
        .buildGroup(groupDao.getById(idGroup))
        .buildSubject(subjectDao.getById(idSubject))
        .buildLecturer(lecturerDao.getById(idLecturer))
        .buildSubjectType(new SubjectTypeBuilder().buildId(idSubjectType).build())
        .buildSubjectFor(new SubjectForBuilder().buildId(idSubjectFor).build())
        .buildDateFrom(dateFrom)
        .buildDateTo(dateTo)
        .buildClassroom(classroomDao.getById(idClassroom))
        .build();

    dao.update(record);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/record/deleteRecord.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteRecordWithIdOne() throws DAOException, ParseException {
    final Integer idRecord = 1;

    Record record = dao.getById(idRecord);
    dao.delete(record);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordListByGroupWithIdOneAndDates() throws DAOException, ParseException {
    final int expectedSize = 5;
    final Short idGroup = 1;

    Group group = groupDao.getById(idGroup);
    final Date dateFrom = FORMAT.parse("2017-08-28");
    final Date dateTo = FORMAT.parse("2017-08-28");

    List<Record> recordList = dao
        .getRecordListByGroupAndDatesWhichNotCancelled(group, dateFrom, dateTo);

    assertThat(recordList).isNotNull();
    assertThat(recordList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @ExpectedDatabase("/data/record/deleteAllRecordsByFlow.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteAllRecordsByFlowWithIdOne() throws DAOException, ParseException {
    final Short idFlow = 1;
    final Flow flow = flowDao.getById(idFlow);

    dao.deleteAllRecordsByFlow(flow);
  }
}
