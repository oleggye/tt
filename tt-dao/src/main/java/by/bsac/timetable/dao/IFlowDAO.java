package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Flow;

public interface IFlowDAO {

  public void add(Flow flow) throws DAOException;

  public void addAll(List<Flow> listFlow) throws DAOException;

  public void update(Flow flow) throws DAOException;

  public Flow getById(Short idFlow) throws DAOException;

  public List<Flow> getAll() throws DAOException;

  public void delete(Flow flow) throws DAOException;

  public List<Flow> getAllWithSimilarName(String nameFlow) throws DAOException;
}
