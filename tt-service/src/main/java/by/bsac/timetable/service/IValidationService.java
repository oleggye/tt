package by.bsac.timetable.service;

import java.util.Date;

import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IValidationService {

  public void validateRecord(Record record, boolean withId) throws ServiceValidationException;

  public void validateFaculty(Faculty faculty, boolean withId) throws ServiceValidationException;

  public void validateChair(Chair chair, boolean withId) throws ServiceValidationException;

  public void validateSubject(Subject subject, boolean withId) throws ServiceValidationException;

  public void validateLecturer(Lecturer lecturer, boolean withId) throws ServiceValidationException;

  public void validateClassroom(Classroom classroom, boolean withId)
      throws ServiceValidationException;

  public void validateGroup(Group group, boolean withId) throws ServiceValidationException;

  public void validateFlow(Flow flow, boolean withId) throws ServiceValidationException;

  public void validateEducationLevel(byte educationLevel) throws ServiceValidationException;

  public void validateDates(Date dateFrom, Date dateTo) throws ServiceValidationException;
}
