package by.bsac.timetable.service.impl;

import by.bsac.timetable.dao.IGroupDAO;
import by.bsac.timetable.dao.IRecordDAO;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;
import by.bsac.timetable.entity.Record;
import by.bsac.timetable.entity.SubjectFor;
import by.bsac.timetable.service.IGroupService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;
import by.bsac.timetable.util.LessonFor;
import java.util.List;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements IGroupService {

  @Autowired
  private IValidationService service;

  @Autowired
  private IGroupDAO dao;

  @Autowired
  private IRecordDAO recordDao;

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void addGroup(Group group) throws ServiceException, ServiceValidationException {
    service.validateGroup(group, false);
    try {
      dao.add(group);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при вставке", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void updateGroup(Group group) throws ServiceException, ServiceValidationException {
    service.validateGroup(group, true);
    try {
      dao.update(group);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении группы", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public Group getGroupById(short idGroup) throws ServiceException {
    try {
      return dao.getById(idGroup);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при поиске группы", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Group> getGroupList() throws ServiceException {
    try {
      return dao.getAll();
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении списка групп", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void deleteGroup(Group group) throws ServiceException, ServiceValidationException {
    service.validateGroup(group, true);
    try {
      dao.delete(group);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при удалении группы", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Group> getGroupListByFaculty(Faculty faculty)
      throws ServiceException, ServiceValidationException {
    service.validateFaculty(faculty, true);
    try {
      return dao.getGroupListByFaculty(faculty);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении списка групп факультета", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel)
      throws ServiceException, ServiceValidationException {
    service.validateFaculty(faculty, true);
    service.validateEducationLevel(eduLevel);
    try {
      return dao.getGroupListByFacultyAndEduLevel(faculty, eduLevel);
    } catch (RuntimeException e) {
      throw new ServiceException(
          "Ошибка при получении списка групп факультета для уровня образования", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public List<Group> getGroupListByFlow(Flow flow)
      throws ServiceException, ServiceValidationException {
    try {
      return dao.getGroupListByFlow(flow);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении списка групп потока", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  @Override
  public void changeGroupFlow(Group group, Flow newFlow)
      throws ServiceException, ServiceValidationException {
    service.validateGroup(group, true);
    Flow flow = group.getFlow();
    service.validateFlow(flow, true);
    try {
      SubjectFor subjectFor = (LessonFor.FULL_FLOW).lessonForToSubjectFor();

      List<Record> recordList = recordDao.getRecordListByGroupAndSubjectFor(group, subjectFor);
      recordDao.deleteAll(recordList);

      group.setFlow(newFlow);
      dao.update(group);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при обновлении потока группы", e);
    }
  }

  @Transactional(value = TxType.REQUIRED, rollbackOn = ServiceException.class,
      dontRollbackOn = ServiceValidationException.class)
  // FIXME: add check of name param
  @Override
  public List<Group> getGroupListByName(String name)
      throws ServiceException, ServiceValidationException {
    try {
      return dao.getAllWithSimilarName(name);
    } catch (RuntimeException e) {
      throw new ServiceException("Ошибка при получении списка групп", e);
    }
  }
}
