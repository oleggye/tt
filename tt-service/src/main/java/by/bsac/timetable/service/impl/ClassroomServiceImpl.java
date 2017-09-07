package by.bsac.timetable.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.service.IClassroomService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class ClassroomServiceImpl implements IClassroomService {
  @Autowired
  private IValidationService service;

  @Autowired
  private IClassroomDAO dao;

  @Override
  public void addClassroom(Classroom classroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(classroom, false);
    try {
      dao.add(classroom);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при добавлении аудиторий", e);
    }
  }

  @Override
  public void updateClassroom(Classroom classroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(classroom, true);
    try {
      dao.update(classroom);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при обновлении аудиторий", e);
    }
  }

  @Override
  public Classroom getClassroom(short idClassroom) throws ServiceException {
    try {
      return dao.getById(idClassroom);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении аудитории", e);
    }
  }

  @Override
  public void deleteClassroom(Classroom classroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(classroom, true);
    try {
      dao.delete(classroom);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при удалении аудиторий", e);
    }
  }

  @Override
  public List<Classroom> getClassroomListForGroupByDates(Group group, Date dateFrom, Date dateTo)
      throws ServiceException, ServiceValidationException {
    service.validateGroup(group, true);
    service.validateDates(dateFrom, dateTo);
    try {
      return dao.getClassroomListForGroupByDates(group, dateFrom, dateTo);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении аудиторий", e);
    }
  }

  @Override
  public List<Classroom> getClassroomList() throws ServiceException {
    try {
      return dao.getAll();
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении аудиторий", e);
    }
  }
}
