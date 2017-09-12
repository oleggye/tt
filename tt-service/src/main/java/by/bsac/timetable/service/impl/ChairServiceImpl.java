package by.bsac.timetable.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.IChairDAO;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.service.IChairService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class ChairServiceImpl implements IChairService {
  @Autowired
  private IValidationService service;

  @Autowired
  private IChairDAO dao;

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addChair(Chair chair) throws ServiceException, ServiceValidationException {
    service.validateChair(chair, false);
    try {
      dao.add(chair);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при добавлении кафедры", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateChair(Chair chair) throws ServiceException, ServiceValidationException {
    service.validateChair(chair, true);
    try {
      dao.update(chair);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении кафедры", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Chair getChairById(byte idChair) throws ServiceException {
    try {
      return dao.getById(idChair);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при поиске кафедры", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Chair> getAllChair() throws ServiceException {
    try {
      return dao.getAll();
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении списка кафедр", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteChair(Chair chair) throws ServiceException, ServiceValidationException {
    service.validateChair(chair, true);
    try {
      dao.delete(chair);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при удалении", e);
    }
  }
}
