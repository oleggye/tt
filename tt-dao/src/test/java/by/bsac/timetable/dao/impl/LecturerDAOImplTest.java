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

import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.builder.ChairBuilder;
import by.bsac.timetable.entity.builder.LecturerBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class LecturerDAOImplTest {
  @Autowired
  private ILecturerDAO dao;

  @Autowired
  private IChairDAO chairDao;

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetLecturerList() throws DAOException {
    final int expectedSize = 3;

    List<Lecturer> lecturerList = dao.getAll();

    assertThat(lecturerList).isNotNull();
    assertThat(lecturerList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetLecturerListByChar() throws DAOException {
    final int expectedSize = 1;
    final Byte idChair = 2;
    final String nameChair = "Кафедра программного обеспечения";

    final short idLecturer = 3;
    final String nameLecturer = "Петров Петр Петрович";

    Chair chair = new ChairBuilder().buildId(idChair).buildNameChair(nameChair).build();

    Lecturer expectedLecturer = new LecturerBuilder().buildId(idLecturer).buildChair(chair)
        .buildNameLecturer(nameLecturer).build();


    List<Lecturer> lecturerList = dao.getLecturerListByChair(chair);

    assertThat(lecturerList).isNotNull();
    assertThat(lecturerList.size()).isEqualTo(expectedSize);

    Lecturer takenLecturer = lecturerList.get(0);
    assertThat(takenLecturer).isNotNull();
    assertThat(takenLecturer.getIdLecturer()).isEqualTo(expectedLecturer.getIdLecturer());
    assertThat(takenLecturer.getChair()).isEqualTo(expectedLecturer.getChair());
    assertThat(takenLecturer.getNameLecturer()).isEqualTo(expectedLecturer.getNameLecturer());

  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetLecturerListWithSimilarNameByOneLetters() throws DAOException {

    final int expectedSize = 2;

    final String name = "П";
    List<Lecturer> lecturerList = dao.getAllWithSimilarName(name);

    assertThat(lecturerList).isNotNull();
    assertThat(lecturerList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/lecturer/addLecturer.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddLecturerWithIdFour() throws DAOException {
    final short idLecturer = 4;
    final String nameLecturer = "Бухарова Светлана Сергеевна";
    final byte idChair = 2;

    Chair chair = chairDao.getById(idChair);
    Lecturer lecturer = new LecturerBuilder().buildId(idLecturer).buildNameLecturer(nameLecturer)
        .buildChair(chair).build();

    dao.add(lecturer);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/lecturer/updateLecturer.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddUpdateWithIdTwo() throws DAOException {
    final short idLecturer = 2;
    final String nameLecturer = "Сидоренко Евгения Петровна";
    final byte idChair = 2;

    Chair chair = chairDao.getById(idChair);
    Lecturer lecturer = dao.getById(idLecturer);
    lecturer.setChair(chair);
    lecturer.setNameLecturer(nameLecturer);

    dao.update(lecturer);
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/lecturer/deleteLecturer.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteUpdateWithIdOne() throws DAOException {
    final short idLecturer = 1;

    Lecturer lecturer = dao.getById(idLecturer);
    dao.delete(lecturer);
  }
}
