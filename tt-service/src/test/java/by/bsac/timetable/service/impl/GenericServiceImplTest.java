package by.bsac.timetable.service.impl;

import by.bsac.timetable.dao.ICancellationDAO;
import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.service.IValidationService;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public abstract class GenericServiceImplTest {

  @Mock
  protected IValidationService validationService;

  @Mock
  protected IRecordDAO recordDAO;

  @Mock
  protected ICancellationDAO cancellationDao;

  @Mock
  protected IGroupDAO groupDao;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }
}
