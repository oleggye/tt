package by.bsac.timetable.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.impl.configuration.TestEntitiesFactory;
import by.bsac.timetable.util.LessonFor;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceImplTest extends GenericServiceImplTest {

  private static final TestEntitiesFactory testEntitiesFactory = TestEntitiesFactory.getInstance();

  @InjectMocks
  private RecordServiceImpl recordService;

  private Record record;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    //RecordServiceImplTestConfiguration.getInstance().initField(this, Record.class, "record");
    record = testEntitiesFactory.newRecord();
  }

  @Test
  public void addRecordForFirstSubGroup() throws Exception {
    final SubjectFor subjectFor = testEntitiesFactory.newSubjectFor(LessonFor.FIRST_SUBGROUP);
    addRecordFor(subjectFor);
  }

  @Test
  public void addRecordForSecondSubGroup() throws Exception {
    final SubjectFor subjectFor = testEntitiesFactory.newSubjectFor(LessonFor.SECOND_SUBGROUP);
    addRecordFor(subjectFor);
  }

  @Test
  public void addRecordForFullGroup() throws Exception {
    final SubjectFor subjectFor = testEntitiesFactory.newSubjectFor(LessonFor.FULL_GROUP);
    addRecordFor(subjectFor);
  }

  @Test
  public void addRecordForFullFlow() throws Exception {
    final SubjectFor subjectFor = testEntitiesFactory.newSubjectFor(LessonFor.FULL_FLOW);
    record.setSubjectFor(subjectFor);

    recordService.addRecord(record);

    verify(validationService, times(1))
        .validateRecord(record, false);
    verifyNoMoreInteractions(validationService);

    verify(recordDAO, times(1))
        .addAll(any(List.class));
    verifyNoMoreInteractions(recordDAO);
  }

  private void addRecordFor(SubjectFor subjectFor)
      throws ServiceException, ServiceValidationException {
    record.setSubjectFor(subjectFor);

    recordService.addRecord(record);

    verify(validationService, times(1))
        .validateRecord(record, false);
    verify(validationService, times(1))
        .validateGroup(any(Group.class), any(Boolean.class));
    verifyNoMoreInteractions(validationService);

    verify(recordDAO).add(record);
  }
}
