package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.entity.builder.SubjectBuilder;
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
public class SubjectDAOImplTest {

  @Autowired
  private ISubjectDAO dao;
  
  @Autowired
  private IChairDAO chairDao;

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetSubjectList() throws DAOException {
    final int expectedSize = 5;

    List<Subject> subjectList = dao.getAll();

    assertThat(subjectList).isNotNull();
    assertThat(subjectList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testSubjectListByCharTwo() throws DAOException {
    final int expectedSize = 1;
    final Byte idChair = 2;

    final short idSubject = 3;
    final String nameSubject = "Основы алгоритмизации и программирования";
    final byte educationLevel = 1;
    final String abnameSubject="ОАиП";

    Chair chair = chairDao.getById(idChair);

    Subject expectedSubject = new SubjectBuilder().buildId(idSubject)
                        .buildChair(chair)
                        .buildNameSubject(nameSubject)
                        .buildEduLevel(educationLevel)
                        .buildAbname(abnameSubject)
                        .build();

    List<Subject> subjectList = dao.getSubjectListByChair(chair);

    assertThat(subjectList).isNotNull();
    assertThat(subjectList.size()).isEqualTo(expectedSize);

    Subject takenSubject = subjectList.get(0);
    assertThat(takenSubject).isNotNull();
    assertThat(takenSubject.getIdSubject()).isEqualTo(expectedSubject.getIdSubject());
    assertThat(takenSubject.getChair()).isEqualTo(expectedSubject.getChair());
    assertThat(takenSubject.getNameSubject()).isEqualTo(expectedSubject.getNameSubject());
    assertThat(takenSubject.getEduLevel()).isEqualTo(expectedSubject.getEduLevel());
    assertThat(takenSubject.getAbnameSubject()).isEqualTo(expectedSubject.getAbnameSubject());
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testSubjectListByCharTwoAndEducationLevelOne() throws DAOException {
    final int expectedSize = 1;
    final Byte idChair = 2;

    final short idSubject = 3;
    final String nameSubject = "Основы алгоритмизации и программирования";
    final byte educationLevel = 1;
    final String abnameSubject="ОАиП";

    Chair chair = chairDao.getById(idChair);

    Subject expectedSubject = new SubjectBuilder()
                        .buildId(idSubject)
                        .buildChair(chair)
                        .buildNameSubject(nameSubject)
                        .buildEduLevel(educationLevel)
                        .buildAbname(abnameSubject)
                        .build();

    List<Subject> subjectList = dao.getSubjectListByChairAndEduLevel(chair, educationLevel);

    assertThat(subjectList).isNotNull();
    assertThat(subjectList.size()).isEqualTo(expectedSize);

    Subject takenSubject = subjectList.get(0);
    assertThat(takenSubject).isNotNull();
    assertThat(takenSubject.getIdSubject()).isEqualTo(expectedSubject.getIdSubject());
    assertThat(takenSubject.getChair()).isEqualTo(expectedSubject.getChair());
    assertThat(takenSubject.getNameSubject()).isEqualTo(expectedSubject.getNameSubject());
    assertThat(takenSubject.getEduLevel()).isEqualTo(expectedSubject.getEduLevel());
    assertThat(takenSubject.getAbnameSubject()).isEqualTo(expectedSubject.getAbnameSubject());
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testSubjectListWithSimilarNameByOneLetters() throws DAOException {

    final int expectedSize = 2;

    final String name = "Ф";
    List<Subject> subjectList = dao.getAllWithSimilarName(name);

    assertThat(subjectList).isNotNull();
    assertThat(subjectList.size()).isEqualTo(expectedSize);
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/subject/addSubject.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddLecturerWithIdSix() throws DAOException {
    
    final Byte idChair = 2;

    final short idSubject = 6;
    final String nameSubject = "Конструирование программ и языки программирования";
    final byte educationLevel = 1;
    final String abnameSubject="КПиЯП";

    Chair chair = chairDao.getById(idChair);

    Subject subject = new SubjectBuilder()
                        .buildId(idSubject)
                        .buildChair(chair)
                        .buildNameSubject(nameSubject)
                        .buildEduLevel(educationLevel)
                        .buildAbname(abnameSubject)
                        .build();

    dao.add(subject);
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/subject/updateSubject.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testUpdateLecturerWithIdFour() throws DAOException {
    
    final Byte idChair = 2;

    final short idSubject = 4;
    final String nameSubject = "Супер Физика";
    final byte educationLevel = 1;
    final String abnameSubject="СФ";

    Chair chair = chairDao.getById(idChair);

    Subject subject = dao.getById(idSubject);
    subject.setNameSubject(nameSubject);
    subject.setAbnameSubject(abnameSubject);
    subject.setChair(chair);
    subject.setEduLevel(educationLevel);
    
    dao.update(subject);
  }
  
  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/subjectSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/subject/deleteSubject.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteLecturerWithIdThree() throws DAOException {
    final short idSubject = 3;

    Subject subject = dao.getById(idSubject);
    dao.delete(subject);
  }  
}