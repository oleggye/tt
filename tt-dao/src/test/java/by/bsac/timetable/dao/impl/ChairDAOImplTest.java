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
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.dao.impl.ChairDAOImpl;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.builder.ChairBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class ChairDAOImplTest extends ChairDAOImpl {

  @Autowired
  private IChairDAO dao;

  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetChairList() throws DAOException {
    final int expectedSize = 2;

    List<Chair> chairList = dao.getAll();

    assertThat(chairList).isNotNull();
    assertThat(chairList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetByIdZero() throws DAOException {
    final Byte id = 0;
    Chair chair = dao.getById(id);

    assertThat(chair).isNull();
  }

  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetByIdOne() throws DAOException {
    final Byte id = 1;
    Chair expectedChair =
        new ChairBuilder().buildId(id).buildNameChair("Кафедра математики").build();

    Chair takenChair = dao.getById(id);

    assertThat(takenChair).isNotNull();
    assertThat(takenChair).isEqualTo(expectedChair);
    assertThat(takenChair.getIdChair()).isEqualTo(expectedChair.getIdChair());
    assertThat(takenChair.getNameChair()).isEqualTo(expectedChair.getNameChair());
  }

  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetByIdTwo() throws DAOException {
    final Byte id = 2;
    Chair expectedChair =
        new ChairBuilder().buildId(id).buildNameChair("Кафедра программного обеспечения").build();

    Chair takenChair = dao.getById(id);

    assertThat(takenChair).isNotNull();
    assertThat(takenChair).isEqualTo(expectedChair);
    assertThat(takenChair.getIdChair()).isEqualTo(expectedChair.getIdChair());
    assertThat(takenChair.getNameChair()).isEqualTo(expectedChair.getNameChair());
  }



  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/chair/addChair.xml")
  public void testAddChairWithIdThree() throws DAOException {
    byte chairId = 3;
    String chairName = "Кафедра заочного образования";

    Chair expectedChair = new ChairBuilder().buildId(chairId).buildNameChair(chairName).build();

    dao.add(expectedChair);
  }

  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/chair/updateChair.xml")
  public void testUpdateChairWithIdOne() throws DAOException {
    byte chairId = 1;
    String chairName = "Кафедра заочного образования";

    Chair chair = new ChairBuilder().buildId(chairId).buildNameChair(chairName).build();
    dao.update(chair);
  }

  @Test
  @DatabaseSetup("/data/setup/chairSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/chair/deleteChair.xml")
  public void testDeleteChairWithIdOne() throws DAOException {
    byte chairId = 1;
    String chairName = "Кафедра заочного образования";

    Chair chair = new ChairBuilder().buildId(chairId).buildNameChair(chairName).build();
    dao.delete(chair);
  }
}
