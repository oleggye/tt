package by.bsac.timetable.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.builder.FlowBuilder;
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
public class FlowDAOImplTest {

  @Autowired
  private IFlowDAO dao;


  @Test
  @DatabaseSetup("/data/setup/flowSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFlowList() throws DAOException {
    final int expectedSize = 3;

    List<Flow> flowList = dao.getAll();

    assertThat(flowList).isNotNull();
    assertThat(flowList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup("/data/setup/flowSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFlowByIdOne() throws DAOException {
    final Short idFlow = 1;
    final String name = "СТ_СП_44";

    Flow expectedFlow = new FlowBuilder().id(idFlow).name(name).build();

    Flow takenFlow = dao.getById(idFlow);

    assertThat(takenFlow).isNotNull();
    assertThat(takenFlow).isEqualTo(expectedFlow);
  }

  @Test
  @DatabaseSetup("/data/setup/flowSetup.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetFlowByIdTwo() throws DAOException {
    final Short idFlow = 2;
    final String name = "Сарк_44";

    Flow expectedFlow = new FlowBuilder().id(idFlow).name(name).build();

    Flow takenFlow = dao.getById(idFlow);

    assertThat(takenFlow).isNotNull();
    assertThat(takenFlow).isEqualTo(expectedFlow);
  }

  @Test
  @DatabaseSetup("/data/setup/flowSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/flow/addFlow.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testAddFlowWithIdFour() throws DAOException {
    final Short idFlow = 4;
    final String name = "ТЕСТ";

    Flow flow = new FlowBuilder().id(idFlow).name(name).build();

    dao.add(flow);
  }

  @Test
  @DatabaseSetup("/data/setup/flowSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/flow/updateFlow.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testUpdateFlowWithIdOne() throws DAOException {
    final Short idFlow = 1;
    final String name = "ТЕСТ";

    Flow flow = dao.getById(idFlow);
    flow.setName(name);

    dao.update(flow);
  }

  @DatabaseSetup("/data/setup/flowSetup.xml")
  @ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED,
      value = "/data/expected/flow/deleteFlow.xml")
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testDeleteFlowWithIdOne() throws DAOException {
    final Short idFlow = 1;
    Flow flow = dao.getById(idFlow);
    dao.delete(flow);
  }

  @Test
  @DatabaseSetup(value = "/data/setup/flowSetup.xml", type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListWithSimilarNameByOneLetters() throws DAOException {

    final int expectedSize = 3;

    final String name = "С";
    List<Flow> flowList = dao.getAllWithSimilarName(name);

    assertThat(flowList).isNotNull();
    assertThat(flowList.size()).isEqualTo(expectedSize);
  }

  @Test
  @DatabaseSetup(value = "/data/setup/flowSetup.xml", type = DatabaseOperation.CLEAN_INSERT)
  @DatabaseTearDown(value = "classpath:data/databaseTearDown.xml",
      type = DatabaseOperation.CLEAN_INSERT)
  public void testGetGroupListWithSimilarNameByTwoLetters() throws DAOException {

    final int expectedSize = 2;

    final String name = "СТ";
    List<Flow> flowList = dao.getAllWithSimilarName(name);

    assertThat(flowList).isNotNull();
    assertThat(flowList.size()).isEqualTo(expectedSize);
  }
}
