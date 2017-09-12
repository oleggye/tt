package by.bsac.timetable.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.IFacultyDAO;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.service.IFacultyService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class FacultyServiceImpl implements IFacultyService {

  @Autowired
  private IValidationService service;

  @Autowired
  private IFacultyDAO dao;

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addFaculties(Faculty faculty) throws ServiceException, ServiceValidationException {
    service.validateFaculty(faculty, false);
    try {
      dao.add(faculty);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при добавлении факультета", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateFaculty(Faculty faculty) throws ServiceException, ServiceValidationException {
    service.validateFaculty(faculty, true);
    try {
      dao.update(faculty);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении факультета", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Faculty getFacultyById(byte idFaculty) throws ServiceException {
    try {
      return dao.getById(idFaculty);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении факультета", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Faculty> getAllFaculties() throws ServiceException {
    try {
      return dao.getAll();
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении факультетов", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteFaculty(Faculty faculty) throws ServiceException, ServiceValidationException {
    service.validateFaculty(faculty, true);
    try {
      dao.delete(faculty);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при удалении группы", e);
    }
  }
}
