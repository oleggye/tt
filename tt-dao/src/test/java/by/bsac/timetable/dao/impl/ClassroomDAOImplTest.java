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
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.builder.ClassroomBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class ClassroomDAOImplTest {

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
    final short number = 123;
    final byte building = 1;

    Classroom expectedClassroom =
        new ClassroomBuilder().buildId(id).buildNumber(number).buildBuilding(building).build();

    Classroom takedClassroom = dao.getById(id);

    assertThat(takedClassroom).isNotNull();
    assertThat(takedClassroom).isEqualTo(expectedClassroom);
    assertThat(takedClassroom.getIdClassroom()).isEqualTo(expectedClassroom.getIdClassroom());
    assertThat(takedClassroom.getName()).isEqualTo(expectedClassroom.getName());
    assertThat(takedClassroom.getBuilding()).isEqualTo(expectedClassroom.getBuilding());
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetByIdTwo() throws DAOException {
    final Short id = 2;
    final short number = 124;
    final byte building = 1;

    Classroom expectedClassroom =
        new ClassroomBuilder().buildId(id).buildNumber(number).buildBuilding(building).build();

    Classroom takedClassroom = dao.getById(id);

    assertThat(takedClassroom).isNotNull();
    assertThat(takedClassroom).isEqualTo(expectedClassroom);
    assertThat(takedClassroom.getIdClassroom()).isEqualTo(expectedClassroom.getIdClassroom());
    assertThat(takedClassroom.getName()).isEqualTo(expectedClassroom.getName());
    assertThat(takedClassroom.getBuilding()).isEqualTo(expectedClassroom.getBuilding());
  }



  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/Classroom/addClassroom.xml")
  public void testAddClassroomWithIdSix() throws DAOException {
    final Short id = 6;
    final short number = 777;
    final byte building = 1;

    Classroom classroom =
        new ClassroomBuilder().buildId(id).buildNumber(number).buildBuilding(building).build();

    dao.add(classroom);
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/Classroom/updateClassroom.xml")
  public void testUpdateClassroomWithIdOne() throws DAOException {
    final Short id = 1;
    final short number = 777;
    final byte building = 2;

    Classroom classroom =
        new ClassroomBuilder().buildId(id).buildNumber(number).buildBuilding(building).build();
    dao.update(classroom);
  }

  @Test
  @DatabaseSetup("/data/setup/classroomSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/Classroom/deleteClassroom.xml")
  public void testDeleteClassroomWithIdOne() throws DAOException {
    final Short id = 1;
    final short number = 123;
    final byte building = 1;

    Classroom classroom =
        new ClassroomBuilder().buildId(id).buildNumber(number).buildBuilding(building).build();
    dao.delete(classroom);
  }
}
