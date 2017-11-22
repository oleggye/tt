package by.bsac.timetable.service;

import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.Date;
import java.util.List;

public interface IClassroomService {

  public void addClassroom(Classroom classRoom)
      throws ServiceException, ServiceValidationException;;

  public void updateClassroom(Classroom classRoom)
      throws ServiceException, ServiceValidationException;;

  public Classroom getClassroom(short idClassroom) throws ServiceException;

  public void deleteClassroom(Classroom classRoom)
      throws ServiceException, ServiceValidationException;

  public List<Classroom> getClassroomListByDatesAndRecordParams(Date dateFrom, Date dateTo, Record record)
      throws ServiceException, ServiceValidationException;

  public List<Classroom> getClassroomList() throws ServiceException;
}
