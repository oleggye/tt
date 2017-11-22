package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.builder.GroupBuilder;
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
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class GroupDAOImplTest {

  @Autowired
  private IGroupDAO dao;

  @Autowired
  private IFacultyDAO facultyDao;

  @Autowired
  private IFlowDAO flowDao;


  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"}, type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupList() throws DAOException {

    final int expectedSize = 3;

    List<Group> groupList = dao.getAll();

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"}, type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListByFacultyIdOne() throws DAOException {

    final int expectedSize = 2;

    final Byte idFaculty = 1;
    Faculty faculty = facultyDao.getById(idFaculty);

    List<Group> groupList = dao.getGroupListByFaculty(faculty);

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"}, type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListByFacultyIdOneAndEduLevelOne() throws DAOException {

    final int expectedSize = 2;

    final byte eduLevel = 1;
    final Byte idFaculty = 1;
    Faculty faculty = facultyDao.getById(idFaculty);

    List<Group> groupList = dao.getGroupListByFacultyAndEduLevel(faculty, eduLevel);

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"}, type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListByFlowOne() throws DAOException {

    final int expectedSize = 2;

    final Short idFlow = 1;
    Flow flow = flowDao.getById(idFlow);

    List<Group> groupList = dao.getGroupListByFlow(flow);

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"}, type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListWithSimilarNameByTwoLetters() throws DAOException {

    final int expectedSize = 2;

    final String groupName = "СП";
    List<Group> groupList = dao.getAllWithSimilarName(groupName);

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"}, type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListWithSimilarNameByOneLetters() throws DAOException {

    final int expectedSize = 3;

    final String groupName = "С";
    List<Group> groupList = dao.getAllWithSimilarName(groupName);

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/group/addGroup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddGroupWithIdFour() throws DAOException {
    final short idGroup = 4;
    final String nameGroup = "СО333";
    final byte idFaculty = 2;
    final byte eduLevel = 2;

    Faculty faculty = facultyDao.getById(idFaculty);
    Group group = new GroupBuilder().buildId(idGroup).buildFaculty(faculty).buildName(nameGroup)
        .buildEduLevel(eduLevel).build();

    dao.add(group);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/group/updateGroup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testUpdateGroupWithIdTwo() throws DAOException {
    final short idGroup = 2;
    final String nameGroup = "СП777";
    final byte idFaculty = 2;
    final short idFlow = 2;
    final byte eduLevel = 2;

    Group group = dao.getById(idGroup);
    Faculty faculty = facultyDao.getById(idFaculty);
    Flow flow = flowDao.getById(idFlow);

    group.setNameGroup(nameGroup);
    group.setFaculty(faculty);
    group.setFlow(flow);
    group.setEduLevel(eduLevel);

    dao.update(group);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml",
      "/data/setup/groupSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/group/deleteGroup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteGroupWithIdOne() throws DAOException {
    final short idGroup = 1;

    Group group = dao.getById(idGroup);
    dao.delete(group);
  }
}