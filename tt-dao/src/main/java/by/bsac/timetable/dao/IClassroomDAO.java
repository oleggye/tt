package by.bsac.timetable.dao;

import java.util.Date;
import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Classroom;
import by.bsac.timetable.entity.Group;

public interface IClassroomDAO {

  public void add(Classroom chair) throws DAOException;

  public void addAll(List<Classroom> listClassroom) throws DAOException;

  public void update(Classroom chair) throws DAOException;

  public void updateAll(List<Classroom> listClassroom) throws DAOException;

  public Classroom getById(Short idChair) throws DAOException;

  public List<Classroom> getAll() throws DAOException;

  public void delete(Classroom chair) throws DAOException;

  public List<Classroom> getClassroomListForGroupByDates(Group group, Date dateFrom, Date dateTo)
      throws DAOException;
}
