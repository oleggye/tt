package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Subject;

public interface ISubjectDAO {

  public void add(Subject subject) throws DAOException;

  public void addAll(List<Subject> listSubject) throws DAOException;

  public void update(Subject subject) throws DAOException;

  public void updateAll(List<Subject> listSubject) throws DAOException;

  public Subject getById(Short idSubject) throws DAOException;

  public List<Subject> getAll() throws DAOException;

  public void delete(Subject subject) throws DAOException;

  public List<Subject> getSubjectListByChair(Chair chair) throws DAOException;

  public List<Subject> getSubjectListByChairAndEduLevel(Chair chair, byte eduLevel)
      throws DAOException;

  public List<Subject> getAllWithSimilarName(String nameSubject) throws DAOException;
}
