package by.bsac.timetable.service.impl;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.service.impl.configuration.TestEntitiesFactory;
import by.bsac.timetable.util.LessonFor;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecordServiceImplTest {

  private static final TestEntitiesFactory testEntitiesFactory =
      TestEntitiesFactory.getInstance();

  @InjectMocks
  private RecordServiceImpl recordService;

  @Mock
  private IValidationService validationService;

  @Mock
  private IRecordDAO recordDAO;

  @Mock
  private ICancellationDAO cancellationDao;

  @Mock
  private IGroupDAO groupDao;

  private Record record;

  @Before
  public void setUp() {
    //RecordServiceImplTestConfiguration.getInstance().initField(this, Record.class, "record");
    record = testEntitiesFactory.newRecord();
  }

  @Test
  public void addRecordForFirstSubGroup() throws Exception {
    final SubjectFor subjectFor =
        testEntitiesFactory.newSubjectFor(LessonFor.FIRST_SUBGROUP);
    addRecordFor(subjectFor);
  }

  @Test
  public void addRecordForSecondSubGroup() throws Exception {
    final SubjectFor subjectFor =
        testEntitiesFactory.newSubjectFor(LessonFor.SECOND_SUBGROUP);
    addRecordFor(subjectFor);
  }

  @Test
  public void addRecordForFullGroup() throws Exception {
    final SubjectFor subjectFor =
        testEntitiesFactory.newSubjectFor(LessonFor.FULL_GROUP);
    addRecordFor(subjectFor);
  }

  @Test
  public void addRecordForFullFlow() throws Exception {
    final SubjectFor subjectFor =
        testEntitiesFactory.newSubjectFor(LessonFor.FULL_FLOW);
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

    addRecordForVerifications();
  }

  private void addRecordForVerifications() throws ServiceValidationException {
    verify(validationService, times(1))
        .validateRecord(record, false);
    verify(validationService, times(1))
        .validateGroup(any(Group.class), any(Boolean.class));
    verifyNoMoreInteractions(validationService);

    verify(recordDAO)
        .getRecordListByGroupAndDatesWhichNotCancelled(
            any(Group.class), any(Date.class), any(Date.class));
    verify(recordDAO).add(any(Record.class));
    verifyNoMoreInteractions(recordDAO);
  }
}
