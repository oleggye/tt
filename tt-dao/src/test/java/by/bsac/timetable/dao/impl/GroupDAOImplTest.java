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

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Group;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class GroupDAOImplTest {

  @Autowired
  private IGroupDAO dao;

  @Test
  public void test() {
    // FIXME: stub!
  }

  @Test
  @DatabaseSetup(value= {"/data/setup/facultySetup.xml",
      "/data/setup/flowSetup.xml","/data/setup/groupSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupList() throws DAOException {
    final int expectedSize = 3;

    List<Group> groupList = dao.getAll();

    assertThat(groupList).isNotNull();
    assertThat(groupList.size()).isEqualTo(expectedSize);
  }
  
  /*
   * @Test
   * 
   * @DatabaseSetup("/data/setup/groupSetup.xml") public void
   * testGetGroupListByFirstFacultyAndOneEduLevel() { Fac }
   */
}
