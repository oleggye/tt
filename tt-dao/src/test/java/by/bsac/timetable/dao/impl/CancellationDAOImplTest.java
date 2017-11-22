package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Cancellation;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.builder.CancellationBuilder;
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
public class CancellationDAOImplTest {
  private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

  @Autowired
  private ICancellationDAO dao;

  @Autowired
  private IRecordDAO recordDao;

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetCancellationList() throws DAOException {
    final int expectedSize = 2;

    List<Cancellation> list = dao.getAll();

    assertThat(list).isNotNull();
    assertThat(list.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/cancellation/addCancellation.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddCancellationWithIdThree() throws DAOException, ParseException {
    final int idRecord = 3;
    final int idCancellation = 3;

    final Date dateFrom = FORMAT.parse("2017-08-28");
    final Date dateTo = FORMAT.parse("2017-08-28");

    Record record = recordDao.getById(idRecord);

    Cancellation cancellation = new CancellationBuilder().buildId(idCancellation)
        .buildRecord(record).buildDateFrom(dateFrom).buildDateTo(dateTo).build();
    dao.add(cancellation);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/cancellation/updateCancellation.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testUpdateCancellationWithIdOne() throws DAOException, ParseException {
    final int idRecord = 4;
    final int idCancellation = 1;

    final Date dateFrom = FORMAT.parse("2017-08-30");
    final Date dateTo = FORMAT.parse("2017-08-30");

    Record record = recordDao.getById(idRecord);

    Cancellation cancellation = dao.getById(idCancellation);
    cancellation.setRecord(record);
    cancellation.setDateFrom(dateFrom);
    cancellation.setDateTo(dateTo);

    dao.update(cancellation);
  }

  @Test
  @DatabaseSetup(value = {"/data/setup/chairSetup.xml", "/data/setup/lecturerSetup.xml",
      "/data/setup/subjectSetup.xml", "/data/setup/classroomSetup.xml",
      "/data/setup/facultySetup.xml", "/data/setup/flowSetup.xml", "/data/setup/groupSetup.xml",
      "/data/setup/subjectForSetup.xml", "/data/setup/subjectTypeSetup.xml",
      "/data/setup/recordSetup.xml", "/data/setup/cancellationSetup.xml"})
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/cancellation/deleteCancellation.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteCancellationWithIdTwo() throws DAOException, ParseException {
    final int idCancellation = 2;

    Cancellation cancellation = dao.getById(idCancellation);
    dao.delete(cancellation);
  }
}
