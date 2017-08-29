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

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Lecturer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springDBUnitContext.xml")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
public class LecturerDAOImplTest {

  @Autowired
  private ILecturerDAO dao;

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
}
