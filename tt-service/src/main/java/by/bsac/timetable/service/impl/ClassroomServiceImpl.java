package by.bsac.timetable.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.IClassroomDAO;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Record;
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

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addClassroom(Classroom classroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(classroom, false);
    try {
      dao.add(classroom);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при добавлении аудиторий", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateClassroom(Classroom classroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(classroom, true);
    try {
      dao.update(classroom);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении аудиторий", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Classroom getClassroom(short idClassroom) throws ServiceException {
    try {
      return dao.getById(idClassroom);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении аудитории", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteClassroom(Classroom classroom)
      throws ServiceException, ServiceValidationException {
    service.validateClassroom(classroom, true);
    try {
      dao.delete(classroom);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при удалении аудиторий", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Classroom> getClassroomListByDatesAndRecordParams(Date dateFrom, Date dateTo, Record record)
      throws ServiceException, ServiceValidationException {
    service.validateDates(dateFrom, dateTo);
    try {
      List<Classroom> allClassroomList = dao.getAll();
      List<Classroom> reservedClassroomList = dao.getReservedClassroomListByDatesAndRecord(dateFrom, dateTo, record);
      
      for (Classroom classroom : allClassroomList) {
        for (Classroom reservedClassroom : reservedClassroomList) {
          if (classroom.equals(reservedClassroom)) {
            classroom.setReserved(true);
            break;
          }
        }
      }
      return allClassroomList;
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении аудиторий", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Classroom> getClassroomList() throws ServiceException {
    try {
      return dao.getAll();
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении аудиторий", e);
    }
  }
}
