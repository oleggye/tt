package by.bsac.timetable.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import by.bsac.timetable.dao.ISubjectDAO;
import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;
import by.bsac.timetable.service.ISubjectService;
import by.bsac.timetable.service.IValidationService;
import by.bsac.timetable.service.exception.ServiceException;
import by.bsac.timetable.service.exception.ServiceValidationException;

@Service
public class SubjectServiceImpl implements ISubjectService {
  @Autowired
  private IValidationService service;

  @Autowired
  private ISubjectDAO dao;

  @Override
  public void addSubject(Subject subject) throws ServiceException, ServiceValidationException {
    service.validateSubject(subject, false);
    try {
      dao.add(subject);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при вставке", e);
    }
  }

  @Override
  public void updateSubject(Subject subject) throws ServiceException, ServiceValidationException {
    service.validateSubject(subject, true);
    try {
      dao.update(subject);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при обновлении", e);
    }
  }

  @Override
  public Subject getSubjectById(short subject_id) throws ServiceException {
    try {
      return dao.getById(subject_id);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении предмета", e);
    }
  }

  @Override
  public List<Subject> getAllSubjects() throws ServiceException {
    try {
      return dao.getAll();
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении предметов", e);
    }
  }

  @Override
  public void deleteSubject(Subject subject) throws ServiceException, ServiceValidationException {
    service.validateSubject(subject, true);
    try {
      dao.delete(subject);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при удалении", e);
    }
  }

  @Override
  public List<Subject> getSubjectsRecordsByChairId(Chair chair)
      throws ServiceException, ServiceValidationException {
    service.validateChair(chair, true);
    try {
      return dao.getSubjectListByChair(chair);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении предметов кафедры", e);
    }
  }

  @Override
  public List<Subject> getSubjectsRecordsByChairIdAndEduLevel(Chair chair, byte eduLevel)
      throws ServiceException, ServiceValidationException {
    service.validateChair(chair, true);
    try {
      return dao.getSubjectListByChairAndEduLevel(chair, eduLevel);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении предметов кафедры для уровня образования",
          e);
    }
  }

  // FIXME: add check of name param
  @Override
  public List<Subject> getSubjectListByName(String name)
      throws ServiceException, ServiceValidationException {
    try {
      return dao.getAllWithSimilarName(name);
    } catch (DAOException e) {
      throw new ServiceException("Ошибка при получении списка групп", e);
    }
  }
}
