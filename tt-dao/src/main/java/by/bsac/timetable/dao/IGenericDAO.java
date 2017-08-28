package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;

public interface IGenericDAO<E, PK> {

  public void add(E object) throws DAOException;

  public void addAll(List<E> listObject) throws DAOException;

  public void update(E object) throws DAOException;

  public void updateAll(List<E> listObject) throws DAOException;

  public void delete(E object) throws DAOException;

  public E getById(PK id) throws DAOException;

  public List<E> getAll() throws DAOException;

  void deleteAll(List<E> listObject) throws DAOException;
}
