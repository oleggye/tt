package by.bsac.timetable.command.impl.add;

import by.bsac.timetable.command.util.Request;
import by.bsac.timetable.service.IRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddRecordTest {

  @InjectMocks
  private AddRecord addRecord;

  @Mock
  private IRecordService recordService;

  @Mock
  private Request request;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void execute() throws Exception {

    addRecord.execute(request);
  }

}