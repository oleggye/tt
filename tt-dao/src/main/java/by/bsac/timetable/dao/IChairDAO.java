package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Chair;

public interface IChairDAO {

  public void add(Chair chair) throws DAOException;

  public void addAll(List<Chair> listChair) throws DAOException;

  public void update(Chair chair) throws DAOException;

  public void updateAll(List<Chair> listChair) throws DAOException;

  public Chair getById(Byte idChair) throws DAOException;

  public List<Chair> getAll() throws DAOException;

  public void delete(Chair chair) throws DAOException;
}