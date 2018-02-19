package by.bsac.timetable.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Date;
import org.junit.Test;

@SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
public class CheckerTest {

  @Test
  public void testThatGroupIsInFlow() throws Exception {
    final Group groupMock = mock(Group.class);
    final Flow flowMock = mock(Flow.class);

    when(groupMock.getFlow()).thenReturn(flowMock);

    final boolean groupInFlow = Checker.isGroupInFlow(groupMock);

    assertThat(groupInFlow).isEqualTo(true);

    verify(groupMock).getFlow();
    verifyNoMoreInteractions(groupMock);
    verifyNoMoreInteractions(flowMock);
  }

  @Test
  public void testThatGroupIsNotInFlowWhenGroupIsNull() throws Exception {
    final Group group = null;

    final boolean groupInFlow = Checker.isGroupInFlow(group);

    assertThat(groupInFlow).isEqualTo(false);
  }

  @Test
  public void testThatGroupIsNotInFlowWhenFlowIsNull() throws Exception {
    final Group groupMock = mock(Group.class);
    final Flow flow = null;

    when(groupMock.getFlow()).thenReturn(flow);

    final boolean groupInFlow = Checker.isGroupInFlow(groupMock);

    assertThat(groupInFlow).isEqualTo(false);

    verify(groupMock).getFlow();
    verifyNoMoreInteractions(groupMock);
  }

  @Test
  public void isRecordNotForOneDateWhenRecordIsNull() throws Exception {
    final Record recordMock = null;

    final boolean recordForOneDate = Checker.isRecordForOneDate(recordMock);

    assertThat(recordForOneDate).isEqualTo(false);
  }

  @Test
  public void isRecordForOneDateWhenRecordDatesAreEquals() throws Exception {
    final Record recordMock = mock(Record.class);
    final Date dateFrom = new Date(123);
    final Date dateTo = new Date(123);

    when(recordMock.getDateFrom()).thenReturn(dateFrom);
    when(recordMock.getDateTo()).thenReturn(dateTo);

    final boolean recordForOneDate = Checker.isRecordForOneDate(recordMock);

    assertThat(recordForOneDate).isEqualTo(true);

    verify(recordMock).getDateFrom();
    verify(recordMock).getDateTo();
    verifyNoMoreInteractions(recordMock);
  }

  @Test
  public void isRecordNotForOneDateWhenRecordDatesAreNotEqualsAndDateFromLess() throws Exception {
    final Record recordMock = mock(Record.class);
    final Date dateFrom = new Date(123);
    final Date dateTo = new Date(124);

    when(recordMock.getDateFrom()).thenReturn(dateFrom);
    when(recordMock.getDateTo()).thenReturn(dateTo);

    final boolean recordForOneDate = Checker.isRecordForOneDate(recordMock);

    assertThat(recordForOneDate).isEqualTo(false);

    verify(recordMock).getDateFrom();
    verify(recordMock).getDateTo();
    verifyNoMoreInteractions(recordMock);
  }

  @Test
  public void isRecordNotForOneDateWhenRecordDatesAreNotEqualsAndDateFromHigher() throws Exception {
    final Record recordMock = mock(Record.class);
    final Date dateFrom = new Date(124);
    final Date dateTo = new Date(123);

    when(recordMock.getDateFrom()).thenReturn(dateFrom);
    when(recordMock.getDateTo()).thenReturn(dateTo);

    final boolean recordForOneDate = Checker.isRecordForOneDate(recordMock);

    assertThat(recordForOneDate).isEqualTo(false);

    verify(recordMock).getDateFrom();
    verify(recordMock).getDateTo();
    verifyNoMoreInteractions(recordMock);
  }
}
