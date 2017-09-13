package by.bsac.timetable.service;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

public interface IClassroomService {

  public void addClassroom(Classroom classRoom)
      throws ServiceException, ServiceValidationException;;

  public void updateClassroom(Classroom classRoom)
      throws ServiceException, ServiceValidationException;;

  public Classroom getClassroom(short idClassroom) throws ServiceException;

  public void deleteClassroom(Classroom classRoom)
      throws ServiceException, ServiceValidationException;

  public List<Classroom> getClassroomListByDates(Date dateFrom, Date dateTo)
      throws ServiceException, ServiceValidationException;

  public List<Classroom> getClassroomList() throws ServiceException;
}
