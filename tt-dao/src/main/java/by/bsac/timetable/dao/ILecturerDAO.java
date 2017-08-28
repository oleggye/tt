package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;
import by.bsac.timetable.entity.Lecturer;

public interface ILecturerDAO {

  public void add(Lecturer lecturer) throws DAOException;

  public void addAll(List<Lecturer> listLecturer) throws DAOException;

  public void update(Lecturer lecturer) throws DAOException;

  public void updateAll(List<Lecturer> listLecturer) throws DAOException;

  public Lecturer getById(Short lecturer_id) throws DAOException;

  public List<Lecturer> getAll() throws DAOException;

  public void delete(Lecturer lecturer) throws DAOException;

  public List<Lecturer> getLecturerListByChair(Chair chair) throws DAOException;

  public List<Lecturer> getAllWithSimilarName(String nameLecturer) throws DAOException;

  /**
   * Method updates all references of oldLecturer to newLecturer
   */
  public void changeLecturer(Lecturer oldLecturer, Lecturer newLecturer) throws DAOException;
}