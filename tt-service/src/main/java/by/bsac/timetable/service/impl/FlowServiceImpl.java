package by.bsac.timetable.service.impl;

import by.bsac.timetable.dao.IFlowDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.service.IFlowService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowServiceImpl implements IFlowService {

  @Autowired
  private IValidationService service;

  @Autowired
  private IFlowDAO dao;
  
  @Autowired
  private IRecordDAO recordDao;

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addFlow(Flow flow) throws ServiceException, ServiceValidationException {
    service.validateFlow(flow, false);
    try {
      dao.add(flow);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при добавлении потока", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateFlow(Flow flow) throws ServiceException, ServiceValidationException {
    service.validateFlow(flow, true);
    try {
      dao.update(flow);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении потока", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Flow getFlowById(short idFlow) throws ServiceException, ServiceValidationException {
    try {
      return dao.getById(idFlow);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении потока с id: " + idFlow, e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Flow> getFlowList() throws ServiceException {
    try {
      return dao.getAll();
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении всех потоков", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteFlow(Flow flow) throws ServiceException, ServiceValidationException {
    service.validateFlow(flow, true);
    try {
      recordDao.deleteAllRecordsByFlow(flow);
      dao.delete(flow);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при удалении потока", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  // FIXME: add check of name param
  @Override
  public List<Flow> getFlowListByName(String name)
      throws ServiceException, ServiceValidationException {
    try {
      return dao.getAllWithSimilarName(name);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении потоков", e);
    }
  }
}
