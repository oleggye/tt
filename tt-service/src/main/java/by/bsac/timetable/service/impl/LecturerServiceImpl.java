package by.bsac.timetable.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.ILecturerDAO;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;
import by.bsac.timetable.service.ILecturerService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class LecturerServiceImpl implements ILecturerService {

  @Autowired
  private IValidationService service;

  @Autowired
  private ILecturerDAO dao;

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addLecturer(Lecturer lecturer) throws ServiceException, ServiceValidationException {
    service.validateLecturer(lecturer, false);
    try {
      dao.add(lecturer);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при вставке", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateLecturer(Lecturer lecturer)
      throws ServiceException, ServiceValidationException {
    service.validateLecturer(lecturer, true);
    try {
      dao.update(lecturer);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Lecturer getLecturerById(short lecturer_id) throws ServiceException {
    try {
      return dao.getById(lecturer_id);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении преподавателя", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Lecturer> getAllLecturers() throws ServiceException {
    try {
      return dao.getAll();
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении преподавателей", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteLecturer(Lecturer lecturer)
      throws ServiceException, ServiceValidationException {
    service.validateLecturer(lecturer, true);
    try {
      dao.delete(lecturer);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при удалении", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Lecturer> getLecturersRecordsByChairId(Chair chair)
      throws ServiceException, ServiceValidationException {
    service.validateChair(chair, true);
    try {
      return dao.getLecturerListByChair(chair);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении преподавателей", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  // FIXME: add check of name param
  @Override
  public List<Lecturer> getLecturerListByName(String nameLecturer)
      throws ServiceException, ServiceValidationException {
    if (nameLecturer == null || nameLecturer.isEmpty()) {
      throw new ServiceValidationException("wrong nameLecturer param");
    }

    try {
      return dao.getAllWithSimilarName(nameLecturer);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении преподавателей", e);
    }
  }
}
