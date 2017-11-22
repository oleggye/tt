package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.builder.FacultyBuilder;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
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
/*
 * @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml", type =
 * DatabaseOperation.CLEAN_INSERT)
 */
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class FacultyDAOImplTest {

  @Autowired
  private IFacultyDAO dao;

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFacultyList() throws DAOException {
    final int expectedSize = 2;

    List<Faculty> facultyList = dao.getAll();

    assertThat(facultyList).isNotNull();
    assertThat(facultyList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFacultyByIdZero() throws DAOException {
    final Byte idFaculty = 0;

    Faculty faculty = dao.getById(idFaculty);
    assertThat(faculty).isNull();
  }

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFacultyByIdOne() throws DAOException {
    final Byte idFaculty = 1;

    String nameFaculty = "Факультет электросвязи";
    Faculty expectedFaculty =
        new FacultyBuilder().buildId(idFaculty).buildName(nameFaculty).build();

    Faculty takenFaculty = dao.getById(idFaculty);
    assertThat(takenFaculty).isNotNull();
    assertThat(takenFaculty).isEqualTo(expectedFaculty);
  }

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFacultyByIdTwo() throws DAOException {
    final Byte idFaculty = 2;

    String nameFaculty = "Факультет инжиниринга и технологий связи";
    Faculty expectedFaculty =
        new FacultyBuilder().buildId(idFaculty).buildName(nameFaculty).build();

    Faculty takenFaculty = dao.getById(idFaculty);
    assertThat(takenFaculty).isNotNull();
    assertThat(takenFaculty).isEqualTo(expectedFaculty);
  }

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/faculty/addFaculty.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddFacultyWithIdThree() throws DAOException {
    final Byte idFaculty = 3;

    String nameFaculty = "Факультет довузовской подготовки";
    Faculty faculty = new FacultyBuilder().buildId(idFaculty).buildName(nameFaculty).build();

    dao.add(faculty);
  }

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/faculty/updateFaculty.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testUpdateFacultyWithIdThree() throws DAOException {
    final Byte idFaculty = 3;

    String nameFaculty = "Факультет подготовки";
    Faculty faculty = new FacultyBuilder().buildId(idFaculty).buildName(nameFaculty).build();

    dao.update(faculty);
  }

  @Test
  @DatabaseSetup("/data/setup/facultySetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/faculty/deleteFaculty.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteFacultyWithIdOne() throws DAOException {
    final Byte idFaculty = 1;

    Faculty faculty = dao.getById(idFaculty);
    assertThat(faculty).isNotNull();

    dao.delete(faculty);
  }
}
