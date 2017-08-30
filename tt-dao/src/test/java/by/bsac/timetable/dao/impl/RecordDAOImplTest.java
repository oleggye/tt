package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class RecordDAOImplTest {

  @Autowired
  private IRecordDAO dao;

  @Autowired
  private IClassroomDAO classroomDao;
  
  @Autowired
  private IGroupDAO groupDao;

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml",})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordList() throws DAOException {
    final int expectedSize = 2;

    List<Record> recordList = dao.getAll();

    assertThat(recordList).isNotNull();
    assertThat(recordList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml",})
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
      "/data/setup/recordSetup.xml",})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordForGroupLikeThisWithRecordIdOne() throws DAOException {
    final Integer usingRecordId = 1;
    final Integer expectedLikeUsingRecordId = 2;

    Record usingRecord = dao.getById(usingRecordId);
    Group usingRecordGroup = groupDao.getById(usingRecord.getGroup().getIdGroup());

    Record expectedLikeUsingRecord = dao.getById(expectedLikeUsingRecordId);

    Record takenRecord = dao.getRecordForGroupLikeThis(usingRecordGroup, usingRecord);

    assertThat(takenRecord).isNotNull();
    assertThat(takenRecord).isEqualTo(expectedLikeUsingRecord);
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml",})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetRecordWithGroupIdOneAndSubjectFor() throws DAOException {
    final Integer usingRecordId = 1;
    final Integer expectedLikeUsingRecordId = 2;

    Record usingRecord = dao.getById(usingRecordId);
    Group usingRecordGroup = groupDao.getById(usingRecord.getGroup().getIdGroup());

    Record expectedLikeUsingRecord = dao.getById(expectedLikeUsingRecordId);

    Record takenRecord = dao.getRecordForGroupLikeThis(usingRecordGroup, usingRecord);

    assertThat(takenRecord).isNotNull();
    assertThat(takenRecord).isEqualTo(expectedLikeUsingRecord);
  }
}
