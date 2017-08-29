package by.bsac.timetable.dao;

import java.util.List;

import by.bsac.timetable.dao.exception.DAOException;
import by.bsac.timetable.entity.Faculty;
import by.bsac.timetable.entity.Flow;
import by.bsac.timetable.entity.Group;

public interface IGroupDAO {

  public void add(Group group) throws DAOException;

  public void addAll(List<Group> listGroup) throws DAOException;

  public void update(Group group) throws DAOException;

  public void updateAll(List<Group> listGroup) throws DAOException;

  public Group getById(Short idGroup) throws DAOException;

  public List<Group> getAll() throws DAOException;

  public void delete(Group group) throws DAOException;

  public List<Group> getGroupListByFaculty(Faculty faculty) throws DAOException;

  public List<Group> getGroupListByFacultyAndEduLevel(Faculty faculty, byte eduLevel)
      throws DAOException;

  public List<Group> getGroupListByFlow(Flow flow) throws DAOException;

  public void changeGroupFlow(Group group, Flow newFlow) throws DAOException;

  public List<Group> getAllWithSimilarName(String nameLecturer) throws DAOException;
}
