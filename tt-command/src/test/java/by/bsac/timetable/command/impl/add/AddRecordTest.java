package by.bsac.timetable.command.impl.add;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import by.bsac.timetable.command.exception.CommandException;
import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.IRecordService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.WeekNumber;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AddRecordTest {

  private static final String RECORD_VALUE_KEY = "addRecord";
  private static final String WEEK_SET_VALUE_KEY = "weekSet";

  @InjectMocks
  private AddRecord addRecord;

  @Mock
  private IRecordService recordService;

  @Mock
  private Request request;

  @Mock
  private Record record;

  @Mock
  private Set<WeekNumber> weekSet;

  @Test
  public void shouldExecuteAddRecordByAtWeekSetFromService() throws Exception {
    basicMockInitialization(2);

    addRecord.execute(request);

    basicMockVerification();
    verify(recordService).addRecordByAtWeekSet(record, weekSet);
  }

  @Test(expected = CommandException.class)
  public void shouldThrowCommandExceptionWhenAddRecordByAtWeekSetFromServiceThrowServiceException()
      throws Exception {
    basicMockInitialization(2);
    doThrow(ServiceException.class).when(recordService).addRecordByAtWeekSet(record, weekSet);

    addRecord.execute(request);
  }

  @Test(expected = CommandException.class)
  public void shouldThrowCommandExceptionWhenAddRecordByAtWeekSetFromServiceThrowServiceValidationException()
      throws Exception {
    basicMockInitialization(2);
    doThrow(ServiceValidationException.class).when(recordService)
        .addRecordByAtWeekSet(record, weekSet);

    addRecord.execute(request);
  }

  @Test
  public void shouldExecuteAddRecordFromService() throws Exception {
    basicMockInitialization(1);

    addRecord.execute(request);

    basicMockVerification();
    verify(recordService).addRecord(record);
  }

  @Test(expected = CommandException.class)
  public void shouldThrowCommandExceptionWhenAddRecordFromServiceThrowServiceValidationException()
      throws Exception {
    basicMockInitialization(1);
    doThrow(ServiceValidationException.class).when(recordService).addRecord(record);

    addRecord.execute(request);
  }

  @Test(expected = CommandException.class)
  public void shouldThrowCommandExceptionWhenAddRecordFromServiceThrowServiceException()
      throws Exception {
    basicMockInitialization(1);
    doThrow(ServiceValidationException.class).when(recordService).addRecord(record);

    addRecord.execute(request);
  }

  private void basicMockInitialization(int weekNumberSizeReturnValue) {
    when(weekSet.size()).thenReturn(weekNumberSizeReturnValue);
    when(request.getValue(RECORD_VALUE_KEY)).thenReturn(record);
    when(request.getValue(WEEK_SET_VALUE_KEY)).thenReturn(weekSet);
  }

  @SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
  private void basicMockVerification() {
    verify(weekSet).size();
    verify(request).getValue(RECORD_VALUE_KEY);
    verify(request).getValue(WEEK_SET_VALUE_KEY);
  }
}
