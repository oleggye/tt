package by.bsac.timetable.service;

import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.LessonPeriod;
import by.bsac.timetable.util.WeekNumber;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IRecordService {

  public void addRecord(Record record) throws ServiceException, ServiceValidationException;

  public void updateRecord(Record initialRecord, Record updateRecord)
      throws ServiceException, ServiceValidationException;

  public Record getRecord(int idRecord) throws ServiceException;

  public void cancelRecord(Record initialRecord, Record cancelRecord,
      LessonPeriod cancelLessonPeriod) throws ServiceException, ServiceValidationException;

  public void deleteRecord(Record record) throws ServiceException, ServiceValidationException;

  public List<Record> getAllRecordListForGroup(Group group, Date dateFrom, Date dateTo)
      throws ServiceException, ServiceValidationException;

  public void changeClassroomForAllRecords(Classroom oldClassroom, Classroom newClassroom)
      throws ServiceException, ServiceValidationException;

  public void changeLecturerForAllRecords(Lecturer oldLecturer, Lecturer newLecturer)
      throws ServiceException, ServiceValidationException;

  void addRecordByAtWeekSet(Record record, Set<WeekNumber> weekSet)
      throws ServiceException, ServiceValidationException;
}
