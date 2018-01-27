package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.builder.ClassroomBuilder;
import by.bsac.timetable.entity.builder.RecordBuilder;
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
public class ClassroomDAOImplTest {
  private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  private IClassroomDAO dao;

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetClassroomList() throws DAOException {
    final int expectedSize = 5;

    List<Classroom> list = dao.getAll();

    assertThat(list).isNotNull();
    assertThat(list.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetClassroomByIdZero() throws DAOException {
    final Short id = 0;
    Classroom classroom = dao.getById(id);

    assertThat(classroom).isNull();
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetByIdOne() throws DAOException {
    final Short id = 1;
    final String name = "123";
    final byte building = 1;

    Classroom expectedClassroom =
        new ClassroomBuilder().id(id).name(name).building(building).build();

    Classroom takenClassroom = dao.getById(id);

    assertThat(takenClassroom).isNotNull();
    assertThat(takenClassroom).isEqualTo(expectedClassroom);
    assertThat(takenClassroom.getIdClassroom()).isEqualTo(expectedClassroom.getIdClassroom());
    assertThat(takenClassroom.getName()).isEqualTo(expectedClassroom.getName());
    assertThat(takenClassroom.getBuilding()).isEqualTo(expectedClassroom.getBuilding());
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetByIdTwo() throws DAOException {
    final Short id = 2;
    final String name = "124";
    final byte building = 1;

    Classroom expectedClassroom =
        new ClassroomBuilder().id(id).name(name).building(building).build();

    Classroom takenClassroom = dao.getById(id);

    assertThat(takenClassroom).isNotNull();
    assertThat(takenClassroom).isEqualTo(expectedClassroom);
    assertThat(takenClassroom.getIdClassroom()).isEqualTo(expectedClassroom.getIdClassroom());
    assertThat(takenClassroom.getName()).isEqualTo(expectedClassroom.getName());
    assertThat(takenClassroom.getBuilding()).isEqualTo(expectedClassroom.getBuilding());
  }



  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/Classroom/addClassroom.xml")
  public void testAddClassroomWithIdSix() throws DAOException {
    final Short id = 6;
    final String name = "777";
    final byte building = 1;

    Classroom classroom =
        new ClassroomBuilder().id(id).name(name).building(building).build();

    dao.add(classroom);
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/Classroom/updateClassroom.xml")
  public void testUpdateClassroomWithIdOne() throws DAOException {
    final Short id = 1;
    final String name = "777";
    final byte building = 2;

    Classroom classroom =
        new ClassroomBuilder().id(id).name(name).building(building).build();
    dao.update(classroom);
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/Classroom/deleteClassroom.xml")
  public void testDeleteClassroomWithIdOne() throws DAOException {
    final Short id = 1;
    final String name = "123";
    final byte building = 1;

    Classroom classroom =
        new ClassroomBuilder().id(id).name(name).building(building).build();
    dao.delete(classroom);
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetClassroomListByDates() throws DAOException, ParseException {
    final int expectedSize = 1;    
    final Date dateFrom = FORMAT.parse("2017-08-28");
    final Date dateTo = FORMAT.parse("2017-08-28");
    byte weekNumber = 1;
    byte weekDay = 1;
    byte subjectOrdinalNumber = 1;
    Record record = new RecordBuilder()
                      .weekNumber(weekNumber)
                      .weekDay(weekDay)
                      .subjOrdinalNumber(subjectOrdinalNumber)
                      .build();

    List<Classroom> classroomList = dao.getReservedClassroomListByDatesAndRecord(dateFrom, dateTo, record);
    assertThat(classroomList).isNotNull();
    classroomList.stream().forEach(System.out::println);
    assertThat(classroomList.size()).isEqualTo(expectedSize);
  }
}
